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
import java.io.BufferedWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import javax.crypto.*;
public class Bank
{
    public static int randomord;
    private static BigInteger privatekey;
    public static int to_send;
    private static ArrayList<String> decrypted_message= new ArrayList <>();
    private static int money_order1;
    private static BigInteger publickey_bank;
    private static BigInteger modulus_bank;
    private static byte[] temp_bankstore;
    private static ArrayList<String> ID = new ArrayList<>();
    private static ArrayList<String> chal_iden = new ArrayList<>();
    private static ArrayList<byte[]> Identity_hash_check = new ArrayList<>();
    public Bank(int random_toselect, BigInteger pvkey,int money_order)
    {
        money_order1=money_order;
        randomord=random_toselect;
        privatekey=pvkey;
    }
    public int to_selectorder()
    {
        to_send=0;
        if(randomord!=0)
        {
            Random to_select = new Random();
            int Max=randomord-1;
            int Min=0;
//  to_send = to_select.nextInt(randomord); // Limit values to total number of orders
            to_send= Min + to_select.nextInt(Max - Min + 1);
        }
        else
        {
            System.out.println("No money order to process");
        }
        return to_send;
    }
    public String unblindingmoney(ArrayList<BigInteger> a1, ArrayList<byte[]> a2,BigInteger e1, BigInteger n1)
    {
        publickey_bank=e1;
        modulus_bank=n1;
        temp_bankstore=a2.get(to_send);
        for(int i=0;i<a1.size();i++)
        {
            if(i!=to_send)
            {
                BigInteger inverse=(a1.get(i)).modPow(e1,n1);
                byte[] decrypted = (((new BigInteger(a2.get(i))).divide(inverse)).toByteArray());
                //System.out.println("-------------------");//comment
                String temp= new String(decrypted);
//  System.out.println(temp);//comment
//  System.out.println("-------------------");//comment
                decrypted_message.add(temp);
            }
            else
            {
                decrypted_message.add(new String(a2.get(i)));
            }
        }
        try
        {
            writing_id();
        }
        catch(Exception e)
        {
            System.out.println("Cant write ID to file");
        }
        boolean moc=money_check();
        if(moc==true)
        {
            return "Transaction is valid,all money orders are okay";
        }
        return "Trying to Cheat the bank ";
    }
    private void writing_id()throws IOException
    {
        File file = new File("ID_Mo.txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        String to_write="";
        System.out.println(to_send);
        for(int i=0;i<decrypted_message.size();i++)
        {
            if(i==to_send)
            {
                to_write=to_write+"00N/A"+"::"+"00N/A"+"::";
            }
            else if(i==(decrypted_message.size()-1))
            {
                String to_write1[]=(decrypted_message.get(i)).split("::");
                to_write=to_write+to_write1[0]+"::"+to_write1[1];
            }
            else
            {
                String to_write1[]=(decrypted_message.get(i)).split("::");
                to_write=to_write+to_write1[0]+"::"+to_write1[1]+"::";
            }
        }
        writer.write(to_write);
        writer.flush();
        writer.close();
    }
    public void Merchant_order()
    {
        System.out.println("Order from merchant");
    }
    public boolean ID_check(String idtocheck)
    {
        String filename2="ID_Mo.txt";
        String line1="";
        String line2="";
        String to_checkarr2[];
        String array_split[]=idtocheck.split("::");
        String IDtocheck_here=array_split[0];
        boolean to_merchant=true;
        try
        {
            FileReader filereader2 = new FileReader(filename2);
            BufferedReader buff2 = new BufferedReader(filereader2);
            while((line1=buff2.readLine())!=null)
            {
                line2=line2+line1;
            }
            buff2.close();
        }
        catch(Exception e)
        {
            System.out.println("File not found");
        }
        to_checkarr2=line2.split("::");
        for(int i=0;i<to_checkarr2.length;i=i+2)
        {
            if(i!=((to_send*2)-1))
            {
                ID.add(to_checkarr2[i]);
                //System.out.println("here here here");
            }
        }
        if(ID.contains(IDtocheck_here))
        {
            return false;
        }
        ID.add(IDtocheck_here);
        return to_merchant;
    }
    public boolean money_check()
    {
        String filename1="ID_Mo.txt";
        String line1="";
        String line2="";
        String to_checkarr[];
        boolean f = true;
        try
        {
            FileReader filereader1 = new FileReader(filename1);
            BufferedReader buff1 = new BufferedReader(filereader1);
            while((line1=buff1.readLine())!=null)
            {
                line2=line2+line1;
            }
            buff1.close();
        }
        catch(Exception e)
        {
            System.out.println("File not found");
        }
        to_checkarr=line2.split("::");
        for(int i=1;i<to_checkarr.length;i=i+2)
        {
            if(i!=((to_send*2)+1))
            {
                if(to_checkarr[i].equals(Integer.toString(money_order1)))
                {
                    //System.out.println(to_checkarr[i]+"="+Integer.toString(money_order1));
                    f=f;
                }
                else
                {
                    f=false;
                    break;
                }
            }
        }
        return f;
    }
    public byte[] Signature()
    {
//String to_sign=decrypted_message.get(to_send);
//String to_sign1=to_sign;//"::"+to_sign.hashCode();
        System.out.println("Bank");
//byte[] temp_message2 = to_sign1.getBytes();
        System.out.println(temp_bankstore);
        byte[] signed_message=((((new BigInteger(temp_bankstore)).modPow(privatekey,modulus_bank))).toByteArray());
        return signed_message;
    }
    public void Store_iden(String e,ArrayList<byte[]> MC)
    {
        chal_iden.add(e);
        for(int j=0;j<MC.size();j++)
        {
            Identity_hash_check.add(MC.get(j));
        }
    }
    public String Reveal_Identity()
    {
        String result="";
        String challenge_bits1=chal_iden.get(0);
        String challenge_bits2=chal_iden.get(1);
        System.out.println(challenge_bits1);
        System.out.println(challenge_bits2);
        System.out.println(Identity_hash_check.size());
        for(int it1=0;it1<challenge_bits1.length();it1++)
        {
            char y=challenge_bits1.charAt(it1);
            char y1=challenge_bits2.charAt(it1);
            int z=y-'0';
            int z1=y1-'0';
            if(((z==0)&&(z1==1))||((z==1)&&(z1==0)))
            {
                byte[] p = Identity_hash_check.get(it1);
                byte[] p1=Identity_hash_check.get(it1+10);
                BigInteger q=new BigInteger(p);
                BigInteger q1=new BigInteger(p1);
                byte[] q2=(q.xor(q1)).toByteArray();
                return (new String(q2));
            }
        }
        return result;
    }
    public Bank()
    {
    }
}

//import java.io.*;
//
//public class Bank {
//    private static String bankSignatureList[];
//    private static String fileName = "BankSignatures.txt";
//    private static int[] publicKey = {29,328583};
//    private static int[] privateKey = {169349,328583};
//    private static MoneyOrder[] moneyOrder = new MoneyOrder[100];
//    private static int moneyOrderCount = 0;
//    private static int comparison;
//    private static String merchKey = "";
//
//    public Bank(MoneyOrder[] moneyOrder)
//    {
//        Bank.moneyOrder = moneyOrder;
//    }
//
//    //Generates a random bitstring based of the number of moneyOrders
//    //The bank requests for the customer to make for the one money order.
//    public static void setMerchKey(int n)
//    {
//        for(int i = 0 ; i < n ; i++)
//        {
//            int randomBit = (int) Math.round(Math.random());
//            merchKey = merchKey.concat(Integer.toString(randomBit));
//        }
//    }
//
//    /*
//     * This method obtains an integer
//     * to be used during the unblinding process
//     * which will be used to determine the
//     * legitimacy of the various money orders.
//     */
//    public static void setComparisonInt(int comp)
//    {
//        comparison = comp;
//    }
//
//    /*
//     * This method determines the legitimacy
//     * of the various money orders that the bank
//     * is given.
//     */
//    public static boolean compare(int comp)
//    {
//        if(comparison != comp)
//        {
//            return true;
//        }
//        return false;
//    }
//
//    /*
//     * Returns the public key to a
//     * calling function.
//     */
//    public static int[] getPublicKey()
//    {
//        //returns the public key {e,n}
//        return publicKey;
//    }
//
//    /*
//     * Returns the private key to a
//     * calling function.
//     */
//    public int[] getPrivateKey()
//    {
//        //returns the private key {d,n}
//        return privateKey;
//    }
//
//    /*
//     * Gets a list of bank signatures from a
//     * file and stores them in a signature list.
//     * Then returns the list.
//     */
//    public static String[] getBankSignatures()
//    {
//        try{
//            retrieveSignaturesFromFile();
//        }catch(IOException e)
//        {
//            System.out.println("File does not exist yet!");
//            return null;
//        }
//
//        return bankSignatureList;
//    }
//
//	/*
//	 * This function implements the RSA Digital Signature
//	 * algorithm for the bank, it saves the digital signature
//	 * to the end of the money order file.
//	 * It first opens the file and then retrieves the data within,
//	 * each line is run through the RSA algorithm and the private
//	 * key and public keys are both saved for later use.
//	 */
//
//    public void makeBankDigitalSignature(MoneyOrder MO) throws IOException
//    {
//        //RSA variables.
//        int p = 457;	//A prime
//        int q = 719;	//Another Prime
//        int n = 328583;	//p multiplied by q
//        int tn = 327408;// (p-1)x(q-1)
//        int e = 29;		//selected integer
//        int d = 169349; //Gotten from wolfram alpha.
//
//
//		/*
//		 * This will read each character individually
//		 * and then the RSA function will be applied to the
//		 * character (character is read as an int).
//		 * The bank raises the message multiplied by
//		 * the customer's random number by d.
//		 * The result will be put into a string builder
//		 * which will then be inserted into the file
//		 * at the bottom of the file.
//		 */
//
//        for(int i=0;i<3;i++)
//        {
//            if(i == 0)
//            {
//                MO.setBankSig((int) Math.pow(MO.getAmount(),d)%n, i);
//            }
//            if(i == 1)
//            {
//                MO.setBankSig((int) Math.pow(MO.getUnString(),d)%n, i);
//            }
//            if(i == 2)
//            {
//                if(i == 1)
//                {
//                    MO.setBankSig((int) Math.pow(MO.getK(),d)%n, i);
//                }
//            }
//        }
//
//
//    }
//    /*
//     * Verify the bank's signature given an
//     * unblinded money order.
//     */
//    public static boolean verifyBankSignature(MoneyOrder MO)
//    {
//        int e = publicKey[0];
//        int n = publicKey[1];
//        int i = 0;
//        if(MO.getAmount() != (Math.pow(MO.getBankSig(i), e)%n))
//        {
//            return false;
//        }
//        i++;
//        if(MO.getUnString() != (Math.pow(MO.getBankSig(i), e)%n))
//        {
//            return false;
//        }
//        i++;
//        if(MO.getK() != (Math.pow(MO.getBankSig(i), e)%n))
//        {
//            return false;
//        }
//
//        return true;
//    }
//
//	/*
//	 * This method excepts a file name and a digital signature string
//	 * and stores the digital signature on the file by appending it
//	 * to the end of the file.
//	 */
//
//    public static void storeDigitalSignature(String fileName, String digitalSignature) throws IOException{
//
//        File f = new File(fileName);
//
//        if(!f.exists())
//        {
//            f.createNewFile();
//        }
//
//        try {
//            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
//            out.println(digitalSignature);
//            out.close();
//        } catch (IOException e) {
//            System.out.println("File does not exist.");
//        }
//
//        System.out.println("Digital Signature Written to: " + fileName);
//
//    }
//
//
//
//    /*
//     * A method that obtains all bank uniqueness
//     * strings from a file and stores them in the
//     * bankSignatureList string array.
//     */
//    private static void retrieveSignaturesFromFile() throws IOException {
//        //Total number of uniqueness strings should be at the top of the file.
//        int totalEntries;
//
//        //Set up a buffered reader to read each line from the file.
//        BufferedReader br = new BufferedReader(new FileReader(fileName));
//
//        try {
//            //get the first string at the top of the file
//            //This should be the total number of uniqueness
//            //strings in the file.
//            String line = br.readLine();
//
//            //parse the first line into an integer.
//            totalEntries = Integer.parseInt(line);
//
//            bankSignatureList = new String[totalEntries];
//
//            for(int i = 0; i < totalEntries; i++)
//            {
//
//                bankSignatureList[i] = br.readLine();
//
//            }
//
//        } finally {
//
//            br.close();
//        }
//    }
//
//    //Adds a bank order to the database array of bank orders
//    public static void addBankOrder(MoneyOrder x)
//    {
//        moneyOrder[moneyOrderCount] = x;
//        moneyOrderCount++;
//    }
//
//    //Used to check to see if the bank order has been previously used
//    //If already used, returns the MoneyOrder that is already in the
//    //database to find out who is the cheater, and their identification
//    //information.
//    public static MoneyOrder compareBankUniqueIDs(MoneyOrder x)
//    {
//        for (int i = 0; i < moneyOrderCount; i++)
//        {
//            if (x.getMOID() == moneyOrder[i].getMOID())
//                return moneyOrder[i];
//        }
//        return null;
//    }
//
//}