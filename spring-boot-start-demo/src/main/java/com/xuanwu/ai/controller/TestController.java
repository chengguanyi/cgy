package com.xuanwu.ai.controller;


import com.xuanwu.ai.base.response.BaseResponse;
import com.xuanwu.ai.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "测试controller")
@Slf4j
@RestController
public class TestController {

    @GetMapping("/getUser")
    @ApiOperation("获取用户信息")
    public BaseResponse<User> getUser(@RequestParam("userName") String userName){

        User user = new User();
        user.setUserName(userName);
        user.setPassword("123456");

        return BaseResponse.ok(user);
    }

}
