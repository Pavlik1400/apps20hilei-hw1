package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {
    private final double DELTA = 0.00001;

    @Test
    public void testConstructorWithTemps(){
        // one element
        double[] temperatureSeries = {-1, -2, 0, -41, 12};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        for (int idx = 0; idx < temperatureSeries.length; idx++)
            assertEquals(temperatureSeries[idx], seriesAnalysis.getTemperatures()[idx], DELTA);
    }

    @Test(expected = InputMismatchException.class)
    public void testConstructorTooSmallTemperature(){
        // one element
        double[] temperatureSeries = {-1, -2, 0, -274, 12};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
    }

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;
        // call tested method
        double actualResult = seriesAnalysis.average();
        // compare expected result with actual result
        assertEquals(expResult, actualResult, DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;
        double actualResult = seriesAnalysis.average();
        assertEquals(expResult, actualResult, 0.00001);        
    }

    @Test
    public void testDeviationWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 0.0;
        // call tested method
        double actualResult = seriesAnalysis.deviation();
        // compare expected result with actual result
        assertEquals(expResult, actualResult, DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviationWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.deviation();
    }

    @Test
    public void testDeviation() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 3.74165738;

        double actualResult = seriesAnalysis.deviation();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinEmptyArray(){
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.min();
    }

    @Test
    public void testMin(){
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0, -78};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        assertEquals(-78.0, seriesAnalysis.min(), DELTA);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testMaxEmptyArray(){
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.max();
    }

    @Test
    public void testMax(){
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0, 78};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        assertEquals(78.0, seriesAnalysis.max(), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTempClosestToZeroEmpty(){
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.findTempClosestToZero();
    }

    @Test
    public void testTempClosestToZeroAllBiggerSmaller(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        assertEquals(seriesAnalysis.findTempClosestToZero(), 1.0, DELTA);

        double[] temperatureSeries2 = {-1.0, -1.8, -12.32, -2.31};
        seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries2);
        assertEquals(seriesAnalysis.findTempClosestToZero(), -1.0, DELTA);

    }

    @Test
    public void testTempClosestToZero(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        assertEquals(seriesAnalysis.findTempClosestToZero(), 1.0, DELTA);

        temperatureSeries1 = new double[]{-1.0, -1.8, -12.32, -2.31, 1.0, 2.0, 2.5, 3.1};
        seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        assertEquals(seriesAnalysis.findTempClosestToZero(), 1.0, DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTempClosestToValueEmpty(){
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.findTempClosestToValue(1.0);
    }

    @Test
    public void testTempClosestToValueAllBiggerSmaller(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        assertEquals(seriesAnalysis.findTempClosestToValue(0.7), 1.0, DELTA);

        double[] temperatureSeries2 = {-1.0, -1.8, -12.32, -2.31};
        seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries2);
        assertEquals(seriesAnalysis.findTempClosestToValue(-0.7), -1.0, DELTA);

    }

    @Test
    public void testTempClosestToValue(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        assertEquals(seriesAnalysis.findTempClosestToValue(1.5), 2.0, DELTA);

        temperatureSeries1 = new double[]{-1.0, -1.8, -12.32, -2.31, 1.0, 2.0, 2.5, 3.1};
        seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        assertEquals(seriesAnalysis.findTempClosestToValue(-3.0), -2.31, DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempsLessThenEmpty(){
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.findTempsLessThen(1.0);
    }

    @Test
    public void testFindTempsLessThenNoLess(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        assertEquals(seriesAnalysis.findTempsLessThen(0).length, 0);
    }

    @Test
    public void testFindTempsLessThenAllLess(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);

        double[] less = seriesAnalysis.findTempsLessThen(6.0);
        for (int i = 0; i < less.length; i++)
            assertEquals(less[i], temperatureSeries1[i], DELTA);
    }

    @Test
    public void testFindTempsLessThen(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);

        double[] less = seriesAnalysis.findTempsLessThen(2.0);
        double[] expected = new double[] {1.0, -1.0, -1.8, -12.32, -2.31};
        for (int i = 0; i < less.length; i++)
            assertEquals(less[i], expected[i], DELTA);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testFindTempsGreaterThenEmpty(){
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.findTempsGreaterThen(1.0);
    }

    @Test
    public void testFindTempsGreaterThenNoGreater(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        assertEquals(seriesAnalysis.findTempsGreaterThen(10.0).length, 0);
    }

    @Test
    public void testFindTempsGreaterThenAllGreater(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);

        double[] less = seriesAnalysis.findTempsGreaterThen(0);
        for (int i = 0; i < less.length; i++)
            assertEquals(less[i], temperatureSeries1[i], DELTA);
    }

    @Test
    public void testFindTempsGreaterThen(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);

        double[] less = seriesAnalysis.findTempsGreaterThen(1.0);
        double[] expected = {2.0, 2.5, 3.1};
        for (int i = 0; i < less.length; i++)
            assertEquals(less[i], expected[i], DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatisticsEmpty(){
        double[] temperaturesSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperaturesSeries);
        seriesAnalysis.summaryStatistics();
    }

    @Test
    public void testSummaryStatistics(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);

        TempSummaryStatistics summaryStatistics = seriesAnalysis.summaryStatistics();
        TempSummaryStatistics expectedStatistics = new TempSummaryStatistics(-1.10375, 4.643172238620919, -12.32, 3.1);

        assertEquals(summaryStatistics.getAvgTemp(), -1.10375, DELTA);
        assertEquals(summaryStatistics.getDeviation(), 4.643172238620919, DELTA);
        assertEquals(summaryStatistics.getMinTemp(), -12.32, DELTA);
        assertEquals(summaryStatistics.getMaxTemp(), 3.1, DELTA);

    }

    @Test(expected = InputMismatchException.class)
    public void testAddTempTooSmall(){
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
        seriesAnalysis.addTemps(-280.0);
    }

    @Test
    public void testAddTempNewMin(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        seriesAnalysis.addTemps(-20.0);
        double[] temperatureSeriesExpected = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31, -20.0};
        for (int i = 0; i < temperatureSeries1.length; i++){
            assertEquals(seriesAnalysis.getTemperatures()[i], temperatureSeriesExpected[i], DELTA);
        }
        assertEquals(seriesAnalysis.min(), -20.0, DELTA);
    }

    @Test
    public void testAddTempNewMax(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        seriesAnalysis.addTemps(5.0);
        double[] temperatureSeriesExpected = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31, 5.0};
        for (int i = 0; i < temperatureSeries1.length; i++){
            assertEquals(seriesAnalysis.getTemperatures()[i], temperatureSeriesExpected[i], DELTA);
        }
        assertEquals(seriesAnalysis.max(), 5.0, DELTA);
    }

    @Test
    public void testAddTempMany(){
        double[] temperatureSeries1 = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries1);
        seriesAnalysis.addTemps(5.0, 12.0, -3.12, 2.41);
        double[] temperatureSeriesExpected = {1.0, 2.0, 2.5, 3.1, -1.0, -1.8, -12.32, -2.31, 5.0, 12.0, -3.12, 2.41};
        double[] actualSeries = seriesAnalysis.getTemperatures();
        for (int i = 0; i < temperatureSeriesExpected.length; i++){
            assertEquals(actualSeries[i], temperatureSeriesExpected[i], DELTA);
        }
        assertEquals(seriesAnalysis.max(), 12.0, DELTA);
        assertEquals(seriesAnalysis.min(), -12.32, DELTA);
        assertEquals(seriesAnalysis.getSize(), 16);
    }



}
