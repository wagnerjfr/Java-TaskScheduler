package task;

import factory.Task;

public class BitcoinPrice extends Task {

	@Override
	public String executeTask() throws Exception {
		
		String value = "";
		
		if (hasFatherResponse() && isFatherResponseType(String.class)) {
			
			String info = (String)father.getResponse();
			
			value = info.substring(info.indexOf("\"last\""), info.indexOf(", \"times")).replace("\"", "").trim();
			
			response = Double.parseDouble(value.substring(value.indexOf(":") + 1).trim());
		}
		
		return String.valueOf(value);
	}

}
