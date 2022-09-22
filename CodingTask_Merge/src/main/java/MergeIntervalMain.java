import java.util.*;

/**
 * The class including the main method that will be executed when the program is run.
 *
 * @author Karolin Dürr
 */
public class MergeIntervalMain {

    // Uses IntervalMerger class to merge the overlapping intervals within a list and print the result to the console.
    public static void main(String[] args) {
        List<Interval> intervals = new LinkedList<>();
        if (args == null || args.length == 0) {
            intervals.add(new Interval(25, 30));
            intervals.add(new Interval(2, 19));
            intervals.add(new Interval(14, 23));
            intervals.add(new Interval(4, 8));
        } else {
            intervals = convertConsoleInput(args[0]);
        }

        System.out.println("---------------------");
        System.out.println("Input: " + intervals);
        System.out.println("\nMerging overlapping intervals...\n");

        IntervalMerger intervalMerger = new IntervalMerger();
        System.out.println(intervalMerger.merge(intervals));
        System.out.println("---------------------");
    }

    /**
     * Converts the input into a list of {@link Interval} objects, if the input is valid. Otherwise, a corresponding
     * message is printed to the console and an empty list returned. There are two valid formats using commas,
     * brackets and numbers but no whitespace characters: e.g. {@code [[25,30],[2,19],[14,23],[4,8]]} or
     * {@code [25,30],[2,19],[14,23],[4,8]}
     *
     * @param intervalListInput the input list including the intervals in string format
     * @return the provided list of {@link Interval}s
     */
    private static List<Interval> convertConsoleInput(final String intervalListInput) {
        if (intervalListInput == null || !intervalListInput.startsWith("[") || !intervalListInput.endsWith("]")) {
            System.out.println("You're input list was invalid. Please check your input and make sure that you only " +
                    "use numbers, commas and the respective brackets, without any whitespace characters.");
            System.out.println("The following two examples would be valid: [[25,30],[2,19],[14,23],[4,8]] or [25,30]," +
                    "[2,19],[14,23],[4,8]");
            return new LinkedList<>();
        }

        String[] inputIntervals = intervalListInput.split("],\\[");

        List<Interval> intervals = new LinkedList<>();
        for (String interval : inputIntervals) {
            String[] inputIntervalValues = interval.replace("[", "").replace("]", "").split(",");
            try {
                double start = Double.parseDouble(inputIntervalValues[0].trim());
                double end = Double.parseDouble(inputIntervalValues[1].trim());
                Interval parsedInterval = new Interval(start, end);
                intervals.add(parsedInterval);
            } catch (NumberFormatException exception) {
                System.out.println("Your provided list includes at least one invalid input or interval: " + interval);
                return new LinkedList<>();
            }
        }

        return intervals;
    }
}
