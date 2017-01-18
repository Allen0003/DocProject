package test;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.Test;

public class TestLog {
	private static final Logger LOGGER = Logger.getLogger(TestLog.class.getName());

	@Test
	public void log() {

		try {
			FileHandler fh = new FileHandler("C:/var/Doc/MyLogFile.log");
			LOGGER.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			// the following statement is used to log any messages

			LOGGER.info(" QQQQQ ");
		} catch (Exception e) {

		}
	}

}
