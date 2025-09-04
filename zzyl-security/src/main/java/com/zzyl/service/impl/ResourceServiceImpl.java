package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.utils.EmptyUtil;
import com.zzyl.utils.NoProcessing;
import com.zzyl.utils.StringUtils;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeItemVo;
import com.zzyl.vo.TreeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private ResourceService resourceService;

    @Override
    public List<ResourceVo> findResourceList(ResourceDto resourceDto) {
        List<Resource> resourceList = resourceMapper.selectList(resourceDto);
        return BeanUtil.copyToList(resourceList, ResourceVo.class);
    }

    @Override
    public TreeVo resourceTreeVo(ResourceDto resourceDto) {
        //构造查询条件
        ResourceDto dto = ResourceDto.builder()
                .dataState(SuperConstant.DATA_STATE_0)
                .parentResourceNo(NoProcessing.processString(SuperConstant.ROOT_PARENT_ID))
                .resourceType(SuperConstant.MENU)
                .build();
        List<Resource> resourceList = resourceMapper.selectList(dto);
        if (EmptyUtil.isNullOrEmpty(resourceList)){
            throw new RuntimeException("资源信息未定义");
        }
        //没有根节点,构建根节点
        Resource rootResource = new Resource();
        rootResource.setResourceNo(SuperConstant.ROOT_PARENT_ID);
        rootResource.setResourceName("智慧养老院");

        //返回的树形集合
        List<TreeItemVo> itemVos = new ArrayList<>();
        //使用递归构建树形结构
        recursionTreeItem(itemVos, rootResource, resourceList);

        return TreeVo.builder().items(itemVos).build();
    }
    /**
     * 递归构建树形结构
     * @param itemVos
     * @param rootResource
     * @param resourceList
     */
    private void recursionTreeItem(List<TreeItemVo> itemVos, Resource rootResource, List<Resource> resourceList) {
        //构建每个资源的属性
        TreeItemVo treeItem = TreeItemVo.builder()
                .id(rootResource.getResourceNo())
                .label(rootResource.getResourceName())
                .build();
        //获取当前资源下的子资源
        List<Resource> childrenResourceList = resourceList.stream()
                .filter(n -> n.getParentResourceNo().equals(rootResource.getResourceNo()))
                .collect(Collectors.toList());
        //判断子资源是否为空
        if (!EmptyUtil.isNullOrEmpty(childrenResourceList)) {
            List<TreeItemVo> listChildren = new ArrayList<>();
            //构建子资源
            childrenResourceList.forEach(
                    resource -> {
                        recursionTreeItem(listChildren,resource, resourceList);
                    }
            );
            treeItem.setChildren(listChildren);
        }
        //添加到集合
        itemVos.add(treeItem);
    }

    /**
     * 添加资源
     * @param resourceDto
     */
    @Override
    public void createResource(ResourceDto resourceDto) {
        //属性拷贝
        Resource resource = BeanUtil.toBean(resourceDto, Resource.class);
        //查询父资源
        Resource parentResource = resourceMapper.selectByResourceNo(resourceDto.getParentResourceNo());
        resource.setDataState(parentResource.getDataState());
        boolean isIgnore = true;
        //判断是否是按钮,是按钮则不限制层级
        if (StringUtils.isNotEmpty(resourceDto.getResourceType())
                && resourceDto.getResourceType().equals(SuperConstant.BUTTON))
        {
            isIgnore = false;
        }
        //创建当前资源的编号
        String resourceNo = createResourceNo(resourceDto.getParentResourceNo(), isIgnore);
        resource.setResourceNo(resourceNo);
        resourceMapper.insert( resource);
    }

    private String createResourceNo(String parentResourceNo, boolean isIgnore) {
        //判断资源编号是否大于三级
        if (isIgnore && NoProcessing.processString(parentResourceNo).length() / 3 >= 5){
            throw new RuntimeException("资源层级不能超过三级");
        }
        //根据父资源查询子资源
        ResourceDto dto = ResourceDto.builder()
                .parentResourceNo(parentResourceNo)
                .build();
        List<Resource> resourceList = resourceMapper.selectList(dto);
        if(EmptyUtil.isNullOrEmpty(resourceList)){
            //无下属节点,创建新的资源编号
            return NoProcessing.createNo(parentResourceNo, true);
        }else {
            //有下属节点,在已有节点上追加
            Long maxNo = resourceList.stream()
                    .map(resource -> {
                        return Long.valueOf(resource.getResourceNo());
                    }).max(Comparator.comparing(i -> i)).get();
            return NoProcessing.createNo(String.valueOf(maxNo), true);
        }
    }
}
