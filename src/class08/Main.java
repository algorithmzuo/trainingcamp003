package class08;

// 本题是本节MoneyWays问题的在线测试
// 牛客测试链接 : https://www.nowcoder.com/questionTerminal/93bcd2190da34099b98dfc9a9fb77984
// 提交以下的代码
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N1 = sc.nextInt();
		int N2 = sc.nextInt();
		int M = sc.nextInt();
		int[] arr1 = new int[N1];
		int[] arr2 = new int[N2];
		for (int i = 0; i < N1; i++) {
			arr1[i] = sc.nextInt();
		}
		for (int i = 0; i < N2; i++) {
			arr2[i] = sc.nextInt();
		}
		System.out.println(moneyWays(arr1, arr2, M));
		sc.close();
	}

	public static final long mod = 1000000007;

	public static int moneyWays(int[] arbitrary, int[] onlyone, int money) {
		long[][] dparb = getDpArb(arbitrary, money);
		long[][] dpone = getDpOne(onlyone, money);
		long res = 0;
		for (int i = 0; i <= money; i++) {
			res = (res + dparb[dparb.length - 1][i] * dpone[dpone.length - 1][money - i]) % mod;
		}
		return (int) res;
	}

	public static long[][] getDpArb(int[] arr, int money) {
		long[][] dp = new long[arr.length][money + 1];
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		for (int j = 1; arr[0] * j <= money; j++) {
			dp[0][arr[0] * j] = 1;
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= money; j++) {
				dp[i][j] = (dp[i - 1][j] + (j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0)) % mod;
			}
		}
		return dp;
	}

	public static long[][] getDpOne(int[] arr, int money) {
		long[][] dp = new long[arr.length][money + 1];
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		if (arr[0] <= money) {
			dp[0][arr[0]] = 1;
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= money; j++) {
				dp[i][j] = (dp[i - 1][j] + (j - arr[i] >= 0 ? dp[i - 1][j - arr[i]] : 0)) % mod;
			}
		}
		return dp;
	}

}