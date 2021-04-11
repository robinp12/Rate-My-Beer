package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private String url;
    BottomNavigationView bottomNavigationView;

    // Pour addbeer:

    private EditText editTextname, editTextorigin ,editTextalcohol, editTextdescription ;
    private ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
        final Button add=findViewById(R.id.addBeer);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        AutoCompleteTextView ediText = findViewById(R.id.origin) ;
        //countries
        String[] countries = getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        ediText.setAdapter(adapter);

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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), Activity_Timeline.class);
                        break;
                    case R.id.add:
                        intent = new Intent(getApplicationContext(), Activity_AddBeer.class);
                        break;
                    case R.id.favorite:
                        //intent = new Intent(getApplicationContext(), Activity_Informations.class);
                        return true;
                }
                startActivity(intent);
                finish();
                return true;
            }
        });
    }

    private void addBeer() {
        String name = editTextname.getText().toString().trim() ;
        String origin = editTextorigin.getText().toString().trim() ;
        String alcohol = editTextalcohol.getText().toString().trim();
        alcohol = alcohol.replaceAll(",",".");
        float degree = Float.parseFloat(alcohol) ;
        String description = editTextdescription.getText().toString().trim() ;

        if (name.isEmpty()){
            editTextname.setError("Nom requis");
            editTextname.requestFocus();
            return;
        }
        if (origin.isEmpty()){
            editTextorigin.setError("Origine requis");
            editTextorigin.requestFocus();
            return;
        }
        if ((degree > 71.0) || (degree < 0.0) ){
            editTextalcohol.setError("Degré d'alcool incorrect");
            editTextalcohol.requestFocus();
            return;
        }
        if (description.isEmpty()){
            editTextdescription.setError("Description requis");
            editTextdescription.requestFocus();
            return;
        }

        Toast.makeText(getApplicationContext(), "Bière ajoutée avec succès", Toast.LENGTH_LONG).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Beers");
        if(url==null){
            url="https://firebasestorage.googleapis.com/v0/b/rate-my-beer-8566e.appspot.com" +
                    "/o/images%2FbiereSimple.JPEG?alt=media&token=d21c359e-ee6c-4288-828d-77e2acb7d19c";
        }
        Biere beer = new Biere(name, origin,degree,description, url);
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

    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        final String randomKey = editTextname.getText().toString().trim();

        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference riversRef = storageReference.child("images/"+randomKey) ;

        riversRef.putFile(imageUri)
              .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      pd.dismiss();
                      Snackbar.make(findViewById(android.R.id.content), "Image uploaded.", Snackbar.LENGTH_LONG).show();
                       taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               url=uri.toString();
                           }
                       });


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
