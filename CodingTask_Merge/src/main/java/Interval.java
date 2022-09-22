import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * An object representing Intervals such as [0,10].
 *
 * @author Karolin Dürr
 */
public class Interval implements Comparable<Interval> {

    private final static Logger LOGGER = Logger.getLogger(Interval.class.getName());

    private double start;

    private double end;

    /**
     * Creates an {@code Interval} object with the given {@code start} and {@code end} values. These values represent
     * the included boundaries of the Interval. If the given start and end values are reversed, the value of the
     * {@code end} parameter becomes the actual {@code start} value and vice versa. If start and end value are equal,
     * the Interval represents a single real number.
     *
     * @param start The value of the included starting boundary of the interval.
     * @param end   The value of the included end boundary of the interval.
     */
    public Interval(final double start, final double end) {
        this.start = Math.min(start, end);
        this.end = Math.max(end, start);
    }

    /**
     * Sets the start boundary of the interval, if the new value is smaller than or equal to the current
     * {@link Interval#getEnd()}
     * value.
     *
     * @param newStart The new included starting boundary of the interval.
     */
    public void setStart(final double newStart) {
        if (Double.compare(newStart, this.getEnd()) <= 0) {
            this.start = newStart;
            return;
        }
        LOGGER.info("New start boundary was invalid since it was >= the current end boundary of the interval.");
    }

    /**
     * Sets the end boundary of the interval, if the new value is bigger than or equal to the current
     * {@link Interval#getStart()}
     * value.
     *
     * @param newEnd The new included end boundary of the interval.
     */
    public void setEnd(final double newEnd) {
        if (Double.compare(newEnd, this.getStart()) >= 0) {
            this.end = newEnd;
            return;
        }
        LOGGER.info("New end boundary was invalid since it was <= the current start boundary of the interval.");
    }

    /**
     * Returns the included start value of the interval, e.g. for the interval [0,10] the getter returns 0.
     *
     * @return the interval's included start value
     */
    public double getStart() {
        return this.start;
    }

    /**
     * Returns the included end value of the interval, e.g. for the interval [0,10] the getter returns 10.
     *
     * @return the interval's included end value
     */
    public double getEnd() {
        return this.end;
    }

    /**
     * Returns the current interval as a {@link String} using the format [start,end]. Trailing zeros will be removed and
     * floating numbers are limited to six decimal places.
     *
     * @return current interval
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.######", new DecimalFormatSymbols(Locale.ENGLISH));
        return "[" + decimalFormat.format(this.getStart()) + "," + decimalFormat.format(this.getEnd()) + "]";
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Interval interval = (Interval) object;
        return Double.compare(start, interval.getStart()) == 0 && Double.compare(end, interval.getEnd()) == 0;
    }

    /**
     * Compares whether the current interval is smaller, equal or greater to the compared one. First, the start values
     * are compared. If the start value is smaller compared to the start value of the compared interval then {@code
     * -1} is
     * returned, and if the value is bigger {@code 1}. Whereas, when the start values are equal, then the end values are
     * compared. If the end value is smaller, {@code -1} is returned, if it is the same {@code 0} and otherwise
     * {@code 1}.
     *
     * @param intervalToCompare The {@link Interval} to which the current interval is being compared
     * @return -1 if the compared interval is bigger, 0 if they are equal, and 1 if the compare one is smaller
     */
    @Override
    public int compareTo(final Interval intervalToCompare) {
        if (intervalToCompare == null) {
            return 1;
        }

        int comparedStartBoundaries = Double.compare(this.getStart(), intervalToCompare.getStart());
        if (comparedStartBoundaries < 0) {
            return -1;
        } else if (comparedStartBoundaries == 0) {
            int comparedEndBoundaries = Double.compare(this.getEnd(), intervalToCompare.getEnd());
            if (comparedEndBoundaries == 0) {
                return 0;
            } else if (comparedEndBoundaries < 0) {
                return -1;
            }
            return 1;
        }
        return 1;
    }
}
