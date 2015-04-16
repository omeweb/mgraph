package com.taobao.mgraph

import com.taobao.mgraph.domain._

class MessageDao extends com.taobao.freeproj.orm.AbstractDao[Message] {
    override def getNamespace(): String = {
        "message"
    }
}