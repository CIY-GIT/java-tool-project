package common.cy.tool.hbase;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/** 
 * @Title: HBasetest.java 
 * @Package com.netease.epay.bill.hbase 
 * @Description: TODO
 * @author hzchenya
 * @date 2018年3月29日 上午11:03:02 
 * @version TODO 
 */
public class HBaseTest
{

	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;
	public static String tableName = "epayTest:accountDetail";

	public static void main(String[] args) throws IOException, InterruptedException
	{
		
		//create 'epayTest:accountDetail', {NAME=>'detail', COMPRESSION => 'SNAPPY', TTL=>'2592000'}, SPLITS => ['1', '2', '3', '4','5','6', '6', '7', '8','9']
		//create 'epayTest:platformAccountDetail', {NAME=>'detail', COMPRESSION => 'SNAPPY', TTL=>'2592000'}, SPLITS => ['1', '2', '3', '4','5','6', '6', '7', '8','9']
		init();
//		addRowBatch();
		
//		getData(tableName, "epay_cy@163.com_20180329153840919", null, null);
		
//		scanData(tableName, "0_epay_cy@163.com_2018032915", "0_epay_cy@163.com_2018032921", null, null, null);
//		scanData(tableName, "0_epay_cy@163.com_2018032915", "0_epay_cy@163.com_2018032921", "detail", "type", "in");
//		scanData(tableName, "0_epay_cy@163.com_2018032915", "0_epay_cy@163.com_2018032921", "detail", "bizType", "转账");
//		scanData(tableName, "0_epay_cy@163.com_201803291530", "epay_cy@163.com_201803291537", null, null, null);//全表扫描
//		scanData(tableName, "0_epay_cy@163.com_2018032915", "0_epay_cy@163.com_2018032917", null, null, null);
		
		scanPlatformData(tableName, "platfrom_B@wyb.com", "detail", "tradeTime", "20180402115320", "20180402115322");
		
//		deleRow("t2", "rw1", "cf1", "q1");
		// deleteTable("t2");
	}

