package common.cy.tool.count;

import com.netease.epay.common.constant.IpConstant;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetReader.Builder;
import org.apache.parquet.hadoop.example.GroupReadSupport;

import java.io.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hzchenya
 * @version TODO
 * @Title: AccessDataReaderEpay
 * @Package epay.uc
 * @Description:
 * @date 2018-11-05 21:29
 */
public class AccessDataReaderEpay
{
    public static Map<String, Integer> ipLocationMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            hdfs2txt("D:\\epay.ng.20181101", "D:\\epay.ng.ip.20181101.txt");
            countIpLocation("D:\\epay.ng.ip.20181101.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
	 * 提取HDFS文件到TXT
     * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
     */
    private static void hdfs2txt(String hdfsFile, String txtFile) throws IOException, ParseException, SQLException {
        GroupReadSupport readSupport = new GroupReadSupport();
        Builder<Group> reader = ParquetReader.builder(readSupport,
                new Path(hdfsFile));
        ParquetReader<Group> build = reader.build();
        Group line = null;
        FileWriter writer = new FileWriter(txtFile);
        while ((line = build.read()) != null) {
            String ip = line.getString("_c0", 0);
            writer.write(ip + "\n");
        }
        System.out.println("读取结束");
    }

    /**
	 * 统计IP 省份、请求次数、占比
     * @param fileName
     */
    public static void countIpLocation(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            long total = 0;
            DecimalFormat df = new DecimalFormat("0.0000");
            while ((tempString = reader.readLine()) != null) {
                String ip = tempString;
                String location = getLocation(ip);
                if (!ipLocationMap.containsKey(location)) {
                    ipLocationMap.put(location, 1);
                } else {
                    ipLocationMap.put(location, ipLocationMap.get(location) + 1);
                }
            }
            for (String key :
                    ipLocationMap.keySet()) {
                total = total + ipLocationMap.get(key);
            }
            for (String key :
                    ipLocationMap.keySet()) {
                System.out.println(key + "\t" + ipLocationMap.get(key) + "\t" + df.format(ipLocationMap.get(key) * 1.0 / total));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            long total = 0;
            DecimalFormat df = new DecimalFormat("0.0000");
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String[] ss = tempString.split("\t");
                if (!ipLocationMap.containsKey(ss[0])) {
                    ipLocationMap.put(ss[0], Integer.parseInt(ss[1].trim()));
                } else {
                    ipLocationMap.put(ss[0], ipLocationMap.get(ss[0]) + Integer.parseInt(ss[1].trim()));
                }
                total = total + Integer.parseInt(ss[1].trim());
            }
            for (String key :
                    ipLocationMap.keySet()) {
                System.out.println(key + "\t" + ipLocationMap.get(key) + "\t" + df.format(ipLocationMap.get(key) * 1.0 / total));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 获取省份
     * @param ip
     * @return
     */
    private static String getLocation(String ip) {
        return IpConstant.getProvinceCN(IpConstant.convertToIPArea(ip));
    }

}
