package com.taobao.mgraph.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileTest {
	public static void main(String[] args) throws Throwable {
		
	}

	public static void main_(String[] args) throws Throwable {
		File file = new File("D:/Downloads/odps-dship/odl_qq_cross_group_rela_11.txt");
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), 5 * 1024 * 1024);// 用5M的缓冲读取文本文件

		long max = 100219998;
		long base = 10000;
		long i = 0;
		long timeMillis = System.currentTimeMillis();

		String line = null;
		while ((line = reader.readLine()) != null) {
			// if (i < max)
			// System.out.println(line);
			// else
			// break;

			// line = 296796,19688141,夏章容,24,0,1,544913

			i++;
			if ((i % base) == 0) {
				System.out.println(i / base + " W is done");
			}

			String[] arr = tools.StringUtil.split(line, ',');
			// if (arr.length != 7) {
			// System.err.println(line + " is error");
			// }

			int maxIndex = arr.length - 1;
			if (maxIndex <= 0)
				continue;

			long num = tools.Convert.toLong(arr[maxIndex], 0);
			if (num > max) {
				max = num;
				System.out.println("found " + max);
			}
		}

		reader.close();

		System.out.println("max " + max + ", cost " + (System.currentTimeMillis() - timeMillis));
	}
}
