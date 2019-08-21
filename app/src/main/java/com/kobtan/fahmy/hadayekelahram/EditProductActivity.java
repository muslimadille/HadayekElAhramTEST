package com.kobtan.fahmy.hadayekelahram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EditProductActivity extends AppCompatActivity {

    private Button add_btn , bwaba_kind;
    private EditText name_adv , address_adv , telephone_adv  , rate_adv ;
    private ImageView img_one ;
    private LinearLayout linearLayout_img ;

    private List<Image> images =new ArrayList<>();

    private ProgressDialog progressDialog ;

    private int x ;

    private HashMap map;
    private String item ;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    private Uri filePath ;
    private Image image ;


    private DatabaseReference current_user_db , current_user_db_get ;
    private String kind_bwaba_txt = "" ;
    private String newString , newStringtwo  , newStringThree;
    private int newStringFour ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        backky();

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


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        name_adv = (EditText) findViewById(R.id.input_name) ;
        address_adv = (EditText) findViewById(R.id.input_address) ;
        telephone_adv = (EditText) findViewById(R.id.input_telefone) ;
        rate_adv = (EditText) findViewById(R.id.input_rate) ;


        img_one = (ImageView) findViewById(R.id.imgProfile1) ;
        linearLayout_img = (LinearLayout) findViewById(R.id.liner_profileimagee) ;

        add_btn = (Button) findViewById(R.id.btn_login) ;

        bwaba_kind = (Button) findViewById(R.id.input_bwaba) ;

        getData();


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


                MaterialDialog matrial = new MaterialDialog.Builder(EditProductActivity.this)
                        .title("اختر البوابة")
                        .items(items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                if (text != null) {
                                    bwaba_kind.setText(text);
                                    kind_bwaba_txt = String.valueOf(which+1) ;
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



        linearLayout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.create(EditProductActivity.this)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .multi() // multi mode (default mode)
                        .limit(1) // max images can be selected (99 by default)
                        .start();
            }
        });



        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressDialog = new ProgressDialog(EditProductActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("جاري الاضافة...");
                progressDialog.show();

                final String name = name_adv.getText().toString();
                final String telephone = telephone_adv.getText().toString();
                final String address = address_adv.getText().toString();
                final String bwaba = kind_bwaba_txt;

                if (name.equals("") || telephone.equals("") || address.equals("") ) {
                    progressDialog.dismiss();
                    Toast.makeText(EditProductActivity.this, "من فضلك ادخل البيانات كاملة اولا", Toast.LENGTH_LONG).show();

                }
                else {

                    newPost();

                }


            }
        });



    }

    private void newPost() {


        if (newStringtwo != null)
        {
            current_user_db = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements")
                    .child(newStringtwo).child(newStringThree);
        }
        else
        {
            current_user_db = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements").child(newStringThree);
        }

        progressDialog = new ProgressDialog(EditProductActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("جاري التعديل...");
        progressDialog.show();



        map = new HashMap();
        map.put("name", name_adv.getText().toString());
        map.put("telephone", telephone_adv.getText().toString());
        map.put("address", address_adv.getText().toString());
        map.put("rating", rate_adv.getText().toString());

        if (bwaba_kind.getText().equals("")) {
            map.put("gate", "جميع البوابات");
        }
        else
        {
            map.put("gate", bwaba_kind.getText().toString());
        }


        current_user_db.updateChildren(map);
        uploadImage(current_user_db);

        Toast.makeText(this, "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();


        if (newStringtwo != null)
        {
            Intent i = new Intent(getApplicationContext() , ObjectPreviewActivity.class) ;
            i.putExtra("STRING_I_NEED" , newString) ;
            i.putExtra("STRING_I_NEED2" , newStringtwo) ;
            i.putExtra("STRING_I_NEED3" , newStringThree) ;
            i.putExtra("STRING_I_NEED4" , newStringFour) ;
            startActivity(i);

        }
        else
        {
            Intent i = new Intent(getApplicationContext() , ObjectPreviewActivity.class) ;
            i.putExtra("STRING_I_NEED" , newString) ;
            //i.putExtra("STRING_I_NEED2" , newStringtwo) ;
            i.putExtra("STRING_I_NEED3" , newStringThree) ;
            i.putExtra("STRING_I_NEED4" , newStringFour) ;
            startActivity(i);
        }


    }

    private void getData() {
        if (newStringtwo != null)
        {
            current_user_db_get = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements")
                    .child(newStringtwo).child(newStringThree);
        }
        else
        {
            current_user_db_get = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements").child(newStringThree);
        }

        current_user_db_get.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("name")) {
                    name_adv.setText(dataSnapshot.child("name").getValue().toString());
                    if (dataSnapshot.hasChild("address")) {
                        address_adv.setText(dataSnapshot.child("address").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("telephone")) {
                        telephone_adv.setText(dataSnapshot.child("telephone").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("rating")) {
                        rate_adv.setText(dataSnapshot.child("rating").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("profile_img")) {
                        Glide.with(getApplicationContext())
                                .load(dataSnapshot.child("profile_img").getValue().toString())
                                .placeholder(R.drawable.logopng)
                                .apply(new RequestOptions().override(300, 250))
                                .into(img_one);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;

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
            Toast.makeText(this, "Hey", Toast.LENGTH_SHORT).show();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("new_images/"+name_adv.getText().toString()+"_"+ UUID.randomUUID().toString());

            Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());

            ref.putFile(getImageUri(EditProductActivity.this,myBitmap))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            HashMap map_sp = new HashMap();
                            map_sp.put("profile_img", taskSnapshot.getDownloadUrl().toString());
                            current.updateChildren(map_sp) ;
                            Toast.makeText(EditProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProductActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
}
