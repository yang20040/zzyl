package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.entity.NursingProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NursingProjectMapper {

    Page<NursingProject> selectByPage(String name, Integer status);

    void insert(NursingProject nursingProject);

    @Select("select * from nursing_project where id = #{id}")
    NursingProject selectById(Long id);

    void update(NursingProjectDto nursingProjectDto);

    void updateStatus(Long id, Integer status);
}
