package com.example.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    EditText myEditText = findViewById(R.id.password);
    CheckBox showHidePass = findViewById(R.id.showHidePass);

//    showHidePass.setOnCheckedChangedListener(new CompoundButton.OnCheckedChangeListener()) {
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked;){
//            if (isChecked) {
//                myEditText.setTransformationMethod(null);
//            } else {
//                myEditText.setTransformationMethod(new PasswordTransformationMethod());
//            }
//        }
//    }
}