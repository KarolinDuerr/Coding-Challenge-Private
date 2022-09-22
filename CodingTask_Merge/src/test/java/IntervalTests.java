import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The testing class for the {@link Interval} class.
 *
 * @author Karolin Dürr
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntervalTests {

    /**
     * Testing constructor for Interval object
     */

    @Test
    public void givenValidBoundaries_whenCreatingInterval_thenIntervalIsCreated() {
        // given
        int start = 0;
        int end = 10;

        // when
        Interval interval = new Interval(start, end);

        // then
        int expectedStart = 0;
        int expectedEnd = 10;
        assertEquals(expectedStart, interval.getStart(), "Wrong start boundary");
        assertEquals(expectedEnd, interval.getEnd(), "Wrong end boundary");
    }

    @Test
    public void givenReversedBoundaries_whenCreatingInterval_thenBoundariesAreSwitchedAndIntervalCreated() {
        // given
        int start = 10;
        int end = 0;

        // when
        Interval interval = new Interval(start, end);

        // then
        int expectedStart = 0;
        int expectedEnd = 10;
        assertEquals(expectedStart, interval.getStart(), "Wrong start boundary: Given reversed boundaries, the " +
                "smaller one should be taken as the start boundary.");
        assertEquals(expectedEnd, interval.getEnd(), "Wrong end boundary: Given reversed boundaries, the bigger one " +
                "should be taken as the start boundary.");
    }

    @Test
    public void givenSameStartAndEndBoundaries_whenCreatingInterval_thenReturnCreatedIntervalRepresentingSingleRealNumber() {
        // given
        int singleRealNumber = 1;

        // when
        Interval result = new Interval(singleRealNumber, singleRealNumber);

        // then
        assertEquals(singleRealNumber, result.getStart());
        assertEquals(singleRealNumber, result.getEnd());
    }

    /**
     * Testing setStart() for Interval
     */

    @Test
    public void givenNewStartBoundaryInvalid_whenSetStart_thenIgnoreChange() {
        // given
        Interval interval = new Interval(1, 10);

        // when
        int newStartBoundary = 20;
        interval.setStart(newStartBoundary);

        // then
        int expectedStartValue = 1;
        assertNotEquals(newStartBoundary, interval.getStart(), "Wrong start boundary: Given invalid new start " +
                "boundary, the value should not have changed.");
        assertEquals(expectedStartValue, interval.getStart(), "Wrong start boundary: Given invalid new start " +
                "boundary, the value should have remained the same as before.");
    }

    @Test
    public void givenValidStartBoundary_whenSetStart_thenStartIsUpdated() {
        // given
        int oldStartBoundary = 1;
        Interval interval = new Interval(oldStartBoundary, 10);

        // when
        int newStartBoundary = -20;
        interval.setStart(newStartBoundary);

        // then
        assertNotEquals(oldStartBoundary, interval.getStart());
        assertEquals(newStartBoundary, interval.getStart());
    }

    /**
     * Testing setEnd() for Interval
     */

    @Test
    public void givenNewEndBoundaryInvalid_whenSetEnd_thenIgnoreChange() {
        // given
        Interval interval = new Interval(1, 10);

        // when
        int newEndBoundary = -20;
        interval.setEnd(newEndBoundary);

        // then
        int expectedStartValue = 10;
        assertNotEquals(newEndBoundary, interval.getEnd(), "Wrong end boundary: Given invalid new end boundary, the " +
                "value should not have changed.");
        assertEquals(expectedStartValue, interval.getEnd(), "Wrong end boundary: Given invalid new end boundary, the " +
                "value should have remained the same as before.");
    }

    @Test
    public void givenValidEndBoundary_whenSetEnd_thenEndIsUpdated() {
        // given
        int oldEndBoundary = 10;
        Interval interval = new Interval(1, oldEndBoundary);

        // when
        int newEndBoundary = 100;
        interval.setEnd(newEndBoundary);

        // then
        assertNotEquals(oldEndBoundary, interval.getEnd());
        assertEquals(newEndBoundary, interval.getEnd());
    }

    /**
     * Testing compareTo() for Interval
     */
    @Test
    public void givenIntervalToCompareIsNull_whenIntervalCompareTo_thenReturnOne() {
        // given
        Interval currentInterval = new Interval(1, 20);

        // when
        int actualResult = currentInterval.compareTo(null);

        // then
        int expectedValue = 1;
        assertEquals(expectedValue, actualResult);
    }

    @Test
    public void givenStartValueIsLower_whenIntervalCompareTo_thenReturnMinusOne() {
        // given
        Interval higherStartInterval = new Interval(10, 20);
        Interval currentInterval = new Interval(1, 20);

        // when
        int actualResult = currentInterval.compareTo(higherStartInterval);

        // then
        int expectedValue = -1;
        assertEquals(expectedValue, actualResult);
    }

    @Test
    public void givenStartValueIsHigher_whenIntervalCompareTo_thenReturnPlusOne() {
        // given
        Interval lowerStartInterval = new Interval(-10, 20);
        Interval currentInterval = new Interval(1, 20);

        // when
        int actualResult = currentInterval.compareTo(lowerStartInterval);

        // then
        int expectedValue = 1;
        assertEquals(expectedValue, actualResult);
    }

    @Test
    public void givenStartValueIdenticalAndEndValueIsHigher_whenIntervalCompareTo_thenReturnPlusOne() {
        // given
        Interval lowerEndInterval = new Interval(1, 20);
        Interval currentInterval = new Interval(1, 30);

        // when
        int actualResult = currentInterval.compareTo(lowerEndInterval);

        // then
        int expectedValue = 1;
        assertEquals(expectedValue, actualResult);
    }

    @Test
    public void givenStartValueIdenticalAndEndValueIsLower_whenIntervalCompareTo_thenReturnMinusOne() {
        // given
        Interval lowerEndInterval = new Interval(1, 20);
        Interval currentInterval = new Interval(1, 10);

        // when
        int actualResult = currentInterval.compareTo(lowerEndInterval);

        // then
        int expectedValue = -1;
        assertEquals(expectedValue, actualResult);
    }

    @Test
    public void givenStartValueIdenticalAndEndValueIdentical_whenIntervalCompareTo_thenReturnZero() {
        // given
        Interval lowerEndInterval = new Interval(1, 20);
        Interval currentInterval = new Interval(1, 20);

        // when
        int actualResult = currentInterval.compareTo(lowerEndInterval);

        // then
        int expectedValue = 0;
        assertEquals(expectedValue, actualResult);
    }

    /**
     * Testing equals() for Interval
     */

    @Test
    public void givenDifferentIntervals_whenIntervalEquals_thenReturnFalse() {
        // given
        Interval firstInterval = new Interval(1, 20);
        Interval secondInterval = new Interval(-10, 5);

        // when
        boolean equalsResult = firstInterval.equals(secondInterval);

        // then
        assertFalse(equalsResult, "Different intervals should not be identified as equal.");
    }

    @Test
    public void givenTwoEqualIntervals_whenIntervalEquals_thenReturnTrue() {
        // given
        Interval firstInterval = new Interval(1, 20);
        Interval secondInterval = new Interval(1, 20);

        // when
        boolean equalsResult = firstInterval.equals(secondInterval);

        // then
        assertTrue(equalsResult, "Although different objects, the same intervals should be identified as equal.");
    }

    @Test
    public void givenOneInterval_whenIntervalEquals_thenReturnTrue() {
        // given
        Interval firstInterval = new Interval(1, 20);

        // when
        boolean equalsResult = firstInterval.equals(firstInterval);

        // then
        assertTrue(equalsResult, "The same interval should be identified as equal.");
    }

    @Test
    public void givenNull_whenIntervalEquals_thenReturnFalse() {
        // given
        Interval firstInterval = new Interval(1, 20);

        // when
        boolean equalsResult = firstInterval.equals(null);

        // then
        assertFalse(equalsResult, "Checking if an interval equals null, it should not be identified as equal.");
    }

    @Test
    public void givenAnyOtherObject_whenIntervalEquals_thenReturnFalse() {
        // given
        Interval firstInterval = new Interval(1, 20);

        // when
        boolean equalsResult = firstInterval.equals(new Object());

        // then
        assertFalse(equalsResult, "Checking if an interval equals Object, it should not be identified as equal.");
    }

    /**
     * Testing toString() for Interval
     */

    @Test
    public void givenValidInterval_whenIntervalToString_thenReturnFormattedString() {
        // given
        Interval interval = new Interval(1, 20);

        // when
        String result = interval.toString();

        // then
        String expectedResult = "[1,20]";
        assertEquals(expectedResult, result, "Representing an Interval as an String should have the format: [start," +
                "end]");
    }

    @Test
    public void givenValidIntervalUsingFloatingNumbers_whenIntervalToString_thenReturnFormattedString() {
        // given
        Interval interval = new Interval(1.5, 20.33);

        // when
        String result = interval.toString();

        // then
        String expectedResult = "[1.5,20.33]";
        assertEquals(expectedResult, result, "Representing an Interval as an String should have the format: [start," +
                "end]");
    }

}
