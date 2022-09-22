import java.util.*;

/**
 * The class allows merging overlapping {@link Interval}s included in a {@link List} object.
 *
 * @author Karolin Dürr
 */
public class IntervalMerger {

    /**
     * Merges the overlapping {@link Interval}s included in a given input {@link List}. If the list parameter is empty
     * or null, an empty {@link List} will be returned. Otherwise, the returned list contains the merged
     * {@link Interval}s, sorted by their starting boundaries.
     *
     * @param inputIntervals A {@link List} including {@link Interval}s.
     * @return A sorted list including the {@link Interval}s after merging the overlapping ones.
     */
    public List<Interval> merge(final List<Interval> inputIntervals) {

        if (inputIntervals == null || inputIntervals.size() == 0) {
            return new LinkedList<>();
        }

        // Sorts the list by comparing the intervals' start values, and, if they are equal, the respective end values
        // of the intervals
        inputIntervals.sort((interval1, interval2) -> {
            if (interval1 == null) {
                return -1;
            }

            if (interval2 == null) {
                return 1;
            }

            return interval1.compareTo(interval2);
        });

        LinkedList<Interval> resultIntervals = new LinkedList<>();

        /* Since the interval list is sorted, if the current interval does not overlap with the previously merged one,
        any following interval in the list can also not overlap with the previous one. */
        for (Interval currentInterval : inputIntervals) {

            if (currentInterval == null) {
                // ignore invalid input included in the input list
                continue;
            }

            Interval previousInterval = resultIntervals.peekLast();

            if (previousInterval == null || Double.compare(previousInterval.getEnd(), currentInterval.getStart()) < 0) {
                // Intervals do not overlap --> add to result list and continue with next pair
                resultIntervals.add(currentInterval);
                continue;
            }

            // Previous interval includes at least the start boundary of the current interval --> merge intervals
            previousInterval.setEnd(Math.max(previousInterval.getEnd(), currentInterval.getEnd()));
        }

        return resultIntervals;
    }

    /**
     * Merges the overlapping intervals included in a given input array. If the input parameter is empty
     * or null, an empty {@link List} will be returned. Otherwise, the returned list contains the merged
     * intervals, sorted by their starting boundaries. Invalid input within the array will be ignored.
     *
     * @param inputIntervals A 2D-array including intervals. An interval is represented as another array including
     *                       exactly two elements, e.g. [1,10].
     * @return A sorted list including the intervals after merging the overlapping ones.
     */
    public List<double[]> merge(final double[][] inputIntervals) {
        LinkedList<double[]> resultIntervals = new LinkedList<>();

        if (inputIntervals == null || inputIntervals.length == 0) {
            return new LinkedList<>();
        }

        /* Sorts the array by comparing the intervals' start values, and, if they are equal, the respective end
        values of the intervals */
        Arrays.sort(inputIntervals, createComparatorUsingStartBoundary());

        /* Since the interval list is sorted, if the current interval does not overlap with the previously merged one,
        any following interval in the list can also not overlap with the previous one. */
        for (double[] currentInterval : inputIntervals) {
            if (currentInterval == null || currentInterval.length != 2) {
                // ignore invalid input included in the input array
                continue;
            }

            double[] previousInterval = resultIntervals.peekLast();

            if (previousInterval == null || Double.compare(previousInterval[1], currentInterval[0]) < 0) {
                // Intervals do not overlap --> add to result list and continue with next pair
                resultIntervals.add(currentInterval);
                continue;
            }

            // Previous interval includes at least the start boundary of the current interval --> merge intervals
            previousInterval[1] = Math.max(previousInterval[1], currentInterval[1]);
        }

        return resultIntervals;
    }

    /**
     * Compares two intervals represented as an array using their start boundaries. The first element, array[0], will be
     * interpreted as the start boundary and the second element, array[1], as the end. Therefore, if the given start and
     * end values are reversed, the value previously stored at array[0] will now be stored at array[1] instead, and vice
     * versa.
     *
     * @return the Comparator object that compares intervals using their start boundaries
     */
    private Comparator<double[]> createComparatorUsingStartBoundary() {
        return (interval1, interval2) -> {
            if (interval1 == null || interval1.length != 2) {
                checkIntervalBoundaries(interval2);
                return -1;
            }

            if (interval2 == null || interval2.length != 2) {
                checkIntervalBoundaries(interval2);
                return 1;
            }

            // ensure start and thus smaller boundary comes first
            checkIntervalBoundaries(interval1);
            checkIntervalBoundaries(interval2);

            return Double.compare(interval1[0], interval2[0]);
        };
    }

    /**
     * If start and end boundaries are reversed in the interval array, they will be switched.
     *
     * @param interval The interval for which the values should be checked
     */
    private void checkIntervalBoundaries(double[] interval) {
        if (interval != null && interval.length == 2 && Double.compare(interval[0], interval[1]) > 0) {
            double temp = interval[0];
            interval[0] = interval[1];
            interval[1] = temp;
        }
    }
}
