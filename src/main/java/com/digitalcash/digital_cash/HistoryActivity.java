package com.digitalcash.digital_cash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryActivity extends AppCompatActivity {

    private TextView out;
    String message;

   // Button re_use;

    //StringBuilder builder= new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle b = getIntent().getExtras();
         message = b.getString("text");



         out = (TextView) findViewById(R.id.text_mer);
         out.setText(message);


        //re_use = (Button) findViewById(R.id.reuse);
        /*re_use.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (arg0.getId() == R.id.reuse) {

                    Toast.makeText(getApplicationContext(), "Bank cannot Process this!! You are using same money order", Toast.LENGTH_LONG).show();


                }

            }
        });*/
    }


}
