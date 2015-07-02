package main.java.ttt.auxil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DoubleRound {
	
	/**
	 * Rounds a double (up and down) to a number of places
	 * @param value a double value to round
	 * @param places the number of places to round to
	 * @return a rounded double
	 */
	public static final double doubleRound(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
