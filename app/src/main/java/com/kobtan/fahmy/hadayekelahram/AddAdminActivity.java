package com.kobtan.fahmy.hadayekelahram;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AddAdminActivity extends AppCompatActivity {

    private Button login ;
    private EditText editText_name , editText_address , editText_tel , editText_gate , editText_tel_two ;
    private DatabaseReference current_user_db ;
    private String name , address , telephone , gate ;
    private TextView kind_txt  , kind_txt_main ;
    private String text_kind ;
    private String text_kind_main ;
    private String newStringz = " ";
    private ArrayList<String> items = new ArrayList<>();


    // photo firebase
    private HashMap map;
    FirebaseStorage storage;
    StorageReference storageReference;

    private ImageView img_one ;

    private Uri filePath;
    private Image image ;
    private ArrayList<String> osNameList = new ArrayList<String>() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        login = (Button) findViewById(R.id.btn_login) ;
        editText_name = (EditText) findViewById(R.id.input_name) ;
        editText_address = (EditText) findViewById(R.id.input_address) ;
        editText_tel = (EditText) findViewById(R.id.input_telefone) ;
        editText_gate = (EditText) findViewById(R.id.input_bwaba) ;
        kind_txt = (TextView) findViewById(R.id.kind) ;
        kind_txt_main = (TextView) findViewById(R.id.kind_main) ;
        img_one = (ImageView) findViewById(R.id.imgProfile1) ;
        editText_tel_two = (EditText) findViewById(R.id.input_telefone_two) ;


        img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.create(AddAdminActivity.this)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .multi() // multi mode (default mode)
                        .limit(1) // max images can be selected (99 by default)
                        .start();
            }
        });

        kind_txt_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                osNameList = new ArrayList<String>();
                items = new ArrayList<>();
                items.add("تسوق");
                items.add("مطاعم");
                items.add("كافيهات");
                items.add("مستشفيات");
                items.add("دكتور خاص");
                items.add("صيدليات");
                items.add("معامل");
                items.add("تعليمي");
                items.add("جيم و رياضة");
                items.add("موضة و ملابس");
                items.add("اجهزة الكترونية") ;
                items.add("سيارات");
                items.add("ماكينات الصرافة");
                items.add("عقارات");
                items.add("خدمات حكومية");
                items.add("صيانة منزلية") ;
                items.add("مستلزمات اطفال") ;
                items.add("اتصالات") ;
                items.add("حيوانات اليفة") ;
                items.add("مكاتب محاماه");
                items.add("اثاث منزلي") ;
                items.add("شركات الاتصالات") ;
                items.add("موبيلات") ;
                items.add("ترفيهي");
                items.add("حفلات ومناسبات") ;


                final String[] list = getStringArray(items);

                AlertDialog sMatrial = new AlertDialog.Builder(AddAdminActivity.this)
                        .setTitle("اختر")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                kind_txt_main.setText(list[which]);
                                text_kind_main = list[which] ;
                                newStringz = list[which] ;
                            }
                        })
                        .show();
            }
        });

        kind_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                osNameList = new ArrayList<String>();

                int i = 1;

                if (newStringz.equals("تسوق")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("سوبر ماركت");
                    osNameList.add("خضار و فاكهة");
                    osNameList.add("حلويات");
                    osNameList.add("جزار");
                    osNameList.add("دواجن و طيور");
                    osNameList.add("عطاره");
                    osNameList.add("دراي كلين");
                    osNameList.add("ادوات منزلية");
                    osNameList.add("منظفات"); // جيم و رياضة

                    osNameList.add("محلات 2,5") ;
                    osNameList.add("مكتبات");
                    osNameList.add("محمصات و عصائر");
                }
                else if (newStringz.equals("جيم و رياضة")){
                    osNameList.add("Gym");
                    osNameList.add("انشطة اخري");
                }
                else if (newStringz.equals("ترفيهي")){
                    osNameList.add("Playstation");
                    osNameList.add("ملاعب كرة");
                    osNameList.add("العاب اطفال");
                }
                else if (newStringz.equals("مطاعم")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مشويات");
                    osNameList.add("اسماك");
                    osNameList.add("وجبات سريعة");
                    osNameList.add("بيتزا");
                    osNameList.add("كريب");
                    osNameList.add("كبدة وسجق");
                    osNameList.add("مطاعم سورية");
                    osNameList.add("كشري");
                    osNameList.add("اكل بيتي");
                    //
                }
                else if (newStringz.equals("مستشفيات")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مستشفيات");
                    osNameList.add("عيادات و مراكز طبية");
                    osNameList.add("مركز تخاطب");
                    osNameList.add("دار رعاية");
                    osNameList.add("مستشفي نفسي");
                    osNameList.add("مستشفي بيطري");
                    //

                }
                else if (newStringz.equals("مستلزمات اطفال")) {
                    //
                    osNameList = new ArrayList<>() ;

                    osNameList.add("ملابس اطفال");
                    osNameList.add("لعب اطفال");

                }
                else if (newStringz.equals("دكتور خاص")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("اسنان");
                    osNameList.add("عيون");
                    osNameList.add("جلدية");
                    osNameList.add("باطنة");
                    osNameList.add("اطفال حديث الولادة");
                    osNameList.add("مخ و أعصاب");
                    osNameList.add("انف وأذن");
                    osNameList.add("عظام");
                    osNameList.add("نساء و توليد");
                    osNameList.add("قلب و أوعية دموية");
                    osNameList.add("اورام");
                    osNameList.add("تجميل");
                    osNameList.add("جهاز هضمي و مناظير");
                    osNameList.add("ذكورة وعقم");
                    osNameList.add("روماتيزم");
                    osNameList.add("سكر وغدد صماء");
                    osNameList.add("صدر و جهاز تنفس");
                    osNameList.add("علاج طبيعي");
                    osNameList.add("سمنة ومناظير");
                    osNameList.add("حقن مجهري");
                    osNameList.add("نطق وتخاطب");
                    osNameList.add("نفسي");
                    osNameList.add("مسالك بولية");
                    //


                }
                else if (newStringz.equals("معامل")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مراكز اشعة");
                    osNameList.add("معامل تحليل");
                    //


                }
                else if (newStringz.equals("موضة و ملابس")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("بيوتي سنتر حريمي");
                    osNameList.add("كوافير رجالي");
                    osNameList.add("ملابس حريمي");
                    osNameList.add("ملابس رجالي");
                    osNameList.add("لانجيري");
                    osNameList.add("اكسسوارات و هدايا");
                    osNameList.add("مجوهرات");
                    osNameList.add("نظارات و عدسات");
                    osNameList.add("ملابس رياضية");
                    osNameList.add("تصوير");
                    osNameList.add("مساج وسبا");
                    osNameList.add("فراشة وحفلات");
                    osNameList.add("زهور");
                    osNameList.add("مستحضرات تجميل");
                    osNameList.add("خياطه و تفصيل");
                    osNameList.add("دعاية و اعلان");
                    osNameList.add("شنط و احذية");
                    //


                }
                else if (newStringz.equals("حفلات ومناسبات")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("بيوتي سنتر حريمي");
                    osNameList.add("كوافير رجالي");
                    osNameList.add("تصوير");
                    osNameList.add("زهور");
                    osNameList.add("دعاية و اعلان");
                    osNameList.add("تنظيم حفلات");
                    osNameList.add("مساج وسبا");
                    //


                }
                else if (newStringz.equals("اجهزة الكترونية")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("تكيفات");
                    osNameList.add("اجهزة منزلية");
                    osNameList.add("راديو شاك");
                    osNameList.add("كمبيوتر");


                }
                else if (newStringz.equals("سيارات")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مراكز صيانة");
                    osNameList.add("مغسلة");
                    osNameList.add("معارض سيارات");
                    osNameList.add("كماليات سيارات");
                    osNameList.add("زجاج سيارات");
                    osNameList.add("ايجار سيارات");
                    osNameList.add("قطع غيار");
                    osNameList.add("اطارات و بطاريات");
                    osNameList.add("نقل اثاث");
                    osNameList.add("ونش انقاذ");


                }
                else if (newStringz.equals("تعليمي")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مدارس");
                    osNameList.add("حضانات");
                    osNameList.add("سنتر تعليمي");
                    osNameList.add("دروس خصوصية");


                }
                else if (newStringz.equals("ماكينات الصرافة")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("فوري");
                    osNameList.add("امان");
                    osNameList.add("البنك الاهلي");
                    osNameList.add("بنك مصر");
                    osNameList.add("بنك CIB");
                    osNameList.add("بنك QNB");
                    osNameList.add("البنك العربي الافريقي");
                    osNameList.add("بنك فيصل الاسلامي");
                    osNameList.add("بنك الاسكندرية");
                    //


                }
                else if (newStringz.equals("عقارات")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("شركات عقارية");
                    osNameList.add("عقارات افراد");
                    osNameList.add("تشطيبات وديكور");
                    //

                }
                else if (newStringz.equals("خدمات حكومية")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مقر الجمعية");
                    osNameList.add("شهر العقاري");
                    osNameList.add("شركة المياه");
                    osNameList.add("ديني");
                    osNameList.add("شركة الغاز");// 4
                    osNameList.add("سنترال الرماية");// 5
                    osNameList.add("مكتب البريد");//
                    osNameList.add("مطافي");//
                    osNameList.add("اسعاف");//
                    osNameList.add("نقطة شرطة");// 9
                    osNameList.add("الوحدة الصحية");// 10
                    osNameList.add("طوارئ كهرباء");// 11

                    osNameList.add("حي الهرم");//
                    osNameList.add("شركة الكهرباء");// 13
                    osNameList.add("طوارئ المياه");//
                    osNameList.add("شركة تي داتا");//15
                    osNameList.add("مأذون");//
                    osNameList.add("مرور");//
                    osNameList.add("نادي حدائق الاهرام");//17
                    //


                }
                else if (newStringz.equals("صيانة منزلية")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("صيانة الاجهزة");
                    osNameList.add("سباكة");
                    osNameList.add("الموتال");
                    osNameList.add("حدادة");
                    osNameList.add("بويات");
                    osNameList.add("نقل عفش");
                    osNameList.add("رخام و جرانيت");
                    osNameList.add("توريدات كهرباء");

                    //


                }
                else if (newStringz.equals("اثاث منزلي")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("اثاث");
                    osNameList.add("نجف و تحف");
                    osNameList.add("مراتب و مفروشات");
                    osNameList.add("معارض سجاد");
                    osNameList.add("ابواب مصفحة");
                    osNameList.add("ستائر  و اقمشة");
                    osNameList.add("زجاج و مرايا");
                    //
                }
                else if (newStringz.equals("شركات الاتصالات")) {
                    osNameList.add("Vodafone");
                    osNameList.add("Etisalat");
                    osNameList.add("WE");
                    osNameList.add("Orange");
                }
                else {
                     i = 0 ;
                }


                if (i == 1) {
                    final String[] list = getStringArray(osNameList);

                    AlertDialog sMatrial = new AlertDialog.Builder(AddAdminActivity.this)
                            .setTitle("اختر")
                            .setItems(list, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    kind_txt.setText(list[which]);
                                    text_kind = list[which] ;
                                }
                            })
                            .show();
                }
                else {
                    kind_txt.setText("لا يوجد فروع");
                }

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText_name.getText().toString();
                address = editText_address.getText().toString();
                telephone = editText_tel.getText().toString();
                gate = editText_gate.getText().toString();


                if (name.equals("") || address.equals("") || telephone.equals("")  || gate.equals("") /*|| text_kind == null*/  ){
                    Toast.makeText(AddAdminActivity.this, "No Full Information", Toast.LENGTH_SHORT).show();
                }else
                {
                    if (newStringz.equals("كافيهات") || newStringz.equals("صيدليات") ||
                    newStringz.equals("موبيلات")|| newStringz.equals("حيوانات اليفة") || newStringz.equals("مكاتب محاماه")) {
                        newPostُTwo(newStringz);
                    }
                    else {
                        newPost(newStringz, text_kind);
                    }
                }


            }
        });


    }

    private void newPost(String s , String m) {

        final String sub = s ;
        final String sub_sub = m;

        FirebaseDatabase.getInstance().getReference().child("all").child(sub).child("elements").child(sub_sub).push() ;
        String key = FirebaseDatabase.getInstance().getReference().child("all").child(sub).child("elements").child(sub_sub).push().getKey();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("all").child(sub).child("elements").child(sub_sub).child(key);

        HashMap map = new HashMap();
        map.put("name", name);
        map.put("address", address);
        map.put("telephone", telephone);
        map.put("gate", gate);
        map.put("deactive", "no");
        map.put("kind", sub_sub);
        map.put("profile_img", "waiting");
        map.put("profile_img2", "no");
        map.put("profile_img3", "no");
        map.put("profile_img4", "no");
        map.put("profile_img5", "no");
        map.put("profilesp", "no");
        map.put("rating", "3");
        map.put("special", "no");
        map.put("website", "no");
        map.put("speciality", text_kind);



        current_user_db.updateChildren(map);
        uploadImage(current_user_db);

        Toast.makeText(this, "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
        editText_name.setText("");
        editText_address.setText("");
        editText_gate.setText("");
        editText_tel.setText("");
        kind_txt.setText("");

        startActivity(new Intent(getApplicationContext() , MainActivity.class));

    }

    private void newPostُTwo(String s) {

        final String sub = s ;

        FirebaseDatabase.getInstance().getReference().child("all").child(sub).child("elements").push() ;
        String key = FirebaseDatabase.getInstance().getReference().child("all").child(sub).child("elements").push().getKey();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("all").child(sub).child("elements").child(key);

        HashMap map = new HashMap();
        map.put("name", name);
        map.put("address", address);
        map.put("telephone", telephone);
        if (editText_tel_two.getText() != null) {
            map.put("telephone_two", editText_tel_two.getText().toString());
        }else {
            map.put("telephone_two", "off");
        }
        map.put("gate", gate);
        map.put("deactive", "no");
        map.put("kind", sub);
        map.put("profile_img", "waiting");
        map.put("profile_img2", "no");
        map.put("profile_img3", "no");
        map.put("profile_img4", "no");
        map.put("profile_img5", "no");
        map.put("profilesp", "no");
        map.put("rating", "3");
        map.put("special", "no");
        map.put("website", "no");





        current_user_db.updateChildren(map);
        uploadImage(current_user_db);

        Toast.makeText(this, "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
        editText_name.setText("");
        editText_address.setText("");
        editText_gate.setText("");
        editText_tel.setText("");



        startActivity(new Intent(getApplicationContext() , MainActivity.class));

    }

    public static String[] getStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
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

            StorageReference ref = storageReference.child("new_images/"+name+"_"+UUID.randomUUID().toString());

            Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());

            ref.putFile(getImageUri(AddAdminActivity.this,myBitmap))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            HashMap map_sp = new HashMap();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    map_sp.put("profile_img", downloadUrl.toString());
                                    current.updateChildren(map_sp) ;
                                }
                            });

                            Toast.makeText(AddAdminActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddAdminActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
