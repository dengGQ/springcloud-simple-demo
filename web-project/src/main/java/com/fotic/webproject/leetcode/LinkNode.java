package com.fotic.webproject.leetcode;

/**
 * @ClassName: LinkNode
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2019年7月22日
 */
public class LinkNode {
	
	private int val;
	private LinkNode next;
	
	public LinkNode(int val) {
		this.val = val;
	}
	
	//列表中的数字逆序存储
	private static LinkNode add(LinkNode l1, LinkNode l2) {
		LinkNode temp = new LinkNode(0);
		
		LinkNode q = l1, p = l2, curr = temp;
		int carry = 0;
		while (q != null || p != null) {
			
			int x = q != null ? q.val : 0;
			int y = p != null ? p.val : 0;
			
			int sum = carry + x + y;
			
			carry = sum/10;
			int currVal = sum%10;
			
			curr.next = new LinkNode(currVal);
			curr = curr.next;
			
			if(q != null) q = q.next;
			if(p != null) p = p.next;
		}
		
		if(carry > 0) {
			curr.next = new LinkNode(carry);
		}
		
		return temp.next;
	}
	
	//列表中的数字非逆序存储
	@SuppressWarnings("unused")
	private static LinkNode add1(LinkNode l1, LinkNode l2) {
		
		return null;
	}
	
	public static void main(String[] args) {
		LinkNode l1 = new LinkNode(8);
		l1.next = new LinkNode(1);
//		l1.next.next = new LinkNode(9);
		
		LinkNode l2 = new LinkNode(0);
//		l2.next = new LinkNode(1);
//		l2.next.next = new LinkNode(1);
		
		System.out.println(add(l1, l2));
		
		System.out.println(19%10);
	}
}
