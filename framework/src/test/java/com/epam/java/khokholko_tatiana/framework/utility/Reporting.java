package com.epam.java.khokholko_tatiana.framework.utility;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;

/**
 * Class has been reports
 * 
 * @author Tatiana Khokholko
 */
public class Reporting extends AppenderSkeleton {

	@Override
	protected void append(LoggingEvent event) {
		String message = this.layout.format(event);
		// System.out.print("message");
		message = message.replaceAll("\n", "<br>"); // The LOG in one line
		                                            // prevent
		Reporter.log(message);
	}

	@Override
	public void close() {

	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}
