package game;			

import java.util.ArrayList;

public class testing {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> test = new ArrayList<Integer>();
		test.add(1);
		test.add(2);
		test.add(3);

		
		BiasRandomGenerator b = new BiasRandomGenerator();
		for(int i =0; i < 24; i++) {
			int index = b.biasRandom(test, 80, 2);
			System.out.println(index);
		}		
	}
		
}
