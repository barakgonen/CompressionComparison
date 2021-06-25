package org.bg.compression.datatype;

import java.io.Serializable;
import java.util.Objects;

public class Bill implements Serializable {

    // Declaring the private variables
    private String billno;
    private String buyerName;

    // Creating constructor
    // of Java Class Bill
    public Bill(
            String bill, String buyer) {
        this.billno = bill;
        this.buyerName = buyer;
    }

    // Defining methods initializing
    // variables billno and buyerName
    public String getBill() {
        return billno;
    }

    public void setBill(
            String billno) {
        this.billno = billno;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(
            String buyer) {
        this.buyerName = buyer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(billno, bill.billno) && Objects.equals(buyerName, bill.buyerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billno, buyerName);
    }
}