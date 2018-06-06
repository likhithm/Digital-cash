package com.digitalcash.digital_cash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DriverActivity extends AppCompatActivity {

    private TextView out;
    StringBuilder builder= new StringBuilder();
    Button done_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Bundle b = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);




        String message1 = b.getString("pass_email");
        out = (TextView) findViewById(R.id.transfer);
        builder.append("Thank you for choosing Digital Cash,\n\n'$");
        String message2 = b.getString("pass_amt");  //message to is amount sent here!!

        //important part ( to update balance
        int bal = b.getInt("bal"); // This value is parsed from Db->php->LoginActivity->SignInActivity->Driver
        int a = Integer.parseInt(message2);

        int res = bal - a; // update balance and store in res
       final  String balance = String.valueOf(res);
        builder.append(message2);
        builder.append("' was sent to ");
        builder.append(message1);
        String m = builder.toString(); // m = transfer string
        out.setText(m);

        done_bt = (Button) findViewById(R.id.done);
        done_bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (arg0.getId() == R.id.done) {

                    //String balance = b.getString("balance");
                    String username = b.getString("username");
                    //int bal = b.getInt("bal");
                    //String balance = String.valueOf(bal);

                    Intent myIntent = new Intent(DriverActivity.this,
                            SignInActivity.class);
                    Bundle extras = new Bundle();

                    extras.putString("username",username);
                    extras.putString("balance",balance);
                    myIntent.putExtras(extras);
                    startActivity(myIntent);

                }

            }
            });
    }

}
