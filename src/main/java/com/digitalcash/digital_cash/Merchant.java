package com.digitalcash.digital_cash;

/**
 * Created by Likhith on 4/16/2018.
 */



import java.io.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.io.BufferedWriter;
import javax.crypto.*;
public class Merchant
{
    public static String challenge_tosendbank;
    public static String decrypting_temp1;
    private static ArrayList<byte[]> temp_tobank = new ArrayList<>();
    public Merchant()
    {
        System.out.println("Merchant");
    }
    public static String UniqueID1() // To create Unique ID
    {
        String s ="";
        Random r= new Random();
        while(s.length()<10)
        {
            int r1= r.nextInt(2);
            s=s+Integer.toString(r1);
        }
        return s;
    }
    public String challenge()
    {
        String to_challenge=UniqueID1();
        challenge_tosendbank=to_challenge;
        return to_challenge;
    }
    public void tosendm_back()
    {
        System.out.println("to send to the bank");
    }
    public boolean tocheckhash(ArrayList<byte[]> temp_2)throws IOException
    {
        boolean c_temp = true;
        temp_tobank=temp_2;
        String array1[]=decrypting_temp1.split("::");
        ArrayList<String> temp_inmerch=new ArrayList<>();
        int j=2;
        int k=3;
        for(int i=0;i<challenge_tosendbank.length();i++)
        {
            //  System.out.println("---------------------------");
            //  System.out.println(challenge_tosendbank.length());
            //  System.out.println(array1.length);
            //  System.out.println(j);
            //  System.out.println(k);
            //  System.out.println("---------------------------");
            char i1=challenge_tosendbank.charAt(i);
            int index_challenge=i1-'0';
            if(index_challenge==0)
            {
                temp_inmerch.add(array1[j]);
            }
            else
            {
                temp_inmerch.add(array1[k]);
            }
            j=j+2;
            k=k+2;
        }
        for(int i=0;i<temp_inmerch.size();i++)
        {
            String hash_test = new String(temp_2.get(i));
            String hash_test1=Integer.toString(hash_test.hashCode());
            if(hash_test1.equals(temp_inmerch.get(i)))
            {
                c_temp=c_temp;
            }
            else
            {
                return false;
            }
        }
        return c_temp;
    }
    public String send_chalb()
    {
        return challenge_tosendbank;
    }
    public String received_orderfromCustomer(byte[] received,BigInteger public_key1,BigInteger Mod)
    {
        String confirmation="";
        byte[] decrypted1_c=((((new BigInteger(received)).modPow(public_key1,Mod))).toByteArray());
        String to_checkhash=(new String(decrypted1_c));
        decrypting_temp1=to_checkhash;
//System.out.println(decrypting_temp1);
//System.out.println(new String(decrypted1_c));
//System.out.println(to_checkhash);
        return to_checkhash;
    }
}



//public class Merchant {
//
//    /*
//     * A method that accepts a list of uniqueness strings from the bank
//     * and a moneyorder object. If a signature matches the moneyorder, true is
//     * returned, else false is returned.
//     * This is the merchant's step in the verification of the legitimacy
//     * of the money order.
//     */
//    public boolean verifyBankSigniture(String signatureList[], MoneyOrder MO)
//    {
//        //for loop to search through a list of uniqueness strings from the bank.
//        for(int i = 0; i < signatureList.length; i++)
//        {
//            //an if statement tests each string and compares it to the moneyorder's
//            // signature to find a match.
//            if(signatureList[i].equals(Integer.toString(MO.getMOID())))
//            {
//                return true;
//            }
//        }
//        //No match was found, person is lying.
//        return false;
//    }
//
//
//	/*
//	 * A random selector 'bit' that determines the half of the
//	 * identity string that the customer has to reveal to the
//	 * merchant as per the bit commitment protocol.
//	 * In this case the 'bit' is just an integer for now.
//	 */
//
//    public int randomSelectorBit()
//    {
//        //Get a random number, multiply it by some number
//        //(I choose 100) then mod by 2
//        return ((int)((Math.random())*100)%2);
//    }
//
//}