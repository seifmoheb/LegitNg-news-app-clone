package com.app.legitngclone.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.app.legitngclone.R;
import com.app.legitngclone.ui.settings.SettingsFragment;

public class dialoge_notifiications extends AppCompatActivity {

    Switch aSwitch,bSwitch,cSwitch;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialoge_pop);
        aSwitch = findViewById(R.id.switch1);
        bSwitch = findViewById(R.id.switch2);
        cSwitch = findViewById(R.id.switch3);
        apply = findViewById(R.id.apply);
        final SharedPreferences sharedPreferences = getSharedPreferences("PREFS",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        bSwitch.setChecked(sharedPreferences.getBoolean("sound",true));

        cSwitch.setChecked(sharedPreferences.getBoolean("vibration",true));
        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("sound",b);
                editor.commit();
            }
        });
        cSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("vibration",b);
                editor.commit();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}