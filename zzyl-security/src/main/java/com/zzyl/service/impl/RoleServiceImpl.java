package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.RoleDto;
import com.zzyl.entity.Role;
import com.zzyl.entity.RoleResource;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.RoleResourceMapper;
import com.zzyl.service.RoleService;
import com.zzyl.utils.EmptyUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.RoleVo;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Override
    public PageResponse<RoleVo> findRolePage(RoleDto roleDto, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<List<Role>> page = roleMapper.selectPage(roleDto);
        PageResponse<RoleVo> pageResponse = PageResponse.of(page, RoleVo.class);
        return pageResponse;
    }

    @Override
    public void createRole(RoleDto roleDto) {
        //转换RoleDto为Role
        Role role = BeanUtil.toBean(roleDto, Role.class);
        roleMapper.insert(role);
    }

    @Override
    public Set<String> findCheckedResource(Long roleId) {
        return roleResourceMapper.selectResourceNoByRoleId(roleId);
    }

    @Override
    @Transactional
    public Boolean updateRole(RoleDto roleDto) {
        //1.对象拷贝
        Role role = BeanUtil.toBean(roleDto, Role.class);
        //2.修改角色
        roleMapper.updateByPrimaryKeySelective(role);
        //3.判断是否修改角色对应的资源数据
        if(ObjectUtil.isEmpty(roleDto.getCheckedResourceNos())){
            return true;
        }
        //4.删除原有角色资源中间信息
        roleResourceMapper.deleteRoleResourceByRoleId(role.getId());
        //5.保存角色资源中间信息
        List<RoleResource> roleResourcesList = Lists.newArrayList();
        Arrays.asList(roleDto.getCheckedResourceNos()).forEach(n -> {
                    RoleResource roleResource = RoleResource.builder()
                            .roleId(role.getId())
                            .resourceNo(n)
                            .dataState(SuperConstant.DATA_STATE_0)
                            .build();
                    roleResourcesList.add(roleResource);
                });
        //6.如果集合为空，则结束请求
        if(EmptyUtil.isNullOrEmpty(roleResourcesList)){
            return true;
        }
        //7.批量保存角色和资源关系数据
        roleResourceMapper.batchInsert(roleResourcesList);

        return true;
    }

    @Override
    @Transactional
    public void deleteRoleById(Long roleId) {
        //删除角色与菜单关联
        roleResourceMapper.deleteRoleResourceByRoleId(roleId);
        //删除角色基本信息
        roleMapper.deleteByPrimaryKey(roleId);
    }
}
