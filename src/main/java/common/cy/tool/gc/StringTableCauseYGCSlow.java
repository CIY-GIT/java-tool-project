package common.cy.tool.gc;

import java.io.IOException;
import java.util.UUID;

/**
 * @Title: StringTableCauseYGCSlow
 * @Package common.cy.tool.gc
 * @Description: String.intern(value) 方法会从 StringTable 常量池从拿对应值，如果不存在就放进去，
 * 		如果每次拿值都一样，会导致 StringTable 过程
 * 	JVM参数：
 * 	 -verbose:gc -XX:+PrintGC -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintStringTableStatistics -Xmx1g -Xms1g -Xmn64m
 * 	 -XX:StringTableSize=3000000
 * @author hzchenya
 * @date 2019-12-09 09:43
 * @version TODO
 */
public class StringTableCauseYGCSlow
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Thread.sleep(20000);
		for (int i = 0; i < Integer.MAX_VALUE; i++)
		{
			String sessionId = "EPAY-" + UUID.randomUUID().toString();
//			UUID.randomUUID().toString();
			if (i >= 100000 && i % 100000 == 0)
			{
//				System.out.println("i=" + i);
			}
		}
	}

}
