package system;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Diagnostics {
	public static class Console{

		private static final SimpleDateFormat sdf = new SimpleDateFormat("[h:mm:ss a]");
		
		public static void printLn(PrintStream stream, String message){
			Date date = new Date();
			String timestamp = sdf.format(date);
			stream.println(timestamp + " "+ message);
		}
	}
}
