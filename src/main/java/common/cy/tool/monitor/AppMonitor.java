package common.cy.tool.monitor;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Title: AppMonitor
 * @Package common.cy.tool.file
 * @Description: 应用监控
 * java -jar -DftpPath=E:\线上GC及Dump\32 -DstartSh=startUp.bat -DstopSh=shutdoown.bat -DrestartNum=50 -Dheartbeat=5 appMonitor.jar > appMonitor.log
 * @author hzchenya
 * @date 2018-12-28 15:37
 * @version TODO
 */
public class AppMonitor
{
	public static String OPT_FTP_PTAH = "-DftpPath";
	public static String OPT_START_SH = "-DstartSh";
	public static String OPT_STOP_SH = "-DstopSh";
	public static String OPT_RESTART_NUM = "-DrestartNum";
	public static String OPT_HEART_BEAT = "-Dheartbeat";

	public static String FTP_PATH = "";
	public static String START_SH_PATH = "";
	public static String STOP_SH_PATH = "";
	public static int RESTART_NUM = -1;
	public static int HEART_BEAT = 10;//second

	public static void main(String[] args)
	{
		init();
		heartBeat();
	}

	public static void init()
	{
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		List<String> optionsList = bean.getInputArguments();

		for (String option : optionsList)
		{
			String[] kv = option.split("=");
			if (kv[0].equals(OPT_FTP_PTAH))
			{
				FTP_PATH = kv[1];
			}
			else if (kv[0].equals(OPT_START_SH))
			{
				START_SH_PATH = kv[1];
			}
			else if (kv[0].equals(OPT_STOP_SH))
			{
				STOP_SH_PATH = kv[1];
			}
			else if (kv[0].equals(OPT_RESTART_NUM))
			{
				RESTART_NUM = Integer.parseInt(kv[1]);
			}
			else if (kv[0].equals(OPT_HEART_BEAT))
			{
				HEART_BEAT = Integer.parseInt(kv[1]);
			}
		}
		System.out.println(getCurrentTime() + FTP_PATH);
		System.out.println(getCurrentTime() + START_SH_PATH);
		System.out.println(getCurrentTime() + STOP_SH_PATH);
		System.out.println(getCurrentTime() + RESTART_NUM);
		System.out.println(getCurrentTime() + HEART_BEAT);
	}

	public static void heartBeat()
	{
		while (true)
		{
			try
			{
				Thread.sleep(1000 * HEART_BEAT);
				File file = new File(FTP_PATH);
				if (!file.exists())
				{
					System.out.println(getCurrentTime() + "FTP_PATH not exist ... " + FTP_PATH);
					continue;
				}
				File[] fileList = file.listFiles();
				int fileCnt = fileList.length;
				if (fileCnt > RESTART_NUM)
				{
					//restart
					Runtime run = Runtime.getRuntime();
					try {
						run.exec("cmd start /c "+STOP_SH_PATH);
						run.exec("cmd start /c "+START_SH_PATH);
					} catch (Exception e) {
						System.out.println(getCurrentTime() + "run sh Exception ... " + e.getMessage());
					}
					System.out.println(getCurrentTime() + "Restart success!");
				}
				System.out.println(getCurrentTime() + "HEART_BEAT ... " + fileCnt);
			}
			catch (Exception e)
			{
				System.out.println(getCurrentTime() + "Exception ... " + e.getMessage());
			}
		}

	}

	public static String getCurrentTime()
	{
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + " : ";
	}

}
