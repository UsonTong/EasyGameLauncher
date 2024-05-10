package com.github.usontong.easygamelauncher.entity;

public class PartyConfig {
    private final String partyType;
    private final int startAmount;
    private final int startSecond;
    private final int accelerateAmount;
    private final int accelerateSecond;

    public String getPartyType() {
        return partyType;
    }

    public int getStartAmount() {
        return startAmount;
    }

    public int getStartSecond() {
        return startSecond;
    }

    public int getAccelerateAmount() {
        return accelerateAmount;
    }

    public int getAccelerateSecond() {
        return accelerateSecond;
    }

    public PartyConfig(String partyType, int startAmount, int startSecond, int accelerateAmount, int accelerateSecond) {
        this.partyType = partyType;
        this.startAmount = startAmount;
        this.startSecond = startSecond;
        this.accelerateAmount = accelerateAmount;
        this.accelerateSecond = accelerateSecond;
    }

    @Override
    public String toString() {
        return "PartyConfig{" +
                "partyType='" + partyType + '\'' +
                ", startAmount=" + startAmount +
                ", startSecond=" + startSecond +
                ", accelerateAmount=" + accelerateAmount +
                ", accelerateSecond=" + accelerateSecond +
                '}';
    }
}
