package common.cy.tool.file;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Title: ExcelReader
 * @Package common.cy.tool.file
 * @Description:
 * @author hzchenya
 * @date 2024-05-21 19:37
 * @version TODO
 */
public class ExcelReader
{
	public static Set<String> platformIds = new HashSet<>();

	public static void main(String[] args)
	{
		try
		{
			String filePath = "/Users/cy-mac/cbg_pltId.xlsx";
			read(filePath);
			filePath = "/Users/cy-mac/cbg_apollo.xlsx";
			read(filePath);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void read(String filePath) throws Exception{
		//输入流对象
		FileInputStream in = new FileInputStream(new File(filePath));
		//读取磁盘上已经存在的Excel文件
		XSSFWorkbook excel = new XSSFWorkbook(in);
		//读取Excel文件中的第一个sheet页
		XSSFSheet sheet = excel.getSheetAt(0);

		//获取sheet页文字中的最后一行的行号
		int lastRowNum = sheet.getLastRowNum();

		for (int i = 1; i <= lastRowNum; i++) {
			//获取某一行
			XSSFRow row = sheet.getRow(i);
			//获取单元格对象
			if (filePath.contains("cbg_pltId"))
			{
				Cell cell = row.getCell(0);
				String cellValue = cell.getStringCellValue();
				platformIds.add(cellValue);
			}
			else
			{
				Cell cell = row.getCell(4);
				CellType cellType = cell.getCellTypeEnum();
				String cellValue = null;
				if (cellType == CellType.STRING)
				{
					cellValue = cell.getStringCellValue();
				}
				else if (cellType == CellType.NUMERIC)
				{
					cellValue = String.valueOf(cell.getNumericCellValue());
				}

				String result = check(cellValue);
				if (!StringUtils.isEmpty(result))
				{
					System.out.println(row.getCell(1).getStringCellValue() + " : " + row.getCell(2).getStringCellValue() + " : " + row.getCell(3).getStringCellValue() + " : "+ result);
				}
			}
		}
		System.out.println(platformIds.size());
		//关闭资源
		in.close();
		excel.close();
	}

	public static String check(String cellValue)
	{
		String pids = "";
		for (String id : platformIds)
		{
			if (cellValue.contains(id))
			{
				pids = pids + "，" + id;
			}
		}
		return pids;
	}

	public static void write(String filePath) throws Exception {
		//在内存中创建一个Excel文件
		XSSFWorkbook excel = new XSSFWorkbook();
		//在Excel文件中创建一个sheet页
		XSSFSheet sheet = excel.createSheet("info");
		//在Sheet中创建行对象，rownum编号从0开始,1为第二行
		XSSFRow row = sheet.createRow(1);
		//创建单元格并且写入文件内容 1为第二个单元格
		row.createCell(1).setCellValue("姓名");
		row.createCell(2).setCellValue("城市");

		//创建一个新行
		row = sheet.createRow(2);
		row.createCell(1).setCellValue("张三");
		row.createCell(2).setCellValue("北京");

		row = sheet.createRow(3);
		row.createCell(1).setCellValue("李四");
		row.createCell(2).setCellValue("南京");

		//通过输出流把内存里的Excel文件写入到D磁盘中
		FileOutputStream out = new FileOutputStream(new File(filePath)); //流对象
		excel.write(out);

		//关闭资源
		out.close();
		excel.close();
	}

}
