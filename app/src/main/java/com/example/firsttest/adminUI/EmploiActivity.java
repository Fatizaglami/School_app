package com.example.firsttest.adminUI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firsttest.R;
import com.example.firsttest.models.Emploi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmploiActivity extends AppCompatActivity {

    TextView text1;
    Button uploadBtn;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    SimpleDateFormat formatter;
    Date now;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emploi);

        text1 = (TextView) findViewById(R.id.text1);
        uploadBtn =  findViewById(R.id.uploadBtn);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("emploi");

        uploadBtn.setEnabled(false);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();

            }
        });

    }



    private void uploadPDFFileFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading....");
        progressDialog.show();
        formatter= new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.FRANCE);
        now=new Date();
        fileName=formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("emploi/"+fileName);

       storageReference.putFile(data)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                       while (!uriTask.isComplete());
                       Uri uri = uriTask.getResult();

                       Emploi emploi = new Emploi(text1.getText().toString(),uri.toString());
                       databaseReference.child(databaseReference.push().getKey()).setValue(emploi);
                       Toast.makeText(EmploiActivity.this, "File Upload", Toast.LENGTH_SHORT).show();
                       progressDialog.dismiss();
                   }
               }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
               double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
               progressDialog.setMessage("File Upload.." +(int) progress+ "%");

           }
       });
    }




    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
       // startActivityForResult(Intent.createChooser(intent," PDF FILE SELECT"),12);
        someActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                           // int result = activityResult.getResultCode();
                            if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                          //  if(result == 12 && result == RESULT_OK && data != null && data.getData() != null ){
                                uploadBtn.setEnabled(true);
                                text1.setText(data.getDataString()
                                        .substring(data.getDataString().lastIndexOf("/")+1)) ;

                            uploadBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    uploadPDFFileFirebase(data.getData());
                                }

                            });
                            }

                        }
                    }
            );
}