package com.zzyl.service;

public interface WechatService {
    /**
     * 获取微信用户openid
     * @param code
     * @return
     */
    public String getOpenid(String code);

    /**
     * 获取微信用户手机号
     * @param code
     * @return
     */
    public String getPhone(String code);
}
