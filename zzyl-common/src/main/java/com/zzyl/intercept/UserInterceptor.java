package com.zzyl.intercept;

import cn.hutool.core.map.MapUtil;
import com.zzyl.constant.Constants;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;
    /**
     * 拦截请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //非映射到方法就放行
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //获取header中的token
        String token = request.getHeader(Constants.USER_TOKEN);
        //判断token是否为空
        if(ObjectUtil.isEmpty(token)){
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }
        //解析token,使用创建token时的秘钥
        Map<String, Object> claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(),token);
        if(ObjectUtil.isEmpty( claims)){
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }
        //获取用户id
        Long userId = MapUtil.get(claims, Constants.USER_TOKEN, Long.class);
        UserThreadLocal.set(userId);
        return true;
    }

    /**
     * 拦截响应
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}