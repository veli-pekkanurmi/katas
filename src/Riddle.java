import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Riddle {

	static Random rand = new Random();

	public static void main(String[] args) {

		int rightCount = 0;
		int count = 0;

		for (int i = 1000; count < i; count++) {
			rightCount += round();
		}

		System.out.println("Oikeita veikkauksia: " + rightCount + "/" + count);

	}

	static public int round() {

		int car = rand.nextInt(3);
		int selection = rand.nextInt(3);

		if (car == selection)
			return 0;

		return 1;
	}

}