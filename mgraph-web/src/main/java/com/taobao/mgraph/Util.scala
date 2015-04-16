package com.taobao.mgraph

import java.io._
import scala.io.Source
import org.apache.commons.io.FileUtils

object Util {
    /**
     *
     */
    def changeCharset(dir: String, from: String, to: String, ext: String): Unit = {
        if (dir == null) return ;

        val f = new File(dir)

        for (item <- f.listFiles()) { //循环目录
            if (item.isDirectory())
                changeCharset(item.getPath, from, to, ext);
            else if (item.getName.toLowerCase.endsWith(ext) || "*".equals(ext)) { //匹配扩展名
                //重新保存内容
                val s = readFileToString(item.getPath, from)
                val newString = new String(s.getBytes(), to); //2015-4-8 15:18:31 by 六三
                //val newString = new String(s.getBytes(from), to);
                FileUtils.write(item, newString, to)
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