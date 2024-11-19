package common.cy.tool.suanfa;

import java.util.Scanner;

/**
 * @Title: Question7
 * @Package common.cy.tool.suanfa
 * @Description: 7-7 不相交的线
 * 		在两条独立的水平线上按给定的顺序写下 nums1 和 nums2 中的整数。
 * 		现在，可以绘制一些连接两个数字 nums1[i] 和 nums2[j] 的直线，这些直线需要同时满足满足：nums1[i] == nums2[j]
 * 		且绘制的直线不与任何其他连线（非水平线）相交。
 * 		请注意，连线即使在端点也不能相交：每个数字只能属于一条连线。
 *
 * 		以这种方法绘制线条，并返回可以绘制的最大连线数。
 * 			1 <= nums1.length, nums2.length <= 500
 * 			1 <= nums1[i], nums2[j] <= 2000
 *
 * 	输入格式:
 * 		每组输入为两行，表示nums1和nums2两个数组。每行有n+1个数字，数字间用空格分开，第一个数字表示数组个数n，后面跟n个数字；如2 2 3，表示数组有2个元素，元素值为2和3
 * 		3 1 4 2
 * 		3 1 2 4
 * 	输出格式:
 * 		输出最多能绘制不相交线的条数。
 * 		2
 *
 * 	力扣：https://leetcode.cn/problems/uncrossed-lines/solutions/787955/bu-xiang-jiao-de-xian-by-leetcode-soluti-6tqz/
 *
 * @author hzchenya
 * @date 2024-11-18 11:21
 * @version TODO
 */
public class Question7
{
	public static void main(String[] args)
	{
		Scanner	scanner = new Scanner(System.in);

		String input = scanner.nextLine();
		String[] split = input.split(" ");
		int m = Integer.parseInt(split[0]);
		int[] nums1 = new int[m];
		for (int i = 1; i <= m; i++)
		{
			nums1[i-1] = Integer.parseInt(split[i]);
		}

		input = scanner.nextLine();
		split = input.split(" ");
		int n = Integer.parseInt(split[0]);
		int[] nums2 = new int[n];
		for (int i = 1; i <= n; i++)
		{
			nums2[i-1] = Integer.parseInt(split[i]);
		}

		//思路：动态规划，最长公共子序列
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++)
		{
			int num1 = nums1[i - 1];
			for (int j = 1; j <= n; j++)
			{
				int num2 = nums2[j - 1];
				if (num1 == num2)
				{
					dp[i][j] = dp[i - 1][j - 1] + 1;
				}
				else
				{
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		System.out.println(dp[m][n]);
	}

}
