package com.taobao.mgraph

import org.scribe.builder._
import org.scribe.builder.api._
import org.scribe.model._

object Oauth {
    def main(args: Array[String]) {
        val service = new ServiceBuilder()
            .provider(classOf[SinaWeiboApi20])
            .apiKey("2350731631")
            .apiSecret("1dd8c08e823d6ea32c9e184056a1ddf6")
            .callback("http://auth.xml5.com/oauth/callback")
            .build();

        //得到url，跳转过去授权
        println(service.getAuthorizationUrl(null))

        //授权后，跳转到callback，获取code参数
        println(service.getAccessToken(null, new Verifier("4df89015f61babf15b3ae1996f023e1e")))
    }
}