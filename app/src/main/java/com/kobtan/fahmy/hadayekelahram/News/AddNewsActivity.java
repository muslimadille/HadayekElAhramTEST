package com.kobtan.fahmy.hadayekelahram.News;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kobtan.fahmy.hadayekelahram.AddAdminActivity;
import com.kobtan.fahmy.hadayekelahram.MainActivity;
import com.kobtan.fahmy.hadayekelahram.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AddNewsActivity extends AppCompatActivity {

    private Button login ;
    private EditText details ;
    private DatabaseReference current_user_db ;
    private String newStringz = " ";
    private ArrayList<String> items = new ArrayList<>();
    private String details_txt ;
    // photo firebase
    private HashMap map;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private ImageView img_one ;

    private Uri filePath;
    private Image image ;
    private ArrayList<String> osNameList = new ArrayList<String>() ;
    private String newString , newString2 , newString3  ,newString4  , newString5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);


        //
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString2= null;
            } else {
                newString2= extras.getString("STRING_I_NEED2");
            }
        } else {
            newString2= (String) savedInstanceState.getSerializable("STRING_I_NEED2");
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString3= null;
            } else {
                newString3= extras.getString("STRING_I_NEED3");
            }
        } else {
            newString3= (String) savedInstanceState.getSerializable("STRING_I_NEED3");
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString4= null;
            } else {
                newString4= extras.getString("STRING_I_NEED4");
            }
        } else {
            newString4= (String) savedInstanceState.getSerializable("STRING_I_NEED4");
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString5= null;
            } else {
                newString5= extras.getString("STRING_I_NEED5");
            }
        } else {
            newString5= (String) savedInstanceState.getSerializable("STRING_I_NEED5");
        }
        //

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        login = (Button) findViewById(R.id.btn_login) ;
        details = (EditText) findViewById(R.id.input_details) ;
        img_one = (ImageView) findViewById(R.id.imgProfile1) ;


        img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.create(AddNewsActivity.this)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .multi() // multi mode (default mode)
                        .limit(1) // max images can be selected (99 by default)
                        .start();
            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details_txt = details.getText().toString();


                if (details_txt.equals("") ){
                    Toast.makeText(AddNewsActivity.this, "No Full Information", Toast.LENGTH_SHORT).show();
                }else
                {
                        newPost();
                }


            }
        });
    }

    private void newPost() {


        FirebaseDatabase.getInstance().getReference().child("news").push() ;
        String key = FirebaseDatabase.getInstance().getReference().child("news").push().getKey();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("news").child(key);

        HashMap map = new HashMap();

        map.put("name", newString);
        map.put("profile_img", newString2);
        map.put("id_post", newString3);
        map.put("details", details_txt);
        map.put("telephone", newString4);
        map.put("address" , newString5) ;
        map.put("date" , getdate()) ;



        current_user_db.updateChildren(map);




        uploadImage(current_user_db);

        Toast.makeText(this, "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();

        details.setText("");



        startActivity(new Intent(getApplicationContext() , MainActivity.class));


    }

    private String getdate () {
        String date = (DateFormat.format(" التاريخ "+" dd-MM-yyyy", new java.util.Date()).toString());
        return date;
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            image = ImagePicker.getFirstImageOrNull(data) ;
            filePath = data.getData();
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
            img_one.setImageBitmap(myBitmap);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage(DatabaseReference current) {

        if(image != null)
        {
            Toast.makeText(this, "Confirm Image", Toast.LENGTH_SHORT).show();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("news/"+newString+"_"+ UUID.randomUUID().toString());

            Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());

            ref.putFile(getImageUri(AddNewsActivity.this,myBitmap))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            HashMap map_sp = new HashMap();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    map_sp.put("post_img", downloadUrl.toString());
                                    current.updateChildren(map_sp) ;
                                }
                            });

                            Toast.makeText(AddNewsActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddNewsActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
