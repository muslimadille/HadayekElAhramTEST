package com.kobtan.fahmy.hadayekelahram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Typeface typeface;
    private ListView list;
    private ArrayList<String> itemname = new ArrayList<>();
    private ArrayList<String> itemaddress = new ArrayList<>();
    private ArrayList<Float> itemrate = new ArrayList<>();
    private ArrayList<String> itemimg = new ArrayList<>();
    private ArrayList<String> itemtel = new ArrayList<>();
    private ArrayList<String> itemkey = new ArrayList<>();
    private CustomListAdapterTwo adapter;
    private TextView text_bwaba, text_sub, text_sub_sub;
    private ArrayList<String> osNameList = new ArrayList<>() ;
    private String newString ;
    private DatabaseReference mCustomerDatabase ;
    private Integer numberGate ;
    private  ArrayList<Double> maplong = new ArrayList<>();
    private  ArrayList<Double> map_ard = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(" ");
        typeface = Typeface.createFromAsset(getAssets(), "font.ttf");

        backky();


        list = (ListView) findViewById(R.id.list);
        text_bwaba = (TextView) findViewById(R.id.one);
        text_sub = (TextView) findViewById(R.id.two);
        text_sub_sub = (TextView) findViewById(R.id.three);
        text_bwaba.setTypeface(typeface);
        text_sub.setTypeface(typeface);
        text_sub_sub.setTypeface(typeface);








        text_sub.setEnabled(false);
        text_sub_sub.setEnabled(false);

        text_bwaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> items = new ArrayList<String>();

                items.add("البوابة الاولي") ;
                items.add("البوابة الثانية") ;
                items.add("البوابة الثالثة") ;
                items.add("البوابة الرابعة") ;
                items.add("بوابة احمس") ;
                items.add("بوابة حورس") ;
                items.add("جميع البوابات") ;




                final String[] list = getStringArray(items);

                AlertDialog sMatrial = new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("اختر البوابة")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                text_bwaba.setText(list[which]);
                                numberGate = which+1 ;
                                text_sub.setEnabled(true);

                            }
                        })
                        .show();


            }
        });

        text_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> items = new ArrayList<String>();
                osNameList = new ArrayList<>();
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
                items.add("مكاتب محامة");
                items.add("اثاث منزلي") ;


                final String[] list = getStringArray(items);

                AlertDialog ssMatrial = new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("اختر النوع")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                text_sub.setText(list[which]);
                                newString = list[which];
                                text_sub_sub.setEnabled(true);

                            }
                        })
                        .show();


            }
        });

        text_sub_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                osNameList = new ArrayList<>();


                if (newString.equals("تسوق")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("سوبر ماركت");
                    osNameList.add("خضار و فاكهة");
                    osNameList.add("حلويات");
                    osNameList.add("جزار");
                    osNameList.add("دواجن و طيور");
                    osNameList.add("عطارة");
                    osNameList.add("دراي كلين");
                    osNameList.add("ادوات منزلية");
                    osNameList.add("منظفات"); // جيم و رياضة
                    osNameList.add("خياطه و تفصيل");
                    osNameList.add("محلات 2,5") ;
                    osNameList.add("مكتبات");
                    osNameList.add("محمصات و عصائر");
                }
                else if (newString.equals("مطاعم")) {
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
                else if (newString.equals("مستشفيات")) {
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
                else if (newString.equals("مستلزمات اطفال")) {
                    //
                    osNameList = new ArrayList<>() ;

                    osNameList.add("ملابس اطفال");
                    osNameList.add("لعب اطفال");

                }
                else if (newString.equals("دكتور خاص")) {
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
                    //


                }
                else if (newString.equals("معامل")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مراكز اشعة");
                    osNameList.add("معامل تحليل");
                    //


                }
                else if (newString.equals("موضة و ملابس")) {
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
                else if (newString.equals("اجهزة الكترونية")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("تكيفات");
                    osNameList.add("اجهزة منزلية");
                    osNameList.add("راديو شاك");
                    osNameList.add("كمبيوتر وصيانة");


                }
                else if (newString.equals("سيارات")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مراكز صيانة");
                    osNameList.add("مغسلة");
                    osNameList.add("معارض سيارات");
                    osNameList.add("كماليات سيارات");
                    osNameList.add("زجاج سيارات");
                    osNameList.add("ايجار سيارات");


                }
                else if (newString.equals("تعليمي")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مدارس");
                    osNameList.add("حضانات");
                    osNameList.add("سنتر تعليمي");
                    osNameList.add("انشطة اخري");


                }
                else if (newString.equals("ماكينات الصرافة")) {
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
                    //


                }
                else if (newString.equals("عقارات")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("ايجار");
                    osNameList.add("تمليك");
                    osNameList.add("تشطيبات وديكور");
                    //

                }
                else if (newString.equals("خدمات حكومية")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("مقر الجمعية");
                    osNameList.add("شهر العقاري");
                    osNameList.add("شركة المياه");
                    osNameList.add("شركة الغاز");
                    osNameList.add("سنترال الرماية");
                    osNameList.add("مكتب البريد");
                    osNameList.add("مطافي");
                    osNameList.add("اسعاف");
                    osNameList.add("نقطة شرطة");
                    osNameList.add("الوحدة الصحية");
                    osNameList.add("طوارئ كهرباء");
                    osNameList.add("نادي حدائق الاهرام");
                    //


                }
                else if (newString.equals("صيانة منزلية")) {
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
                else if (newString.equals("موبيلات")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("موبلات واكسسوارات");
                    osNameList.add("شركات الاتصالات");


                }
                else if (newString.equals("حيوانات اليفة")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("كل الحيوانات");
                    osNameList.add("مستشفي بيطري");
                    //

                }
                else if (newString.equals("اثاث منزلي")) {
                    //
                    osNameList = new ArrayList<>() ;
                    //
                    osNameList.add("اثاث");
                    osNameList.add("نجف و تحف");
                    osNameList.add("مراتب و مفروشات");
                    osNameList.add("معارض سجاد");
                    osNameList.add("ادوات منزلية");
                    osNameList.add("ابواب مصفحة");
                    osNameList.add("ستائر  و اقمشة");
                    osNameList.add("زجاج و مرايا");
                    //

                }

                String list[] = new String[osNameList.size()];
                list = getStringArray(osNameList);
                final String list_t[] = list ;

                AlertDialog ssMatrial = new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("اختر الفرع")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                text_sub_sub.setText(list_t[which]);
                                getUserInfo(text_sub.getText().toString() , text_sub_sub.getText().toString() , String.valueOf(numberGate));
                            }
                        })
                        .show();


            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                String selecteditem = itemname.get(position);

                Intent i = new Intent(getApplicationContext() , ObjectPreviewActivity.class);
                i.putExtra("STRING_I_NEED", text_sub.getText().toString() ) ;
                i.putExtra("STRING_I_NEED2", text_sub_sub.getText().toString() ) ;
                i.putExtra("STRING_I_NEED3", itemkey.get(position) ) ;
                startActivity(i);

            }
        });


    }


    private void getUserInfo(final String text_sub_er , final String text_sub_sub_er , final String text_bwabaa){
        itemname = new ArrayList<>();
        itemimg = new ArrayList<>();
        itemrate = new ArrayList<>();
        itemaddress = new ArrayList<>();
        itemtel = new ArrayList<>();
        itemkey = new ArrayList<>();


        maplong = new ArrayList<>();
        map_ard = new ArrayList<>();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(text_sub_er).child("elements").child(text_sub_sub_er);

        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {
                        if (dataSnapshot.child("gate").getValue().toString().equals(text_bwabaa)) {
                            itemname.add(childSnapShot.getValue().toString());
                            itemimg.add(dataSnapshot.child("profile_img").getValue().toString());
                            itemrate.add(Float.valueOf(dataSnapshot.child("rating").getValue().toString()));
                            itemaddress.add(dataSnapshot.child("address").getValue().toString());
                            itemtel.add(dataSnapshot.child("telephone").getValue().toString());
                            itemkey.add(dataSnapshot.getKey().toString());
                            if (dataSnapshot.hasChild("map_long") && dataSnapshot.hasChild("map_ard") ) {
                                maplong.add(Double.valueOf(dataSnapshot.child("map_long").getValue().toString()));
                                map_ard.add(Double.valueOf(dataSnapshot.child("map_ard").getValue().toString()));
                            }

                            else {
                                maplong.add(Double.valueOf(0));
                                map_ard.add(Double.valueOf(0));
                            }
                        } else {
                            itemname.add(childSnapShot.getValue().toString());
                            itemimg.add(dataSnapshot.child("profile_img").getValue().toString());
                            itemrate.add(Float.valueOf(dataSnapshot.child("rating").getValue().toString()));
                            itemaddress.add(dataSnapshot.child("address").getValue().toString());
                            itemtel.add(dataSnapshot.child("telephone").getValue().toString());
                            itemkey.add(dataSnapshot.getKey().toString());
                            if (dataSnapshot.hasChild("map_long") && dataSnapshot.hasChild("map_ard") ) {
                                maplong.add(Double.valueOf(dataSnapshot.child("map_long").getValue().toString()));
                                map_ard.add(Double.valueOf(dataSnapshot.child("map_ard").getValue().toString()));
                            }

                            else {
                                maplong.add(Double.valueOf(0));
                                map_ard.add(Double.valueOf(0));
                            }
                        }
                    }
                    //Log.i("Ameeer" , childSnapShot.toString() ) ;

                }
                adapter = new CustomListAdapterTwo(SearchActivity.this, itemname, itemaddress, itemrate , itemimg , itemtel , R.color.colorPrimary , maplong , map_ard);
                list.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
        finish();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(SearchActivity.this , MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });

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

}
