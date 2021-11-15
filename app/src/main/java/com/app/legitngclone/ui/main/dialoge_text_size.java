package com.app.legitngclone.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.legitngclone.R;

public class dialoge_text_size extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2,radioButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialoge_text_size);
        final SharedPreferences sharedPreferences = getSharedPreferences("PREFS",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton1.setChecked(sharedPreferences.getBoolean("smallButton",false));
        radioButton2.setChecked(sharedPreferences.getBoolean("normalButton",true));
        radioButton3.setChecked(sharedPreferences.getBoolean("largeButton",false));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioButton){
                    editor.putBoolean("smallButton",true);
                    editor.putBoolean("normalButton",false);
                    editor.putBoolean("largeButton",false);
                }else if(i == R.id.radioButton2){
                    editor.putBoolean("smallButton",false);
                    editor.putBoolean("normalButton",true);
                    editor.putBoolean("largeButton",false);
                }else if(i == R.id.radioButton3){
                    editor.putBoolean("smallButton",false);
                    editor.putBoolean("normalButton",false);
                    editor.putBoolean("largeButton",true);
                }
                editor.commit();
                finish();
            }
        });
    }
}