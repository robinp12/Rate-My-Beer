package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_Register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner, registerUser ;
    private EditText editTextFirstName, editTextLastName, editTextAge ,editTextEmail, editTextPassword, editTextPseudo ;
    private ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        banner = (TextView) findViewById(R.id.banner) ;
        banner.setOnClickListener(this) ;

        registerUser = (Button) findViewById(R.id.registerUser) ;
        registerUser.setOnClickListener(this);

        editTextFirstName = findViewById(R.id.fullname) ;
        editTextLastName = findViewById(R.id.fullname2) ;
        editTextPseudo = findViewById(R.id.pseudo) ;
        editTextAge = findViewById(R.id.age) ;
        editTextEmail = findViewById(R.id.email) ;
        editTextPassword = findViewById(R.id.password) ;

        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, Activity_Login.class));
                break ;
            case R.id.registerUser :
                registerUser() ;
                break ;
        }
    }

    private void registerUser() {
        String firstname = editTextFirstName.getText().toString().trim() ;
        String lastname = editTextLastName.getText().toString().trim() ;
        String Pseudo = editTextPseudo.getText().toString().trim() ;
        String age = editTextAge.getText().toString().trim() ;
        String email = editTextEmail.getText().toString().trim() ;
        String password = editTextPassword.getText().toString().trim() ;

        if (firstname.isEmpty() || (firstname.length() < 2)){
            editTextFirstName.setError("Prénom requis ou trop court");
            editTextFirstName.requestFocus();
            return;
        }
        if (lastname.isEmpty() || (lastname.length() < 2)){
            editTextLastName.setError("Nom requis ou trop court");
            editTextLastName.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Mot de passe requis");
            editTextPassword.requestFocus();
            return;
        }
        if (age.isEmpty() || Integer.parseInt(age) < 16 || Integer.parseInt(age) > 120){
            editTextAge.setError("Age requis ou incorrect");
            editTextAge.requestFocus();
            return;
        }
        if (email.isEmpty()){
            editTextEmail.setError("Email requis");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email incorrect !");
            editTextEmail.requestFocus() ;
            return ;
        }
        if(password.length() < 6){
            editTextPassword.setError("Longueur minimum de 6 charactères!");
            editTextPassword.requestFocus();
            return ;
        }
        if (Pseudo.isEmpty() || (Pseudo.length() < 5)){
            editTextPseudo.setError("Pseudo requis ou trop court");
            editTextPseudo.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User (firstname,lastname,age,email,Pseudo);
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Activity_Register.this, "Inscrit avec succès!",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Activity_Register.this, Activity_Login.class));
                                    }
                                    else {
                                        Toast.makeText(Activity_Register.this, "Erreur d'inscription! Veuillez réessayer.",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(lastname + " " + firstname).build();
                        userr.updateProfile(profileUpdates);
                    }
                    else {
                        Toast.makeText(Activity_Register.this, "Erreur d'inscription! Veuillez réessayer.",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
    }
}