package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.entity.NursingProject;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class NursingProjectServiceImpl implements NursingProjectService {
    @Autowired
    private NursingProjectMapper nursingProjectMapper;
    @Override
    public PageResponse<NursingProjectVO> queryNursingProjectPageInfo(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<NursingProject> nursingProjects = nursingProjectMapper.selectByPage(name, status);
        PageResponse<NursingProjectVO> projectVOPageResponse = PageResponse.of(nursingProjects, NursingProjectVO.class);
        return projectVOPageResponse;
    }

    @Override
    public void add(NursingProjectDto nursingProjectDto) {
        NursingProject nursingProject = new NursingProject();
        BeanUtils.copyProperties(nursingProjectDto, nursingProject);
//        nursingProject.setCreateBy(NursingProjectConstant.creatBy);;
//        nursingProject.setCreateTime(LocalDateTime.now());
        nursingProjectMapper.insert(nursingProject);
    }

    @Override
    public NursingProjectVO getById(Long id) {
        NursingProject nursingProject = nursingProjectMapper.selectById(id);
        NursingProjectVO nursingProjectVO = BeanUtil.copyProperties(nursingProject, NursingProjectVO.class);
        return nursingProjectVO;
    }

    @Override
    public void update(NursingProjectDto nursingProjectDto) {
        nursingProjectMapper.update(nursingProjectDto);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        nursingProjectMapper.updateStatus(id, status);
    }
}
