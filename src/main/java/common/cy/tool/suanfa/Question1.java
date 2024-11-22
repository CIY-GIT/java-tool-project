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
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		String[] strArr = str.split(" ");
		String link1Index = strArr[0];
		String link2Index = strArr[1];

		//<addr, Node> 根据输入构造 node和Map，方面后面构造链表
		Map<String, Node> nodeMap = new HashMap<>();
		int total = Integer.parseInt(strArr[2]);
		for (int i = 0; i < total; i++)
		{
			String[] strArr1 = scanner.nextLine().split(" ");
			String address = strArr1[0];
			int data = Integer.parseInt(strArr1[1]);
			String nextAddress = strArr1[2];

			if (nodeMap.containsKey(address))
			{
				Node node = nodeMap.get(address);
				node.setData(data);
				node.setNext(getNode(nextAddress, nodeMap));
				nodeMap.put(address, node);
			}
			else
			{
				Node node = new Node(address, data, null, getNode(nextAddress, nodeMap));
				nodeMap.put(address, node);
			}
		}
		scanner.close();

		//构造链表，返回 链表长度和尾节点地址（用于反转）
		String[] link1Info = buildLinkList(link1Index, nodeMap, false);
		String[] link2Info = buildLinkList(link2Index, nodeMap, false);

		//merge 合并
		if (Integer.parseInt(link2Info[0]) > Integer.parseInt(link1Info[0]))
		{
			merge(nodeMap.get(link2Index), nodeMap.get(link1Info[1]), 2);
			buildLinkList(link2Index, nodeMap, true);
		}
		else
		{
			merge(nodeMap.get(link1Index), nodeMap.get(link2Info[1]), 2);
			buildLinkList(link1Index, nodeMap, true);
		}

	}

	private static Node getNode(String address, Map<String, Node> nodeMap)
	{
		if (!nodeMap.containsKey(address) && !"-1".equals(address))
		{
			nodeMap.put(address, new Node(address));
		}
		return nodeMap.get(address);
	}

	public static String[] buildLinkList(String indexAddress, Map<String, Node> nodeMap, boolean isPrint)
	{
		Node index = nodeMap.get(indexAddress);
		int size = 0;
		String tailAddress = null;
		while (index != null)
		{
			size ++;
			if (isPrint)
				System.out.println(index.toString());
			Node temp = index;
			index = index.getNext();
			if (index != null)
			{
				index.setPre(temp);
			}
			else
			{
				tailAddress = temp.getAddress();
			}
		}

		return new String[]{String.valueOf(size), tailAddress};
	}

	public static void merge(Node bigNodeIndex, Node smallNodeIndex, int mergeInterval)
	{
		Node bigNodeIndexTemp = bigNodeIndex;
		Node smallNodeIndexTemp = smallNodeIndex;
		int interval = 0;

		while (bigNodeIndexTemp != null)
		{
			interval ++;
			if (interval == mergeInterval)
			{
				Node temp = bigNodeIndexTemp.getNext();
				bigNodeIndexTemp.setNext(smallNodeIndexTemp);
				smallNodeIndexTemp.setNext(temp);
				bigNodeIndexTemp = temp;
				smallNodeIndexTemp = smallNodeIndexTemp.getPre();
				interval= 0;

				if (smallNodeIndexTemp == null)
				{
					break;
				}
			}
			else
			{
				bigNodeIndexTemp = bigNodeIndexTemp.getNext();
			}
		}
	}

	static class Node
	{
		String address;
		int data;
		Node pre;
		Node next;

		public Node(String address, int data, Node pre, Node next)
		{
			this.address = address;
			this.data = data;
			this.next = next;
			this.pre = pre;
		}

		public Node(String address)
		{
			this(address, 0, null, null);
		}

		public String getAddress()
		{
			return address;
		}

		public void setAddress(String address)
		{
			this.address = address;
		}

		public int getData()
		{
			return data;
		}

		public void setData(int data)
		{
			this.data = data;
		}

		public Node getPre()
		{
			return pre;
		}

		public void setPre(Node pre)
		{
			this.pre = pre;
		}

		public Node getNext()
		{
			return next;
		}

		public void setNext(Node next)
		{
			this.next = next;
		}


		@Override
		public String toString()
		{
			return address + " " + data + " " + (next != null ? next.getAddress() : "-1");
		}
	}
}
