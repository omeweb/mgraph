package com.taobao.mgraph

import org.springframework.context.support.ClassPathXmlApplicationContext

object MybatisNew {
    def main(args: Array[String]) {
        val ctx = new ClassPathXmlApplicationContext("classpath:bean/applicationContext_mybatis.xml");
        System.out.println(ctx.getStartupDate());

        def dao = new MessageDao
        println(tools.Json.toString(dao.getOne(90)))

        //分页
        println(tools.Json.toString(dao.getPagedList(null, 1, 10)))
    }
}