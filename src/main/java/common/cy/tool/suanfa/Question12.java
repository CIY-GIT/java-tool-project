package common.cy.tool.suanfa;
import java.util.*;

/**
 * @Title: Question12
 * @Package common.cy.tool.suanfa
 * @Description:	最长有效括号
 * 		给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 *  输入样例:
 * 		(()
 * 		)()(()))
 * 输出样例:
 * 		2
 * 		6
 * @author hzchenya
 * @date 2024-11-19 19:55
 * @version TODO
 */
public class Question12
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println(longestValidParentheses(in.nextLine()));
	}

	public static int longestValidParentheses(String s)
	{
		Stack<Integer> st = new Stack<Integer>();
		int ans = 0;
		for (int i = 0, start = 0; i < s.length(); i++)
		{
			if (s.charAt(i) == '(')
				st.add(i);
			else
			{
				if (!st.isEmpty())
				{
					st.pop();
					if (st.isEmpty())
						ans = Math.max(ans, i - start + 1);
					else
						ans = Math.max(ans, i - st.peek());
				}
				else
					start = i + 1;
			}
		}
		return ans;
	}
}
