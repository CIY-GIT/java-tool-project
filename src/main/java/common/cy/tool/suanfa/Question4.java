package common.cy.tool.suanfa;

import java.util.Scanner;

/**
 * @Title: Question4
 * @Package common.cy.tool.suanfa
 * @Description: 最长递增子序列
 * 				给你一个整数数组nums，找到其中最长严格递增子序列的长度。
 * 				子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * 				解题：https://leetcode.cn/problems/longest-increasing-subsequence/solutions/147667/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/
 * @author hzchenya
 * @date 2024-11-04 15:14
 * @version TODO
 */
public class Question4
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String[] str = scanner.nextLine().split(" ");
		int[] nums = new int[str.length];
		for (int i = 0; i < nums.length; i++)
			nums[i] = Integer.parseInt(str[i]);
		System.out.println(new Question4().lengthOfLIS(nums));
	}
	public int lengthOfLIS(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}
		int[] dp = new int[nums.length];
		dp[0] = 1;
		int maxans = 1;
		for (int i = 1; i < nums.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (nums[i] > nums[j]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			maxans = Math.max(maxans, dp[i]);
		}
		return maxans;
	}

}
