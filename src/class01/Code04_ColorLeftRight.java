package class01;

public class Code04_ColorLeftRight {

	// RGRGR -> RRRGG
	public static int minPaint(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] chs = s.toCharArray();
		int[] right = new int[chs.length];
		right[chs.length - 1] = chs[chs.length - 1] == 'R' ? 1 : 0;
		for (int i = chs.length - 2; i >= 0; i--) {
			right[i] = right[i + 1] + (chs[i] == 'R' ? 1 : 0);
		}
		int res = right[0];
		int left = 0;
		for (int i = 0; i < chs.length - 1; i++) {
			left += chs[i] == 'G' ? 1 : 0;
			res = Math.min(res, left + right[i + 1]);
		}
		res = Math.min(res, left + (chs[chs.length - 1] == 'G' ? 1 : 0));
		return res;
	}

	public static void main(String[] args) {
		String test = "GGGGGR";
		System.out.println(minPaint(test));

	}

}
