package com.taobao.mgraph

import java.util.Map
import java.util.HashMap
import scala.beans.BeanProperty
import net.sf.cglib.beans._

object BeanTest {
    def main(args: Array[String]) {
        val a = new A
        a.userId = 10086
        a.userName = "liusan";

        val c = new C
        c.gender = "m"

        val copier = BeanCopier.create(classOf[A], classOf[C], false);
        copier.copy(a, c, null);
        
        println(tools.Json.toString(c))
        
        //
        val d = new C
        
        val bm = BeanMap.create(d);
        bm.put("userName", "六三")
        bm.put("userName___", "六三")//不存在也没有关系
        println(tools.Json.toString(d))
    }
}

class A {
    @BeanProperty
    var userId: Long = 0;

    @BeanProperty
    var userName: String = null;
}

class B extends A {
    @BeanProperty
    var gender: String = null;
}

class C {
     @BeanProperty
    var userName: String = null;
     
    @BeanProperty
    var gender: String = null;
}