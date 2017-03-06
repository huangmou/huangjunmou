package com.welink.myapp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.welink.myapp.MainActivity;
import com.welink.myapp.R;

public class NewWelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button button= (Button) findViewById(R.id.bt_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                changeIcon();
                startActivity(new Intent(NewWelcomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void changeIcon() {
        PackageManager pm = getApplicationContext().getPackageManager();
        System.out.println(getComponentName());
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(
                        getBaseContext(),
                        "com.welink.myapp.activity.NewWelcomeActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void changeIconBack() {
        PackageManager pm = getApplicationContext().getPackageManager();
        System.out.println(getComponentName());
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(
                        getBaseContext(),
                        "com.welink.myapp.activity.WelcomeActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }



}