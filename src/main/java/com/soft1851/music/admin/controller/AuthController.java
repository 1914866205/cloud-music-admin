package com.soft1851.music.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.soft1851.music.admin.domain.entity.RoleAdmin;
import com.soft1851.music.admin.domain.entity.SysAdmin;
import com.soft1851.music.admin.service.RoleAdminService;
import com.soft1851.music.admin.service.SysAdminService;
import com.soft1851.music.admin.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/5/6 9:16
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/oauth2/code/github")
@Slf4j
public class AuthController {
    @Resource
    private SysAdminService sysAdminService;
    @Resource
    private RoleAdminService roleAdminService;

    /**
     * 前端访问这个接口
     * 该接口访问github，获取个人信息
     *
     * @param code
     */
    @GetMapping()
    public void getUser(@RequestParam("code") String code) {
        log.info(code);
        String token = "";
        String user = "";
        //创建一个httpClient对象
        try {
            HttpClient client = HttpClients.createDefault();
            //创建一个Post对象
            HttpPost post = new HttpPost("https://github.com/login/oauth/access_token");
            //创建一个entity模拟一个表单
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("client_id", "a29c48c1e7c4f774c6c9"));
            list.add(new BasicNameValuePair("client_secret", "f17051b9bbe0b505a48832e3291c69da5fc41fa8"));
            list.add(new BasicNameValuePair("code", code));
            //包装成一个entity对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            //设置请求内容
            post.setEntity(urlEncodedFormEntity);
//            post.addHeader("accept", "applocation/json");
            post.addHeader("accept", "text/html, application/xhtml+xml, */*");
            post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
            //执行请求内容
            HttpResponse httpResponse = client.execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info(String.valueOf(statusCode));
            HttpEntity contentEntity = httpResponse.getEntity();
            String content = EntityUtils.toString(contentEntity);
            log.info("content>>>>>>>>>>>>" + content);
//            token = JSONObject.parseObject(content).getString("access_token");
//            content>>>>>>>>>>>>access_token=bb00118ab323307450a3f42a42b7d6ee51fed3d4&scope=read%3Auser&token_type=bearer
            token = content.split("&")[0].split("=")[1];
            log.info("token>>>>>>>>>>>>" + token);
            //取user数据
            RequestConfig requestConfig = RequestConfig.custom()
                    .setExpectContinueEnabled(true)
                    .setSocketTimeout(10000)
                    .setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .build();
            //第二次请求取用户数据
            HttpGet get = new HttpGet("https://api.github.com/user?access_token=" + token);
            get.setConfig(requestConfig);
            get.setHeader("Authorization", "token" + token);
            get.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");

            httpResponse = client.execute(get);
            contentEntity = httpResponse.getEntity();
            user = EntityUtils.toString(contentEntity);
            log.info("user>>>>>>>>>>>>>>" + user);

            EntityUtils.consume(contentEntity);
            //重定向跳回客户端
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert sra != null;
            HttpServletResponse response = sra.getResponse();
            JSONObject jsonObject = JSONObject.parseObject(user);

            //如果用户不存在，就添加用户
            log.info(sysAdminService.getAdminByName("mqxu").toString());
            log.info(jsonObject.getString("login"));
            SysAdmin us = sysAdminService.getAdminByName(jsonObject.getString("login"));
            if (us == null) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime time = LocalDateTime.now();
                log.info("time{}", time);
                String createTimeStr = jsonObject.getString("created_at").replace("T", " ").substring(0, jsonObject.getString("created_at").length() - 1);
                log.info("createTimeStr{}", createTimeStr);
                String updateTimeStr = jsonObject.getString("updated_at").replace("T", " ").substring(0, jsonObject.getString("updated_at").length() - 1);
                log.info("updateTimeStr{}", updateTimeStr);
                LocalDateTime createTimeLDT = LocalDateTime.parse(createTimeStr, df);
                LocalDateTime updateTimeLDT = LocalDateTime.parse(updateTimeStr, df);
                SysAdmin sysAdmin = SysAdmin.builder()
                        .id(Md5Util.getMd5(jsonObject.getString("id"), true, 32))
                        .name(jsonObject.getString("login"))
                        .password(Md5Util.getMd5("123456", true, 32))
                        .avatar(jsonObject.getString("avatar_url"))
                        .salt("C33367701511B4F6020EC61DED352059")
                        .createTime(createTimeLDT)
                        .updateTime(updateTimeLDT)
                        .status(1)
                        .githubName(jsonObject.getString("login"))
                        .build();
                log.info(sysAdmin.toString());
                final int num1 = sysAdminService.addAdmin(sysAdmin);
//                        .createTime(LocalDateTime.parse(jsonObject.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
//                        .updateTime(LocalDateTime.parse(jsonObject.getString("update_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                log.info("是否成功添加用户信息{}", num1);
                //设置权限，默认为小编
                final int num2 = roleAdminService.addRoleAdmin(RoleAdmin.builder().adminId(Md5Util.getMd5(jsonObject.getString("id"), true, 32)).roleId("1").build());
                log.info("是否成功添加用户权限{}", num2);
            }

            //    302  重定向
            response.sendRedirect("http://localhost:8081/#/auth?user=" + user);
//            response.sendRedirect("http://localhost:8081/#/login?code="+code);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
