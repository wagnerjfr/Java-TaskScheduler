package task;

import factory.Task;

public class BitcoinEvaluate extends Task {

	@Override
	public String executeTask() throws Exception {
		String opinion = "it's not possible to say..";
		Double price = 0d;
		
		if (hasFatherResponse() && isFatherResponseType(Double.class)) {
			
			price = (Double)father.getResponse();
			
			if (price >= 1000.0) {
				opinion = "SELL!";
			} else if (price >= 500 && price < 1000) {
				opinion = "WAIT!";
			} else {
				opinion = "BUY!";
			}
		}
		
		return String.valueOf(price) + " is better " + opinion;
	}

}
