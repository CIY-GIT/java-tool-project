package common.cy.tool.suanfa;

import java.util.Scanner;

/**
 * @Title: Question8
 * @Package common.cy.tool.suanfa
 * @Description:	解码异或后的排列
 * 		给你一个整数数组 perm ，它是前 n 个正整数（1,2,3,4,5,…,n-1,n 共n个正整数）的排列，且 n 是个奇数 。
 * 		它被加密成另一个长度为 n-1 的整数数组 encoded ，满足 encoded[i] = perm[i] XOR perm[i+1]。比方说，如果 perm=[1,3,2] ，那么 encoded=[2,1]。
 * 		给你 encoded 数组，请你返回原始数组 perm 。题目保证答案存在且唯一。
 *
 * 提示：
 * 		n 是奇数。
 * 		3 <= n < 10^5
 * 		encoded.length == n - 1
 *
 * 输入格式:
 * 		整数数组encoded，以",”分隔字符串形式作为输入
 * 		6,5,4,6
 * 输出格式:
 * 		解码后的原始整数数组perm，以",”分隔字符串形式作为输出
 * 		2,4,1,5,3
 *
 *	力扣：https://leetcode.cn/problems/decode-xored-permutation/solutions/769140/jie-ma-yi-huo-hou-de-pai-lie-by-leetcode-9gw4/
 * @author hzchenya
 * @date 2024-11-18 14:02
 * @version TODO
 */
public class Question8
{
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String line =  in.nextLine();
		String[] linestr = line.split(",");
		int[] num = new int[linestr.length];
		for (int i = 0; i < linestr.length; i++) {
			num[i] = Integer.parseInt(linestr[i]);
		}
		int[] result = encodedCov(num);
		StringBuilder sb = new StringBuilder("");
		for(int re: result){
			sb.append(","+re);
		}
		System.out.println(sb.toString().substring(1));
	}

	public static int[] encodedCov(int[] encoded){
		int n = encoded.length + 1;
		int total = 0;
		for (int i = 1; i <= n; i++) {
			total ^= i;
		}
		int odd = 0;
		for (int i = 1; i < n - 1; i += 2) {
			odd ^= encoded[i];
		}
		int[] perm = new int[n];
		perm[0] = total ^ odd;
		for (int i = 0; i < n - 1; i++) {
			perm[i + 1] = perm[i] ^ encoded[i];
		}
		return perm;
	}
}
