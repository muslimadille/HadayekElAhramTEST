package com.kobtan.fahmy.hadayekelahram.AddNewAdv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kobtan.fahmy.hadayekelahram.EditDetailsAdvActivity;
import com.kobtan.fahmy.hadayekelahram.MainActivity;
import com.kobtan.fahmy.hadayekelahram.MemberPanelAdvActivity;
import com.kobtan.fahmy.hadayekelahram.ObjectPreviewActivity;
import com.kobtan.fahmy.hadayekelahram.R;
import com.kobtan.fahmy.hadayekelahram.StringCurrentUserPref;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AddNewAdvProductActivity extends AppCompatActivity {
    private Button add_btn ;
    private ImageView img_one , img_two , img_three , img_four , img_five ;
    private LinearLayout linearLayout_img ;
    private List<Image> images =new ArrayList<>();
    FirebaseStorage storage;
    StorageReference storageReference;
    private ProgressDialog progressDialog ;


    int up = 0;
    int k =0;

    private HashMap map;

    private String newString , newStringtwo  , newStringThree;
    private int newStringFour ;

    private DatabaseReference current_user_db , current_user_db_get ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_adv_product);

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
                newStringtwo= null;
            } else {
                newStringtwo= extras.getString("STRING_I_NEED2");
            }
        } else {
            newStringtwo= (String) savedInstanceState.getSerializable("STRING_I_NEED2");
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newStringThree= null;
            } else {
                newStringThree= extras.getString("STRING_I_NEED3");
            }
        } else {
            newStringThree= (String) savedInstanceState.getSerializable("STRING_I_NEED3");
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newStringFour= 0;
            } else {
                newStringFour= extras.getInt("STRING_I_NEED4");
            }
        } else {
            newStringFour= (int) savedInstanceState.getSerializable("STRING_I_NEED4");
        }

        backky();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        img_one = (ImageView) findViewById(R.id.imgProfile1) ;
        img_two = (ImageView) findViewById(R.id.imgProfile2) ;
        img_three = (ImageView) findViewById(R.id.imgProfile3) ;
        img_four = (ImageView) findViewById(R.id.imgProfile4) ;
        img_five = (ImageView) findViewById(R.id.imgProfile5) ;
        linearLayout_img = (LinearLayout) findViewById(R.id.liner_profileimagee) ;
        add_btn = (Button) findViewById(R.id.btn_login) ;





        getData();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(AddNewAdvProductActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("جاري الاضافة...");
                progressDialog.show();



                if (0 >= images.size()) {
                    progressDialog.dismiss();
                    Toast.makeText(AddNewAdvProductActivity.this, "من فضلك ادخل البيانات كاملة اولا", Toast.LENGTH_LONG).show();

                    if(0 >= images.size()){
                        linearLayout_img.setBackgroundColor(getResources().getColor(R.color.four));
                    }


                }
                else {

                    newPost();

                }


            }
        });

        linearLayout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.create(AddNewAdvProductActivity.this)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .multi() // multi mode (default mode)
                        .limit(5) // max images can be selected (99 by default)
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            images = ImagePicker.getImages(data) ;
            // or get a single image only
            if(0 >= images.size()){
                img_one.setImageDrawable(null);
            }else{
                Image image = images.get(0) ;
                Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
                img_one.setImageBitmap(myBitmap);
            }
            if(1 >= images.size()){
                img_two.setImageBitmap(null);
            }else{
                Image image = images.get(1) ;
                Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
                img_two.setImageBitmap(myBitmap);
            }
            if(2 >= images.size()){
                img_three.setImageBitmap(null);
            }else{
                Image image = images.get(2) ;
                Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
                img_three.setImageBitmap(myBitmap);
            }
            if(3 >= images.size()){
                img_four.setImageBitmap(null);
            }else{
                Image image = images.get(3) ;
                Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
                img_four.setImageBitmap(myBitmap);
            }
            if(4 >= images.size()){
                img_five.setImageBitmap(null);
            }else{
                Image image = images.get(4) ;
                Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
                img_five.setImageBitmap(myBitmap);
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void newPost() {
        if (newStringtwo != null)
        {
            current_user_db = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements")
                    .child(newStringtwo).child(newStringThree).child("new_adv");
        }
        else
        {
            current_user_db = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements").child(newStringThree).child("new_adv");
        }

        progressDialog = new ProgressDialog(AddNewAdvProductActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("جاري الاضافة...");
        progressDialog.show();

        map = new HashMap();

        //finishUpdate();
        Toast.makeText(this, "تم الارسال الرجاء انتظار المراجعة", Toast.LENGTH_LONG).show();
        uploadImages(newString);



    }

    private  void  uploadImages (String key) {
        ArrayList<String> urls = new ArrayList<String>();
        while (up < images.size()){
            Image image = images.get(k) ;
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
            StorageReference filepath = storageReference.child("final_products").child(newStringThree).child("img"+"_"+k);

            filepath.putFile(getImageUri(AddNewAdvProductActivity.this,myBitmap)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;
                            //Uri downloadURL = taskSnapshot.getDownloadUrl();
                            urls.add(downloadUrl.toString()) ;
                        }
                    });

                }
            });
            up++;
            k++;

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                //Toast.makeText(AddNewAdvProductActivity.this, urls.toString(), Toast.LENGTH_LONG).show();
                if(0 >= urls.size()){
                    map.put("img_"+0,"waiting");

                }else{
                    map.put("img_"+0,urls.get(0));

                }
                if(1 >= urls.size()){
                    map.put("img_"+1,"waiting");

                }else{
                    map.put("img_"+1,urls.get(1));

                }
                if(2 >= urls.size()){
                    map.put("img_"+2,"waiting");

                }else{
                    map.put("img_"+2,urls.get(2));

                }
                if(3 >= urls.size()){
                    map.put("img_"+3,"waiting");

                }else{
                    map.put("img_"+3,urls.get(3));

                }
                if(4 >= urls.size()){
                    map.put("img_"+4,"waiting");

                }else{
                    map.put("img_"+4,urls.get(4));

                }
                current_user_db.updateChildren(map);
                finishUpdate();

            }
        }, 30000);


    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void getData() {
        if (newStringtwo != null)
        {
            current_user_db_get = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements")
                    .child(newStringtwo).child(newStringThree).child("new_adv");
        }
        else
        {
            current_user_db_get = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements").child(newStringThree).child("new_adv");
        }

        current_user_db_get.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("img_0")) {
                        Glide.with(getApplicationContext())
                                .load(dataSnapshot.child("img_0").getValue().toString())
                                .placeholder(R.drawable.logopng)
                                .apply(new RequestOptions().override(300, 250))
                                .into(img_one);
                }
                if (dataSnapshot.hasChild("img_1")) {
                    Glide.with(getApplicationContext())
                            .load(dataSnapshot.child("img_1").getValue().toString())
                            .placeholder(R.drawable.logopng)
                            .apply(new RequestOptions().override(300, 250))
                            .into(img_two);
                }
                if (dataSnapshot.hasChild("img_2")) {
                    Glide.with(getApplicationContext())
                            .load(dataSnapshot.child("img_2").getValue().toString())
                            .placeholder(R.drawable.logopng)
                            .apply(new RequestOptions().override(300, 250))
                            .into(img_three);
                }
                if (dataSnapshot.hasChild("img_3")) {
                    Glide.with(getApplicationContext())
                            .load(dataSnapshot.child("img_3").getValue().toString())
                            .placeholder(R.drawable.logopng)
                            .apply(new RequestOptions().override(300, 250))
                            .into(img_four);
                }
                if (dataSnapshot.hasChild("img_4")) {
                    Glide.with(getApplicationContext())
                            .load(dataSnapshot.child("img_4").getValue().toString())
                            .placeholder(R.drawable.logopng)
                            .apply(new RequestOptions().override(300, 250))
                            .into(img_five);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , ObjectPreviewActivity.class));
        finish();
    }
    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    private void finishUpdate() {
        progressDialog.dismiss();
        Toast.makeText(AddNewAdvProductActivity.this, "Success", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
