import java.util.Arrays;

public class Utilities<T> {

	public boolean arrayContains(T[] array, T object) {
		Arrays.sort(array);
		if (Arrays.binarySearch(array, object) >= 0)
			return true;
		return false;
	}
	
	public Integer arrayIndexOf(T[] array, T object) {
		for (int i = 0; i < array.length; ++i) {
			if (array[i].equals(object))
				return i;
		}
		return -1;
	}

}