	//初始化链接
	public static void init()
	{
		if (configuration != null)
			return;
		
		// Windows环境下，可能需要下载hadoop安装包(如hadoop-2.6.0)，设置HADOOP_HOME
		// 否则，可能会报错： Could not locate executable null\bin\winutils.exe in the Hadoop binaries.
		System.setProperty("hadoop.home.dir", "D:\\hadoop-2.6.0");
		configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum", "hbase17.lt.163.org,hbase18.lt.163.org,hbase19.lt.163.org");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		configuration.set("zookeeper.znode.parent", "/hbase");


		try
		{
			connection = ConnectionFactory.createConnection(configuration);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	//关闭连接
	public static void close()
	{
		try
		{
			if (null != connection)
				connection.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static void addRowBatch() throws IOException, InterruptedException
	{
		String accountId = "platfrom_B@wyb.com";
		for (int i = 1; i < 501; i++)
		{
			Long ctms = new Long(System.currentTimeMillis());
			String tradeTime = DateFormatUtils.format(ctms, "yyyyMMddHHmmssSSS");
			//			int splitKey = (Math.abs(accountId.hashCode())) % 9;//当前表 Region为9,获取region
			int splitKey = (Math.abs(i)) % 9;//当前表 Region为9,获取region
			String rowKey = splitKey + "_" + accountId + "_" + tradeTime;
			System.out.println(rowKey);
			addRow(tableName, rowKey, "detail", "accountId", accountId);
			addRow(tableName, rowKey, "detail", "amount", i + "0" + i + ".00");
			addRow(tableName, rowKey, "detail", "tradeTime", tradeTime);
			if (i % 2 == 0)
			{
				addRow(tableName, rowKey, "detail", "type", "in");
				addRow(tableName, rowKey, "detail", "desc", "交易收入");
				addRow(tableName, rowKey, "detail", "bizType", "TRADE");
			}
			else
			{
				addRow(tableName, rowKey, "detail", "type", "out");
				addRow(tableName, rowKey, "detail", "desc", "交易退款");
				addRow(tableName, rowKey, "detail", "bizType", "TRADE_REFUND");
			}
			if (i % 10 == 0)
			{
				Thread.sleep(500);
				System.out.println(">>>>>>>>>>>>> 第" + i + "跳 <<<<<<<<<<<<<<");
			}
		}
	}
	
	//插入数据
	public static void addRow(String tableName, String rowkey, String colFamily, String col, String val)
			throws IOException
	{
		init();
		Table table = connection.getTable(TableName.valueOf(tableName));
		Put put = new Put(Bytes.toBytes(rowkey));
		put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col), Bytes.toBytes(val));
		table.put(put);

		//批量插入
		/* List<Put> putList = new ArrayList<Put>();
		 puts.add(put);
		 table.put(putList);*/
		//生产环境可以不用每次都close，最后close掉。提高效率。
		table.close();
	}

	//删除数据
	public static void deleRow(String tableName, String rowkey, String colFamily, String col) throws IOException
	{
		init();
		Table table = connection.getTable(TableName.valueOf(tableName));
		Delete delete = new Delete(Bytes.toBytes(rowkey));
		//删除指定列族
		//delete.addFamily(Bytes.toBytes(colFamily));
		//删除指定列
		//delete.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
		table.delete(delete);
		//批量删除
		/* List<Delete> deleteList = new ArrayList<Delete>();
		 deleteList.add(delete);
		 table.delete(deleteList);*/
		table.close();
	}

	//根据rowkey查找数据
	public static void getData(String tableName, String rowkey, String colFamily, String col) throws IOException
	{
		init();
		Table table = connection.getTable(TableName.valueOf(tableName));
		Get get = new Get(Bytes.toBytes(rowkey));
		//获取指定列族数据
		//get.addFamily(Bytes.toBytes(colFamily));
		//获取指定列数据
		//get.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
		Result result = table.get(get);

		showCell(result);
		table.close();
	}

	//格式化输出
	public static void showCell(Result result)
	{
		Cell[] cells = result.rawCells();
		Map<String, ArrayList<Cell>> rows = new HashMap<String, ArrayList<Cell>>();
		for (Cell cell : cells)
		{
			String rowKey = new String(CellUtil.cloneRow(cell));
			if (rows.get(rowKey) == null)
				rows.put(rowKey, new ArrayList<Cell>());
			rows.get(rowKey).add(cell);
			//			System.out.println("RowName:" + new String(CellUtil.cloneRow(cell)) + " ");
			//			System.out.println("Timetamp:" + cell.getTimestamp() + " ");
			//			System.out.println("column Family:" + new String(CellUtil.cloneFamily(cell)) + " ");
		}
		for (Object key : rows.keySet())
		{
			StringBuffer sb = new StringBuffer();
			sb.append(key + " [");
			for (Cell cell : rows.get(key))
			{
				sb.append(new String(CellUtil.cloneQualifier(cell)) + ": "
						+ new String(CellUtil.cloneValue(cell)) + ",");
			}
			sb.append("]");
			System.out.println(sb.toString());
		}
	}

	//批量查找数据
	public static void scanData(String tableName, String startRow, String stopRow, String filterFamily, String filterQualifier, String filterQualifierValue) throws IOException
	{
		init();
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes(startRow));
		scan.setStopRow(Bytes.toBytes(stopRow));
		
		//filter
		if (filterFamily != null)
		{
			FilterList filterList = new FilterList();
			SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes(filterFamily),
					Bytes.toBytes(filterQualifier), CompareOp.EQUAL, Bytes.toBytes(filterQualifierValue));
			filterList.addFilter(filter);
			scan.setFilter(filterList);
		}
		
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner)
		{
			showCell(result);
		}
		table.close();
	}
	
	public static void scanPlatformData(String tableName, String platformId, String filterFamily, String filterQualifier, String startTime, String endTime) throws IOException
	{
		init();
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes("0_platfrom_B@wyb.com_20180402115320"));
		scan.setStopRow(Bytes.toBytes("8_platfrom_B@wyb.com_20180402115321"));
		
		//filter
		if (filterFamily != null)
		{
			FilterList filterList = new FilterList();
			
//			Filter filter = new RowFilter(CompareOp.EQUAL,new SubstringComparator(platformId)); 
//			SingleColumnValueFilter sfilter = new SingleColumnValueFilter(Bytes.toBytes(filterFamily),
//					Bytes.toBytes(filterQualifier), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(startTime));
//			SingleColumnValueFilter efilter = new SingleColumnValueFilter(Bytes.toBytes(filterFamily),
//					Bytes.toBytes(filterQualifier), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(endTime));
//			filterList.addFilter(filter);
//			filterList.addFilter(sfilter);
//			filterList.addFilter(efilter);
			
			String sk = ".*" + platformId + "_" + startTime + ".*";
			String ek = ".*" + platformId + "_" + endTime + ".*";
			Filter sf = new RowFilter(CompareOp.EQUAL,new RegexStringComparator(sk));
			Filter ef = new RowFilter(CompareOp.EQUAL,new RegexStringComparator(ek));
			filterList.addFilter(sf);
//			filterList.addFilter(ef);
			
//			scan.setReversed(true);
//			scan.setFilter(filterList);
		}
		
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner)
		{
			showCell(result);
		}
		table.close();
	}
	
}
