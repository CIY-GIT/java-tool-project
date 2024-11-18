package common.cy.tool.suanfa;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Title: Question2
 * @Package common.cy.tool.suanfa
 * @Description: 给定一个由不同正整数的组成的非空数组 nums ，考虑下面的图：
 * 				有 nums.length 个节点，按从 nums[0] 到 nums[nums.length - 1] 标记；
 * 				只有当 nums[i] 和 nums[j] 共用一个大于 1 的公因数时，nums[i] 和 nums[j]之间才有一条边。
 * 				返回 图中最大连通组件的大小 。
 * 		解题：https://leetcode.cn/problems/largest-component-size-by-common-factor/solutions/1706239/an-gong-yin-shu-ji-suan-zui-da-zu-jian-d-amdx/
 * @author hzchenya
 * @date 2024-11-01 17:49
 * @version TODO
 */
public class Question2
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String[] str = scanner.nextLine().split(" ");
		scanner.close();
		int[] nums = new int[str.length];
		for (int i = 0; i < nums.length; i++)
			nums[i] = Integer.parseInt(str[i]);

		System.out.println(new Question2().largestComponentSize(nums));
	}

	public int largestComponentSize(int[] nums) {
		int m = Arrays.stream(nums).max().getAsInt();
		UnionFind uf = new UnionFind(m + 1);
		for (int num : nums) {
			for (int i = 2; i * i <= num; i++) {
				if (num % i == 0) {
					uf.union(num, i);
					uf.union(num, num / i);
				}
			}
		}
		int[] counts = new int[m + 1];
		int ans = 0;
		for (int num : nums) {
			int root = uf.find(num);
			counts[root]++;
			ans = Math.max(ans, counts[root]);
		}
		return ans;
	}
}

class UnionFind {
	int[] parent;
	int[] rank;

	public UnionFind(int n) {
		parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
		rank = new int[n];
	}

	public void union(int x, int y) {
		int rootx = find(x);
		int rooty = find(y);
		if (rootx != rooty) {
			if (rank[rootx] > rank[rooty]) {
				parent[rooty] = rootx;
			} else if (rank[rootx] < rank[rooty]) {
				parent[rootx] = rooty;
			} else {
				parent[rooty] = rootx;
				rank[rootx]++;
			}
		}
	}

	public int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}
}
