package com.bjss.nhsd.a2si.idt.servicedataapi.domain;

public class AcceptedForServiceIdByTimePeriod {

    private String serviceId;
    private String from;
    private String to;
    private Integer totalAccepted;

    public AcceptedForServiceIdByTimePeriod(String serviceId, String from, String to, Integer totalAccepted) {
        this.serviceId = serviceId;
        this.from = from;
        this.to = to;
        this.totalAccepted = totalAccepted;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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
        return "AcceptedForServiceIdByTimePeriod{" +
                "serviceId='" + serviceId + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", totalAccepted=" + totalAccepted +
                "}\n";
    }
}
