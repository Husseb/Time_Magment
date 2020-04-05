package com.huseen.abo.aita1998.timemagment.createAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.alexzh.circleimageview.ItemSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.huseen.abo.aita1998.timemagment.dashboard.DashboardActivity;
import com.huseen.abo.aita1998.timemagment.R;

import java.util.HashMap;

public class CreateAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Uri imageUri;

    private static final String TAG = CreateAccount.class.getSimpleName();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText useNameEt, emailEt, passwordEt, passwordConfirmEt;
    Button createBtn;
    CircleImageView imageView;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String storagePath = "USERS_PROFILE_IMAGES";

    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int STORAGE_REQUEST_CODE = 200;
    public static final int IMAGE_PICK_GALLARY_REQUEST_CODE = 300;
    public static final int IMAGE_PICK_CAMERA_CODE = 400;

    String camerapermition[];
    String stoaregpermition[];

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        useNameEt = findViewById(R.id.useNameEt);
        emailEt = findViewById(R.id.titleEt);
        passwordEt = findViewById(R.id.descriptionEt);
        passwordConfirmEt = findViewById(R.id.passwordConfirmEt);

        imageView = (CircleImageView) findViewById(R.id.imageView);

        createBtn = findViewById(R.id.createBtn);

        mAuth = FirebaseAuth.getInstance();

        camerapermition = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        stoaregpermition = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(this);

        imageView.setOnItemSelectedClickListener(new ItemSelectedListener() {
            @Override
            public void onSelected(View view) {
                onClickPickImage();
            }

            @Override
            public void onUnselected(View view) {

            }
        });

//                setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickPickImage();
//            }
//        });


    }

    public void onClickFinsh(View view) {
        finish();
    }

    public void onClickCreate(View view) {
        final String email = emailEt.getText().toString();
        final String userName = useNameEt.getText().toString();
        final String password = passwordEt.getText().toString();
        final String confirmPassword = passwordConfirmEt.getText().toString();

        if (imageUri == null) {
            Toast.makeText(this, "Pick The Image", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Enter The Username", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter The correct Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter The password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Enter The confirmPassword", Toast.LENGTH_SHORT).show();
        } else if (!confirmPassword.equals(password)) {
            Toast.makeText(this, "You Are Enter Different Passwords", Toast.LENGTH_SHORT).show();
        } else {
            registerUser(email, password, userName, imageUri.toString());
        }
    }

    private void registerUser(String email, final String password, final String userName, final String image) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success,
                            FirebaseUser user = mAuth.getCurrentUser();
                            addImageToFireStorage(userName, user);

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccount.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateAccount.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addImageToFireStorage(final String userName, FirebaseUser user) {

        String filePathAndName = storagePath + "_" + user.getUid();

        StorageReference storageReference2nd = storageReference.child(filePathAndName);

        storageReference2nd.putFile(imageUri)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                Uri downloadUri = uriTask.getResult();

                                if (uriTask.isSuccessful()) {

                                    addDataToFireStore(userName, downloadUri.toString());

                                } else {

                                    progressDialog.dismiss();
                                    Toast.makeText(CreateAccount.this, "some error occured!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(CreateAccount.this, "11 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDataToFireStore(String userName, String image) {
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();
        String uid = user.getUid();
        HashMap<Object, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("email", email);
        map.put("username", userName);
        map.put("onlineStatus", "online");
        map.put("typingTo", "noOne");
        map.put("image", image);
        db.collection("users").document(user.getUid())
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(CreateAccount.this, "Welcome ... ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateAccount.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void onClickPickImage() {

        String[] options = {"Camera", "Gallary"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!cheackCameraPermition()) {
                        requestCameraPermition();
                    } else {
                        pickFromCamera();
                    }
                } else if (which == 1) {
                    if (!cheackStoaregPermition()) {
                        requestedtoaregPermition();
                    } else {
                        pickFromGallary();
                    }
                }
            }
        });
        builder.create().show();

    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        imageUri = CreateAccount.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean cheackStoaregPermition() {
        boolean result = ContextCompat.checkSelfPermission(CreateAccount.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestedtoaregPermition() {
        ActivityCompat.requestPermissions(this, stoaregpermition, STORAGE_REQUEST_CODE);
    }

    private boolean cheackCameraPermition() {
        boolean result = ContextCompat.checkSelfPermission(CreateAccount.this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(CreateAccount.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermition() {
        ActivityCompat.requestPermissions(this, camerapermition, CAMERA_REQUEST_CODE);
    }

    private void pickFromGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLARY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean stoaregAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && stoaregAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(CreateAccount.this, "please enable Camera  and Stoareg permition", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean stoaregAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (stoaregAccepted) {
                        pickFromGallary();
                    } else {
                        Toast.makeText(CreateAccount.this, "please enable Stoareg permition", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLARY_REQUEST_CODE) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                imageView.setImageURI(imageUri);

             }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}

