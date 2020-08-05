package com.example.upload_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
    private EditText register_email_field;
    private EditText register_pass_field;
    private EditText registe_confir_pass_field;
    private Button reg_btn;
    private Button reg_login_btn;
    private ProgressBar reg_progressBar;
    private CheckBox checkBox;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        reg_progressBar = findViewById(R.id.register_progress);
        checkBox = findViewById(R.id.register_checkbox);

        register_email_field = findViewById(R.id.register_email);
        register_pass_field  = findViewById(R.id.register_password);
        registe_confir_pass_field = findViewById(R.id.register_confirm_password);
        reg_btn = findViewById(R.id.register_button);
        reg_login_btn = findViewById(R.id.register_login_button);
        mAuth = FirebaseAuth.getInstance();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    register_pass_field.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    registe_confir_pass_field.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else

                {
                    register_pass_field.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    registe_confir_pass_field.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = register_email_field.getText().toString();
                String pass = register_pass_field.getText().toString();
                String confirm_pass = registe_confir_pass_field.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass) || !TextUtils.isEmpty(confirm_pass))
                {
                    if(pass.equals(confirm_pass))
                    {
                        reg_progressBar.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    sendtoMain();
                                }
                                else
                                {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(), "Error :" +error, Toast.LENGTH_LONG).show();
                                }
                                reg_progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Confirm Password and password field doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this , Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null)
        {
            sendtoMain();
        }
    }
    private  void  sendtoMain()
    {


        Intent intent = new Intent(Registration.this , MainActivity.class);
        startActivity(intent);
        finish();

    }
}