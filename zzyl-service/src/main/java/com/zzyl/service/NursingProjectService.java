package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.vo.NursingProjectVO;

public interface NursingProjectService {
    /**
     * 查询护理项目列表
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<NursingProjectVO> queryNursingProjectPageInfo(String name, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 新增护理项目
     * @param nursingProjectDto
     */
    void add(NursingProjectDto nursingProjectDto);

    /*
    * 查询护理项目详情
     */
    NursingProjectVO getById(Long id);

    /**
     * 更新护理项目
     * @param nursingProjectDto
     */
    void update(NursingProjectDto nursingProjectDto);

    /**
     * 更新护理项目状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);
}
