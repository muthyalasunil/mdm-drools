package com.example.drools.decision.corpactions.model;

public enum CustomerType {
    LOYAL, NEW, DISSATISFIED;
 
    public String getValue() {
        return this.toString();
    }
}
