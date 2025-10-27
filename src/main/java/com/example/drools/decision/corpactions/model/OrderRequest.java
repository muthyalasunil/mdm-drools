package com.example.drools.decision.corpactions.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class OrderRequest {
    /**
     * [{"customerNumber": "999", "age": "19",  "amount": 1000, "customerType": "DISSATISFIED" },
     * {"customerNumber": "1000", "age": "29",  "amount": 100000, "customerType": "LOYAL" }]
     */
    private String customerNumber;
    private Integer age;
    private Integer amount;
    private CustomerType customerType;
    private int score;
    private List<String> matches = new ArrayList<String>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return Objects.equals(customerNumber, that.customerNumber) && Objects.equals(age, that.age) && Objects.equals(amount, that.amount) && customerType == that.customerType;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "customerNumber='" + customerNumber + '\'' +
                ", age=" + age +
                ", amount=" + amount +
                ", customerType=" + customerType +
                ", score=" + score + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNumber, age, amount, customerType);
    }
}