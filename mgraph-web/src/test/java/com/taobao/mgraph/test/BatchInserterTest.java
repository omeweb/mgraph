package com.taobao.mgraph.test;

import java.util.Map;

import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

public class BatchInserterTest {
	// 已经有qq是11位的了
	static final long DIFF = 200 * 10000 * 10000L;// 200亿

	public static void main(String[] args) throws Throwable {
		String DB_PATH = "D:\\data\\neodb";
		Map<String, String> config = tools.MapUtil.create();
		config.put("neostore.relationshipstore.db.mapped_memory", "1024M");

		final BatchInserter db = BatchInserters.inserter(DB_PATH, config);
		registerShutdownHook(db);
		try {
			// initNodes(db);
			// initNodes_(db);

			insertR(db, 10);

			// System.out.println(IdType.NODE.getMaxValue());// 34359738367

			// System.out.println(DIFF + 1);

			// new tools.code.RunTimer().run("save one", 1, new Runnable() {
			// @Override
			// public void run() {
			// Map<String, Object> properties = tools.MapUtil.create();
			// db.createNode(10000 * 10 + 1, properties);
			// }
			// });

			// System.out.println(db.nodeExists(10000 * 10));

			// 设置属性
			// for (int i = 1; i < 10; i++) {
			// db.setNodeProperty(i, "n", "name-" + i);
			// }
		} finally {
			db.shutdown();
		}
	}

	static void insertR(final BatchInserter db, int num) throws Throwable {
		// System.out.println(tools.Json.toString(parse("296796,19688141,夏章容,24,0,1,544913")));
		tools.Predicate<String> p = new tools.Predicate<String>() {
			Map<String, Object> properties = tools.MapUtil.create();

			int base = 10000;
			int x = 0;

			@Override
			public boolean execute(String t) {
				// 转换 // System.out.println(tools.Json.toString(Entry.parse(t)));
				Entry entry = Entry.parse(t);

				// 关系
				MyRelationshipType type = MyRelationshipType.MEMBER;
				if (entry.role == 2)
					type = MyRelationshipType.MANAGER;
				else if (entry.role == 4)
					type = MyRelationshipType.CREATOR;

				// 插入
				db.createRelationship(entry.qq, getGroupId(entry.group), type, properties);

				x++;

				if ((x % base) == 0) {
					System.out.println(x / base + " W is done");
				}

				return true;
			}
		};

		String path = "D:/Downloads/odps-dship/odl_qq_cross_group_rela_" + num + ".txt";

		// 开始
		long timeMillis = System.currentTimeMillis();
		long all = tools.StringUtil.eachLine(path, "utf-8", 5 * 1024 * 1024, p);
		System.out.println(all + " lines, cost ms " + (System.currentTimeMillis() - timeMillis));
	}

	static long getGroupId(long g) {
		return g + 13 * 10000 * 10000;// 群搞个偏移量
	}

	public static void initNodes_(final BatchInserter db) {
		final Map<String, Object> properties = tools.MapUtil.create();
		new tools.code.RunTimer().run("save node", 1, new Runnable() {
			@Override
			public void run() {
				int base = 10000;
				// QQ群号码有多少位？目前群号码最长是9位数，最短是6位数的。
				long start = 15 * 10000 * 10000;
				long x = 0L;

				while (x < 10000 * 10000 * 1) {
					db.createNode(x + start, properties);
					// System.out.println(x + start);
					// System.out.println(x % base);

					if ((x % base) == 0) {
						System.out.println(x / base + " W is done");
					}

					x++;
				}
			}
		});
	}

	public static void initNodes(final BatchInserter db) {
		final Map<String, Object> properties = tools.MapUtil.create();
		new tools.code.RunTimer().run("save node", 1, new Runnable() {
			@Override
			public void run() {
				int base = 10000;
				// 初始化13亿个节点，129999 W，973069.308125
				long max = 10000 * 1000 * 10 * 13;
				for (int i = 1; i < max; i++) {
					db.createNode(i, properties);

					if (i % base == 0) {
						System.out.println(i / base + " W is done");
					}
				}
			}
		});
	}

	private static void registerShutdownHook(final BatchInserter db) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				db.shutdown();
			}
		});
	}
}
