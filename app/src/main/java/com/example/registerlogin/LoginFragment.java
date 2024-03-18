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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Optional;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PENsl").whereEqualTo ("email",loginEmail)
                .whereEqualTo("password", loginPassword).get().addOnSuccessListener(command -> {
                    if (command.toObjects(User.class).size()>0) {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.main_fragment_host);
                        navController.navigate(R.id.successActiv);

                        Toast.makeText(requireContext(), "Вход успешно выполнен", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Неверные данные. Попробуйсте снова", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
