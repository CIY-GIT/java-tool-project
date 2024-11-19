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
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println(lengthOfLongestSubstring(in.nextLine()));

	}

	public static int lengthOfLongestSubstring(String s) {
		Map<Character, Integer> dic = new HashMap<>();
		int i = -1, res = 0, len = s.length();
		for(int j = 0; j < len; j++) {
			if (dic.containsKey(s.charAt(j)))
				i = Math.max(i, dic.get(s.charAt(j))); // 更新左指针 i
			dic.put(s.charAt(j), j); // 哈希表记录
			res = Math.max(res, j - i); // 更新结果
		}
		return res;
	}
}
