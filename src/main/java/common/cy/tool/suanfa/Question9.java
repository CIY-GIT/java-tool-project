package common.cy.tool.suanfa;

import sun.lwawt.macosx.CSystemTray;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Title: Question9
 * @Package common.cy.tool.suanfa
 * @Description:	最长超赞子字符串
 * 给你一个字符串 s 。请返回 s 中最长的 超赞子字符串 的长度。
 * 「超赞子字符串」需满足满足下述两个条件：
 * 		该字符串是 s 的一个非空子字符串
 * 		进行任意次数的字符交换后，该字符串可以变成一个回文字符串
 * 1 <= s.length <= 10^5
 * s 仅由数字组成
 *
 * 输入格式:
 * 		输入一行只包含数字的字符串s
 *		3242415
 *
 * 输出格式:
 * 		输出s中最长的 超赞子字符串 的长度
 *		5
 *
 * 力扣：https://leetcode.cn/problems/find-longest-awesome-substring/solutions/379067/zhao-chu-zui-chang-de-chao-zan-zi-zi-fu-chuan-by-l/
 * @author hzchenya
 * @date 2024-11-18 16:13
 * @version TODO
 */
public class Question9
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();

		int n = s.length();
		Map<Integer, Integer> prefix = new HashMap<>();
		prefix.put(0, -1);
		int ans = 0;
		int sequence = 0;
		for (int j = 0; j < n; ++j)
		{
			int digit = s.charAt(j) - '0';
			sequence ^= (1 << digit);
			if (prefix.containsKey(sequence))
			{
				ans = Math.max(ans, j - prefix.get(sequence));
			}
			else
			{
				prefix.put(sequence, j);
			}
			for (int k = 0; k < 10; ++k)
			{
				if (prefix.containsKey(sequence ^ (1 << k)))
				{
					ans = Math.max(ans, j - prefix.get(sequence ^ (1 << k)));
				}
			}
		}

		System.out.println(ans);
	}

}
