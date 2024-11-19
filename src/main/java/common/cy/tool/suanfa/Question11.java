package common.cy.tool.suanfa;

import java.util.*;

/**
 * @Title: Question11
 * @Package common.cy.tool.suanfa
 * @Description: 最多能完成排序的块
 *	给你一个整数数组 arr 。将 arr 分割成若干 块 ，并将这些块分别进行排序。之后再连接起来，使得连接的结果和按升序排序后的原数组相同。
 * 	返回能将数组分成的最多块数？
 * 	输入格式：
 * 		5 4 3 2 1
 * 		2 1 3 4 4
 * 	输出：
 * 		1
 * 		4
 *
 * 	力扣：https://leetcode.cn/problems/max-chunks-to-make-sorted/solutions/1886333/zui-duo-neng-wan-cheng-pai-xu-de-kuai-by-gc4k/
 * @author hzchenya
 * @date 2024-11-19 18:14
 * @version TODO
 */
public class Question11
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String[] split = scanner.nextLine().split(" ");
		int[] arr = new int[split.length];
		for (int i = 0; i < split.length; i++)
			arr[i] = Integer.parseInt(split[i]);

		System.out.println(maxChunksToSorted(arr));
	}

	public static int maxChunksToSorted(int[] arr)
	{
		LinkedList<Integer> stack = new LinkedList<Integer>();
		for(int num : arr) {
			if(!stack.isEmpty() && num < stack.getLast()) {
				int head = stack.removeLast();
				while(!stack.isEmpty() && num < stack.getLast()) stack.removeLast();
				stack.addLast(head);
			}
			else stack.addLast(num);
		}
		return stack.size();
	}
}
