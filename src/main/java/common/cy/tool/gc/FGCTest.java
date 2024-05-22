package common.cy.tool.gc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: FGCTest
 * @Package common.cy.tool.gc
 * @Description: 测试触发FGC，打印GC前后堆信息
 * JVM参数：-Xmn10m -Xms50m -Xmx50m -XX:+UseConcMarkSweepGC -XX:+PrintClassHistogramBeforeFullGC -XX:+PrintClassHistogramAfterFullGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintReferenceGC,
 * 新生代中：eden和survivor的比值默认为8:1， 可通过-XX:SurvivorRatio=8调整
 * -Xmn声明新生代大小是10MB，即eden为8MB，survivor为1MB
 *
 * -XX:+HeapDumpBeforeFullGC -XX:+HeapDumpAfterFullGC
 *
 * @author hzchenya
 * @date 2019-11-14 10:11
 * @version TODO
 */
public class FGCTest
{
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args)
	{
		MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapMemoryUsage = mxBean.getHeapMemoryUsage();
		System.out.println("初始化堆：" + (heapMemoryUsage.getInit() >> 10) + "K");
		System.out.println("已用堆：" + (heapMemoryUsage.getUsed() >> 10) + "K");
		System.out.println("最大堆：" + (heapMemoryUsage.getMax() >> 10) + "K");

		List<byte[]> bytes = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			byte[] a = new byte[3 * _1MB];
			bytes.add(a);
			System.out.println("剩余：" + (Runtime.getRuntime().freeMemory() >> 10) + "K");
		}

		bytes.remove(0);
		bytes.add(new byte[3 * _1MB]);

		bytes.subList(0, 8).clear();

		bytes.add(new byte[3 * _1MB]);
		for (int i = 0; i < 6; i++) {
			bytes.add(new byte[3 * _1MB]);
		}
	}
}
