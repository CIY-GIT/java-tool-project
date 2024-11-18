package common.cy.tool.suanfa;

import java.util.Scanner;

/**
 * @Title: Question3
 * @Package common.cy.tool.suanfa
 * @Description: 拼接最大数
 * 				给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，表示两个自然数各位上的数字。
 * 				现在从这两个数组中选出 k (0 <=k <= m + n) 个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
 * 				求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组
 * 				解题：https://leetcode.cn/problems/create-maximum-number/solutions/505931/pin-jie-zui-da-shu-by-leetcode-solution/
 * @author hzchenya
 * @date 2024-11-04 14:32
 * @version TODO
 */
public class Question3
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String[] str1 = scanner.nextLine().split(",");
		String[] str2 = scanner.nextLine().split(",");
		int k = Integer.parseInt(scanner.nextLine());

		int[] nums1 = new int[str1.length];
		int[] nums2 = new int[str2.length];
		for (int i = 0; i < nums1.length; i++)
			nums1[i] = Integer.parseInt(str1[i]);
		for (int i = 0; i < nums2.length; i++)
			nums2[i] = Integer.parseInt(str2[i]);

		int[] res = new Question3().maxNumber(nums1, nums2, k);
		for (int i = 0; i < res.length; i++)
		{
			if (i == res.length - 1)
				System.out.println(res[i]);
			else
				System.out.print(res[i] + ",");
		}
	}

	public int[] maxNumber(int[] nums1, int[] nums2, int k) {
		int m = nums1.length, n = nums2.length;
		int[] maxSubsequence = new int[k];
		int start = Math.max(0, k - n), end = Math.min(k, m);
		for (int i = start; i <= end; i++) {
			int[] subsequence1 = maxSubsequence(nums1, i);
			int[] subsequence2 = maxSubsequence(nums2, k - i);
			int[] curMaxSubsequence = merge(subsequence1, subsequence2);
			if (compare(curMaxSubsequence, 0, maxSubsequence, 0) > 0) {
				System.arraycopy(curMaxSubsequence, 0, maxSubsequence, 0, k);
			}
		}
		return maxSubsequence;
	}

	public int[] maxSubsequence(int[] nums, int k) {
		int length = nums.length;
		int[] stack = new int[k];
		int top = -1;
		int remain = length - k;
		for (int i = 0; i < length; i++) {
			int num = nums[i];
			while (top >= 0 && stack[top] < num && remain > 0) {
				top--;
				remain--;
			}
			if (top < k - 1) {
				stack[++top] = num;
			} else {
				remain--;
			}
		}
		return stack;
	}

	public int[] merge(int[] subsequence1, int[] subsequence2) {
		int x = subsequence1.length, y = subsequence2.length;
		if (x == 0) {
			return subsequence2;
		}
		if (y == 0) {
			return subsequence1;
		}
		int mergeLength = x + y;
		int[] merged = new int[mergeLength];
		int index1 = 0, index2 = 0;
		for (int i = 0; i < mergeLength; i++) {
			if (compare(subsequence1, index1, subsequence2, index2) > 0) {
				merged[i] = subsequence1[index1++];
			} else {
				merged[i] = subsequence2[index2++];
			}
		}
		return merged;
	}

	public int compare(int[] subsequence1, int index1, int[] subsequence2, int index2) {
		int x = subsequence1.length, y = subsequence2.length;
		while (index1 < x && index2 < y) {
			int difference = subsequence1[index1] - subsequence2[index2];
			if (difference != 0) {
				return difference;
			}
			index1++;
			index2++;
		}
		return (x - index1) - (y - index2);
	}

}
