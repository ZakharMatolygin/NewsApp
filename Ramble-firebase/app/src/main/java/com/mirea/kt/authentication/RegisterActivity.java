package com.mirea.kt.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.mirea.kt.MainActivity;
import com.mirea.kt.R;
import com.mirea.kt.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.signUpButton.setOnClickListener(v -> {
            String email = String.valueOf(binding.emailTextField.getText());
            String password = String.valueOf(binding.passwordTextField.getText());

            if (inputCheck(email, password)) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_LONG).show();
                });
            } else Toast.makeText(RegisterActivity.this, R.string.failed, Toast.LENGTH_LONG).show();
        });

        binding.signInTextButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private Boolean inputCheck(String email, String password) {
        return !(TextUtils.isEmpty(email) || TextUtils.isEmpty(password));
    }
}