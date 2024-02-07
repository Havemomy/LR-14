package com.example.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        EditText myEditText = findViewById(R.id.password);
        CheckBox showHidePass = findViewById(R.id.showHidePass);
        TextView textView = findViewById(R.id.register);
        Button buttonLog = findViewById(R.id.loginBTN);

        showHidePass.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                myEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                myEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegActivity.class);
            startActivity(intent);
        });
        buttonLog.setOnClickListener(v -> validateLogin());
    }
    private void validateLogin() {
        String loginEmail = editTextEmail.getText().toString().trim();
        String loginPassword = editTextPassword.getText().toString();

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String storedEmail = preferences.getString("email", "");
        String storedPassword = preferences.getString("password", "");

        if (loginEmail.equals(storedEmail) && loginPassword.equals(storedPassword)) {
            Intent intent = new Intent(this, SuccsessActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Вход успешно выполнен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Неверные данные. Попробуйсте снова", Toast.LENGTH_SHORT).show();
        }
    }

}