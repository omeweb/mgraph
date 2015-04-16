package com.taobao.mgraph

import scala.beans.BeanProperty

/**
 * scala下的类
 */
@SerialVersionUID(1L)
class UserEntry extends java.io.Serializable {
    var age = 0: Int;
    var name = null: String;

    private var status = 0 //私有变量

    @BeanProperty
    var sex = "male";

    @BeanProperty
    var job = "it";

    def getAge = age
    def getName = name

    def setName(value: String) = name = value
    def setAge(value: Int) = age = value

    override def toString = this.name + "," + this.age;
}