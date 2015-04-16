package com.taobao.mgraph

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session._
import com.taobao.mgraph.domain.Message
import java.util._

object Mybatis {

    var sqlSessionFactory = null: SqlSessionFactory
    //初始化时运行

    val reader = Resources.getResourceAsReader("mybatis-config.xml");
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

    def main(args: Array[String]) {
        val session = sqlSessionFactory.openSession();
        try {
            //val entry = session.selectOne[Message]("message.getOne", 10);
            //println(tools.Json.toString(entry))

            var newOne = createNew("修车", "2015-4-3", 30)

            //val x = session.insert("message.insert", newOne)
            println(tools.Json.toString(newOne))
            //println(x)

            //测试分页
            val parameter: java.util.Map[String, Any] = tools.MapUtil.create(); //Map("limit" -> 10, "skip" -> 0);
            parameter.put("limit", 10)
            parameter.put("skip", 10)

            val list = session.selectList[Message]("message.getPagedList", parameter)
            println(tools.Json.toString(list))

            //格式转换
            var xlist = parse("d:/data/acc.txt");
            println(tools.Json.toJson(xlist))

            session.commit(); //提交事务2015-4-3 16:57:48 by 六三
        } catch {
            case e: Exception => println(e)
        } finally {
            session.close();
        }
    }

    def createNew(title: String, time: String, v: Float, comment: String = null): Message = {
        var newOne = new Message
        newOne.userId = 1114308877;
        newOne.userName = "namefree"
        newOne.title = title
        newOne.numericValue = v;
        newOne.source = "accounting";
        newOne.ip = tools.StringUtil.LOCAL_IP
        newOne.createTime = tools.DateTime.formatNow(null)
        newOne.occurrenceTime = time

        if (comment != null) newOne.content = comment

        newOne
    }

    def parse(file: String): List[Message] = {
        val content = Util.readFileToString(file, "utf-8")
        //println(content)

        var time = "";
        val list = new ArrayList[Message]

        for (item <- content.split("\\n")) {
            //println(item)
            val line = tools.StringUtil.trim(item)
            if (!tools.StringUtil.isNullOrEmpty(line)) {
                val isDate = tools.Validate.isDateTime(line, "YYYY-MM-dd", false)
                if (isDate) time = line;
                else {
                    //解析内容                    
                    val arr = tools.StringUtil.split(line, ' ')
                    println(arr.length)
                    if (arr.length == 2) {
                        val title = arr(0)
                        val v = tools.Convert.toDouble(arr(1), 0)

                        //v可以为0

                        list.add(createNew(title, time, v.toFloat))
                    }
                }
            }
        }

        list
    }
}

//2015-4-3
//修车 30
//电费 100
//电话费 20
//
//2015-3-30
//吃饭 20
//x 10
//咖啡 66