# Coding-Challenge-Private: MERGE Function for Intervals
The program takes a list of intervals as a program argument and merges the overlapping ones.

## Remarks

- the intervals provided as an **argument** will be considered **closed intervals**, so it is assumed that the used **boundaries** are **included** in the interval
- the Interval object represents closed intervals, so it assumes that the used **boundaries** are **included** in the interval, and start < end
- an interval of the form [0,0], meaning the **same start and end** boundary is considered a **valid** interval used to represent
  a single real number
- the merge() method, and the Interval object are explicitly designed such that an interval can **consist** of **integers** combined with **floating numbers**, where **trailing zeros**
  will be **removed** when converting it to a String value.
  Additionally, for a **String value**, the assumption is made that **six decimal places** are **sufficient** to improve readability.

## 1. Structure

The ``MergeIntervalMain`` class includes the ``main()`` method that executes the **MERGE** function for a list of intervals and prints the result to the console.
The list, including the intervals to be merged, can be provided as a program argument using the format *[[1,31],[3,7],[-2,0]]*.
An interval has to be framed using "*\[*" and "*\]*" brackets and the boundaries of the interval are separated using a comma, while floating numbers are represented using dots. Additionally, the intervals have to be seperated using commas as well.
If no argument is being provided, the program will automatically merge the following default interval list:  *[[25,30],[2,19],[14,23],[4,8]]*.

There are several possibilities to run the program:
  - using an **IDE** like IntelliJ
  - using the following **Gradle** tasks:
    1) For the default interval list:
       ```shell
       gradle clean build
       gradle run
       ```
    
    2) For providing an individual interval list:
       ```shell
       gradle clean build
       gradle run --args [[25,30],[2.25,19],[14,23],[4,8.1],[-100,100]]
       ```
  - using **Docker**:
      1) For the default interval list:
         ```shell
         docker build -t mergeprogram .
         docker run mergeprogram
         ```

      2) For providing an individual interval list:
         ```shell
         docker build -t mergeprogram .
         docker run mergeprogram [[25,30],[2.25,19],[14,23],[4,8.1],[-100,100]]
         ```

The ``IntervalMerger`` class provides the ``List<Interval> merge(List<Interval> inputIntervals)`` function and uses an additional ``Interval`` class to represent intervals,
and thus demonstrating an object-oriented approach.
However, since for big interval lists this can become expensive and problematic in terms of storage, the merge method is **overloaded** to provide an **alternative function**: 
``List<double[]> merge(double[][] inputIntervals)``.
It uses a two-dimensional array of doubles to represent the list of intervals instead.
Consequently, an array of length two constitutes an interval in this case.
The solution idea is identical to the other merge function, only the chosen representation type has changed.

Additionally, ``src/test`` includes **tests** for the ``Interval`` and the ``IntervalMerger`` class.
The ``IntervalMergerTests`` class includes tests for both methods, the one using an ``Interval`` object and the one using a ``double[]``.
The tests can either be run using an IDE like IntelliJ or Gradle's test task by running the following command:
```shell
gradle test
````

All tests will also be automatically executed when building the project, e.g. ``gradle build``.

## 2. General Solution Idea
Sort the given list increasingly by comparing the start boundaries of the included intervals.

If the list is increasingly sorted by the start boundaries of the intervals, we can compare an interval with its predecessor in the list.
Because if ``intervals[i].start > intervals[i-1].end``, then the intervals do not overlap and the following intervals in the list can also not overlap with ``intervals[i-1]``.
If that is the case, the current interval will be added to our result list and the next pair will be considered, meaning i becomes ``i = (i+1)``.

However, if the intervals do overlap, the end boundary of the predecessor, meaning ``intervals[i-1]``, will be updated by the end boundary that is larger of the two 
considered intervals.
Thereby, the current interval has been merged with its predecessor.