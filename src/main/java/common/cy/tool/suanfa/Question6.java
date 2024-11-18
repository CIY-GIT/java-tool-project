package common.cy.tool.suanfa;

import java.util.Scanner;

/**
 * @Title: Question6
 * @Package common.cy.tool.suanfa
 * @Description: 计算岛屿最大面积
 * 	给你一个大小为 m x n 的二进制矩阵 grid 。
 * 	岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * 	岛屿的面积是岛上值为 1 的单元格的数目。
 * 	计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0
 *
 * 	输入：[[1,1,0,0,0];[1,1,0,0,0];[0,0,1,0,0];[0,0,0,1,1]]
 * 	输出：4
 * @author hzchenya
 * @date 2024-11-18 09:38
 * @version TODO
 */
public class Question6
{
	private static int[][] array;
	private static int[][] markArray;
	private static int max = 0;
	private static int gridSize;

	public static int dfs(int x, int y)
	{
		if (x < 0 || x >= array.length || y < 0 || y >= array[0].length || array[x][y] == 0 || markArray[x][y] == 1)
		{
			return 0;
		}
		gridSize++;
		markArray[x][y] = 1;
		dfs(x + 1, y);
		dfs(x - 1, y);
		dfs(x, y + 1);
		dfs(x, y - 1);
		return gridSize;
	}

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		String input = scanner.nextLine();
		String[] rows = input.split(";");

		int rowCount = rows.length;
		int colCount = rows[0].split(",").length;

		array = new int[rowCount][colCount];
		markArray = new int[rowCount][colCount];
		for (int i = 0; i < rowCount; i++) {
			String[] elements = rows[i].replaceAll("\\[", "").replaceAll("]", "").split(",");
			for (int j = 0; j < colCount; j++) {
				array[i][j] = Integer.parseInt(elements[j]);
			}
		}

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				gridSize = 0;
				int size = dfs(i, j);
				max = Math.max(max, size);
			}
		}

		System.out.println(max);
	}
}
