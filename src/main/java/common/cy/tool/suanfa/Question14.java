package common.cy.tool.suanfa;

import java.util.*;

/**
 * @Title: Question14
 * @Package common.cy.tool.suanfa
 * @Description: 按位与为零的三元组
 * 	给你一个整数数组 nums ，返回其中 按位与三元组 的数目。
 * 	按位与三元组 是由下标 (i, j, k) 组成的三元组，并满足下述全部条件：
 * 		0 <= i < nums.length
 * 		0 <= j < nums.length
 * 		0 <= k < nums.length
 * nums[i] & nums[j] & nums[k] == 0 ，其中 & 表示按位与运算符。
 *
 * 提示：
 * 	1 <= nums.length <= 1000
 * 	0 <= nums[i] < 2^16
 *
 * 说明：注意算法复杂度，暴力枚举解法，会超出时间限制
 *
 * 输入样例:
 * 		2,1,3
 * 输出样例:
 * 		12
 *
 * @author hzchenya
 * @date 2024-11-19 19:55
 * @version TODO
 */
public class Question14
{
	public static void main(String[] args) {
		String[] in = new Scanner(System.in).nextLine().split(",");
		int[] ins = new int[in.length];
		for(int i = 0; i<in.length; i++){
			ins[i] = Integer.parseInt(in[i]);
		}
		System.out.println(countTriplets(ins));
	}
	public static int countTriplets(int[] nums) {
		int[] cnt = new int[1 << 16];
		for (int x : nums)
			for (int y : nums)
				++cnt[x & y];
		int ans = 0;
		for (int x : nums)
			for (int y = 0; y < 1 << 16; ++y)
				if ((x & y) == 0)
					ans += cnt[y];
		return ans;
	}

}
