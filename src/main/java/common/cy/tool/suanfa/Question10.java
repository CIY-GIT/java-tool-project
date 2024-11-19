package common.cy.tool.suanfa;

import java.util.*;

/**
 * @Title: Question10
 * @Package common.cy.tool.suanfa
 * @Description:  超级回文数
 * 		如果一个正整数自身是回文数，而且它也是一个回文数的平方，那么我们称这个数为超级回文数。
 * 		现在，给定两个正整数 L 和 R ，请按照从小到大的顺序打印包含在范围 [L, R] 中的所有超级回文数。
 * 		注：R包含的数字不超过20位
 * 		回文数定义：将该数各个位置的数字反转排列，得到的数和原数一样，例如676，2332，10201。
 *
 * 输入格式:
 * 		L,R。例如 4,1000
 * 输出格式:
 * 		[L, R]范围内的超级回文数，例如[4, 9, 121, 484]
 *
 *	力扣：https://leetcode.cn/problems/super-palindromes/solutions/19530/chao-ji-hui-wen-shu-by-leetcode/
 * @author hzchenya
 * @date 2024-11-18 17:11
 * @version TODO
 */
public class Question10
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String[] split = scanner.nextLine().split(",");
		superpalindromesInRange(split[0], split[1]);
	}

	public static void superpalindromesInRange(String sL, String sR) {
		long L = Long.valueOf(sL);
		long R = Long.valueOf(sR);
		int MAGIC = 100000;
		int ans = 0;
		List<Long> palindromes = new ArrayList<>();

		// count odd length;
		for (int k = 1; k < MAGIC; ++k)
		{
			StringBuilder sb = new StringBuilder(Integer.toString(k));
			for (int i = sb.length() - 2; i >= 0; --i)
				sb.append(sb.charAt(i));
			long v = Long.valueOf(sb.toString());
			v *= v;
			if (v > R)
				break;
			if (v >= L && isPalindrome(v))
			{
				ans++;
				palindromes.add(v);
			}
		}

		// count even length;
		for (int k = 1; k < MAGIC; ++k)
		{
			StringBuilder sb = new StringBuilder(Integer.toString(k));
			for (int i = sb.length() - 1; i >= 0; --i)
				sb.append(sb.charAt(i));
			long v = Long.valueOf(sb.toString());
			v *= v;
			if (v > R)
				break;
			if (v >= L && isPalindrome(v))
			{
				ans++;
				palindromes.add(v);
			}
		}

		StringBuilder result = new StringBuilder();
		result.append("[");
		for (int k = 0; k < palindromes.size(); ++k)
		{
			if (k == palindromes.size() - 1)
				result.append(palindromes.get(k)).append("]");
			else
				result.append(palindromes.get(k) + ", ");
		}
		System.out.println(result.toString());
	}

	public static boolean isPalindrome(long x) {
		return x == reverse(x);
	}

	public static long reverse(long x) {
		long ans = 0;
		while (x > 0) {
			ans = 10 * ans + x % 10;
			x /= 10;
		}

		return ans;
	}

}
