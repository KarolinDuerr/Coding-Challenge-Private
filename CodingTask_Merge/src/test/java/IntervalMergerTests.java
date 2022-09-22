import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The testing class for the {@link IntervalMerger} class.
 *
 * @author Karolin Dürr
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntervalMergerTests {

    private IntervalMerger intervalMerger;

    @BeforeAll
    public void init() {
        this.intervalMerger = new IntervalMerger();
    }

    @Test
    public void givenNull_whenMergeIntervals_thenReturnEmptyList() {
        List<Interval> nullInput = null;

        // when
        List<Interval> actualResult = this.intervalMerger.merge(nullInput);

        //then
        List<Interval> expectedResult = new LinkedList<>();

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenEmptyList_whenMergeIntervals_thenReturnEmptyList() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenOneListItem_whenMergeIntervals_thenReturnSameList() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(25, 30));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        assertEquals(inputIntervals.size(), actualResult.size());
        assertEquals(inputIntervals, actualResult);
    }

    @Test
    public void givenListContainsNullInterval_whenMergeIntervals_thenIgnoreAndReturnMergedList() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(25, 30));
        inputIntervals.add(new Interval(2, 4));
        inputIntervals.add(null);
        inputIntervals.add(new Interval(18, 26));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(2, 4));
        expectedResult.add(new Interval(18, 30));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenNoOverlappingItems_whenMergeIntervals_thenReturnSameList() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(1, 5));
        inputIntervals.add(new Interval(25, 30));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(1, 5));
        expectedResult.add(new Interval(25, 30));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenSameIntervals_whenMergeIntervals_thenReturnListWithOneItem() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(1, 5));
        inputIntervals.add(new Interval(1, 5));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(1, 5));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenTwoOverlappingItems_whenMergeIntervals_thenReturnMergedItemList() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(15, 28));
        inputIntervals.add(new Interval(25, 30));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(15, 30));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenIncludedItems_whenMergeIntervals_thenReturnMergedItemList() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(20, 23));
        inputIntervals.add(new Interval(16, 33));
        inputIntervals.add(new Interval(28, 30));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(16, 33));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenTouchingIntervals_whenMergeIntervals_thenReturnMergedList() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(20, 25));
        inputIntervals.add(new Interval(25, 30));
        inputIntervals.add(new Interval(30, 35));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(20, 35));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenSomeNegativeAndLargeValues_whenMergeIntervals_thenReturnMergedListIncludingOneItem() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(2, 19));
        inputIntervals.add(new Interval(25, 30));
        inputIntervals.add(new Interval(-1000, 1000));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(-1000, 1000));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenSameStartingLimitAndDuplicateItem_whenMergeIntervals_thenReturnMergedListIncludingOneItem() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(-100, -2));
        inputIntervals.add(new Interval(-100, 100));
        inputIntervals.add(new Interval(-100, -2));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(-100, 100));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenSameEndLimit_whenMergeIntervals_thenReturnMergedListIncludingOneItem() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(-100, 100));
        inputIntervals.add(new Interval(2, 100));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(-100, 100));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenSeveralUnsortedIntervals_whenMergeIntervals_thenReturnSortedMergedIntervals() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(25, 30));
        inputIntervals.add(new Interval(2, 19));
        inputIntervals.add(new Interval(14, 23));
        inputIntervals.add(new Interval(4, 8));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(2, 23));
        expectedResult.add(new Interval(25, 30));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenSeveralUnsortedIntervalsIncludingNegativeValues_whenMergeIntervals_thenReturnSortedMergedIntervals() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(0, 1));
        inputIntervals.add(new Interval(25, 30));
        inputIntervals.add(new Interval(2, 19));
        inputIntervals.add(new Interval(-10, -5));
        inputIntervals.add(new Interval(14, 23));
        inputIntervals.add(new Interval(4, 8));
        inputIntervals.add(new Interval(1, 8));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(-10, -5));
        expectedResult.add(new Interval(0, 23));
        expectedResult.add(new Interval(25, 30));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenIntervalsWithFloatingNumbers_whenMergeIntervals_thenReturnSortedMergedIntervals() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(0.5, 10.123));
        inputIntervals.add(new Interval(8.2, 30.1894));
        inputIntervals.add(new Interval(-5.4, 0.1));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(-5.4, 0.1));
        expectedResult.add(new Interval(0.5, 30.1894));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenIntervalsWithFloatingAndIntegerNumbers_whenMergeIntervals_thenReturnSortedMergedIntervals() {
        // given
        List<Interval> inputIntervals = new LinkedList<>();
        inputIntervals.add(new Interval(0.11, 10));
        inputIntervals.add(new Interval(8.2, 30.1894));
        inputIntervals.add(new Interval(-5.4, 0));
        inputIntervals.add(new Interval(-100, -4));

        // when
        List<Interval> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<Interval> expectedResult = new LinkedList<>();
        expectedResult.add(new Interval(-100, 0));
        expectedResult.add(new Interval(0.11, 30.1894));

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Testing the alternative merge(double[][] intervals). These tests are the same as before but using a 2D-Array
     * instead of a List including Interval objects
     */

    @Test
    public void givenNull_whenMergeArrayIntervals_thenReturnEmptyList() {
        double[][] nullInput = null;

        // when
        List<double[]> actualResult = this.intervalMerger.merge(nullInput);

        //then
        List<double[]> expectedResult = new LinkedList<>();

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenEmptyArray_whenMergeArrayIntervals_thenReturnEmptyList() {
        // given
        double[][] inputIntervals = {};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenArrayIncludingInvalidIntervals_whenMergeArrayIntervals_thenIgnoreAndReturnMergedIntervals() {
        // given
        double[][] inputIntervals = {{1, 3}, null, {2, 8}, {}, {-1, -5}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{-5, -1});
        expectedResult.add(new double[]{1, 8});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenInvalidArrayInterval_whenMergeArrayIntervals_thenIgnoreAndReturnMergedIntervals() {
        // given
        double[][] inputIntervals = {{25, 30}, {1}, {2, 28}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{2, 30});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenOneListItem_whenMergeArrayIntervals_thenReturnSameList() {
        // given
        double[][] inputIntervals = {{25, 30}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        assertEquals(inputIntervals.length, actualResult.size());
        assertArrayEquals(inputIntervals, actualResult.toArray());
    }

    @Test
    public void givenNoOverlappingItems_whenMergeArrayIntervals_thenReturnSameList() {
        // given
        double[][] inputIntervals = {{1, 5}, {25, 30}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{1, 5});
        expectedResult.add(new double[]{25, 30});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenSameIntervals_whenMergeArrayIntervals_thenReturnListWithOneItem() {
        // given
        double[][] inputIntervals = {{1, 5}, {1, 5}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{1, 5});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenTwoOverlappingItems_whenMergeArrayIntervals_thenReturnMergedItemList() {
        // given
        double[][] inputIntervals = {{15, 28}, {25, 30}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{15, 30});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenIncludedItems_whenMergeArrayIntervals_thenReturnMergedItemList() {
        // given
        double[][] inputIntervals = {{20, 23}, {16, 33}, {28, 30}};
        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{16, 33});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenTouchingIntervals_whenMergeArrayIntervals_thenReturnMergedList() {
        // given
        double[][] inputIntervals = {{20, 25}, {25, 30}, {30, 35}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{20, 35});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenSomeNegativeAndLargeValues_whenMergeArrayIntervals_thenReturnMergedListIncludingOneItem() {
        // given
        double[][] inputIntervals = {{2, 19}, {25, 30}, {-1000, 1000}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{-1000, 1000});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenSameStartingLimitAndDuplicateItem_whenMergeArrayIntervals_thenReturnMergedListIncludingOneItem() {
        // given
        double[][] inputIntervals = {{-100, -2}, {-100, 100}, {-100, -2}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{-100, 100});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenSameEndLimit_whenMergeArrayIntervals_thenReturnMergedListIncludingOneItem() {
        // given
        double[][] inputIntervals = {{-100, 100}, {2, 100}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{-100, 100});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenSeveralUnsortedIntervals_whenMergeArrayIntervals_thenReturnSortedMergedIntervals() {
        // given
        double[][] inputIntervals = {{25, 30}, {2, 19}, {14, 23}, {4, 8}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{2, 23});
        expectedResult.add(new double[]{25, 30});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenSeveralUnsortedIntervalsIncludingNegativeValues_whenMergeArrayIntervals_thenReturnSortedMergedIntervals() {
        // given
        double[][] inputIntervals = {{0, 1}, {25, 30}, {2, 19}, {-10, -5}, {14, 23}, {4, 8}, {1, 8}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{-10, -5});
        expectedResult.add(new double[]{0, 23});
        expectedResult.add(new double[]{25, 30});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenIntervalsWithFloatingNumbers_whenMergeArrayIntervals_thenReturnSortedMergedIntervals() {
        // given
        double[][] inputIntervals = {{0.5, 10.123}, {8.2, 30.1894}, {-5.4, 0.1}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{-5.4, 0.1});
        expectedResult.add(new double[]{0.5, 30.1894});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }

    @Test
    public void givenIntervalsWithFloatingAndIntegerNumbers_whenMergeArrayIntervals_thenReturnSortedMergedIntervals() {
        // given
        double[][] inputIntervals = {{0.11, 10}, {8.2, 30.1894}, {-5.4, 0}, {-100, -4}};

        // when
        List<double[]> actualResult = this.intervalMerger.merge(inputIntervals);

        //then
        List<double[]> expectedResult = new LinkedList<>();
        expectedResult.add(new double[]{-100, 0});
        expectedResult.add(new double[]{0.11, 30.1894});

        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }
}
