package com.zzyl.service;

import com.zzyl.dto.BedDto;
import com.zzyl.entity.Bed;
import com.zzyl.vo.BedVo;

import java.util.List;

/**
 * 服务接口：BedService（床位管理服务）
 */
public interface BedService {


    /**
     * 通过房间ID检索床位
     * @param roomId 房间ID
     * @return 床位视图对象列表
     */
    List<BedVo> getBedsByRoomId(Long roomId);

    /**
     * 创建床位
     * @param bedDto 床位数据传输对象
     * @return 创建结果
     */
    void createBed(BedDto bedDto);

    /**
     * 通过ID检索床位
     * @param id 床位ID
     * @return 床位视图对象
     */
    BedVo getBedById(Long id);

    /**
     * 更新床位
     * @param bed 床位对象
     * @return 更新结果
     */
    void updateBed(Bed bed);

    /**
     * 通过ID删除床位
     * @param id 床位ID
     * @return 删除结果
     */
    void deleteBed(Long id);
}
