package com.taobao.mgraph

import java.io._
import scala.io.Source
import org.apache.commons.io.FileUtils
import org.mozilla.universalchardet._

object Util {
	// from http://svn.codespot.com/a/eclipselabs.org/dawb/trunk/org.dawb.common.util/src/org/dawb/common/util/io/FileUtils.java
    def getBOM(): String = {
        var b = new Array[Byte](3)
        b(0) = 0xEF.toByte
        b(1) = 0xBB.toByte
        b(2) = 0xBF.toByte

        new String(b, 0, b.length, "UTF-8");
    }

    def detectEncoding(fileName: String): String = {
        val buf = new Array[Byte](4096);
        val fis = new java.io.FileInputStream(fileName);

        // (1)
        val detector = new UniversalDetector(null);

        // (2)
        var nread: Int = 0;

        nread = fis.read(buf)
        while (nread > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);

            nread = fis.read(buf)
        }
        // (3)
        detector.dataEnd();

        // (4)
        val encoding = detector.getDetectedCharset();
        //        if (encoding != null) {
        //            System.out.println("Detected encoding = " + encoding);
        //        } else {
        //            System.out.println("No encoding detected.");
        //        }

        // (5)
        detector.reset();

        //返回
        if (encoding != null)
            encoding.toString() else null
    }

    /**
     * ext为java\nxml\ntxt
     */
    def changeCharset(dir: String, to: String, ext: String): Unit = {
        if (dir == null) return ;

        val f = new File(dir)
        var bom = this.getBOM()

        for (item <- f.listFiles()) { //循环目录
            val path = item.getPath
            if (item.isDirectory())
                changeCharset(item.getPath, to, ext);
            else if (tools.StringUtil.containsAny(path, ext) || "*".equals(ext)) { //匹配扩展名
                //获取编码
                var from = detectEncoding(path)
                if (from == null) from = "gbk";

                //读取内容
                val s = readFileToString(path, from)

                //重新保存内容
                var newString = new String(s.getBytes(), to); //2015-4-8 15:18:31 by 六三

                if (newString != null && newString.length() > 0) {

                    //val newString = new String(s.getBytes(from), to);
                    newString = tools.StringUtil.replace(newString, "\"GB2312\"", "\"UTF-8\"")
                    newString = tools.StringUtil.replace(newString, "\"GBK\"", "\"UTF-8\"")
                    newString = tools.StringUtil.replace(newString, "GB18030", "UTF-8")
                    newString = tools.StringUtil.replace(newString, "\"gb2312\"", "\"UTF-8\"")
                    //newString = tools.StringUtil.replace(newString, "mgateway", "underline")
                    //println("正在替换：" + item)

                    //去除bom
                    var firstChar = newString.charAt(0);
                    if (firstChar == bom.charAt(0)) {
                        println("去重bom：" + item);
                        newString = newString.substring(1);
                    }

                    FileUtils.write(item, newString, to)
                }
            }
        }
    }

    def backup(dir: String): Unit = {
        val f = new File(dir)
        val d = new File(f.getParent + "_bak")
        FileUtils.copyDirectory(f, d, true);
    }

    def readFileToString(file: String, encoding: String): String = {
        if (file == null || encoding == null)
            null
        else
            FileUtils.readFileToString(new File(file), encoding)
    }

    def uslCase2CamelCase(v: String): String = {
        //db_info => dbInfo

        if (v == null) null;

        val len = v.length()
        val ch = '_'
        val sb = new StringBuilder;
        var x = false; //上一个字符是否是下划线

        for (i <- 0 to len - 1) {
            if (x) {
                sb.append(v.charAt(i).toUpper)
            } else if (v.charAt(i) != ch)
                sb.append(v.charAt(i))

            x = v.charAt(i) == ch;

            //println(x)
        }

        sb.toString
    }

    /**
     * 得到文件的扩展名
     */
    def getExtension(file: String): String = file.substring(file.lastIndexOf('.') + 1)

    def getExtensionV2(file: String): String = {
        if (file == null) null
        else
            file.substring(file.lastIndexOf('.') + 1)
    }
}