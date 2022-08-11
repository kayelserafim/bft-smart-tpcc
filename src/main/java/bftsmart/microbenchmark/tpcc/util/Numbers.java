package bftsmart.microbenchmark.tpcc.util;

public class Numbers {

    private Numbers() {
        super();
    }

    public static int roundEven(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Negative number");
        }
        return number % 2 == 0 ? number : number + 1;
    }

    /**
     * Returns a Long whose value is (this / divisor)
     * 
     * @param number
     *            to be divided.
     * @param divisor
     *            value by which this Long is to be divided.
     * @return number / divisor
     */
    public static double divide(Number number, Number divisor) {
        if (number == null || divisor == null) {
            return 0D;
        }
        return number.doubleValue() / divisor.doubleValue();
    }

    /**
     * Returns the value of the specified number as an int.
     * 
     * @param number
     *            to be converted.
     * @return the numeric value represented by this object after conversion to
     *         type int.
     */
    public static int toInt(Number number) {
        if (number == null) {
            return 0;
        }
        return number.intValue();
    }

    /**
     * Returns the value of the specified number as an int.
     * 
     * @param number
     *            to be converted.
     * @return the numeric value represented by this object after conversion to
     *         type int.
     */
    public static long toLong(Number number) {
        if (number == null) {
            return 0l;
        }
        return number.longValue();
    }

    /**
     * Returns the value of the specified number as a double
     * 
     * @param number
     *            to be converted.
     * @return the numeric value represented by this object after conversion to
     *         type double.
     */
    public static double toDouble(Number number) {
        if (number == null) {
            return 0d;
        }
        return number.doubleValue();
    }

    /**
     * checks that the specified number is not zero or not null.
     * 
     * @param number
     *            to be checked
     * @return false if the number is null or zero, true otherwise
     */
    public static boolean isGreaterThanZero(Number number) {
        return number != null && number.longValue() > 0;
    }

}
