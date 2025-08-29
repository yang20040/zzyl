package com.zzyl.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zzyl.service.WechatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {
    // 登录
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    // 获取token
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    // 获取手机号
    private static final String PHONE_REQUEST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

    @Value("${zzyl.wechat.appId}")
    private String appid;
    @Value("${zzyl.wechat.appSecret}")
    private String secret;
    /**
     * 获取微信openid
     * @param code
     * @return
     */
    @Override
    public String getOpenid(String code) {
        Map<String, Object> requsetUrlParam = getAppConfig();
        requsetUrlParam.put("js_code", code);
        String result = HttpUtil.get(REQUEST_URL, requsetUrlParam);
        if (result != null) {
            JSONObject jsonObject = JSONUtil.parseObj(result);
            return jsonObject.getStr("openid");
        }
        throw new RuntimeException("请求的openId为空");
    }


    @Override
    public String getPhone(String code) {
        //获取access_token
        String accessToken = getAccessToken();
        //拼接请求路径
        String requestUrl = PHONE_REQUEST_URL + accessToken;
        Map<String, Object> requestUrlParam = new HashMap<>();
        requestUrlParam.put("code", code);
        String result = HttpUtil.post(requestUrl, JSONUtil.toJsonStr(requestUrlParam));
        if (result != null) {
            JSONObject jsonObject = JSONUtil.parseObj(result);
            return jsonObject.getJSONObject("phone_info").getStr("phoneNumber");
        }
        throw new RuntimeException("请求的手机号为空");
    }

    private String getAccessToken() {
        Map<String, Object> requestUrlParam = getAppConfig();
        String result = HttpUtil.get(TOKEN_URL, requestUrlParam);
        if (result != null) {
            JSONObject jsonObject = JSONUtil.parseObj(result);
            return jsonObject.getStr("access_token");
        }
        throw new RuntimeException("请求的access_token为空");
    }
    /**
     * 封装公共配置
     * @return
     */
    private Map<String, Object> getAppConfig() {
        Map<String, Object> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid", appid);
        requestUrlParam.put("secret", secret);
        return requestUrlParam;
    }
}
