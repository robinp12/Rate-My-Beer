
package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Activity_AddBeer extends AppCompatActivity {
    private ImageView image ;
    private Button boutonImage;
    public Uri imageUri;
    private FirebaseStorage storage ;
    private StorageReference storageReference ;
    private String url;
    String origine;


    BottomNavigationView bottomNavigationView;
    AutoCompleteTextView autoCompleteTextView;

    // Pour addbeer:

    private EditText editTextname, editTextorigin ,editTextalcohol, editTextdescription ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
        final Button add=findViewById(R.id.addBeer);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //countries
        autoCompleteTextView=findViewById(R.id.autocomplete);
        String[] countries = getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.countries_item,countries);

        autoCompleteTextView.setAdapter(adapter);



        //creer les inputs de la biere
        editTextname = (EditText) findViewById(R.id.name) ;
        editTextorigin=(EditText)findViewById(R.id.autocomplete);
        editTextalcohol = (EditText) findViewById(R.id.alcohol) ;
        editTextdescription = (EditText) findViewById(R.id.description) ;




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int result=addBeer() ;
               if(result==1){
                   Intent intent= new Intent(getApplicationContext(),Activity_Timeline.class);
                   startActivity(intent);
                   finish();
               }

            }
        });

        //choose a picture
        image = findViewById(R.id.image);
        boutonImage = findViewById(R.id.btnImg) ;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        boutonImage.setOnClickListener(new View.OnClickListener() {
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
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.add:
                        break;
                    case R.id.favorite:
                        intent = new Intent(getApplicationContext(), activity_favoris.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });


    }

    private int  addBeer() {

        String name = editTextname.getText().toString().trim() ;
        String origin=editTextorigin.getText().toString().trim();
        String alcohol = editTextalcohol.getText().toString().trim();
        String description = editTextdescription.getText().toString().trim() ;

        if (name.isEmpty()){
            editTextname.setError("Nom requis");
            editTextname.requestFocus();
            return -1;
        }
        if (origin.isEmpty()){
            editTextorigin.setError("Origine requis");
            editTextorigin.requestFocus();
            return -1;
        }

        if (alcohol.isEmpty()){
            editTextalcohol.setError("Degré d'alcool requis");
            editTextalcohol.requestFocus();
            return -1;
        }

        alcohol = alcohol.replaceAll(",",".");
        float degree = Float.parseFloat(alcohol) ;

        if ((degree > 71.0) || (degree < 0.0) ){
            editTextalcohol.setError("Degré d'alcool incorrect");
            editTextalcohol.requestFocus();
            return -1;
        }

        if (description.isEmpty()){
            editTextdescription.setError("Description requis");
            editTextdescription.requestFocus();
            return -1;
        }
        Toast.makeText(getApplicationContext(), "Bière ajoutée avec succès", Toast.LENGTH_LONG).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(url==null){
            url="https://firebasestorage.googleapis.com/v0/b/rate-my-beer-8566e.appspot.com" +
                    "/o/images%2FbiereSimple.JPEG?alt=media&token=d21c359e-ee6c-4288-828d-77e2acb7d19c";
        }
        Biere beer = new Biere(name, origin,degree,description, url, user.getUid());
        ref.child("Beers").child(name).setValue(beer);
        return 1;
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

        pd.setTitle("Upload de l'image en cours...");
        pd.show();

        StorageReference riversRef = storageReference.child("images/"+randomKey) ;

        riversRef.putFile(imageUri)
              .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      pd.dismiss();
                      Snackbar.make(findViewById(android.R.id.content), "Image uploadé", Snackbar.LENGTH_LONG).show();
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
                      Toast.makeText(getApplicationContext(), "Erreur lors du chargement de l'image", Toast.LENGTH_LONG).show();
                  }
              })
              .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                      double progressPercent = (100.00* snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                      pd.setMessage("Chargement: " + (int) progressPercent + "%");
                  }
              });
    }
}
