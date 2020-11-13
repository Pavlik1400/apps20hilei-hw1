package ua.edu.ucu.tempseries;


import lombok.Getter;

@Getter
public class TempSummaryStatistics {
    private final double avgTemp;
    private final double deviation;
    private final double minTemp;
    private final double maxTemp;


    public TempSummaryStatistics(double avgTemp, double deviation,
                                 double minTemp, double maxTemp) {
        this.avgTemp = avgTemp;
        this.deviation = deviation;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }
}
