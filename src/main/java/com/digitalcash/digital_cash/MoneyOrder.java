package com.digitalcash.digital_cash;

/**
 * Created by Likhith on 4/16/2018.
 */


// i will update this as we go through it
import java.util.*;

public class MoneyOrder {
    // variables for customer info
    private int ssn;
    private int unString; // uniqueString in driver

    // variables for info
    private int am;

    private int[] bankSig = new int[4];
    private int k;

    private int[] randomNum;
    private int[] secretNum;

    // parameterized constructor
    public MoneyOrder(int ssn, int uniqueString, int amount) {
        this.ssn = ssn;
        unString = uniqueString;
        am = amount;
        k = (int)((Math.random()*7)+2);
    }

    public String toString() { // return parameters to the display method
        // in MoneyOrder class
        return (ssn + " " + unString + " " + am);
    }

    public int[] getRandomNum()
    {
        return randomNum;
    }

    public int[] getSecretNum()
    {
        return secretNum;
    }

    // Get uniqueString
    public int getMOID() {
        return unString;
    }

    //Set uniqueString
    public void setMOID(int uniqueString) {
        unString = uniqueString;
    }

    //Get SSN
    public int getSSN()
    {
        return ssn;
    }

    //Get uniqueString
    public int getUnString()
    {
        return unString;
    }

    //Get bankSig
    public int getBankSig(int i)
    {
        return bankSig[i];
    }

    //Set bankSig
    public void setBankSig(int bankSig, int i)
    {
        this.bankSig[i] = bankSig;
    }

    public int getAmount()
    {
        return am;
    }

    public void setSSN(int i)
    {
        ssn = i;
    }

    public void setAmount(int i)
    {
        am = i;
    }

    public void setUnString(int i)
    {
        unString = i;
    }

    public int getK()
    {
        return k;
    }

    public void setK(int k)
    {
        this.k = k;
    }
}