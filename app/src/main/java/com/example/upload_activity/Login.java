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

public class Login extends AppCompatActivity {

    private EditText loginEmailtext;
    private EditText loginPasswordtext;
    private Button loginbtn;
    private Button loginRegisterbtn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.login_progress);
        checkBox = findViewById(R.id.login_checkbox);

        loginEmailtext = findViewById(R.id.login_email);
        loginPasswordtext = findViewById(R.id.login_password);
        loginbtn = findViewById(R.id.login_button);
        loginRegisterbtn = findViewById(R.id.login_register_button);



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to click user email and psswd and comparing data with string object ie simran and datatype is not same covert both
                //  object into string
                String loginEmail = loginEmailtext.getText().toString();
                String loginPass = loginPasswordtext.getText().toString();

                if(!TextUtils.isEmpty(loginEmail) || !TextUtils.isEmpty(loginPass))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(loginEmail , loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                sendtoMain();
                            }
                            else
                            {
                                String error = task.getException().getMessage();
                                Toast.makeText(getApplicationContext() , "Error :" +error , Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);

        }
    }
});

        }
        }


        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b)
        {
        loginPasswordtext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else
        {
        loginPasswordtext.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        }
        });

        loginRegisterbtn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(Login.this , Registration.class);
        startActivity(intent);
        //  finish();
        }
        });
        }

@Override
protected void onStart() {
        super.onStart();

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentuser != null)
        {
        Intent intent = new Intent(Login.this , MainActivity.class);
        startActivity(intent);
        finish();
        }

        }
private  void  sendtoMain()
        {
        Intent intent = new Intent(Login.this , MainActivity.class);
        startActivity(intent);
        finish();

        }



        }
