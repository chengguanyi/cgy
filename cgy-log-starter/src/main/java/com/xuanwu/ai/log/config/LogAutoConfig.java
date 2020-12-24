package com.xuanwu.ai.log.config;

import com.xuanwu.ai.log.aspect.WebLogAspect;
import lombok.Data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 日志配置
 *
 * @author gourd.hu
 */

@Data
@Configuration
@EnableScheduling
@MapperScan({"org.gourd.hu.log.dao"})
@Import({WebLogAspect.class})
public class LogAutoConfig {

}