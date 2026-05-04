package com.example.korean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;
import android.util.Log;
import android.util.Patterns;

public class registerPage extends BaseActivity {

    public Button btnRegister;
    public TextView tvGoToLogin, tvPasswordRequirements;
    public EditText etName, etEmail, etPassword, etConfirmPassword;
    DatabaseHelper db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        
        db = new DatabaseHelper(this);
        mAuth = FirebaseAuth.getInstance();

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);
        tvPasswordRequirements = findViewById(R.id.tvPasswordRequirements);

        // Show requirements when password field is focused
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tvPasswordRequirements.setVisibility(View.VISIBLE);
                    validatePassword(etPassword.getText().toString());
                }
            }
        });

        // Real-time password validation
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(registerPage.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(registerPage.this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
                } else if (!isPasswordValid(password)) {
                    Toast.makeText(registerPage.this, "Password does not meet requirements", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        if (!db.checkEmail(email)) {
                            // Attempt to send the Firebase verification link first
                            sendVerificationLink(name, email, password);
                        } else {
                            Toast.makeText(registerPage.this, "User already exists", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(registerPage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validatePassword(String password) {
        StringBuilder requirements = new StringBuilder();
        boolean isValid = true;

        if (password.length() < 8) {
            requirements.append("• Minimum 8 characters\n");
            isValid = false;
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            requirements.append("• Needs at least 1 uppercase letter\n");
            isValid = false;
        }
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            requirements.append("• Needs at least 1 lowercase letter\n");
            isValid = false;
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            requirements.append("• Password needs at least 1 number\n");
            isValid = false;
        }
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            requirements.append("• Needs at least 1 special character\n");
            isValid = false;
        }

        if (isValid && !password.isEmpty()) {
            tvPasswordRequirements.setText("Password looks good!");
            tvPasswordRequirements.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else if (password.isEmpty()) {
            tvPasswordRequirements.setText("Password must have minimum of 8 characters, uppercase, lowercase, number, and special character.");
            tvPasswordRequirements.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            tvPasswordRequirements.setText(requirements.toString().trim());
            tvPasswordRequirements.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }

    private void sendVerificationLink(String name, String email, String password) {
        // 1. Create user in Firebase with Email and Password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Update Firebase profile with the name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(updateTask -> {
                                        // 2. Send standard verification email
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(verifyTask -> {
                                                    if (verifyTask.isSuccessful()) {
                                                        // Save to SQLite immediately so you can see it in the database table
                                                        db.addUser(name, email, password);

                                                        // Also save to SharedPreferences as a backup
                                                        SharedPreferences prefs = getSharedPreferences("KLearnPrefs", MODE_PRIVATE);
                                                        prefs.edit()
                                                                .putString("reg_name", name)
                                                                .putString("reg_email", email)
                                                                .putString("reg_password", password)
                                                                .apply();

                                                        new AlertDialog.Builder(registerPage.this)
                                                                .setTitle("Verify Your Email")
                                                                .setMessage("A verification link has been sent to " + email + ". Please verify your email, then come back and Login.")
                                                                .setPositiveButton("OK", (dialog, which) -> {
                                                                    Intent intent = new Intent(registerPage.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                })
                                                                .setCancelable(false)
                                                                .show();
                                                    }
                                                });
                                    });
                        }
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Registration failed";
                        Toast.makeText(registerPage.this, error, Toast.LENGTH_LONG).show();
                    }
                });
    }
}