package com.soft1851.music.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soft1851.music.admin.common.ResponseResult;
import com.soft1851.music.admin.domain.entity.GithubUser;
import com.soft1851.music.admin.domain.entity.Repositories;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/5/6 9:16
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/oauth2/code/github")
@Slf4j
public class AuthController2 {
    private static int time1 = 1;
    private static int time2 = 1;

    /**
     * 前端访问这个接口
     * 该接口访问github，获取个人信息
     * <p>
     * //     * @param code
     */
    @GetMapping()
    public ResponseResult getUser(@RequestParam(value = "code", required = false) String code) {
        log.info(code + "***********");
        log.info("访问/oauth2/code/github接口");
        Map<String, List> map = new HashMap<>();
        List<Object> users = new ArrayList<>();
        List<Object> followings = new ArrayList<>();
        List<Object> followers = new ArrayList<>();
        List<Object> repositories = new ArrayList<>();
        map.put("users", users);
        map.put("followings", followings);
        map.put("followers", followers);
        map.put("repositories", repositories);

        String token = "";
        String user = "";
        //创建一个httpClient对象
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setExpectContinueEnabled(true)
                    .setSocketTimeout(10000)
                    .setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .build();
            HttpClient client = HttpClients.createDefault();
            if (code == null) {
                log.info("code为空");
                log.info("client{}", client);
                HttpGet get = new HttpGet("https://github.com/login/oauth/authorize?client_id=a29c48c1e7c4f774c6c9&redirect_uri=http://localhost:8080/oauth2/code/github");
                log.info("get{}", get);
                get.setConfig(requestConfig);
                get.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
                HttpResponse httpResponse = client.execute(get);

//                log.info("response{}", response);
//                HttpEntity contentEntity = httpResponse.getEntity();
//                log.info("contentEntity{}", contentEntity);
            } else {
                log.info("code获取成功{}", code);
                if (time1 != time2) {
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
//                log.info("token>>>>>>>>>>>>" + token);
                    //取user数据

                    //第二次请求取用户数据

                    HttpGet get = new HttpGet("https://api.github.com/user?access_token=" + token);
                    get.setConfig(requestConfig);
                    get.setHeader("Authorization", "token" + token);
                    get.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
                    httpResponse = client.execute(get);
                    contentEntity = httpResponse.getEntity();
                    user = EntityUtils.toString(contentEntity);
                    log.info("user>>>>>>>>>>>>>>" + user);
                    JSONObject object = JSONObject.parseObject(user);
                    users.add(object);


//            Map<String, List<Map<String, String>>> map = new HashMap<>();

                    JSONObject jsonObject = JSONObject.parseObject(user);

                    //请求取following数据
                    HttpGet get2 = new HttpGet("https://api.github.com/users/" + jsonObject.getString("login") + "/following?access_token=" + token);
                    get2.setConfig(requestConfig);
                    get2.setHeader("Authorization", "token" + token);
                    get2.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
                    httpResponse = client.execute(get2);
                    contentEntity = httpResponse.getEntity();
                    String following = EntityUtils.toString(contentEntity);
                    log.info("following>>>>>>>>>>>>>>" + following);
                    List<GithubUser> githubUserList = JSON.parseArray(following, GithubUser.class);
                    followings.add(githubUserList);

                    //请求取followers数据
                    HttpGet get3 = new HttpGet("https://api.github.com/users/" + jsonObject.getString("login") + "/followers?access_token=" + token);
                    get3.setConfig(requestConfig);
                    get3.setHeader("Authorization", "token" + token);
                    get3.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
                    httpResponse = client.execute(get3);
                    contentEntity = httpResponse.getEntity();
                    String followerStr = EntityUtils.toString(contentEntity);
                    log.info("followerStr>>>>>>>>>>>>>>" + followerStr);
                    List<GithubUser> githubUserFollowerList = JSON.parseArray(followerStr, GithubUser.class);
                    followers.add(githubUserFollowerList);

                    //请求取repos数据
                    HttpGet get4 = new HttpGet("https://api.github.com/users/" + jsonObject.getString("login") + "/repos?access_token=" + token);
                    get4.setConfig(requestConfig);
                    get4.setHeader("Authorization", "token" + token);
                    get4.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
                    httpResponse = client.execute(get4);
                    contentEntity = httpResponse.getEntity();
                    String repos = EntityUtils.toString(contentEntity);
                    log.info("repos>>>>>>>>>>>>>>" + repos);
                    List<Repositories> repositoriesList = JSON.parseArray(repos, Repositories.class);
                    repositories.add(repositoriesList);
//                System.out.println(map);
                    time1++;
                    System.out.println("time1" + time1);
                    System.out.println("time2" + time2);
                } else {
                    time2++;
                    //重定向跳回客户端
                    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    assert sra != null;
                    HttpServletResponse response = sra.getResponse();
                    response.sendRedirect("http://localhost:8081/#/githubinfo?code=" + code);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.success(map);
    }
}
