package common.cy.tool.file;

import java.io.File;

/**
 * @Title: PackageReader
 * @Package common.cy.tool.file
 * @Description:
 * @author hzchenya
 * @date 2019-01-02 20:19
 * @version TODO
 */
public class PackageReader
{
	public static void main(String[] args)
	{
		File file = new File("E:\\workspace\\epay-lib");
		if (!file.exists()) {
			return ;
		}
		File[] fileList = file.listFiles();
		System.out.println(fileList.length);
	}
}
