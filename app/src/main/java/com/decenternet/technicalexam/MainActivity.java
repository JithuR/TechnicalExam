package com.decenternet.technicalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBrandingLogo;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivBrandingLogo = (ImageView) findViewById(R.id.ivBrandingLogo);
        txtTitle       = (TextView) findViewById(R.id.textView);

        txtTitle.setText(getResources().getString(R.string.quote));
        ivBrandingLogo.setImageDrawable(getResources().getDrawable(R.drawable.fear_overcome));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,TaskActivity.class));
                finish();
            }
        },3000);
    }
}