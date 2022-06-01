package cf.vbnm.covidchina

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*


@SpringBootApplication
@MapperScan("cf.vbnm.covidchina.mapper", "cf.vbnm.covidchina.schedule.mapper")
@EnableScheduling
class CovidChinaApplication

fun main(args: Array<String>) {
//    val prop = Properties()
//
//    prop.setProperty("spring.thymeleaf.cache", "false")
//    prop.setProperty("spring.thymeleaf.mode", "HTML")
//    prop.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver")
//    prop.setProperty("spring.datasource.username", "root")
//    prop.setProperty("spring.datasource.password", "Rootasdf")
//    prop.setProperty(
//        "spring.datasource.url",
//        "jdbc:mysql://localhost:3306/covid19_data?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Asia/Shanghai"
//    )
//    prop.setProperty("mybatis-plus.configuration.map-underscore-to-camel-case", "false")
//    prop.setProperty("mybatis-plus.configuration.log-impl", "org.apache.ibatis.logging.stdout.StdOutImpl")
//    prop.setProperty("mybatis-plus.mapper-locations", "classpath:mapper/*Mapper.xml")
//    prop.setProperty("mybatis-plus.type-aliases-package", "cf.vbnm.model.*")
//    prop.setProperty("server.port", "8081")
//    val app = SpringApplication(CovidChinaApplication::class.java)
//    //springboot启动时设置外部的application.properties为默认配置文件
//    //springboot启动时设置外部的application.properties为默认配置文件
//    app.setDefaultProperties(prop)
//    //启动项目
//    //启动项目
//    app.run(*args)
    runApplication<CovidChinaApplication>(*args)
}


