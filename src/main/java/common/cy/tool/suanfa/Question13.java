package common.cy.tool.suanfa;

import java.util.*;

/**
 * @Title: Question13
 * @Package common.cy.tool.suanfa
 * @Description:	无重复字符的最长子串
 * 	给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * 	输入样例:
 * 		abcabcbb
 * 		bbbbb
 * 	输出样例:
 * 		3
 * 		1
 * @author hzchenya
 * @date 2024-11-19 19:55
 * @version TODO
 */
public class Question13
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println(lengthOfLongestSubstring2(in.nextLine()));
	}

	public static int lengthOfLongestSubstring2(String s)
	{
		int len = s.length();
		if (len == 1)
			return 1;
		int max = 0;
		Set<Character> set = new HashSet<>();
		String sub = "";
		for (int i = 0; i < len; i++)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(s.charAt(i));
			for (int j = i+1; j < len; j++)
			{
				sb.append(s.charAt(j));
				sub = sb.toString();
				System.out.println(sub);
				set.clear();
				if (isUnRepeatStr(sub, set))
					max = Math.max(max, sub.length());
			}
			System.out.println(">>>>>>>>>>>>>>>>");
		}
		return max;
	}

	public static boolean isUnRepeatStr(String s,	Set<Character> set)
	{
		int len = s.length();
		if (len == 1)
			return true;

		for (int i = 0; i < len; i++)
		{
			if (set.contains(s.charAt(i)))
				return false;
			set.add(s.charAt(i));
		}
		return true;
	}

	public static int lengthOfLongestSubstring(String s)
	{
		Map<Character, Integer> dic = new HashMap<>();
		int i = -1;//左指针：尽量重复元素的最后一次出现的坐 标
		int ans = 0;
		int len = s.length();
		for (int j = 0; j < len; j++)
		{
			if (dic.containsKey(s.charAt(j)))
				i = Math.max(i, dic.get(s.charAt(j))); // 更新左指针 i
			dic.put(s.charAt(j), j); // 哈希表记录
			ans = Math.max(ans, j - i); // 更新结果
		}
		return ans;
	}
}
