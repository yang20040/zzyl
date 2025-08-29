package com.zzyl.controller.customer;

import com.zzyl.base.ResponseResult;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.service.MemberService;
import com.zzyl.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/customer/user")
public class CustomerUserController {
    @Autowired
    private MemberService memberService;
    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public ResponseResult<LoginVo> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        LoginVo loginVo = memberService.login(userLoginRequestDto);
        return ResponseResult.success(loginVo);
    }
}
