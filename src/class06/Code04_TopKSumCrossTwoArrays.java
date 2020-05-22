package class06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Code04_TopKSumCrossTwoArrays {

	// 放入大根堆中的结构
	public static class Node {
		public int index1;// arr1中的位置
		public int index2;// arr2中的位置
		public int sum;// arr1[index1] + arr2[index2]的值

		public Node(int i1, int i2, int s) {
			index1 = i1;
			index2 = i2;
			sum = s;
		}
	}

	// 生成大根堆的比较器
	public static class MaxHeapComp implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o2.sum - o1.sum;
		}
	}

	public static int[] topKSum(int[] arr1, int[] arr2, int topK) {
		if (arr1 == null || arr2 == null || topK < 1) {
			return null;
		}
		// arr1 50  arr2 20   1000   topk 100万
		topK = Math.min(topK, arr1.length * arr2.length);

		int[] res = new int[topK];
		int resIndex = 0;

		PriorityQueue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComp());

		// set[i][j] == false, arr1[i]  arr2[j] 之前，没进过
		// set[i][j] == true;  arr1[i]  arr2[j] 之前，进过
		boolean[][] set = new boolean[arr1.length][arr2.length];
		int i1 = arr1.length - 1;
		int i2 = arr2.length - 1;
		maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2]));
		set[i1][i2] = true;
		while (resIndex != topK) {
			Node curNode = maxHeap.poll();
			res[resIndex++] = curNode.sum;
			i1 = curNode.index1;
			i2 = curNode.index2;
			if (i1 - 1 >= 0 && !set[i1 - 1][i2]) {
				set[i1 - 1][i2] = true;
				maxHeap.add(new Node(i1 - 1, i2, arr1[i1 - 1] + arr2[i2]));
			}
			if (i2 - 1 >= 0 && !set[i1][i2 - 1]) {
				set[i1][i2 - 1] = true;
				maxHeap.add(new Node(i1, i2 - 1, arr1[i1] + arr2[i2 - 1]));
			}
		}
		return res;
	}

	// For test, this method is inefficient but absolutely right
	public static int[] topKSumTest(int[] arr1, int[] arr2, int topK) {
		int[] all = new int[arr1.length * arr2.length];
		int index = 0;
		for (int i = 0; i != arr1.length; i++) {
			for (int j = 0; j != arr2.length; j++) {
				all[index++] = arr1[i] + arr2[j];
			}
		}
		Arrays.sort(all);
		int[] res = new int[Math.min(topK, all.length)];
		index = all.length - 1;
		for (int i = 0; i != res.length; i++) {
			res[i] = all[index--];
		}
		return res;
	}

	public static int[] generateRandomSortArray(int len) {
		int[] res = new int[len];
		for (int i = 0; i != res.length; i++) {
			res[i] = (int) (Math.random() * 50000) + 1;
		}
		Arrays.sort(res);
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static boolean isEqual(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i != arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int a1Len = 5000;
		int a2Len = 4000;
		int k = 20000000;
		int[] arr1 = generateRandomSortArray(a1Len);
		int[] arr2 = generateRandomSortArray(a2Len);
		long start = System.currentTimeMillis();
		int[] res = topKSum(arr1, arr2, k);
		long end = System.currentTimeMillis();
		System.out.println(end - start + " ms");

		start = System.currentTimeMillis();
		int[] absolutelyRight = topKSumTest(arr1, arr2, k);
		end = System.currentTimeMillis();
		System.out.println(end - start + " ms");

		System.out.println(isEqual(res, absolutelyRight));

	}

}
