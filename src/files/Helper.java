package files;

public class Helper {
	public static void printMatrix(int[][] m) {
		String output = "";
		int dX = m[0].length;
		int dY = m.length;
		int longestOut[] = new int[dY];
		for (int i = 0; i < dX; i++) {
			for (int j = 0; j < dY; j++) {
				if (String.valueOf(m[j][i]).length() > longestOut[j]) {
					longestOut[j] = String.valueOf(m[j][i]).length();
				}
			}
		}
		for (int i = 0; i < dX; i++) {
			for (int j = 0; j < dY; j++) {
				String val = m[j][i] + "";
				output += "[";
				for (int k = 0; k < longestOut[j] - String.valueOf(val).length(); k++) {
					output += " ";
				}
				output += val + "]";
			}
			output += "\n";
		}
		System.out.println(output);
	}
}
