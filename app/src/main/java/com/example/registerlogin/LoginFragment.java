package com.example.registerlogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.registerlogin.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.showHidePass.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        binding.register.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.main_fragment_host);
            navController.navigate(R.id.registerFragment);
        });
        binding.loginBTN.setOnClickListener(v -> validateLogin());

        return binding.getRoot();
    }
    private void validateLogin() {
        String loginEmail = binding.email.getText().toString().trim();
        String loginPassword = binding.password.getText().toString();

        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String storedEmail = preferences.getString("email", "");
        String storedPassword = preferences.getString("password", "");

        if (loginEmail.equals(storedEmail) && loginPassword.equals(storedPassword)) {

            NavController navController = Navigation.findNavController(requireActivity(), R.id.main_fragment_host);
            navController.navigate(R.id.successActiv);

            Toast.makeText(requireContext(), "Вход успешно выполнен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Неверные данные. Попробуйсте снова", Toast.LENGTH_SHORT).show();
        }
    }

}
