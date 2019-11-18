package common.cy.tool.gc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Title: GCLogParser
 * @Package common.cy.tool.gc
 * @Description:
 * @author hzchenya
 * @date 2019-06-24 09:28
 * @version TODO
 */
public class GCLogParser
{
	/**
	 * log example:
	 * 2019-06-11T15:05:21.345+0800: 25.315: [GC2019-06-11T15:05:21.346+0800: 25.316: [ParNew: 838912K->66890K(943744K), 0.0956590 secs] 838912K->66890K(4089472K), 0.0958210 secs] [Times: user=0.15 sys=0.03, real=0.09 secs]
	 * @param args
	 */
	public static void main(String[] args)
	{
		File file = new File("");
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null)
			{
				line++;
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public GCLog getGCLog(String logLine)
	{
		if (logLine.contains("[ParNew: "))
		{
			logLine = logLine.substring(logLine.indexOf("ParNew: "));

			GCLog log = new GCLog();
			return log;
		}
		return null;
	}

	class GCLog
	{
		private int parNewSizeTotal;
		private int parNewSizeUsedB4GC;
		private int parNewSizeAfterGC;

		private int heapSizeTotal;
		private int heapSizeUsedB4GC;
		private int heapSizeAfterGC;

		private int parNewToOldSize;

		private String gcTime;
		private int gcCostTimeUser;
		private int gcCostTimeSys;

		public int getParNewSizeTotal()
		{
			return parNewSizeTotal;
		}

		public void setParNewSizeTotal(int parNewSizeTotal)
		{
			this.parNewSizeTotal = parNewSizeTotal;
		}
	}

}


