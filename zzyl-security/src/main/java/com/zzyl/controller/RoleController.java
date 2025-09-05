package com.zzyl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.RoleDto;
import com.zzyl.service.RoleService;
import com.zzyl.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@Api(tags = "角色管理")
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    /**
     * 角色分页查询
     * @param roleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "角色分页",notes = "角色分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto"),
            @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
            @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    @ApiOperationSupport(includeParameters = {"roleDto.roleName"})
    public ResponseResult<PageResponse<RoleVo>> findRoleVoPage(
            @RequestBody RoleDto roleDto,
            @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize) {

        PageResponse<RoleVo> roleVoPage = roleService.findRolePage(roleDto, pageNum, pageSize);
        return ResponseResult.success(roleVoPage);
    }

    /**
     *  保存角色
     * @param roleDto 角色DTO对象
     * @return RoleVo
     */
    @PutMapping
    @ApiOperation(value = "角色添加",notes = "角色添加")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto")
    @ApiOperationSupport(includeParameters = {"roleDto.roleName","roleDto.dataState"})
    public ResponseResult<RoleVo> createRole(@RequestBody RoleDto roleDto) {
        roleService.createRole(roleDto);
        return ResponseResult.success();
    }

    /**
     * 根据角色id查询选中的资源列表
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据角色查询选中的资源数据")
    @GetMapping("/find-checked-resources/{roleId}")
    public ResponseResult<Set<String>> findCheckedResources(@PathVariable("roleId") Long roleId){
        Set<String> resources =  roleService.findCheckedResource(roleId);
        return ResponseResult.success(resources);
    }

    /**
     * 角色修改
     * @param roleDto
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "角色修改",notes = "角色修改")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto")
    @ApiOperationSupport(includeParameters = {"roleDto.roleName","roleDto.dataState","roleDto.dataScope","roleDto.checkedResourceNos","roleDto.checkedDeptNos","roleDto.id"})
    public ResponseResult<Boolean> updateRole(@RequestBody RoleDto roleDto) {
        Boolean flag = roleService.updateRole(roleDto);
        return ResponseResult.success(flag);
    }

    /**
     * 删除角色
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/{roleId}")
    public ResponseResult remove(@PathVariable("roleId") Long roleId) {
        roleService.deleteRoleById(roleId);
        return ResponseResult.success();
    }
}
