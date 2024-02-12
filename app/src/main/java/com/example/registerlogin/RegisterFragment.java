package com.example.registerlogin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.registerlogin.databinding.FragmentRegisterBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;

    private EditText editTextEmail;
    private EditText editTextNumber;
    private EditText editTextDateOfBirth;
    private EditText editTextPassword;
    private EditText editTextPasswordAgain;
    private DatePickerDialog datePickerDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        editTextEmail = binding.email;
        editTextNumber = binding.phoneNumber;
        editTextDateOfBirth = binding.birthdayText;
        editTextPassword = binding.password;
        editTextPasswordAgain = binding.passwordAgain;

        TextView textView = binding.register;

        PhoneNumberFormat.formatPhoneNumber(editTextNumber);

        textView.setOnClickListener(v -> {
            FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();
        });

        binding.registerBTN.setOnClickListener(v -> {
            if (validateAndSaveData()) {
                handleRegistrationSuccess();
            }
        });

        binding.birthday.setEndIconOnClickListener(this::OpenDatePicker);

        return binding.getRoot();
    }

    private boolean validateAndSaveData() {
        String email = editTextEmail.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();
        String dateOfBirth = editTextDateOfBirth.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String repeatPassword = editTextPasswordAgain.getText().toString();

        if (!isValidEmail(email)) {
            editTextEmail.setError("Неверный email адрес");
            return false;
        }

        if (!isValidPhoneNumber(number)) {
            editTextNumber.setError("Неверный номер телефона");
            return false;
        }

        if (!isValidDateOfBirth(dateOfBirth)) {
            editTextDateOfBirth.setError("Неверная дата рождения");
            return false;
        }

        if (!isValidPassword(password)) {
            editTextPassword.setError("Неверный пароль. Длина пароля должна быть не менее 8 символов, должна присутстовать хотя бы 1 заглавная буква и 1 цифра");
            return false;
        }

        if (!password.equals(repeatPassword)) {
            editTextPasswordAgain.setError("Пароли не совпадают");
            return false;
        }

        SharedPreferences preferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String hashedPassword = password;

        editor.putString("email", email);
        editor.putString("number", number);
        editor.putString("dateOfBirth", dateOfBirth);
        editor.putString("password", hashedPassword);
        editor.apply();

        return true;
    }

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private void OpenDatePicker(View view) {
        MaterialDatePicker<Long> dialog = MaterialDatePicker.Builder.datePicker()
                .build();
        dialog.addOnPositiveButtonClickListener(selection -> {
            final String format = dateFormat.format(new Date(selection));
            binding.birthdayText.setText(format);
        });
        FragmentManager supportFragmentManager = requireActivity().getSupportFragmentManager();
        dialog.show(supportFragmentManager, null);
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        String domainRegex = "^[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        if (!email.matches(emailRegex)) {
            return false;
        }

        String[] parts = email.split("@");
        if (parts.length == 2 && !parts[1].matches(domainRegex)) {
        }

        return true;
    }

    private boolean isValidPhoneNumber(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }

        String digitsOnly = number.replaceAll("[^0-9]", "");

        String phoneRegex = "^\\+7\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}$";

        if (digitsOnly.length() < 11) {
            return false;
        }

        return true;
    }

    private boolean isValidDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return false;
        }

        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

        if (!dateOfBirth.matches(dateRegex)) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date dob = sdf.parse(dateOfBirth);
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(dob);

            Calendar currentDate = Calendar.getInstance();


            currentDate.add(Calendar.YEAR, -18);

            if (dobCalendar.after(currentDate)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            return false;
        }

        return true;
    }

    private void handleRegistrationSuccess() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.main_fragment_host);
        navController.navigate(R.id.action_registerFragment_to_loginFragment);

        Toast.makeText(requireContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show();
    }
}
