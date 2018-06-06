package com.digitalcash.digital_cash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import javax.crypto.*;


public class SignInActivity extends AppCompatActivity {

    public static int n;
    public String flag="true";
    public String check;
    public String yeah = "true";
    public String once_twice;

    public static MoneyOrder[] orders;
    public static Merchant merchant = new Merchant();

    private TextView out;
    private TextView out2;
    StringBuilder builder= new StringBuilder();
    StringBuilder builder2= new StringBuilder();
    Button send,bank,yes,no,once,twice;
    public int e,a;

    private EditText amountView;
    private EditText emailView;
    private EditText ssnView;

    //updating balance
    String HttpURL = "http://unitygame.xyz/UpdateBalance.php";
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;

    String reveal;
    Boolean test;
    String message2;
    String message;
    String pass_email;
    String pass_amt;
    String pass_text;
    int bal;

    public String u_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        // To display a welcome text we make use of Calendar class to check the hour of day
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            builder.append("Good Morning,  ");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            builder.append("Good Afternoon,  ");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            builder.append("Good Evening,  ");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            builder.append("Good Night,  ");
        }

        //builder.append("Good Evening,  ") ;
        Bundle b = getIntent().getExtras();
         message = b.getString("username");
        u_name = message;

        builder.append(message);
        builder.append("!!\n\n\t\t\t\t\t\t\t\t\t Welcome to Digital Cash");
        String m = builder.toString(); // m = welcome string
        out = (TextView) findViewById(R.id.welcome);
        out.setText(m);

        // Now get integer value of bal which we obtained and save it for later
       bal = b.getInt("bal");


         builder2.append("Your Current Balance is:\n");
         message2 = b.getString("balance");


        pass_text = b.getString("text");


        builder2.append("$");
        builder2.append(message2);
        builder2.append(".00");
        String m2 = builder2.toString();
        out2 = (TextView) findViewById(R.id.balanceView);
        out2.setText(m2);

        //Buttons used
        send = (Button) findViewById(R.id.send);
        bank = (Button)findViewById(R.id.bank);
        yes =  (Button)findViewById(R.id.yes);
        no =   (Button)findViewById(R.id.no);
        once = (Button)findViewById(R.id.once);
        twice = (Button)findViewById(R.id.twice);

        amountView= (EditText) findViewById(R.id.amount);
        emailView= (EditText) findViewById(R.id.merchantid);
        ssnView = (EditText) findViewById(R.id.ssn);



        // Capture button clicks
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                try{
                    //int bal = Integer.parseInt(message2);
                if (arg0.getId() == R.id.send) {
                    final String ssn9 = ssnView.getText().toString();
                    String amount$ = amountView.getText().toString();
                    int amt = Integer.parseInt(amount$);
                    String email = emailView.getText().toString();


                    if (amount$.matches("") || email.matches("")||ssn9.matches("")) {
                        Toast.makeText(getApplicationContext(), "Field(s) cannot be empty!!  ", Toast.LENGTH_LONG).show();

                    }

                    else if(ssn9.length()<9 || ssn9.length()>9) {
                        Toast.makeText(getApplicationContext(), "Please Enter your 9 Digits", Toast.LENGTH_LONG).show();
                        ssnView.setText("");

                    }

                    else if (amt == 0 || amt < 0){

                        Toast.makeText(getApplicationContext(), "Invalid Amount!! Try Again", Toast.LENGTH_LONG).show();
                        amountView.setText("");
                    }

                    else if((email.equals(ssn9))){

                        Toast.makeText(getApplicationContext(), "Invalid Merchant ID", Toast.LENGTH_LONG).show();
                        amountView.setText("");
                    }
                    else if (email.length()>=9){
                        pass_email = email;
                         pass_amt =  amount$;
                        e = Integer.parseInt(ssn9);
                        a= Integer.parseInt(amount$);

                       // int b  = Integer.parseInt(bal) - a;
                       // final String balance = String.valueOf(b);

                        //init(e,a,u_name);



                           if (once_twice.equals("true")) {
                               updateBalance(pass_amt,u_name,pass_email);
                               Handler h = new Handler();
                               h.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       Handler h = new Handler();
                                       h.postDelayed(new Runnable() {
                                           @Override
                                           public void run() {
                                               Toast.makeText(getApplicationContext(), "Money is transfered to merchant", Toast.LENGTH_LONG).show();
                                           }
                                       }, 3000);
                                      }
                               }, 3000);



                           }


                           else {

                               Handler h = new Handler();
                               h.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_LONG).show();
                                   }
                               }, 600);


                               h.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(getApplicationContext(), "Bank and Merchant detected that you are Double spending", Toast.LENGTH_LONG).show();
                                   }
                               }, 4000);

                               h = new Handler();
                               h.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(getApplicationContext(), "Your identity is revealed as: \n"+ u_name +"  "+ssn9, Toast.LENGTH_LONG).show();
                                   }
                               }, 7100);



                           }





                    }


                        else {
                        Toast.makeText(getApplicationContext(), "Invalid Recipient Id!! Try Again", Toast.LENGTH_LONG).show();
                        emailView.setText("");
                    }
                }



                }

                catch(Exception i){}

            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try{


                String ssn9 = ssnView.getText().toString();
                String amount$ = amountView.getText().toString();
                int amt = Integer.parseInt(amount$);
                    //pass_email = email;
                    ;



                    // Start NewActivity.class
                    if (arg0.getId() == R.id.bank) {
                        //Toast.makeText(getApplicationContext(),"Applying blind Signature,Secret splitting, Bit Commitment", Toast.LENGTH_LONG).show();
                        // Intent myIntent = new Intent(SignInActivity.this,
                        //  LoginActivity.class);
                        //startActivity(myIntent);
                        pass_amt =  amount$;
                        e = Integer.parseInt(ssn9);
                        a= Integer.parseInt(amount$);

                            if (amount$.matches("") || ssn9.matches("")) {
                                Toast.makeText(getApplicationContext(), "Field(s) cannot be empty!!  ", Toast.LENGTH_LONG).show();

                            } else if (ssn9.length() < 9 || ssn9.length() > 9) {
                                Toast.makeText(getApplicationContext(), "Please Enter your 9 Digits", Toast.LENGTH_LONG).show();
                                ssnView.setText("");

                            } else if (amt == 0 || amt < 0) {

                                Toast.makeText(getApplicationContext(), "Invalid Amount!! Try Again", Toast.LENGTH_LONG).show();
                                amountView.setText("");
                            } else {


                                Toast.makeText(getApplicationContext(), "Please Wait...Genretaing required keys....", Toast.LENGTH_SHORT).show();



                                Handler h1 = new Handler();
                                h1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Applying Blind Signature......", Toast.LENGTH_SHORT).show();
                                    }
                                }, 3400);

                                Handler h = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Applying Secret Splitting and Bit Commitment on Money Orders...... ", Toast.LENGTH_SHORT).show();
                                    }
                                }, 6400);

                                //Handler h2 = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Processing!! ", Toast.LENGTH_SHORT).show();
                                    }
                                }, 9400);


                                if (yeah.equals("false")) {
                                    h.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Amount is different!!! Money Order is a Fraud", Toast.LENGTH_LONG).show();
                                        }
                                    }, 12400);


                                } else {
                                    h.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Success!!You can use Money Order", Toast.LENGTH_LONG).show();
                                        }
                                    }, 12400);

                                    h.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Enter Merchant ID and proceed", Toast.LENGTH_LONG).show();
                                        }
                                    }, 15400);
                                }



                            }



                    }
                }
                catch(Exception e){}


            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                if (arg0.getId() == R.id.yes) {
                    yeah = "true";
                    yes.setBackgroundColor(Color.YELLOW);
                    no.setBackgroundColor(Color.BLUE);
                    Toast.makeText(getApplicationContext(),"Amount will be same", Toast.LENGTH_LONG).show();


                }

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                if (arg0.getId() == R.id.no) {
                    yeah = "false";
                    no.setBackgroundColor(Color.YELLOW);
                    yes.setBackgroundColor(Color.BLUE);
                    Toast.makeText(getApplicationContext(),"Amounts will be different", Toast.LENGTH_LONG).show();


                }

            }
        });

        once.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                if (arg0.getId() == R.id.once) {
                    once_twice = "true";
                    once.setBackgroundColor(Color.YELLOW);
                    twice.setBackgroundColor(Color.BLUE);
                    Toast.makeText(getApplicationContext(),"You are using it Once", Toast.LENGTH_LONG).show();


                }

            }
        });

        twice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                if (arg0.getId() == R.id.twice) {
                    once_twice = "false";
                    twice.setBackgroundColor(Color.YELLOW);
                    once.setBackgroundColor(Color.BLUE);
                    Toast.makeText(getApplicationContext(),"You are using it Twice", Toast.LENGTH_LONG).show();


                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(SignInActivity.this,
                    LoginActivity.class);
            startActivity(myIntent);
            return true;
        }

        if (id == R.id.history) {
            Intent myIntent = new Intent(SignInActivity.this,
                    HistoryActivity.class);
            Bundle extras = new Bundle();
            extras.putString("text",pass_text);
            myIntent.putExtras(extras);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public void init(int ssn, int moneyorder,String name){




        boolean t=true;
        int total_orders=0;
        String filename="PK_M.txt";
        String line="";
        String line1="";
        BigInteger public_key;
        BigInteger modulusn;
        BigInteger phi;
        try
        {
            FileReader filereader = new FileReader(filename);
            BufferedReader buff = new BufferedReader(filereader);
            while((line=buff.readLine())!=null)
            {
                line1=line1+line;
            }
            buff.close();
        }
        catch(Exception e)
        {
            System.out.println("File not found");
        }
        String array3[] = line1.split("::");
        public_key = new BigInteger(array3[0]);
        modulusn = new BigInteger(array3[1]);
        phi= new BigInteger(array3[2]);
        BigInteger d =public_key.modInverse(phi);

        Customer c =new Customer();
        Bank b = new Bank();
        //int moneyorder=0;

        c= new Customer(name,ssn);
        total_orders=c.Create_Moneyorder(moneyorder);
        c.Blinding(public_key,modulusn);

        //Bank

        int unblindvar=0;
        System.out.println("Bank option selected");
        b = new Bank(total_orders,d,moneyorder);
        int z =b.to_selectorder();
        unblindvar=z;
        ArrayList<BigInteger> ar1= new ArrayList<>();
        ar1=c.Unblindkey(z);
        ArrayList<byte[]> ar2= new ArrayList<>();
        ar2=c.get_Encrypted();
        String to_print=b.unblindingmoney(ar1,ar2,public_key,modulusn);
        if(to_print.equals("Transaction is valid,all money orders are okay"))
        {
           // System.out.println(to_print);
           // System.out.println("Bank is signing the money order");
            byte[] temp1=b.Signature();
            c.Received_signedorder(temp1,public_key,modulusn);

        }

        //Merchant
        Merchant m = new Merchant();
        String cstr=m.challenge();
        byte[] temp_5=c.sendordertomerchant();
        String m2_temp=m.received_orderfromCustomer(temp_5,public_key,modulusn);
        ArrayList<byte[]> temp_4=new ArrayList<>();
        temp_4=c.challenge_merchant(cstr);
        boolean testing=true;
        try
        {
            testing = m.tocheckhash(temp_4);
        }
        catch(Exception e)
        {
            System.out.println("Try Again");
        }
        System.out.println(testing);
        if(testing)
        {
            System.out.println("Identity bits valid, hash is valid, will send order to bank");
            boolean r = b.ID_check(m2_temp);
            String t2=m.send_chalb();
            String t3="";
            b.Store_iden(t2,temp_4);
            if(r)
            {
                System.out.println("Merchant-Bank Transaction Approved");
                test = true;
                reveal=b.Reveal_Identity();
            }
            else
            {
                System.out.println("CHEATING!!! WILL PUBLISH IDENTITY OF CUSTOMER");
                reveal=b.Reveal_Identity();
                test = false;

               // System.out.println(finalresult);
            }
        }
        else
        {
            System.out.println("Identity bits not valid, hash is not valid, CANNOT PROCESS THIS ORDER");
        }


    }
    public void updateBalance(final String bal_amt, final String username, String mer_email){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(SignInActivity.this,"Processing..Please Wait!!",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                String[] text = httpResponseMsg.split("!"); // we split it because the httpresponse
                // contains some additional data appended to it
                // Now, Compare string before bang!!!


                flag = text[1];
                check = text[0];
                //Toast.makeText(SignInActivity.this, flag, Toast.LENGTH_LONG).show();

                if(flag.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Your Money Will Be Transferred, Please Wait!!  ", Toast.LENGTH_LONG).show();


                    Timer RunSplash = new Timer();

                    // Task to do when the timer ends
                    TimerTask ShowSplash = new TimerTask() {
                        @Override
                        public void run() {
                            // Close SplashScreenActivity.class
                            finish();

                            // Start MainActivity.class
                            Intent myIntent = new Intent(SignInActivity.this,
                                    DriverActivity.class);
                            Bundle extras = new Bundle();
                            // Toast.makeText(SignInActivity.this, balance, Toast.LENGTH_LONG).show();
                            extras.putString("username", message);
                            extras.putString("pass_amt", pass_amt);
                            extras.putString("pass_email", pass_email);
                            extras.putString("balance",message2);
                            extras.putInt("bal",bal);



                            myIntent.putExtras(extras);
                            startActivity(myIntent);
                        }
                    };

                    // Start the timer
                    RunSplash.schedule(ShowSplash, 4000);
                }
                else

                    Toast.makeText(SignInActivity.this, check, Toast.LENGTH_LONG).show();



            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("balancePost",params[0]);

                hashMap.put("usernamePost",params[1]);

                hashMap.put("receiver_emailPost",params[2]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(bal_amt,username,mer_email);
    }

}
