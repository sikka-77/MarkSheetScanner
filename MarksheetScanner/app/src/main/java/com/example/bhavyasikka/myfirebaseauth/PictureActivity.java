package com.example.bhavyasikka.myfirebaseauth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhavyasikka.myfirebaseauth.Network.Api;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureActivity extends AppCompatActivity {

    private Button mSavePhoto;
    private FirebaseAuth mfirebaseAuthP;

    //private ImageView mImage;

    TextView mExamsName;

    private Uri photoUri;

    private StorageReference mStorageReference;

    private ProgressDialog mprogressDialog;

    private static final int CAMERA_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getSupportActionBar()!=null;
        setContentView(R.layout.activity_picture);


       /* ArrayList<MarksModel> marks_list = new ArrayList<MarksModel>();

        marks_list.add(new MarksModel("12","169103037"));
        marks_list.add(new MarksModel("13","169105067"));
        marks_list.add(new MarksModel("14","169103039"));
        marks_list.add(new MarksModel("10","169105054"));

        ListView marks_list_view=(ListView)findViewById(R.id.listOfMarks);

        UserAdapterMarksActivity adapter = new UserAdapterMarksActivity(this,marks_list);

        marks_list_view.setAdapter(adapter); */




        mprogressDialog=new ProgressDialog(this);

        mfirebaseAuthP=FirebaseAuth.getInstance();

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();
        String uniqueUserId=user.getUid();

        mExamsName=(TextView)findViewById(R.id.examsName);



        FloatingActionButton save_new_photo=(FloatingActionButton)findViewById(R.id.savePhoto);

       // mImage=(ImageView)findViewById(R.id.displayImage);

        Intent picIntent=getIntent();

        String new_exam_id=picIntent.getStringExtra(ExamActivity.EXAM_ID);
        String new_exam_name=picIntent.getStringExtra(ExamActivity.EXAM_NAME);
        String new_course_code_id=picIntent.getStringExtra(ExamActivity.NEWCOURSECODEID);

        mExamsName.setText(new_exam_name);

        if(new_exam_name.equalsIgnoreCase("Sessional1"))
        {

            ArrayList<MarksModel> marks_list = new ArrayList<MarksModel>();

            marks_list.add(new MarksModel("12","169103037"));
            marks_list.add(new MarksModel("13","169105067"));
            marks_list.add(new MarksModel("14","169103039"));
            marks_list.add(new MarksModel("10","169105054"));

            ListView marks_list_view=(ListView)findViewById(R.id.listOfMarks);

            UserAdapterMarksActivity adapter = new UserAdapterMarksActivity(this,marks_list);

            marks_list_view.setAdapter(adapter);
        }

        if(new_exam_name.equalsIgnoreCase("Sessional 2"))
        {

            ArrayList<MarksModel> marks_list = new ArrayList<MarksModel>();

            marks_list.add(new MarksModel("12","169103037"));
            marks_list.add(new MarksModel("13","169105067"));
            marks_list.add(new MarksModel("14","169103039"));
            marks_list.add(new MarksModel("10","169105054"));

            ListView marks_list_view=(ListView)findViewById(R.id.listOfMarks);

            UserAdapterMarksActivity adapter = new UserAdapterMarksActivity(this,marks_list);

            marks_list_view.setAdapter(adapter);
        }

        if(new_exam_name.equalsIgnoreCase("End sem2") || new_exam_name.equalsIgnoreCase("End sem 1"))
        {

            ArrayList<MarksModel> marks_list = new ArrayList<MarksModel>();

            marks_list.add(new MarksModel("70","169103037"));
            marks_list.add(new MarksModel("65","169105067"));
            marks_list.add(new MarksModel("69","169103039"));
            marks_list.add(new MarksModel("55","169105054"));

            ListView marks_list_view=(ListView)findViewById(R.id.listOfMarks);

            UserAdapterMarksActivity adapter = new UserAdapterMarksActivity(this,marks_list);

            marks_list_view.setAdapter(adapter);
        }


        mStorageReference= FirebaseStorage.getInstance().getReference("Images").child(uniqueUserId)
                .child(new_course_code_id).child(new_exam_id);


        save_new_photo.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Roll No.");

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_NUMBER);

            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String rollno = input.getText().toString();
                Log.d("ALERT DIAGOL", rollno);
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(rollno);
                        Log.d("FILE CREATOR", photoFile.getName());
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoUri = FileProvider.getUriForFile(this,
                                "com.example.android.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.activity(photoUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setInitialCropWindowPaddingRatio(0)
                    .setAspectRatio(1, 1)
                    .setFixAspectRatio(true)
                    .setMaxZoom(0)
                    .setMinCropResultSize(512,512)
                    .setMaxCropResultSize(2096,2096)
                    .start(this);

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                File imageFile = new File(result.getUri().getPath());

                RequestBody reuqestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

                MultipartBody.Part body = MultipartBody.Part.createFormData("upload", imageFile.getName(), reuqestFile);

                Call<ResponseBody> call = Api.Service().submitResult(body, reuqestFile);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                        try {
                            Log.d("RESPONSE", response.body().string());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("NETWORK STATE", "Ohh Noo!!!");
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    private File createImageFile(String imageFileName) throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageFileName += "_";

        // Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }
}
