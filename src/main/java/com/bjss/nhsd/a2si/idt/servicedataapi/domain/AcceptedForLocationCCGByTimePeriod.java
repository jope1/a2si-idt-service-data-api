package com.bjss.nhsd.a2si.idt.servicedataapi.domain;

public class AcceptedForLocationCCGByTimePeriod {

    private String locationCCG;
    private String from;
    private String to;
    private Integer totalAccepted;

    public AcceptedForLocationCCGByTimePeriod(String locationCCG, String from, String to, Integer totalAccepted) {
        this.locationCCG = locationCCG;
        this.from = from;
        this.to = to;
        this.totalAccepted = totalAccepted;
    }

    public String getLocationCCG() {
        return locationCCG;
    }

    public void setLocationCCG(String locationCCG) {
        this.locationCCG = locationCCG;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getTotalAccepted() {
        return totalAccepted;
    }

    public void setTotalAccepted(Integer totalAccepted) {
        this.totalAccepted = totalAccepted;
    }

    @Override
    public String toString() {
        return "AcceptedForLocationCCGByTimePeriod{" +
                "locationCCG='" + locationCCG + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", totalAccepted=" + totalAccepted +
                "}\n";
    }
}
