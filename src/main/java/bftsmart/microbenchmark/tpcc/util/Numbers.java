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

    public static boolean gt(Number number1, Number number2) {
        if (number1 == null || number2 == null) {
            return false;
        }
        return number1.doubleValue() > number2.doubleValue();
    }

}
