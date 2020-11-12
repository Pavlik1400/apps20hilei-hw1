package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

// TODO: Documentation
public class TemperatureSeriesAnalysis {
    private final double ZERO = 0;
    private final double ABSOLUTEZERO = -273.15;
    private final int INCPARAM = 2;
    private double[] temperatures;
    private double minimal = ABSOLUTEZERO;
    private double maximum = ABSOLUTEZERO;
    private int size = 10;
    private int actualSize = 0;

    public TemperatureSeriesAnalysis() {
        temperatures = new double[size];
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        // TODO: test this
        temperatures = new double[size];
        minimal = temperatureSeries[0];
        addTemps(temperatureSeries);
//        for (double temp : temperatureSeries)
//            addTemps(temp);
    }

    public double average() {
        checkZeroSize("average");
        double sum = 0;
        for (int idx = 0; idx < actualSize; idx++)
            sum += temperatures[idx];
        // result = ( t1 + t2 + t3 + ... tn ) / n
        System.out.println(Arrays.toString(temperatures));
        System.out.println(actualSize);
        return sum / actualSize;
    }

    public double deviation() {
        checkZeroSize("deviation");
        double avg = average();
        double result = 0;
        // result = (t1-mean)^2 + (t2-mean)^2 + ,,, (tn-mean)^2
        for (int idx = 0; idx < actualSize; idx ++)
            result += Math.pow(temperatures[idx] - avg, 2);
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
        if (Math.abs(minimal - tempValue) < Math.abs(maximum - tempValue))
            closest = minimal;
        for (int idx = 0; idx < actualSize; idx++)
            // first condition - just found closer; second condition - found the same close but bigger temperature
            if ((Math.abs(temperatures[idx] - tempValue) < Math.abs(closest - tempValue)) ||
                    ((Math.abs(temperatures[idx] - tempValue) == Math.abs(closest - tempValue))
                            && temperatures[idx] > closest))
                closest = temperatures[idx];
        return closest;
    }

    public double[] findTempsLessThen(final double tempValue) {
        checkZeroSize("findTempsLessThen");
        return Arrays.stream(temperatures).filter(x -> x < tempValue).toArray();
    }

    public double[] findTempsGreaterThen(double tempValue) {
        checkZeroSize("findTempsGreaterThen");
        return Arrays.stream(temperatures).filter(x -> x > tempValue).toArray();
    }

    public TempSummaryStatistics summaryStatistics() {
        checkZeroSize("summaryStatistics");
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        checkAbsouleValue(temps);
        for (double temp : temps){
            temperatures[actualSize++] = temp;
            if (actualSize == size)
                temperatures = increaseArray();
            if (temp < minimal)
                minimal = temp;
            else if (temp > maximum)
                maximum = temp;

        }
        return actualSize;
    }

    private void checkZeroSize(String operation){
        if (size == 0)
            throw new IllegalArgumentException("The size of array is zero: Can't execute " + operation);
    }

    private void checkAbsouleValue(double[] temps){
        for (double temp : temps)
            if (temp < ABSOLUTEZERO)
                throw new InputMismatchException("Temperature less then absolute zero!");
    }

    private double[] increaseArray(){
        size *= INCPARAM;
        double[] newArr = new double[size];
        System.arraycopy(temperatures, 0, newArr, 0, actualSize);
        return newArr;
    }

}
