package com.zzyl.service.impl;


import com.google.common.collect.Lists;
import com.zzyl.constant.Constants;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.entity.Member;
import com.zzyl.mapper.MemberMapper;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.service.MemberService;
import com.zzyl.service.WechatService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.StringUtils;
import com.zzyl.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    WechatService wechatService;

    @Autowired
    JwtTokenManagerProperties jwtTokenManagerProperties;

    @Autowired
    MemberMapper memberMapper;
    static ArrayList DEFAULT_NICKNAME_PREFIX = Lists.newArrayList(
               "生活更美好",
                "大桔大利",
                "日富一日",
                "好柿开花",
                "柿柿如意",
                "一椰暴富",
                "大柚所为",
                "杨梅吐气",
                "天生荔枝"
    );
    /**
     * 登录
     * @param userLoginRequestDto
     * @return
     */
    @Override
    public LoginVo login(UserLoginRequestDto userLoginRequestDto) {
        //1.调用微信官方接口通过Appid+secret+code获取openid
        String openid = wechatService.getOpenid(userLoginRequestDto.getCode());
        //2.调用微信官方接口通过detail.code获取用户手机号
        String phone = wechatService.getPhone(userLoginRequestDto.getPhoneCode());
        //3.根据openid查询用户信息
        Member member = memberMapper.getByOpenId(openid);
        //4.如果用户不存在，则创建用户
        if (ObjectUtil.isEmpty(member)) {
            member = Member.builder()
                    .openId(openid)
                    .build();
        }
        //5.如果用户存在,则更新用户信息
        saveOrUpdate(member, phone);
        //6.生成token返回
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.JWT_USERID, member.getId());
        claims.put(Constants.JWT_USERNAME, member.getName());
        String token = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), claims);
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setNickName(member.getName());
        return loginVo;
    }

    private void saveOrUpdate(Member member, String phone) {
        if(ObjectUtil.notEqual(member.getPhone(), phone)){
            member.setPhone(phone);
        }
        //非新用户更新
        if(ObjectUtil.isNotNull(member.getId())){
            memberMapper.updateById(member);
            return;
        }
        //新用户创建
        String nickName = DEFAULT_NICKNAME_PREFIX.get((int)(Math.random() * DEFAULT_NICKNAME_PREFIX.size()))
                + StringUtils.substring(phone,7);
        member.setName(nickName);
        memberMapper.insert(member);
    }
}
