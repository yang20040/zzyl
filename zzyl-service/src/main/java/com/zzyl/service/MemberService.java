package com.zzyl.service;

import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.vo.LoginVo;

public interface MemberService {
    /**
     * 用户登录
     * @param userLoginRequestDto
     * @return
     */
    LoginVo login(UserLoginRequestDto userLoginRequestDto);
}
