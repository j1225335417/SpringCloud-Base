package com.xinghuo.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Api(tags = "mysql 测试")
public class MysqlController {

    @ApiOperation("测试")
    @RequestMapping(value = "selectList")
    public List<String> select() {
        return Arrays.asList("1", "2");
    }

}
