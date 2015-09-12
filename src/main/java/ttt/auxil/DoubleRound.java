package main.java.ttt.auxil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DoubleRound {
	private static final Logger LOGGER = LoggerFactory.getLogger(DoubleRound.class);
	
	/**
	 * Rounds a double (up and down) to a number of places
	 * @param value a double value to round
	 * @param places the number of places to round to
	 * @return a rounded double
	 * @throws ParseException 
	 */
	public static final double doubleRound(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    
//	    double ans = 0;
//LOGGER.debug("1");
//	    DecimalFormat df = new DecimalFormat("#.00");
//	    String formate = df.format(value); 
//	    try {
//	    	LOGGER.debug("2");
//			ans = (double)df.parse(formate);
//			LOGGER.debug("3");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			LOGGER.debug("4");
//			e.printStackTrace();
//		}
//	    LOGGER.debug("5");
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    
//	    return bd.doubleValue();
//	    return ans;
	    return Math.round( value * 100.0 ) / 100.0;
	}
}
