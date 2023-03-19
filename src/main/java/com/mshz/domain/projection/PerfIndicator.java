package com.mshz.domain.projection;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class PerfIndicator implements Serializable{
    private Double count;
    private Boolean countIsAPercent;
    private Double rate;
    private Instant dateMin;
    private Instant dateMax;
    private Double avg;

    public PerfIndicator(){
        this.countIsAPercent = false;
    }
    
    public PerfIndicator(Double count, Double rate, Instant dateMin, Instant dateMax) {
        this.count = count;
        this.rate = rate;
        this.dateMin = dateMin;
        this.dateMax = dateMax;
        this.countIsAPercent = false;
        autoCalCulAvg();
    }
    
    public PerfIndicator(Double count, Double rate, Instant dateMin, Instant dateMax, Boolean countIsAPercent) {
        this.count = count; // limitDecimalTo2(count);
        this.rate = limitDecimalTo2(rate);
        this.dateMin = dateMin;
        this.dateMax = dateMax;
        this.countIsAPercent = countIsAPercent;
        autoCalCulAvg();
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = limitDecimalTo2(count);
        autoCalCulAvg();
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = limitDecimalTo2(rate);
    }

    public Instant getDateMin() {
        return dateMin;
    }

    public void setDateMin(Instant dateMin) {
        this.dateMin = dateMin;
        autoCalCulAvg();
    }

    public Instant getDateMax() {
        return dateMax;
    }

    public void setDateMax(Instant dateMax) {
        this.dateMax = dateMax;
        autoCalCulAvg();
    }

    public Boolean getCountIsAPercent() {
        return countIsAPercent;
    }

    public void setCountIsAPercent(Boolean countIsAPercent) {
        this.countIsAPercent = countIsAPercent;
        autoCalCulAvg();
    }

    public Double getAvg(){
        return this.avg;
    }

    private void autoCalCulAvg(){
       try {
            if(this.count != null && this.dateMax != null && this.dateMin != null){
                Duration duration = Duration.between(this.dateMin, this.dateMax).abs();
                long days = TimeUnit.DAYS.convert(duration);
                double avg = 0;
                if(days != 0 && this.count.doubleValue() != 0){
                    avg = this.count / days;
                    /* if(this.countIsAPercent)
                        avg = avg * 0.01; */
                }
                this.avg = limitDecimalTo2(avg);
            }else{
                this.avg = null;
            }
       } catch (Exception e) {
         this.avg = null;
       }
    }

    public static Double limitDecimalTo2(Double number){
        try {
            if(number != null && number.toString().matches("^\\d+\\.\\d+")){
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                return Double.valueOf(decimalFormat.format(number));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    @Override
    public String toString() {
        return "PerfIndicator [avg=" + avg + ", count=" + count + ", countIsAPercent=" + countIsAPercent + ", dateMin="
                + dateMin + ", dateMax=" + dateMax + ", rate=" + rate + "]";
    }
    
}
