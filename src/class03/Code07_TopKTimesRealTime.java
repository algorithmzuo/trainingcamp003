package class03;

import java.util.HashMap;

public class Code07_TopKTimesRealTime {

	public static class Node {
		public String str;
		public int times;

		public Node(String s, int t) {
			str = s;
			times = t;
		}
	}

	public static class TopKRecord {
		private Node[] heap;
		private int heapSize;
		// string -> Node(times)
		private HashMap<String, Node> strNodeMap;
		private HashMap<Node, Integer> nodeIndexMap;

		public TopKRecord(int K) {
			heap = new Node[K];
			heapSize = 0;
			strNodeMap = new HashMap<String, Node>();
			nodeIndexMap = new HashMap<Node, Integer>();
		}

		// str用户现在给我的
		public void add(String str) {
			Node curNode = null;
			int preIndex = -1; // str之前在堆上的位置
			// 查词频表，看看有没有关于这个str的记录
			if (!strNodeMap.containsKey(str)) { // str之前没进来过
				curNode = new Node(str, 1);
				strNodeMap.put(str, curNode);
				nodeIndexMap.put(curNode, -1);
			} else { // str之前进来过
				curNode = strNodeMap.get(str);
				curNode.times++;
				preIndex = nodeIndexMap.get(curNode);
			}

			// 词频表修改完毕，
			if (preIndex == -1) { // 不在堆上
				if (heapSize == heap.length) { // 堆满了
					if (heap[0].times < curNode.times) {
						nodeIndexMap.put(heap[0], -1);
						nodeIndexMap.put(curNode, 0);
						heap[0] = curNode;
						heapify(0, heapSize);
					}
				} else {// 堆没有满
					nodeIndexMap.put(curNode, heapSize);
					heap[heapSize] = curNode;
					heapInsert(heapSize++);
				}
			} else { // str已经在堆上了
				heapify(preIndex, heapSize);
			}
		}

		public void printTopK() {
			System.out.println("TOP: ");
			for (int i = 0; i != heap.length; i++) {
				if (heap[i] == null) {
					break;
				}
				System.out.print("Str: " + heap[i].str);
				System.out.println(" Times: " + heap[i].times);
			}
		}

		private void heapInsert(int index) {
			while (index != 0) {
				int parent = (index - 1) / 2;
				if (heap[index].times < heap[parent].times) {
					swap(parent, index);
					index = parent;
				} else {
					break;
				}
			}
		}

		private void heapify(int index, int heapSize) {
			int l = index * 2 + 1;
			int r = index * 2 + 2;
			int smallest = index;
			while (l < heapSize) {
				if (heap[l].times < heap[index].times) {
					smallest = l;
				}
				if (r < heapSize && heap[r].times < heap[smallest].times) {
					smallest = r;
				}
				if (smallest != index) {
					swap(smallest, index);
				} else {
					break;
				}
				index = smallest;
				l = index * 2 + 1;
				r = index * 2 + 2;
			}
		}

		private void swap(int index1, int index2) {
			nodeIndexMap.put(heap[index1], index2);
			nodeIndexMap.put(heap[index2], index1);
			Node tmp = heap[index1];
			heap[index1] = heap[index2];
			heap[index2] = tmp;
		}

	}

	public static String[] generateRandomArray(int len, int max) {
		String[] res = new String[len];
		for (int i = 0; i != len; i++) {
			res[i] = String.valueOf((int) (Math.random() * (max + 1)));
		}
		return res;
	}

	public static void printArray(String[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		TopKRecord record = new TopKRecord(2);
		record.add("zuo");
		record.printTopK();
		record.add("cheng");
		record.add("cheng");
		record.printTopK();
		record.add("Yun");
		record.add("Yun");
		record.printTopK();

	}
}