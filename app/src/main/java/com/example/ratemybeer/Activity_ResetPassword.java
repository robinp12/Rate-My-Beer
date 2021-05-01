package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_ResetPassword extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton ;
    private ProgressBar progressBar ;

    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById(R.id.email) ;
        resetPasswordButton = (Button) findViewById(R.id.resetPassword) ;
        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword() ;
            }
        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email requis");
            emailEditText.requestFocus();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Veuillez entrer une adresse valide");
            emailEditText.requestFocus();
            return ;
        }
        //progressBar.setVisibility(View.VISIBLE) ;
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Activity_ResetPassword.this, "Un mail pour réinitialiser le mot de passe a été envoyé", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Activity_ResetPassword.this, "Veuillez réessayer, une erreur est survenue", Toast.LENGTH_LONG).show();
                }
            }
        }) ;
    }
}