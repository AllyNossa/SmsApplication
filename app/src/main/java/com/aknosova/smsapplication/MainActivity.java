package com.aknosova.smsapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    public TextView textView;
    private Button button;
    private EditText editText;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
            final String[] permissions = new String[]{Manifest.permission.RECEIVE_SMS};
            int permissionRequestCode = 123;
            ActivityCompat.requestPermissions(this, permissions, permissionRequestCode);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String messageContent = extras.getString("message");
            String messageSender = extras.getString("from");
            textView.append(messageSender + ": " + messageContent + "\n");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String number = "89001000000";
                if (editText.getText() != null) {
                    message = editText.getText().toString();
                }

                String toNumberSms = "smsto:" + number;
                Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse(toNumberSms));
                sms.putExtra("sms_body", message);
                startActivity(sms);

                textView.append(getString(R.string.you) + editText.getText().toString() + "\n");
            }
        });
    }

    private void initViews() {
        button = findViewById(R.id.button);
        editText = findViewById(R.id.input);
        textView = findViewById(R.id.textview);
    }
}
