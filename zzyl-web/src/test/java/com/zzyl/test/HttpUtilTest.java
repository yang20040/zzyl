package com.zzyl.test;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpUtilTest {
    @Test
    public void test(){
        String result = HttpUtil.get("https://www.baidu.com");
        System.out.println(result);
    }

    //测试本本地接口
    @Test
    public void testProject(){
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 5);
        String result = HttpUtil.get("http://localhost:9995/nursing_project", params);
        System.out.println(result);
    }

    @Test
    public void testProject2(){
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 5);
        HttpResponse response = HttpUtil.createRequest(Method.GET, "http://localhost:9995/nursing_project")
                .form(params)
                .execute();
        if(response.getStatus() == 200){
            System.out.println(response.body());
        }
    }

    @Test
    public void testProject3(){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试1");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);
        String url = "http://localhost:9995/nursing_project";
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(paramMap));
        System.out.println(result);
    }
}
