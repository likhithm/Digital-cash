package com.digitalcash.digital_cash;
/**
 * Created by Likhith on 4/16/2018.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    String HttpURL = "http://unitygame.xyz/InsertUser.php";
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;

    Boolean CheckEditText ;

    private EditText passwordView;
    private EditText confView;
    private EditText emailView;
    private EditText userView;

    private String email;
    private String password;
    private String username;
    private String confirm;

    private Button register,done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        passwordView = (EditText) findViewById(R.id.pw);
        confView = (EditText) findViewById(R.id.confirmPw);
        emailView = (EditText) findViewById(R.id.emailid);
        userView = (EditText) findViewById(R.id.username);

        register = (Button)findViewById(R.id.register);
        done = (Button)findViewById(R.id.done1);

        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                if (arg0.getId() == R.id.done1) {
                    //Toast.makeText(getApplicationContext(),"Please Fill all 15 Array Elements ( up to A[14] )", Toast.LENGTH_LONG).show();

                    Intent myIntent = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    startActivity(myIntent);

                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {



                if(arg0.getId()==R.id.register) {
                    CheckEditTextIsEmptyOrNot();
                    if(CheckEditText) {
                        UserRegisterFunction(email, username, password);

                    }

                    else {
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Registration Failed!!!", Toast.LENGTH_SHORT).show();
                            }
                        }, 3000);

                    }
                }
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        confirm  = confView.getText().toString();
        username = userView.getText().toString();
        email = emailView.getText().toString();
        password= passwordView.getText().toString();



        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
        {

            CheckEditText = false;

            Toast.makeText(RegisterActivity.this,"Empty Fields", Toast.LENGTH_LONG).show();


        }

        else if(email.length()<8){

            CheckEditText = false;
            Toast.makeText(RegisterActivity.this,"Invalid Key length", Toast.LENGTH_LONG).show();

        }
        else if(!(username.contains("_")||username.contains("@")||username.contains("!")||username.contains("."))){

            CheckEditText = false;
            Toast.makeText(RegisterActivity.this,"Invalid username(either too short/doesn't have special character(s)", Toast.LENGTH_LONG).show();

        }


        else if(password.length()<8){
            CheckEditText = false;
            Toast.makeText(RegisterActivity.this,"Invalid Password length", Toast.LENGTH_LONG).show();

        }

        else if(!(password.equals(confirm))){  // will enter if block when passwords do n0t match
            CheckEditText = false;
            Toast.makeText(RegisterActivity.this,"Passwords did not match", Toast.LENGTH_LONG).show();
        }

        else {

            CheckEditText = true ;
        }

    }


    public void UserRegisterFunction(final String email, final String username, final String password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(RegisterActivity.this,"Registering user..!!",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(RegisterActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("emailPost",params[0]);

                hashMap.put("usernamePost",params[1]);

                hashMap.put("passwordPost",params[2]);

                // hashMap.put("password",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(email,username,password);
    }
}
