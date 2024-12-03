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
	public static Map<String, Temp.Node> nodeMap = new HashMap();

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String[] params = scanner.nextLine().split(" ");
		String head1Addr = params[0];
		String head2Addr = params[1];
		int size = Integer.parseInt(params[2]);

		//构造节点
		for(int i=1;i<=size;i++)
		{
			String[] nodeInfo = scanner.nextLine().split(" ");
			buildNode(nodeInfo[0], Integer.parseInt(nodeInfo[1]), nodeInfo[2]);
		}

		//获取列表大小和tail地址
		String[] link1Info = getLinkInfo(head1Addr);
		String[] link2Info = getLinkInfo(head2Addr);

		//合并
		String mergeHeadAddr;
		if(Integer.parseInt(link1Info[0]) > Integer.parseInt(link2Info[0]))
		{
			merge(nodeMap.get(head1Addr),nodeMap.get(link2Info[1]),2);
			mergeHeadAddr = head1Addr;
		}
		else
		{
			merge(nodeMap.get(head2Addr),nodeMap.get(link1Info[1]),2);
			mergeHeadAddr = head2Addr;
		}

		Temp.Node mergeIndex = nodeMap.get(mergeHeadAddr);
		while(mergeIndex != null)
		{
			System.out.println(mergeIndex.toString());
			mergeIndex = mergeIndex.next;
		}
	}

	public static void merge(Temp.Node bigHead, Temp.Node smallTail, int step)
	{
		int index = 1;
		while(smallTail != null)
		{

			if(index%step == 0)
			{
				//插入small节点
				Temp.Node bigNextTemp = bigHead.next;
				bigHead.next = smallTail;
				smallTail.next = bigNextTemp;
				//左移small节点
				smallTail = smallTail.pre;
				bigHead = bigNextTemp;
				index = 1;
			}
			else
			{
				bigHead = bigHead.next;
				index++;
			}
		}

	}

	public static String[] getLinkInfo(String headAddr)
	{
		Temp.Node index = nodeMap.get(headAddr);
		int size = 1;
		while(index.next != null && index.next.value != 0)
		{
			index = index.next;
			size ++;
		}
		return new String[]{size+"", index.addr};
	}

	public static Temp.Node buildNode(String addr,int value, String nextAddr)
	{
		Temp.Node node = nodeMap.get(addr);
		if(node == null)
		{
			node = new Temp.Node(addr, value, null, null);
			nodeMap.put(addr, node);
		}
		if(node.value == 0)
			node.value = value;

		//补全前后节点
		Temp.Node next = nodeMap.get(nextAddr);
		if(next == null && !"-1".equals(nextAddr))
		{
			next = new Temp.Node(nextAddr, 0, null, null);
			nodeMap.put(nextAddr, next);
		}
		node.next = next;
		if (next != null)
			next.pre = node;
		//System.out.println("【Test】"+ node.toString());
		return node;
	}

	static class  Node
	{
		String addr;
		int value;
		Temp.Node pre;
		Temp.Node next;

		public Node(String addr, int value, Temp.Node pre, Temp.Node next)
		{
			this.addr = addr;
			this.value = value;
			this.pre = pre;
			this.next = next;
		}

		@Override
		public String toString()
		{
			return addr + " " + value + " " + (next == null ? -1 : next.addr);
		}
	}
}
