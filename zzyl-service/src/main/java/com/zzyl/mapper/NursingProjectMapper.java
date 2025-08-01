package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingProject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NursingProjectMapper {

    Page<NursingProject> selectByPage(String name, Integer status);

    void insert(NursingProject nursingProject);
}
