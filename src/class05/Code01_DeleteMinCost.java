package class05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Code01_DeleteMinCost {

	// 题目：
	// 给定两个字符串s1和s2，问s2最少删除多少字符可以成为s1的子串？
	// 比如 s1 = "abcde"，s2 = "axbc"
	// 返回 1

	// 解法一，来自群里的解法：
	// 求出str2所有的子序列，然后按照长度排序，长度大的排在前面。
	// 然后考察哪个子序列字符串和s1的某个子串相等(KMP)，答案就出来了。
	// 分析：
	// 因为题目原本的样本数据中，有特别说明s2的长度很小。所以这么做也没有太大问题，也几乎不会超时。
	// 但是如果某一次考试给定的s2长度远大于s1，这么做就不合适了。
	public static int minCost1(String s1, String s2) {
		List<String> s2Subs = new ArrayList<>();
		process(s2.toCharArray(), 0, "", s2Subs);
		s2Subs.sort(new LenComp());
		for (String str : s2Subs) {
			if (s1.indexOf(str) != -1) { // indexOf底层和KMP算法代价几乎一样，也可以用KMP代替
				return s2.length() - str.length();
			}
		}
		return s2.length();
	}

	public static void process(char[] str2, int index, String path, List<String> list) {
		if (index == str2.length) {
			list.add(path);
			return;
		}
		process(str2, index + 1, path, list);
		process(str2, index + 1, path + str2[index], list);
	}

	public static class LenComp implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return o2.length() - o1.length();
		}

	}

	// 解法二
	// 我的方法，看的时间比较短，希望同学们积极反馈
	// 生成所有s1的子串
	// 然后考察每个子串和s2的编辑距离(假设编辑距离只有删除动作且删除一个字符的代价为1)
	// 如果s1的长度较小，s2长度较大，这个方法比较合适
	public static int minCost2(String s1, String s2) {
		int ans = Integer.MAX_VALUE;
		char[] str2 = s2.toCharArray();
		for (int start = 0; start < s1.length(); start++) {
			for (int end = start + 1; end <= s1.length(); end++) {
				ans = Math.min(ans, distance(str2, s1.substring(start, end).toCharArray()));
			}
		}
		return ans;
	}

	// 求str2到s1sub的编辑距离
	// 假设编辑距离只有删除动作且删除一个字符的代价为1
	public static int distance(char[] str2, char[] s1sub) {
		int row = str2.length;
		int col = s1sub.length;
		int[][] dp = new int[row][col];
		// dp[i][j]的含义：
		// str2[0..i]仅通过删除行为变成s1sub[0..j]的最小代价
		// 可能性一：
		// str2[0..i]变的过程中，不保留最后一个字符(str2[i])，
		// 那么就是通过str2[0..i-1]变成s1sub[0..j]之后，再最后删掉str2[i]即可 -> dp[i][j] = dp[i-1][j] + 1
		// 可能性二：
		// str2[0..i]变的过程中，想保留最后一个字符(str2[i])，然后变成s1sub[0..j]，
		// 这要求str2[i] == s1sub[j]才有这种可能, 然后str2[0..i-1]变成s1sub[0..j-1]即可
		// 也就是str2[i] == s1sub[j] 的条件下，dp[i][j] = dp[i-1][j-1]
		dp[0][0] = str2[0] == s1sub[0] ? 0 : Integer.MAX_VALUE;
		for (int j = 1; j < col; j++) {
			dp[0][j] = Integer.MAX_VALUE;
		}
		for (int i = 1; i < row; i++) {
			dp[i][0] = (dp[i - 1][0] != Integer.MAX_VALUE || str2[i] == s1sub[0]) ? i : Integer.MAX_VALUE;
		}
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				dp[i][j] = Integer.MAX_VALUE;
				if (dp[i - 1][j] != Integer.MAX_VALUE) {
					dp[i][j] = dp[i - 1][j] + 1;
				}
				if (str2[i] == s1sub[j] && dp[i - 1][j - 1] != Integer.MAX_VALUE) {
					dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
				}
			}
		}
		return dp[row - 1][col - 1];
	}

	// 以下的代码仅为了测试使用
	// 先生成一个随机字符串，比如 123abcd456
	// 再生根据这个字符串的随机某部分比如abcd，随机添加字符之后生成abckd
	// 生成的123abcd456和abckd都返回，这个方法就是本题的随机样本发生器
	public static String[] generateTwoStrings() {
		int len = (int) (Math.random() * 20) + 5;
		int adds = (int) (Math.random() * 5);
		char[] chas = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z' };
		int N = chas.length;
		char[] str1 = new char[len];
		for (int i = 0; i < str1.length; i++) {
			str1[i] = chas[(int) (Math.random() * N)];
		}
		int a = (int) (Math.random() * (str1.length));
		int b = (int) (Math.random() * (str1.length));
		int left = Math.min(a, b);
		int right = Math.max(a, b) + 1;
		char[] part = String.valueOf(str1).substring(left, right).toCharArray();
		char[] str2 = new char[part.length + adds];
		int count = 0;
		while (count != adds) {
			int i = (int) (Math.random() * str2.length);
			if (str2[i] == 0) {
				str2[i] = chas[(int) (Math.random() * N)];
				count++;
			}
		}
		int index = 0;
		for (int i = 0; i < str2.length; i++) {
			if (str2[i] == 0) {
				str2[i] = part[index++];
			}
		}
		return new String[] { String.valueOf(str1), String.valueOf(str2) };
	}

	public static void main(String[] args) {
		int testTime = 100;
		boolean pass = true;
		for (int i = 0; i < testTime; i++) {
			String[] test = generateTwoStrings();
			if (minCost1(test[0], test[1]) != minCost2(test[0], test[1])) {
				pass = false;
				System.out.println(test[0]);
				System.out.println(test[1]);
				System.out.println(minCost1(test[0], test[1]));
				System.out.println(minCost2(test[0], test[1]));
				break;
			}
		}
		System.out.println("test pass : " + pass);
	}

}
