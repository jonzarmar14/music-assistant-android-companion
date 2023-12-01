// WelcomeActivity.java
package com.example.musicassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;


import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private EditText editTextIP;
    private EditText editTextPort;
    private Switch switchConnection;
    private CheckBox checkBoxRemember;
    private Button buttonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        editTextIP = findViewById(R.id.editTextIP);
        editTextPort = findViewById(R.id.editTextPort);
        switchConnection = findViewById(R.id.switchConnection);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);
        buttonConnect = findViewById(R.id.buttonConnect);

        // Load saved preferences
        SharedPreferences prefs = getSharedPreferences("MusicAssistantPrefs", MODE_PRIVATE);
        String savedIp = prefs.getString("IP", "");
        String savedPort = prefs.getString("Port", "");
        boolean savedIsHttps = prefs.getBoolean("IsHttps", false);
        boolean savedRemember = prefs.getBoolean("Remember", false);

        // Populate the input fields with saved values
        editTextIP.setText(savedIp);
        editTextPort.setText(savedPort);
        switchConnection.setChecked(savedIsHttps);
        checkBoxRemember.setChecked(savedRemember);

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = editTextIP.getText().toString();
                String port = editTextPort.getText().toString();
                boolean isHttps = switchConnection.isChecked();
                boolean remember = checkBoxRemember.isChecked();

                // Only save the input values if "Remember" is checked
                if (remember) {
                    // Save the input values to SharedPreferences
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("IP", ip);
                    editor.putString("Port", port);
                    editor.putBoolean("Remember", remember);
                    editor.putBoolean("IsHttps", isHttps);
                    editor.apply();  // Ensure changes are saved
                }

                // Pass the input values to the MainActivity
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("IP", ip);
                intent.putExtra("Port", port);
                intent.putExtra("IsHttps", isHttps);
                startActivity(intent);
            }
        });
    }
}
