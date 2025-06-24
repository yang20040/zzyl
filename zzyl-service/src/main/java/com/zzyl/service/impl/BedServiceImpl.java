package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzyl.dto.BedDto;
import com.zzyl.entity.Bed;
import com.zzyl.mapper.BedMapper;
import com.zzyl.service.BedService;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.BedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private BedMapper bedMapper;

    @Override
    public List<BedVo> getBedsByRoomId(Long roomId) {
        return bedMapper.getBedsByRoomId(roomId);
    }

    /**
     * 创建床位
     * @param bedDto
     */
    @Override
    public void createBed(BedDto bedDto) {
        Bed bed = BeanUtil.copyProperties(bedDto, Bed.class);
        bed.setCreateTime(LocalDateTime.now());
        bed.setUpdateTime(LocalDateTime.now());
        bed.setBedStatus(0);
        bed.setCreateBy(123456L);
        bedMapper.insertSelective(bed);
    }

    /**
     * 获取床位信息
     * @param id
     * @return
     */
    @Override
    public BedVo getBedById(Long id) {
        Bed bed = bedMapper.selectById(id);
        if (ObjectUtil.isNotEmpty(bed)){
            return BeanUtil.copyProperties(bed, BedVo.class);
        }
        return null;
    }

    /**
     * 修改床位信息
     * @param bed
     */
    @Override
    public void updateBed(Bed bed) {
        bed.setUpdateTime(LocalDateTime.now());
        // TODO 获取当前登录用户
        bed.setUpdateBy(1234L);
        bedMapper.updateBed(bed);
    }


    /**
     * 删除床位信息
     * @param id
     */
    @Override
    public void deleteBed(Long id) {
        bedMapper.deleteById(id);
    }
}

