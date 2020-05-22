package class01;

import java.util.HashMap;
import java.util.Stack;

public class Code07_MaxSumInTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int val) {
			value = val;
		}
	}

	public static int maxSum = Integer.MIN_VALUE;

	public static int maxPath(Node head) {
		p(head, 0);
		return maxSum;
	}

	public static void p(Node x, int pre) {
		if (x.left == null && x.right == null) {
			maxSum = Math.max(maxSum, pre + x.value);
		}
		if (x.left != null) {
			p(x.left, pre + x.value);
		}
		if (x.right != null) {
			p(x.right, pre + x.value);
		}
	}

	public static int maxDis(Node head) {
		if (head == null) {
			return 0;
		}
		return process2(head);
	}

	// x为头的整棵树上，最大路径和是多少，返回。
	// 路径要求，一定从x出发，到叶节点，算做一个路径
	public static int process2(Node x) {
		if (x.left == null && x.right == null) {
			return x.value;
		}
		int next = Integer.MIN_VALUE;
		if (x.left != null) {
			next = process2(x.left);
		}
		if (x.right != null) {
			next = Math.max(next, process2(x.right));
		}
		return x.value + next;
	}

	public static class Inf {
		public int headWalkToLeafMaxPathSum;

		public Inf(int sum) {
			headWalkToLeafMaxPathSum = sum;
		}
	}

	public static int maxPathSum2(Node head) {
		return p(head).headWalkToLeafMaxPathSum;
	}

	public static Inf p(Node x) {
		if (x == null) {
			return null;
		}
		Inf leftInfo = p(x.left);
		Inf rightInfo = p(x.right);
		int p1 = 0;
		int p2 = 0;
		int p3 = x.value;
		if (leftInfo != null) { // 左边有树;
			p1 = x.value + leftInfo.headWalkToLeafMaxPathSum;
		}
		if (rightInfo != null) {
			p2 = x.value + rightInfo.headWalkToLeafMaxPathSum;
		}
		if (x.left == null && x.right == null) {
			return new Inf(p3);
		}
		if (x.left != null && x.right != null) {
			return new Inf(Math.max(p1, p2));
		}
		// 一定是一个为空，另一个不是
		return new Inf(x.left != null ? p1 : p2);
	}

	public static class Info {
		public int maxBSTSize;
		public boolean isAllBST;
		public int min;
		public int max;

		public Info(int o1, boolean o2, int o3, int o4) {
			maxBSTSize = o1;
			isAllBST = o2;
			min = o3;
			max = o4;
		}
	}

	public static int getMaxBSTSize(Node head) {
		return func(head).maxBSTSize;
	}

	public static Info func(Node x) {
		if (x == null) {
			return new Info(0, true, Integer.MAX_VALUE, Integer.MIN_VALUE);
		}
		Info leftInfo = func(x.left);
		Info rightInfo = func(x.right);
		int min = Math.min(x.value, Math.min(leftInfo.min, rightInfo.min));
		int max = Math.max(x.value, Math.max(leftInfo.max, rightInfo.max));
		boolean isAllBST = false;
		int maxBSTSize = Math.max(leftInfo.maxBSTSize, rightInfo.maxBSTSize);
		if (leftInfo.isAllBST && rightInfo.isAllBST && leftInfo.max < x.value && rightInfo.min > x.value) {
			isAllBST = true;
			maxBSTSize = leftInfo.maxBSTSize + rightInfo.maxBSTSize + 1;
		}
		return new Info(maxBSTSize, isAllBST, min, max);
	}

	public static int maxSumRecursive(Node head) {
		return process(head, 0);
	}

	// 当前的子树，是以x为头的，
	// pre : 从头节点一路来到x这条路径上，除去x本身的值的累加和
	// 返回 : 从头，到，x这棵子树的每一个叶节点中的，最大路径和
	public static int process(Node x, int pre) {
		if (x == null) {
			return Integer.MIN_VALUE;
		}
		if (x.left == null && x.right == null) { // 叶节点
			return pre + x.value;
		}
		// x不是空节点，x也不是叶节点
		int leftMax = process(x.left, pre + x.value);
		int rightMax = process(x.right, pre + x.value);
		return Math.max(leftMax, rightMax);
	}

	public static int maxSumUnrecursive(Node head) {
		int max = 0;
		// key 某一个节点
		// value 从头一直来到key节点的路径累加和，包含key节点的值的
		HashMap<Node, Integer> sumsMap = new HashMap<>();
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.add(head);
			sumsMap.put(head, head.value);
			while (!stack.isEmpty()) {
				head = stack.pop();
				if (head.left == null && head.right == null) {
					max = Math.max(max, sumsMap.get(head));
				}
				if (head.right != null) {
					sumsMap.put(head.right, sumsMap.get(head) + head.right.value);
					stack.push(head.right);
				}
				if (head.left != null) {
					sumsMap.put(head.left, sumsMap.get(head) + head.left.value);
					stack.push(head.left);
				}
			}
		}
		return max;
	}

	public static int maxPathSum(Node head) {
		ReturnData allData = process(head);
		return allData.max < 0 ? allData.max : allData.maxPathSumAll;
	}

	public static class ReturnData {
		public int maxPathSumAll; // 整棵树的最大路径和
		public int maxPathSumFromHead; // 整棵树必须从头节点出发的最大路径和
		public int max; // 整棵树的最大值

		public ReturnData(int all, int fromHead, int ma) {
			maxPathSumAll = all;
			maxPathSumFromHead = fromHead;
			max = ma;
		}

	}

	public static ReturnData process(Node x) {
		if (x == null) {
			return new ReturnData(0, 0, Integer.MIN_VALUE);
		}
		ReturnData leftData = process(x.left);
		ReturnData rightData = process(x.right);
		int maxPathSumFromHead = Math.max(x.value,
				Math.max(x.value + leftData.maxPathSumFromHead, x.value + rightData.maxPathSumFromHead));

		int maxPathSumAll = Math.max(leftData.maxPathSumAll, rightData.maxPathSumAll);
		maxPathSumAll = Math.max(maxPathSumAll, maxPathSumFromHead);

		int max = Math.max(x.value, Math.max(leftData.max, rightData.max));
		// ...
		return new ReturnData(maxPathSumAll, maxPathSumFromHead, max);

	}

	public static void main(String[] args) {
		Node head = new Node(4);
		head.left = new Node(1);
		head.left.right = new Node(5);
		head.right = new Node(-7);
		head.right.left = new Node(3);
		System.out.println(maxSumRecursive(head));
		System.out.println(maxSumUnrecursive(head));

	}

}
