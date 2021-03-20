package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Activity_AddBeer extends AppCompatActivity {
    private ImageView image ;
    public Uri imageUri;
    private FirebaseStorage storage ;
    private StorageReference storageReference ;

    // Pour addbeer:

    private EditText editTextname, editTextorigin ,editTextalcohol, editTextdescription ;
    private ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
        final Button retour=findViewById(R.id.turn );
        final Button add=findViewById(R.id.addBeer);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(), Activity_Home.class);
                startActivity(otherActivity);
                finish();
            }
        });

        //creer les inputs de la biere
        editTextname = (EditText) findViewById(R.id.name) ;
        editTextorigin = (EditText) findViewById(R.id.origin) ;
        editTextalcohol = (EditText) findViewById(R.id.alcohol) ;
        editTextdescription = (EditText) findViewById(R.id.description) ;
        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addBeer() ;
            }
        });

        //choose a picture
        image = findViewById(R.id.image) ;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture() ;
            }
        });
    }

    private void addBeer() {
        String name = editTextname.getText().toString().trim() ;
        String origin = editTextorigin.getText().toString().trim() ;
        String alcohol = editTextalcohol.getText().toString().trim() ;
        String description = editTextdescription.getText().toString().trim() ;

        if (name.isEmpty()){
            editTextname.setError("Name is required");
            editTextname.requestFocus();
            return;
        }
        if (origin.isEmpty()){
            editTextorigin.setError("Origin is required");
            editTextorigin.requestFocus();
            return;
        }
        if (alcohol.isEmpty()){
            editTextalcohol.setError("Alcohol per cent is required");
            editTextalcohol.requestFocus();
            return;
        }
        if (description.isEmpty()){
            editTextdescription.setError("Description is required");
            editTextdescription.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), "Beer added successfully .", Toast.LENGTH_LONG).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Beers");
        Biere beer = new Biere(name, origin,alcohol,description, new String(String.valueOf(image)));
        DatabaseReference newBeer = ref.push();
        newBeer.setValue(beer);
    }


    private void choosePicture() {
        Intent intent = new Intent() ;
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT) ;
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri =data.getData();
            image.setImageURI(imageUri);
            uploadPicture() ;
 ;        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        final String randomKey = UUID.randomUUID().toString();

        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference riversRef = storageReference.child("images/"+randomKey) ;

        riversRef.putFile(imageUri)
              .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      pd.dismiss();
                      Snackbar.make(findViewById(android.R.id.content), "Image uploaded.", Snackbar.LENGTH_LONG).show();

                  }
              })
              .addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      pd.dismiss();
                      Toast.makeText(getApplicationContext(), "Failed to uploaded.", Toast.LENGTH_LONG).show();
                  }
              })
              .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                      double progressPercent = (100.00* snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                      pd.setMessage("Progress: " + (int) progressPercent + "%");
                  }
              });
    }
}