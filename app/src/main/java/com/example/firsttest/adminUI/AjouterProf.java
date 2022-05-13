package com.example.firsttest.adminUI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firsttest.R;
import com.example.firsttest.databinding.ActivityAjouterProfBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AjouterProf extends AppCompatActivity {

    SimpleDateFormat formatter;
    Date now;
    String fileName;

    EditText prof_nom,prof_prenom,prof_dep,prof_tel,prof_email;
    MaterialButton btnAdd;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    CircleImageView prof_photo;
    Button btnSel,btnUpload;
    ActivityAjouterProfBinding binding;
    Uri imgUri;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjouterProfBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        formatter= new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.FRANCE);
        now=new Date();
        fileName=formatter.format(now);
        //progress dialog
        progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
      //  progressDialog.setMessage("Loading..");
      //  progressDialog.show();
        db =FirebaseFirestore.getInstance();
        prof_nom=findViewById(R.id.prof_nom);
        prof_prenom=findViewById(R.id.prof_prenom);
        prof_dep=findViewById(R.id.prof_dep);
        prof_tel=findViewById(R.id.prof_tel);
        prof_email=findViewById(R.id.prof_email);
        prof_photo=findViewById(R.id.prof_photo);

        binding.btnSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        //btnUpload = findViewById(R.id.btnUpload);
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = prof_nom.getText().toString();
                String prenom = prof_prenom.getText().toString();
                String tel = prof_tel.getText().toString();
                String email = prof_email.getText().toString();
                String dep = prof_dep.getText().toString();

                //Professeur prof = new Professeur(nom, prenom, email, tel, dep);

                Map<String,Object> prof = new HashMap<>();
                prof.put("nom",nom);
                prof.put("prenom",prenom);
                prof.put("departement",dep);
                prof.put("tel",tel);
                prof.put("photo","/photos/professeurs/"+fileName);


                db.collection("professeur").document(email).set(prof).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        uploadImage();
                        Toast.makeText(AjouterProf.this,"added successful",Toast.LENGTH_SHORT).show();


                        prof_nom.setText(" ");
                        prof_email.setText(" ");
                        prof_tel.setText(" ");
                        prof_dep.setText(" ");
                        prof_prenom.setText(" ");

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(AjouterProf.this,"Failed to add",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
//upload the image to the firebase storage using the name of the current date
    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Enregistrement...");
        progressDialog.show();
        storageReference = FirebaseStorage.getInstance().getReference("photos/professeurs/"+fileName);
        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       binding.profPhoto.setImageURI(null);
                        Toast.makeText(AjouterProf.this,"uploaded",Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(AjouterProf.this," failed uploaded",Toast.LENGTH_SHORT).show();

            }
        });
    }
//to select the image from the phone (drive..)
    private void selectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        someActivityResultLauncher.launch(intent);

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imgUri = data.getData();
                        binding.profPhoto.setImageURI(imgUri);
                       // binding.prof_photo.setImageURI(imgUri);
                    }
                }
            });


}