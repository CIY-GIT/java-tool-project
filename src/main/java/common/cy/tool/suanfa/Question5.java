package common.cy.tool.suanfa;

import java.util.Scanner;
import java.util.*;

/**
 * @Title: Question5
 * @Package common.cy.tool.suanfa
 * @Description: 二叉树的最大路径和
 * 			二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 * 			路径和 是路径中各节点值的总和。
 * 			给你一个二叉树的根节点 root ，返回其 最大路径和 。
 * 			解题：https://leetcode.cn/problems/binary-tree-maximum-path-sum/solutions/297005/er-cha-shu-zhong-de-zui-da-lu-jing-he-by-leetcode-/
 * @author hzchenya
 * @date 2024-11-04 15:21
 * @version TODO
 */
public class Question5
{
	private static int ans = Integer.MIN_VALUE;

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		dfs(buildTree(in.nextLine()));
		System.out.println(ans);
	}

	private static int dfs(Node node)
	{
		if (node == null)
		{
			return 0; // 没有节点，和为 0
		}
		int lVal = dfs(node.left); // 左子树最大链和
		int rVal = dfs(node.right); // 右子树最大链和
		ans = Math.max(ans, lVal + rVal + node.val); // 两条链拼成路径
		return Math.max(Math.max(lVal, rVal) + node.val, 0); // 当前子树最大链和
	}

	public static Node buildTree(String input)
	{
		if (input == null || input.length() == 0)
		{
			return null;
		}
		String[] values = input.split(",");
		Node root = new Node(Integer.parseInt(values[0]));
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		int i = 1;
		while (!queue.isEmpty() && i < values.length)
		{  // 为什么要判断queue.isEmpty()，因为有可能最后一个节点是null，比如1,2,3,null,null,4,null
			Node node = queue.poll();
			if (!"null".equals(values[i]))
			{
				node.left = new Node(Integer.parseInt(values[i]));
				queue.offer(node.left);
			}
			i++;
			if (i < values.length && !"null".equals(values[i]))
			{
				node.right = new Node(Integer.parseInt(values[i]));
				queue.offer(node.right);
			}
			i++;
		}
		return root;
	}
}

class Node
{
	int val;

	Node left;
	Node right;

	Node(int val)
	{
		this.val = val;
	}

}
