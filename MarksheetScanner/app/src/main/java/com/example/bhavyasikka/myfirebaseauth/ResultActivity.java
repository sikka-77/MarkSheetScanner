package com.example.bhavyasikka.myfirebaseauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        JSONArray result;
        try {
            result = new JSONArray(intent.getStringExtra("result")); // this json array contains the data length = 24
            TextView resultView = findViewById(R.id.result);
            String text = "\tQ\t|\ta\t|\tb\t|\tc\t|\td\t\n"
                    + "\t1\t|\t" + result.getString(0) + "\t|\t" + result.getString(1) + "\t|\t" + result.getString(2) + "\t|\t" + result.getString(3) + "\t\n"
                    + "\t2\t|\t" + result.getString(4) + "\t|\t" + result.getString(5) + "\t|\t" + result.getString(6) + "\t|\t" + result.getString(7) + "\t\n"
                    + "\t3\t|\t" + result.getString(8) + "\t|\t" + result.getString(9) + "\t|\t" + result.getString(10) + "\t|\t" + result.getString(11) + "\t\n"
                    + "\t4\t|\t" + result.getString(12) + "\t|\t" + result.getString(13) + "\t|\t" + result.getString(14) + "\t|\t" + result.getString(15) + "\t\n"
                    + "\t5\t|\t" + result.getString(16) + "\t|\t" + result.getString(17) + "\t|\t" + result.getString(18) + "\t|\t" + result.getString(19) + "\t\n"
                    + "\t6\t|\t" + result.getString(20) + "\t|\t" + result.getString(21) + "\t|\t" + result.getString(22) + "\t|\t" + result.getString(23) + "\t\n";


            resultView.setText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
