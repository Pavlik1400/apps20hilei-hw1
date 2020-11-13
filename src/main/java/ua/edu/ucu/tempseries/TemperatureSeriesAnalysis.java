package ua.edu.ucu.tempseries;

import lombok.Getter;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private static final double ZERO = 0;
    private static final double ABSOLUTE_ZERO = -273.15;
    private static final int INC_PARAM = 2;
    private static final double DELTA = 0.000001;
    private double[] temperatures;
    private double minimal = ABSOLUTE_ZERO;
    private double maximum = ABSOLUTE_ZERO;
    @Getter
    private int size = INC_PARAM;
    private int actualSize = 0;

    public TemperatureSeriesAnalysis() {
        temperatures = new double[size];
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (temperatureSeries.length != 0) {
            temperatures = new double[size];
            minimal = temperatureSeries[0];
            addTemps(temperatureSeries);
        }
    }

    public double average() {
        checkZeroSize("average");
        double sum = 0;
        for (int idx = 0; idx < actualSize; idx++) {
            sum += temperatures[idx];
        }
        // result = ( t1 + t2 + t3 + ... tn ) / n
        return sum / actualSize;
    }

    public double deviation() {
        checkZeroSize("deviation");
        double avg = average();
        double result = 0;
        // result = (t1-mean)^2 + (t2-mean)^2 + ,,, (tn-mean)^2
        for (int idx = 0; idx < actualSize; idx++) {
            result += (temperatures[idx] - avg) * (temperatures[idx] - avg);
        }
        // return sqrt(result/n) = deviation
        return Math.sqrt(result/actualSize);
    }

    public double min() {
        checkZeroSize("min");
        return minimal;
    }

    public double max() {
        checkZeroSize("max");
        return maximum;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(ZERO);
    }

    public double findTempClosestToValue(double tempValue) {
        checkZeroSize("findTempClosestToValue");
        // max ot min is on the maximum distance to zero
        double closest = maximum;
        if (Math.abs(minimal - tempValue) < Math.abs(maximum - tempValue)) {
            closest = minimal;
        }
        for (int idx = 0; idx < actualSize; idx++) {
            // first condition - just found closer;
            // second condition - found the same close but bigger temperature
            if ((Math.abs(temperatures[idx] - tempValue)
                    < Math.abs(closest - tempValue))
                    || ((Math.abs(Math.abs(temperatures[idx] - tempValue)
                    - Math.abs(closest - tempValue)) < DELTA)
                    && temperatures[idx] > closest)) {
                closest = temperatures[idx];
            }
        }
        return closest;
    }

    public double[] findTempsLessThen(final double tempValue) {
        checkZeroSize("findTempsLessThen");
        return /*convert to stream to filter*/Arrays.stream(
                /*filter only actual part of temps*/
                Arrays.copyOfRange(temperatures, 0, actualSize))
                .filter(x -> x < tempValue).toArray();    }

    public double[] findTempsGreaterThen(double tempValue) {
        checkZeroSize("findTempsGreaterThen");
        return /*convert to stream to filter*/Arrays.stream(
                /*filter only actual part of temps*/
                Arrays.copyOfRange(temperatures, 0, actualSize))
                .filter(x -> x > tempValue).toArray();
    }

    public TempSummaryStatistics summaryStatistics() {
        checkZeroSize("summaryStatistics");
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        checkAbsouleValue(temps);
        for (double temp : temps) {
            temperatures[actualSize++] = temp;
            if (actualSize == size) {
                temperatures = increaseArray();
            }
            if (temp < minimal) {
                minimal = temp;
            }
            else if (temp > maximum) {
                maximum = temp;
            }

        }
        return actualSize;
    }

    private void checkZeroSize(String operation) {
        if (actualSize == 0) {
            throw new IllegalArgumentException(
                    "The size of array is zero: Can't execute " + operation
            );
        }
    }

    private void checkAbsouleValue(double[] temps) {
        for (double temp : temps) {
            if (temp < ABSOLUTE_ZERO) {
                throw new InputMismatchException(
                        "Temperature less then absolute zero!"
                );
            }
        }
    }

    private double[] increaseArray() {
        size *= INC_PARAM;
        double[] newArr = new double[size];
        System.arraycopy(temperatures, 0, newArr, 0, actualSize);
        return newArr;
    }

    public double[] getTemperatures() {
        return Arrays.copyOfRange(temperatures, 0, actualSize);
    }

}
