package com.example.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;

public class RegActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextNumber;
    private EditText editTextDateOfBirth;
    private EditText editTextPassword;
    private EditText editTextPasswordAgain;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        editTextEmail = findViewById(R.id.email);
        editTextNumber = findViewById(R.id.phoneNumber);
        editTextDateOfBirth = findViewById(R.id.birthday);
        editTextPassword = findViewById(R.id.password);
        editTextPasswordAgain = findViewById(R.id.passwordAgain);
        Button buttonRegister = findViewById(R.id.registerBTN);

        TextView textView = findViewById(R.id.register);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}