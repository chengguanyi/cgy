package com.xuanwu.ai.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "测试用户")
@Data
public class User {

    @ApiModelProperty(value = "用户")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

}
