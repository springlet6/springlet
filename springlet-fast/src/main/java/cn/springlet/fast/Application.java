package cn.springlet.fast;

import cn.springlet.crypt.annotation.EnableSpringletCrypt;
import cn.springlet.log.annotation.EnableSpringletLog;
import cn.springlet.mybatisplus.annotation.EnableSpringletMybatisPlus;
import cn.springlet.redis.annotation.EnableSpringletRedis;
import cn.springlet.rocketmq.annotation.EnableSpringletRocketMq;
import cn.springlet.swagger2.annotation.EnableSpringletSwagger2;
import cn.springlet.xxljob.annotation.EnableSpringletXxljob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@EnableSpringletCrypt
@EnableSpringletMybatisPlus
@EnableSpringletLog
@EnableSpringletRedis
@EnableSpringletSwagger2
@EnableSpringletXxljob
@EnableSpringletRocketMq
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);

        Environment environment = run.getBean(Environment.class);
        String port = environment.getProperty("local.server.port");

        String url = "http://127.0.0.1:" + port + "/doc.html";
        log.info("--------------启动成功-------------   " + url);
        log.info("--------------启动项目环境---------   " + environment.getProperty("spring.profiles.active"));
        log.info("--------------启动项目端口---------   " + port);
        log.info("--------------启动项目名字---------   " + environment.getProperty("spring.application.name"));

    }
}
