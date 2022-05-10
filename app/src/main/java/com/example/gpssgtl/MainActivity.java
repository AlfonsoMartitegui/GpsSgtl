package com.example.gpssgtl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonOne = findViewById(R.id.button);
        buttonOne.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), DayView.class);
            startActivity(i);
           /* Intent i = new Intent(getApplicationContext(), VisorDia.class);
            startActivity(i);*/

           /* Intent activity2Intent = new Intent(getApplicationContext(), VisorDia.class);
            startActivity(activity2Intent);*/
        }
    });


    }
}