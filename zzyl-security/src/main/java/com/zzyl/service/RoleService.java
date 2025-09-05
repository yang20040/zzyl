package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.RoleDto;
import com.zzyl.vo.RoleVo;

import java.util.Set;

/**
 * 角色表服务类
 */
public interface RoleService {
    /**
     * 分页条件查询角色表
     * @param roleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<RoleVo> findRolePage(RoleDto roleDto,int pageNum,int pageSize);

    /**
     * 添加角色
     * @param roleDto
     */
    void createRole(RoleDto roleDto);

    /**
     * 根据角色id查询选中的资源列表
     * @param roleId
     * @return
     */
    Set<String> findCheckedResource(Long roleId);

    /**
     * 角色修改
     * @param roleDto
     * @return
     */
    Boolean updateRole(RoleDto roleDto);

    void deleteRoleById(Long roleId);
}
