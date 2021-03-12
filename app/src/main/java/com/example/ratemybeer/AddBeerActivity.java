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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddBeerActivity extends AppCompatActivity {
    private ImageView image ;
    public Uri imageUri;
    private FirebaseStorage storage ;
    private StorageReference storageReference ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
        final Button retour=findViewById(R.id.turn );
        final Button add=findViewById(R.id.addBeer);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),Activity_home.class);
                startActivity(otherActivity);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),TestActivity.class);
                startActivity(otherActivity);
                finish();
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
        pd.setTitle("Uploading Image...");
        pd.show();
 ;

          final String randomKey = UUID.randomUUID().toString();
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