package com.example.ia_hs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView sourceCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        sourceCode = findViewById(R.id.sourceCode);
        sourceCode.setMovementMethod(LinkMovementMethod.getInstance());

    }
}