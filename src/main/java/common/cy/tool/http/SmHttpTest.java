package common.cy.tool.http;

//import cn.com.bouncycastle.tls.TLSUtils;
//import org.bouncycastle.util.encoders.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @purpose
 * @company Infosec Technology
 * @auther cuilifan
 * @date 2022/1/17
 */
public class SmHttpTest
{
	//国密证书：中级 + 根
	public static String[] TRUSTCERTS = new String[] {
			"MIIDHzCCAsWgAwIBAgIQf4CPuRpGayya9bShUr367TAKBggqgRzPVQGDdTA3MQsw"
					+ "CQYDVQQGEwJDTjERMA8GA1UECgwIVW5pVHJ1c3QxFTATBgNVBAMMDFVDQSBSb290"
					+ "IFNNMjAeFw0yMTAzMTYxNjAwMDBaFw0yNjAzMTYxNTU5NTlaMFsxCzAJBgNVBAYT"
					+ "AkNOMSUwIwYDVQQKDBxUcnVzdEFzaWEgVGVjaG5vbG9naWVzLCBJbmMuMSUwIwYD"
					+ "VQQDDBxUcnVzdEFzaWEgU00yIE9WIFRMUyBDQSAtIFMxMFkwEwYHKoZIzj0CAQYI"
					+ "KoEcz1UBgi0DQgAEo4Ydq2wg8ZcrPdpZmz7zFdzg3+4/fgGsqHZaSOZjlJOAALBv"
					+ "zFEVmoLv9sJB/tfArqYkY3Oy/rlXszNEfXvJIaOCAY0wggGJMEYGA1UdIAQ/MD0w"
					+ "OwYJKoEchu86AQECMC4wLAYIKwYBBQUHAgEWIGh0dHBzOi8vd3d3LnNoZWNhLmNv"
					+ "bS9yZXBvc2l0b3J5MA4GA1UdDwEB/wQEAwIBhjASBgNVHRMBAf8ECDAGAQH/AgEA"
					+ "MB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjB7BggrBgEFBQcBAQRvMG0w"
					+ "MgYIKwYBBQUHMAGGJmh0dHA6Ly9vY3NwLmdsb2JhbC5zaGVjYS5jb20vcm9vdHNt"
					+ "MmcxMDcGCCsGAQUFBzAChitodHRwOi8vY2VydHMuZ2xvYmFsLnNoZWNhLmNvbS9y"
					+ "b290c20yZzEuY2VyMB0GA1UdDgQWBBQ2IxAJiQItY6TJ6Ba8UxSQqioB9zAfBgNV"
					+ "HSMEGDAWgBTu6LCc1dzsc/3vfPpQLMbBQOZMszA/BgNVHR8EODA2MDSgMqAwhi5o"
					+ "dHRwOi8vY3JsLmdsb2JhbC5zaGVjYS5jb20vcm9vdC9yb290c20yZzEuY3JsMAoG"
					+ "CCqBHM9VAYN1A0gAMEUCIHT7GRbNY0O4fBMBj+vKZ4BD1W+3xvN42pMp/4J/zEWt"
					+ "AiEA8rFdgoa0tmFSHnMYx0jSA9uM0WGvBgSKtejVFwHgMVA=",
			"MIIBsjCCAVagAwIBAgIQekcB+uQc2WxgVMBjau8ErTAMBggqgRzPVQGDdQUAMDcxCzAJBgNVBAYTAkNOMREwDwYDVQQKDAhVbmlUcnVzdDEVMBMGA1UEAwwMVUNBIFJvb3QgU00yMB4XDTE1MDMxMzAwMDAwMFoXDTM4MTIzMTAwMDAwMFowNzELMAkGA1UEBhMCQ04xETAPBgNVBAoMCFVuaVRydXN0MRUwEwYDVQQDDAxVQ0EgUm9vdCBTTTIwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAScuw56Nfm/ScjUGwfoaXP/66L9SB7buCbGwh6ZUOY7EqOzQen1k9urNdqNShin1GVx/oGahV7TZjXZm9EcwEJTo0IwQDAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0TAQH/BAUwAwEB/zAdBgNVHQ4EFgQU7uiwnNXc7HP973z6UCzGwUDmTLMwDAYIKoEcz1UBg3UFAANIADBFAiB2b9MZPv01Akz6OUhpObg072Gt5AhbWeZGs3t8kCkcywIhAK0yzbddqaVooWseSiwdTbKPmn4hyVizvY8pm4R4vzhS" };

	private static void testGMHTTPS() throws IOException, GeneralSecurityException
	{
		HttpURLConnection urlConnection = (HttpURLConnection) new URL("https://epay.163.com").openConnection();
		//判断 urlConnection 对象是否为空

		// 判断协议是https的话，需要设置SSLSocketFactory
		if (urlConnection.getURL().getProtocol().equalsIgnoreCase("https"))
		{
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
			// 国密算法协议:GMTLSV1.1
//			SSLSocketFactory sslSocketFactory = TLSUtils.createSSLSocketFactory("GMTLSV1.1", TRUSTCERTS);

			// 使用外部认证设备(SKF设备等)创建SSLSocketFactory，目前仅支持国密算法(此处以文鼎创的Key为例)
			// SSLSocketFactory sslSocketFactory = TLSUtils.createSSLSocketFactory("GMTLSV1.1", TRUSTCERTS, new TestSKFCredentialedProvider());

			// 双向认证需要配置客户端的参数(此处配置的是信安测试环境下的客户端证书，客户在实际集成中需要配置为自己的客户端证书)
			// SSLSocketFactory sslSocketFactory = TLSUtils.createSSLSocketFactory("GMTLSV1.1", TRUSTCERTS, "/mnt/sign.pfx", "keyPassword", "keyPassword", "/mnt/enc.pfx", "keyPassword", "keyPassword");

			// RSA(国际算法协议):TLS
			// SSLSocketFactory sslSocketFactory = TLSUtils.createSSLSocketFactory("TLS", TRUSTCERTS);
			//            SSLSocketFactory sslSocketFactory = TLSUtils.createSSLSocketFactory("TLS", TRUSTCERTS, "D:\\RSA_CLIENT.pfx", "123456", "123456", "", "", "");
//			httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
		}
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("POST");
		urlConnection.getOutputStream().write(new byte[1024]);
		// 响应头和响应消息
		int responseCode = urlConnection.getResponseCode();
		String responseMessage = urlConnection.getResponseMessage();
		System.out.println(responseCode + ":" + responseMessage);
		// 响应内容
		if (200 == responseCode)
		{
			InputStream inputStream = urlConnection.getInputStream();
			InputStreamReader in = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while (null != (line = bufferedReader.readLine()))
			{
				stringBuilder.append(line);
				System.out.println(stringBuilder);
			}
			// 响应头
			Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : headerFields.entrySet())
			{
				String key = entry.getKey();
				List<String> value = entry.getValue();
				System.out.println(key + ":" + value.get(0));
			}

		}
	}

	public static void main(String[] args)
	{
		try
		{
			testGMHTTPS();
		}
		catch (IOException | GeneralSecurityException e)
		{
			e.printStackTrace();
		}
	}
}