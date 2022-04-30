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
    public static Double divide(Number number, Number divisor) {
        if (number == null || divisor == null) {
            return 0D;
        }
        return number.doubleValue() / divisor.doubleValue();
    }

    /**
     * Returns the value of the specified number as a double
     * 
     * @param number
     *            to be converted.
     * @return the numeric value represented by this object after conversion to
     *         type double.
     */
    public static Double toDouble(Number number) {
        if (number == null) {
            return null;
        }
        return number.doubleValue();
    }

    /**
     * Checks if the specified number is null or zero.
     * 
     * @param number
     *            to be checked
     * @return true if the number is null or zero, false otherwise
     */
    public static boolean isNullOrZero(Number number) {
        return number == null || number.doubleValue() == 0;
    }

    /**
     * checks that the specified number is not zero or not null.
     * 
     * @param number
     *            to be checked
     * @return false if the number is null or zero, true otherwise
     */
    public static boolean isNotNullOrZero(Number number) {
        return !isNullOrZero(number);
    }

}
