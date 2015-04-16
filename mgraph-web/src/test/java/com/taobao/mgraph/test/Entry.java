package com.taobao.mgraph.test;

/**
 * 每行的数据结构
 * 
 * @author <a href="mailto:omeweb@taobao.com">omeweb</a>
 * @version 1.0
 * @since 2015年1月13日
 */
public class Entry {
	public int age, sex, qq, group, role;
	public String nick;

	public static Entry parse(String t) {
		String[] arr = tools.StringUtil.split(t, ',');

		Entry entry = new Entry();
		int base = 7;
		if (arr.length == base) { // 标准格式 = 296796,19688141,夏章容,24,0,1,544913
			entry.qq = tools.Convert.toInt(arr[1], 0);
			entry.nick = arr[2];
			entry.age = tools.Convert.toInt(arr[3], 0);
			entry.sex = tools.Convert.toInt(arr[4], 0);
			entry.role = tools.Convert.toInt(arr[5], 0);// 1-成员，2-管理员，4-创建者
			entry.group = tools.Convert.toInt(arr[6], 0);
		} else {
			// 名称里有逗号的情况
			int diff = arr.length - base;

			entry.qq = tools.Convert.toInt(arr[1], 0);

			// nick
			StringBuilder sb = new StringBuilder();
			for (int i = 2; i < 2 + diff + 1; i++) {
				sb.append(arr[i]);
			}
			entry.nick = sb.toString();

			// 其他字段
			entry.age = tools.Convert.toInt(arr[3 + diff], 0);
			entry.sex = tools.Convert.toInt(arr[4 + diff], 0);
			entry.role = tools.Convert.toInt(arr[5 + diff], 0);// 1-成员，2-管理员，4-创建者
			entry.group = tools.Convert.toInt(arr[6 + diff], 0);

		}

		return entry;
	}
}
