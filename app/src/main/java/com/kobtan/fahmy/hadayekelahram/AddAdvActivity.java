package com.kobtan.fahmy.hadayekelahram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddAdvActivity extends AppCompatActivity {


    private Toolbar toolbar ;
    private Button button_kind , add_btn , bwaba_kind;
    private EditText name_adv , details_adv , telephone_adv , price_adv ;
    private ImageView img_one , img_two , img_three , img_four , img_five ;
    private LinearLayout linearLayout_img ;
    private List<Image> images =new ArrayList<>();

    private ProgressDialog progressDialog ;

    private int x ;

    int up = 0;
    int k =0;

    private HashMap map;
    private HashMap map_two;
    private String item ;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    private StringCurrentUserPref currentUserPref ;
    private DatabaseReference current_user_db , current_user_db_two ;
    private String kind_txt = "" ;
    private String kind_bwaba_txt = "" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adv);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(" ");

        backky();

        currentUserPref = new StringCurrentUserPref(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        name_adv = (EditText) findViewById(R.id.input_name) ;
        details_adv = (EditText) findViewById(R.id.input_details) ;
        telephone_adv = (EditText) findViewById(R.id.input_telefone) ;
        price_adv = (EditText) findViewById(R.id.input_price) ;
        img_one = (ImageView) findViewById(R.id.imgProfile1) ;
        img_two = (ImageView) findViewById(R.id.imgProfile2) ;
        img_three = (ImageView) findViewById(R.id.imgProfile3) ;
        img_four = (ImageView) findViewById(R.id.imgProfile4) ;
        img_five = (ImageView) findViewById(R.id.imgProfile5) ;
        linearLayout_img = (LinearLayout) findViewById(R.id.liner_profileimagee) ;

        add_btn = (Button) findViewById(R.id.btn_login) ;

        bwaba_kind = (Button) findViewById(R.id.input_bwaba) ;
        bwaba_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> items = new ArrayList<String>();

                items.add("البوابة الاولي");
                items.add("البوابة الثانية");
                items.add("البوابة الثالثة");
                items.add("البوابة الرابعة");
                items.add("بوابة احمس");
                items.add("بوابة حورس");


                MaterialDialog matrial = new MaterialDialog.Builder(AddAdvActivity.this)
                        .title("اختر البوابة")
                        .items(items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                if (text != null) {
                                    bwaba_kind.setText(text);
                                    kind_txt = String.valueOf(which+1) ;
                                }

                                return true;
                            }
                        })
                        .positiveText("تأكيد الاختيار")
                        .buttonRippleColorRes(R.color.colorAccent)
                        .backgroundColorRes(R.color.colorAccent)
                        .show();

                matrial.getWindow().setLayout(500, Toolbar.LayoutParams.WRAP_CONTENT);
            }
        });

        button_kind = (Button) findViewById(R.id.input_address) ;
        button_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> osNameList = new ArrayList<String>();

                osNameList.add("عقارات");
                osNameList.add("سيارات");
                osNameList.add("هواتف");
                osNameList.add("وظائف");
                osNameList.add("اثاث منزلي");
                osNameList.add("اجهزة و الكترونيات");
                osNameList.add("صيانة منزلية");
                osNameList.add("اجهزة رياضية");
                osNameList.add("موضة وجمال");
                osNameList.add("حيوانات");
                osNameList.add("دروس خصوصية");
                osNameList.add("اغراض اخري");
                osNameList.add("متفرقات"); // delete
                osNameList.add("Online");// delete
                osNameList.add("مستشارك القانوني"); // delete


                new MaterialDialog.Builder(AddAdvActivity.this)
                        .title("اختر التصنيف")
                        .items(osNameList)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                if (text != null) {
                                    kind_txt = String.valueOf(text) ;
                                    button_kind.setText(text);
                                }

                                return true;
                            }
                        })
                        .positiveText("اختر")
                        .buttonRippleColorRes(R.color.colorAccent)
                        .backgroundColorRes(R.color.colorAccent)
                        .show();
            }
        });


        linearLayout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.create(AddAdvActivity.this)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .multi() // multi mode (default mode)
                        .limit(5) // max images can be selected (99 by default)
                        .start();
            }
        });



        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(AddAdvActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("جاري الاضافة...");
                progressDialog.show();

                final String name = name_adv.getText().toString();
                final String telephone = telephone_adv.getText().toString();
                final String details = details_adv.getText().toString();
                final String price = price_adv.getText().toString();
                final String kind = kind_txt;
                final String bwaba = kind_txt;

                if (name.equals("") || telephone.equals("") || details.equals("") || kind.equals("") || price.equals("") || bwaba.equals("") || 0 >= images.size()) {
                    progressDialog.dismiss();
                    Toast.makeText(AddAdvActivity.this, "من فضلك ادخل البيانات كاملة اولا", Toast.LENGTH_LONG).show();
                    if (name.equals(""))
                    {
                        name_adv.setBackgroundColor(getResources().getColor(R.color.four));
                    }
                    if (telephone.equals(""))
                    {
                        telephone_adv.setBackgroundColor(getResources().getColor(R.color.four));
                    }
                    if (details.equals(""))
                    {
                        details_adv.setBackgroundColor(getResources().getColor(R.color.four));
                    }
                    if (price.equals(""))
                    {
                        price_adv.setBackgroundColor(getResources().getColor(R.color.four));
                    }
                    if (kind.equals(""))
                    {
                        button_kind.setBackgroundColor(getResources().getColor(R.color.four));
                    }
                    if(0 >= images.size()){
                        linearLayout_img.setBackgroundColor(getResources().getColor(R.color.four));
                    }


                }
                else {

                    newPost();

                }


            }
        });



    }
    private void newPost() {

        progressDialog = new ProgressDialog(AddAdvActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("جاري الاضافة...");
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference().child("members").child(currentUserPref.getUser()).child("adv").push() ;
        String key = FirebaseDatabase.getInstance().getReference().child("members").child(currentUserPref.getUser()).child("adv").push().getKey();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("members").child(currentUserPref.getUser()).child("adv").child(key);

        map = new HashMap();
        map.put("name", name_adv.getText().toString());
        map.put("telephone", telephone_adv.getText().toString());
        map.put("details", details_adv.getText().toString());
        map.put("kind", button_kind.getText().toString());
        map.put("price", price_adv.getText().toString());
        map.put("gate", kind_txt);
        map.put("date", getdate());
        map.put("views", "0");
        map.put("active", "yes");
        map.put("msgs", "0");
        map.put("calls", "0");




        current_user_db_two = FirebaseDatabase.getInstance().getReference().child("buy").child(button_kind.getText().toString()).child(key);

        map_two = new HashMap();
        map_two.put("name", name_adv.getText().toString());
        map_two.put("telephone", telephone_adv.getText().toString());
        map_two.put("details", details_adv.getText().toString());
        map_two.put("kind", button_kind.getText().toString());
        map_two.put("price", price_adv.getText().toString());
        map_two.put("gate", bwaba_kind.getText().toString());
        map_two.put("date", getdate());
        map_two.put("views", "0");
        map_two.put("active", "yes");
        map_two.put("memberID" , currentUserPref.getUser()) ;
        map_two.put("msgs", "0");
        map_two.put("calls", "0");



        //uploadImage(key);

        finishUpdate();
        Toast.makeText(this, "تم الارسال الرجاء انتظار المراجعة", Toast.LENGTH_LONG).show();
        uploadImages(key);



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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MemberPanelAdvActivity.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(AddAdvActivity.this , MemberPanelAdvActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void uploadImage(String key) {


/*

        final ArrayList<Uri> itemss = new ArrayList<Uri>();
        itemss.add(selectedImage) ;
        itemss.add(selectedImageTwo) ;
        itemss.add(selectedImageThree) ;
        itemss.add(selectedImageFour) ;
        itemss.add(selectedImageFive) ;

        for ( x = 0 ; x < itemss.size() ; x++) {
            Uri filePath = itemss.get(x);

            if (filePath != null) {
                item = "";
                if (filePath == selectedImage) {
                    item = "one";
                } else if (filePath == selectedImageTwo) {
                    item = "two";
                } else if (filePath == selectedImageThree) {
                    item = "three";
                } else if (filePath == selectedImageFour) {
                    item = "four";
                } else if (filePath == selectedImageFive) {
                    item = "five";
                }



                StorageReference ref = storageReference.child("members").child(currentUserPref.getUser()).child(key).child("img"+"_"+ item);
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //progressDialog.dismiss();
                                //Toast.makeText(AddAdvActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                map = new HashMap();
                                map_two = new HashMap();
                                map.put("img" + "_" + item, taskSnapshot.getDownloadUrl().toString());
                                map_two.put("img" + "_" + item, taskSnapshot.getDownloadUrl().toString());
                                current_user_db.updateChildren(map);
                                current_user_db_two.updateChildren(map_two);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(AddAdvActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }
            if (x == itemss.size()) {
                finishUpdate();
            }

*/}

    private  void  uploadImages (String key) {
        StorageReference filepath = storageReference.child("members").child(currentUserPref.getUser()).child(key);
        ArrayList<String> urls = new ArrayList<String>();
        while (up < images.size()){
            Image image = images.get(k) ;
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());

            filepath.child("img"+"_"+ k).putFile(getImageUri(AddAdvActivity.this,myBitmap)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

                if(0 >= urls.size()){
                    map.put("img_"+0,"waiting");
                    map_two.put("img_"+0,"waiting");
                }else{
                    map.put("img_"+0,urls.get(0));
                    map_two.put("img_"+0,urls.get(0));
                }
                if(1 >= urls.size()){
                    map.put("img_"+1,"waiting");
                    map_two.put("img_"+1,"waiting");
                }else{
                    map.put("img_"+1,urls.get(1));
                    map_two.put("img_"+1,urls.get(1));
                }
                if(2 >= urls.size()){
                    map.put("img_"+2,"waiting");
                    map_two.put("img_"+2,"waiting");
                }else{
                    map.put("img_"+2,urls.get(2));
                    map_two.put("img_"+2,urls.get(2));
                }
                if(3 >= urls.size()){
                    map.put("img_"+3,"waiting");
                    map_two.put("img_"+3,"waiting");
                }else{
                    map.put("img_"+3,urls.get(3));
                    map_two.put("img_"+3,urls.get(3));
                }
                if(4 >= urls.size()){
                    map.put("img_"+4,"waiting");
                    map_two.put("img_"+4,"waiting");
                }else{
                    map.put("img_"+4,urls.get(4));
                    map_two.put("img_"+4,urls.get(4));
                }

                current_user_db.updateChildren(map);
                current_user_db_two.updateChildren(map_two);
                 }
        }, 60000);


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getdate () {
        String date = (DateFormat.format(" التاريخ "+" dd-MM-yyyy", new java.util.Date()).toString());
        return date;
    }
    private void finishUpdate() {
        progressDialog.dismiss();
        startActivity(new Intent(getApplicationContext(), MemberPanelAdvActivity.class));
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
}
