package com.taobao.mgraph.domain

import scala.beans.BeanProperty

class Message {
    @BeanProperty
    var id: Long = 0;

    @BeanProperty
    var userId: Long = 0;

    @BeanProperty
    var userName: String = null;

    @BeanProperty
    var content: String = null;

    @BeanProperty
    var createTime: String = null;

    @BeanProperty
    var occurrenceTime: String = null;

    @BeanProperty
    var title: String = null;

    @BeanProperty
    var comment: String = null;

    @BeanProperty
    var extraData: String = null;

    @BeanProperty
    var ip: String = null;

    @BeanProperty
    var numericValue: Float = 0;

    @BeanProperty
    var source: String = null;
}