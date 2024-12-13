package common.cy.tool.suanfa;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Title: Question1
 * @Package common.cy.tool.suanfa
 * @Description: 按格式合并两个链表
 * 			给定两个链表L1 =a1 →a2 →⋯→an−1 →an 和 L2 =b1 →b2 →⋯→bm−1 →bm ，其中n≥2m。
 * 			需要将较短的链表L2  反转并合并到较长的链表L1 中
 * 			使得合并后的链表如下形式：a1 →a2 →bm →a3 →a4 →bm−1 →… 
 * @author hzchenya
 * @date 2024-11-01 17:49
 * @version TODO
 */
public class Question1
{
	public static Map<String, Node> nodeMap = new HashMap();

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String[] params = scanner.nextLine().split(" ");
		String head1Addr = params[0];
		String head2Addr = params[1];
		int size = Integer.parseInt(params[2]);

		//构造链表
		for(int i=0; i< size; i++)
		{
			String[] nodeStr = scanner.nextLine().split(" ");
			//构造节点
			buildNode(nodeStr[0], Integer.parseInt(nodeStr[1]), nodeStr[2]);
		}


		String[] link1Info = getLinkInfo(head1Addr);
		String[] link2Info = getLinkInfo(head2Addr);
		int link1Size = Integer.parseInt(link1Info[2]);
		int link2Size = Integer.parseInt(link2Info[2]);

		//合并两个链表
		if(link1Size>=link2Size)
		{
			merge(head1Addr, link2Info[1]);
		}
		else
		{
			merge(head2Addr, link1Info[1]);
		}
	}

	public static void merge(String bigHeadAddr,String smallTailAddr)
	{
		Node bigIndex = nodeMap.get(bigHeadAddr);
		Node smallIndex = nodeMap.get(smallTailAddr);
		int step = 1;
		while(smallIndex != null)
		{
			if(step == 2)
			{
				Node tempBig = bigIndex.next;
				bigIndex.next = smallIndex;
				smallIndex.next = tempBig;
				bigIndex = tempBig;

				smallIndex = smallIndex.pre;
				step = 1;
			}
			else
			{
				bigIndex = bigIndex.next;
				step++;
			}
		}

		bigIndex = nodeMap.get(bigHeadAddr);
		while(bigIndex != null)
		{
			System.out.println(bigIndex.addr+" "+bigIndex.value+" "+(bigIndex.next==null?"-1":bigIndex.next.addr));
			bigIndex = bigIndex.next;
		}
	}

	public static String[] getLinkInfo(String headAddr)
	{
		Node index = nodeMap.get(headAddr);
		String tailAddr = null;
		int size = 1;

		while(index != null)
		{
			if (index.next == null) {
				break;
			}
			index = index.next;
			size++;
		}
		String[] info = new String[3];
		info[0] = headAddr;
		info[1] = index.addr;
		info[2] = size + "";
		return info;
	}

	public static void buildNode(String addr, int value, String nextAddr)
	{
		Node node = nodeMap.get(addr);
		if(node == null)
		{
			node = new Node(value, addr, null, null);
		}
		else
		{
			node.value = value;
		}
		nodeMap.put(addr, node);

		if("-1".equals(nextAddr))
		{
			return;
		}
		Node next = nodeMap.get(nextAddr);
		if(next == null)
		{
			next = new Node(0, nextAddr, null, node);
		}
		node.next = next;
		next.pre = node;
		nodeMap.put(nextAddr, next);
	}

	public static class Node
	{
		public int value;
		public String addr;
		public Node next;
		public Node pre;

		public Node(int value, String addr, Node next, Node pre)
		{
			this.value = value;
			this.addr = addr;
			this.next = next;
			this.pre = pre;
		}

		@Override
		public String toString()
		{
			return addr + " " + value + " " + (next == null ? "-1" : next.addr);
		}
	}
}
