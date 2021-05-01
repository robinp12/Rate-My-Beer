package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {
    private TextView register, forgotPassword ;
    private EditText editTextEmail, editTextPassword;
    private Button signIn ;
    private CheckBox checkBox ;
    private FirebaseAuth mAuth ;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = findViewById(R.id.signIn) ;
        signIn.setOnClickListener(this);

        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");

        if(checkbox.equals("true")){

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                Intent intent = new Intent(Activity_Login.this, Activity_Timeline.class);
                startActivity(intent);
                finish();
                //Toast.makeText(Activity_Login.this,"Utilisateur : " + user.getDisplayName(),Toast.LENGTH_LONG).show();
                //Toast.makeText(Activity_Login.this,"Identifiant utilisateur : " + user.getUid(),Toast.LENGTH_LONG).show();
            }
        }else if(checkbox.equals("false")){
            Toast.makeText(this,"Veuillez vous connecter", Toast.LENGTH_SHORT).show();
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }else if(!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, Activity_Register.class)) ;
                break ;
            case R.id.signIn:
                userLogin() ;
                break ;
            case R.id.forgotPassword:
                startActivity(new Intent(this, Activity_ResetPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email requis");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Veuillez entrer une adresse mail valide");
            editTextEmail.requestFocus() ;
            return ;
        }

        if(password.length() < 6){
            editTextPassword.setError("Taille minimum de 6 charactères");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

 ;                  if(user.isEmailVerified()){
                        startActivity(new Intent(Activity_Login.this, Activity_Timeline.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(Activity_Login.this,"Veuillez vérifier vos mails pour confirmer l'inscription",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(Activity_Login.this, "La connexion a échoué, veuillez vérifier vos identifiants", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}