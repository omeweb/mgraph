package com.taobao.mgraph

import java.io._
import scala.io.Source
import org.apache.commons.io.FileUtils

object Main {
    val DEFAULT_ENCODING: String = "utf-8";

    private def pri() {} //私有方法，中间的等号可以省略 private def pri() = {}

    def convertSqlMap() {
        val file = raw"D:\codes\bak\mtee\freeproj-biz\src\test\resources\map\catalog.xml"
        val content = Util.readFileToString(file, "gbk")

        val sb = new StringBuilder

        var flag = 0;
        for (item <- content.toCharArray()) {
            var c = item
            if (c == '#' || c == '$') {
                flag = flag + 1;

                if ((flag % 2) == 1) {
                    sb.append(c).append("{")
                } else
                    sb.append("}")
            } else
                sb.append(c)
        }

        //println(flag)
        //println("==========================");
        println(sb.toString())
    }

    def main(args: Array[String]): Unit = {
        //val sc = Util.readFileToString(raw"D:\data\pageSource\bootstrap\activityKV.html", "gbk")
        //val newString = new String(sc.getBytes(), "utf-8");
        //println(newString)

        //FileUtils.write(new File("d:/data/t.html"), newString,"utf-8")

        Util.changeCharset(raw"D:\codes\git\mod-user\t", "gbk", "UTF-8", "java")
        //js,css,xml,java,txt,properties
        //convertSqlMap();
        return ;

        val user = new UserEntry
        user.age = 100;
        user.name = "六三";

        println(tools.Json.toString(user))
        //save(user)

        //列表循环
        val att = Array(1, 2, 3);
        att.filter(_ > 2)
        att.filter(x => x > 2)
        val rlist = att.filter(x => {
            //这里可以很多行
            x > 2
        });

        println(rlist(0))

        // read();

        //多行的字符串
        val s = """
        可以是很多行的哟
        再一行
          """;

        //match
        var count = 100
        val x: String = count match {
            case 20 => "a"
            case _  => "b"
        }

        println(x)

        //println(Util.getExtension("f:/2/t.txt"))
        //println(Util.getExtensionV2(null))
        println("-----" + Util.uslCase2CamelCase("a_b_c_info_type"))
        println(getAStringMaybe(-1))
    }

    def m(v: String): Unit = {
        val a = 1; //生成 int a = 1;
        var b = "2" //生成String b = "2";
        def c = 3 // def会生成一个方法：

        //  private final int c$1() {
        //    return 3;
        //  }
    }

    def getAStringMaybe(num: Int): Option[String] = {
        if (num >= 0) Some("A positive number!")
        else None // A number less than 0?  Impossible!
    }

    /**
     * 序列化
     * @author liusan.dyf
     */
    def save(entry: UserEntry) = {
        val fos = new FileOutputStream("d:/data/t.bin");
        val oos = new ObjectOutputStream(fos);

        oos.writeObject(entry);
        oos.flush();
        oos.close();
    }

    /**
     * 反序列化
     */
    def read() = {
        val fis = new FileInputStream("d:/data/t.bin");
        val ois = new ObjectInputStream(fis);

        val x = ois.readObject();
        println(x.toString())
    }
}