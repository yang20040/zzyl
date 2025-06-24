package com.zzyl.controller;

import com.zzyl.base.ResponseResult;
import com.zzyl.dto.BedDto;
import com.zzyl.entity.Bed;
import com.zzyl.service.BedService;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.BedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bed")
@Api(tags = "床位管理相关接口")
public class BedController extends BaseController {

    @Autowired
    private BedService bedService;

    @GetMapping("/read/room/{roomId}")
    @ApiOperation(value = "根据房间id查询床位", notes = "传入房间id")
    public ResponseResult<List<BedVo>> readBedByRoomId(
            @ApiParam(value = "房间ID", required = true) @PathVariable("roomId") Long roomId) {
        List<BedVo> beds = bedService.getBedsByRoomId(roomId);
        return success(beds);
    }

    /**
     * 创建床位
     * @param bedDto
     * @return
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建床位", notes = "传入床位信息")
    public ResponseResult createBed(@RequestBody BedDto bedDto) {
        bedService.createBed(bedDto);
        return success();
    }

    /**
     * 根据id查询床位
     * @param id
     * @return
     */
    @GetMapping("/read/{id}")
    @ApiOperation(value = "根据id查询床位", notes = "传入床位id")
    public ResponseResult getBedById(@ApiParam (value = "床位ID", required = true)@PathVariable Long id) {
        BedVo bedVo = bedService.getBedById(id);
        if (ObjectUtil.isEmpty(bedVo)) {
            return ResponseResult.error("查询失败,床位信息为空");
        }
        return ResponseResult.success("操作成功",bedVo);
    }

    /**
     * 修改床位
     * @param bed
     * @return
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改床位", notes = "传入床位信息")
    public ResponseResult updateBed(@RequestBody Bed bed) {
        bedService.updateBed(bed);
        return success();
    }

    /**
     * 删除床位
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除床位", notes = "传入床位id")
    public ResponseResult deleteBed(@ApiParam (value = "床位ID", required = true) @PathVariable Long id) {
        bedService.deleteBed(id);
        return success();
    }

}
