package class01;

public class Code02_NeedParentheses {

	public static int needParentheses(String str) {
		int t = 0;
		int needSolveRight = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				t++;
			} else { // 遇到的是')'
				if (t == 0) {
					needSolveRight++;
				} else {
					t--;
				}
			}
		}
		return t + needSolveRight;
	}

	public static void main(String args[]) {

	}

}
