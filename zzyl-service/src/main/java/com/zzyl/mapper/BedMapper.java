package com.zzyl.mapper;

import com.zzyl.entity.Bed;
import com.zzyl.vo.BedVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BedMapper {

    List<BedVo> getBedsByRoomId(Long roomId);

    void insertSelective(Bed bed);

    Bed selectById(Long id);

    void updateBed(Bed bed);

    void deleteById(Long id);
}

