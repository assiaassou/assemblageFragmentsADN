package alignementEtConsensus;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class AlignementMultiple {


	public static char[] voteMajorite(char[][] matrix) throws IOException {
		int cols = matrix[0].length, lines = matrix.length;
		char[] consensus = new char[cols];
		for (int i = 0; i < cols; i++) {
			Map<Character, Integer> hashChars = new HashMap<Character, Integer>();
			Set<Character> keys = hashChars.keySet();
			for (int j = 0; j < lines; j++) {
				if (matrix[j][i] != '.') {
					if (keys.contains(matrix[j][i])) {
						hashChars.put(matrix[j][i], (hashChars.get(matrix[j][i]) + 1));
					} else {
						hashChars.put(matrix[j][i], 1);
					}
				}
			}
			Integer maxWeight = Collections.max(hashChars.values());
			ArrayList<Character> result = new ArrayList<Character>();
			
			Set<Character> keys_ = hashChars.keySet();
			for (Character key : keys_) {
				if (hashChars.get(key).equals(maxWeight)) {
					result.add(key);
				}
			}

			if (result.size() == 1) {
				consensus[i] = result.get(0);
			} else {
				consensus[i] = result.get(new Random().nextInt(result.size() - 1));
				//consensus[i] = ('-');

			}			
		}
		
		return consensus;
	}


}
