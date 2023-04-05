package com.kobtan.fahmy.hadayekelahram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kobtan.fahmy.hadayekelahram.AddNewAdv.AddNewAdvProductActivity;
import com.kobtan.fahmy.hadayekelahram.News.AddNewsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ObjectPreviewActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<String> imagesView  = new ArrayList<>();
    private Toolbar toolbar ;
    private RatingBar ratingBar ;
    private DatabaseReference mCustomerDatabase ;

    private TextView name_link ,value_link ;

    private TextView name , address , telephone , kind , kind_title , instagram , whatsappp ;
    private String newString , newString2 , newString3 ;
    private int newStringThree = 0 ;
    private ImageView img_call  , img_map;
    private Double maplong  , map_ard ;
    private Button b_choosen , b_sponser;

    private ArrayList<String> osNameList = new ArrayList<>() ;
    private ArrayList<String> items = new ArrayList<>() ;
    private String newStringz = " ";
    private String newStringzz = " ";
    private Uri call ;
    float rate_s = 11 ;
    private String img_url = "off" ;
    private RelativeLayout main_layout ;
    private String FacebookLink = "off" ;


    private static ViewPager mPager_new;
    private static int currentPage_new = 0;
    private static int NUM_PAGES_new = 0;
    private ArrayList<String> imagesView_new  = new ArrayList<>();
    final Handler handler_new = new Handler();
    public Timer swipeTimer_new ;
    private String map_link="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_preview);
        getWindow().getAttributes().windowAnimations = R.style.Fade;

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


        //  color


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newStringThree= 0;
            } else {
                newStringThree= extras.getInt("STRING_I_NEED4");
            }
        } else {
            newStringThree= (int) savedInstanceState.getSerializable("STRING_I_NEED4");
        }


        ratingBar = (RatingBar) findViewById(R.id.ratingg) ;
        name = (TextView) findViewById(R.id.name) ;
        address = (TextView) findViewById(R.id.blood_group) ;
        telephone = (TextView) findViewById(R.id.mobileNumber) ;
        kind = (TextView) findViewById(R.id.occupation) ;
        img_call = (ImageView) findViewById(R.id.textView16) ;
        img_map = (ImageView) findViewById(R.id.dob) ;
        b_choosen = (Button) findViewById(R.id.choose) ;
        b_sponser = (Button) findViewById(R.id.addsponser) ;
        name_link  =(TextView) findViewById(R.id.textView5) ;
        value_link  =(TextView) findViewById(R.id.education) ;
        kind_title  =(TextView) findViewById(R.id.textView7) ;
        main_layout  =(RelativeLayout) findViewById(R.id.layout) ;
        whatsappp  = findViewById(R.id.whatsappp) ;
        instagram  =findViewById(R.id.instagram) ;

        if (newString != null)
        {

            if (newString2 != null)
            {
                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements")
                        .child(newString2);

                getUserInfo();
            }
            else
            {
                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements");

                getUserInfo();
            }

        }
        else
        {
            Toast.makeText(this, "خطأ", Toast.LENGTH_SHORT).show();
        }




                img_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (kind_title.getText().toString().equals("Kind: ")) {
                            String number = telephone.getText().toString();
                            call = Uri.parse("tel:" + number);
                            Intent surf = new Intent(Intent.ACTION_DIAL, call);
                            startActivity(surf);

                            Toast.makeText(getApplicationContext(), "Calling", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ArrayList<String> items = new ArrayList<String>();

                            items.add(telephone.getText().toString()) ;
                            items.add(kind.getText().toString()) ;




                            MaterialDialog matrial = new MaterialDialog.Builder(ObjectPreviewActivity.this)
                                    .title("الخيارات")
                                    .items(items)
                                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                        @Override
                                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                            if (text != null) {
                                                String number = telephone.getText().toString();
                                                call = Uri.parse("tel:" + text);
                                                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                                                startActivity(surf);

                                                Toast.makeText(getApplicationContext(), "Calling", Toast.LENGTH_SHORT).show();
                                            }

                                            return true;
                                        }
                                    })
                                    .positiveText("تأكيد الإختيار")
                                    .buttonRippleColorRes(R.color.colorAccent)
                                    .backgroundColorRes(R.color.colorAccent)
                                    .show();
                        }

                    }
                });

        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maplong>0.0&&map_ard>0.0){
                    Intent i =  new Intent(getApplicationContext() , MapsActivity.class) ;
                    i.putExtra("STRING_I_NEED" , maplong) ;
                    i.putExtra("STRING_I_NEED2" , map_ard) ;
                    startActivity(i);
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(map_link));
                    startActivity(intent);
                }

            }
        });

        //main_layout.removeView(b_choosen);
        //main_layout.removeView(b_sponser);

        b_sponser.setAlpha(0);

//        b_sponser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if (newString2 != null)
//                {
//
//                    Intent i = new Intent(getApplicationContext() , SponserAddActivity.class) ;
//                    i.putExtra("STRING_I_NEED" , newString3) ;
//                    i.putExtra("STRING_I_NEED2" , name.getText().toString()) ;
//                    i.putExtra("STRING_I_NEED3" , newString) ;
//                    i.putExtra("STRING_I_NEED4" , newString2) ;
//                    i.putExtra("STRING_I_NEED5" , img_url) ;
//                    startActivity(i);
//
//
//                }
//                else
//                {
//
//                    Intent i = new Intent(getApplicationContext() , SponserAddActivity.class) ;
//                    i.putExtra("STRING_I_NEED" , newString3) ;
//                    i.putExtra("STRING_I_NEED2" , name.getText().toString()) ;
//                    i.putExtra("STRING_I_NEED3" , newString) ;
//                    i.putExtra("STRING_I_NEED4" , "off") ;
//                    i.putExtra("STRING_I_NEED5" , img_url) ;
//                    startActivity(i);
//
//                }
//
//            }
//
//        });

       b_choosen.setAlpha(0);

//       b_choosen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ArrayList<String> items = new ArrayList<String>();
//
//                items.add("مسح الاعلان") ;
//                items.add("تعديل الإعلان") ;
//                items.add("نقل الاعلان") ;
//                items.add("الصور الاعلانية") ;
//                items.add("عروض الاعلان") ;
//
//
//
//
//                MaterialDialog matrial = new MaterialDialog.Builder(ObjectPreviewActivity.this)
//                        .title("الخيارات")
//                        .items(items)
//                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
//                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                            @Override
//                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//
//                                if (text != null) {
//
//                                    if (text == "مسح الاعلان" ) {
//                                        removeOpj(newString3);
//                                    }
//                                    if (text == "الصور الاعلانية" ) {
//                                        if (newString2 != null)
//                                        {
//                                            Intent i  = new Intent(getApplicationContext() , AddNewAdvProductActivity.class);
//                                            i.putExtra("STRING_I_NEED" , newString) ;
//                                            i.putExtra("STRING_I_NEED2" , newString2) ;
//                                            i.putExtra("STRING_I_NEED3" , newString3) ;
//                                            i.putExtra("STRING_I_NEED4" , newStringThree) ;
//                                            startActivity(i);
//                                        }
//                                        else
//                                        {
//                                            Intent i  = new Intent(getApplicationContext() , AddNewAdvProductActivity.class);
//                                            i.putExtra("STRING_I_NEED" , newString) ;
//                                            i.putExtra("STRING_I_NEED3" , newString3) ;
//                                            i.putExtra("STRING_I_NEED4" , newStringThree) ;
//                                            startActivity(i);
//
//                                        }
//
//                                    }
//                                    if (text == "عروض الاعلان" ) {
//                                        Intent i  = new Intent(getApplicationContext() , AddNewsActivity.class);
//                                        i.putExtra("STRING_I_NEED" , name.getText().toString()) ;
//                                        i.putExtra("STRING_I_NEED2" , img_url) ;
//                                        i.putExtra("STRING_I_NEED3" , newString3) ;
//                                        i.putExtra("STRING_I_NEED4" , telephone.getText().toString()) ;
//                                        i.putExtra("STRING_I_NEED5" , address.getText().toString()) ;
//                                        startActivity(i);
//                                    }
//
//                                    if (text == "تعديل الإعلان" ) {
//
//                                        if (newString2 != null)
//                                        {
//                                            Intent i  = new Intent(getApplicationContext() , EditProductActivity.class);
//                                            i.putExtra("STRING_I_NEED" , newString) ;
//                                            i.putExtra("STRING_I_NEED2" , newString2) ;
//                                            i.putExtra("STRING_I_NEED3" , newString3) ;
//                                            i.putExtra("STRING_I_NEED4" , newStringThree) ;
//                                            startActivity(i);
//                                        }
//                                        else
//                                        {
//                                            Intent i  = new Intent(getApplicationContext() , EditProductActivity.class);
//                                            i.putExtra("STRING_I_NEED" , newString) ;
//                                            i.putExtra("STRING_I_NEED3" , newString3) ;
//                                            i.putExtra("STRING_I_NEED4" , newStringThree) ;
//                                            startActivity(i);
//
//                                        }
//
//
//                                    }
//                                    if (text == "نقل الاعلان"){
//                                        displayDialog();
//                                    }
//
//                                }
//
//                                return true;
//                            }
//                        })
//                        .positiveText("تأكيد الإختيار")
//                        .buttonRippleColorRes(R.color.colorAccent)
//                        .backgroundColorRes(R.color.colorAccent)
//                        .show();
//
//            }
//        });


    }

    private void goFacebookUrl () {
        value_link.setTextColor(Color.BLUE);
        value_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ObjectPreviewActivity.this, "Openning Facebook Page Now !", Toast.LENGTH_LONG).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FacebookLink));
                startActivity(browserIntent);
            }
        });

    }
    private void goInstagramUrl () {
        instagram.setTextColor(Color.BLUE);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ObjectPreviewActivity.this, "Openning Instagram Page Now !", Toast.LENGTH_LONG).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagram.getText().toString()));
                startActivity(browserIntent);
            }
        });

    }

    private void getUserInfo(){
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().toString().equals(newString3))
                {

                    if (dataSnapshot.hasChild("name")) {

                            name.setText(dataSnapshot.child("name").getValue().toString());
                            //Toast.makeText(ObjectPreviewActivity.this, "not here", Toast.LENGTH_SHORT).show();
                    }
                    if (dataSnapshot.hasChild("website")) {

                        if (dataSnapshot.child("website").getValue().toString().equals("no")) {

                        }else {
                            name_link.setText("Facebook: ");
                            FacebookLink = dataSnapshot.child("website").getValue().toString() ;
                            value_link.setText("Facebook Page");
                            goFacebookUrl();
                        }
                    }
                    if (dataSnapshot.hasChild("telephone")) {

                        telephone.setText(dataSnapshot.child("telephone").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("instagram")) {

                        instagram.setText(dataSnapshot.child("instagram").getValue().toString());
                        goInstagramUrl();
                    }
                    if (dataSnapshot.hasChild("whatsapp")) {

                        whatsappp.setText(dataSnapshot.child("whatsapp").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("address")) {

                        address.setText(dataSnapshot.child("address").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("rating")) {

                        ratingBar.setRating(Float.valueOf(dataSnapshot.child("rating").getValue().toString()));
                    }

                    if (dataSnapshot.hasChild("telephone_two")) {
                        kind_title.setText( "Mobile: ");
                        kind.setText(dataSnapshot.child("telephone_two").getValue().toString());
                    }else {
                        if (dataSnapshot.hasChild("kind")) {
                            kind_title.setText( "Kind: ");
                            kind.setText(dataSnapshot.child("kind").getValue().toString());
                        }
                    }

                    if (dataSnapshot.hasChild("profile_img"))
                    {
                        imagesView.add(dataSnapshot.child("profile_img").getValue().toString());
                        img_url = dataSnapshot.child("profile_img").getValue().toString() ;

                    }
                    if(dataSnapshot.hasChild("profile_img2")) {
                        if (dataSnapshot.child("profile_img2").getValue().toString().equals("no")){
                            imagesView.add(dataSnapshot.child("profile_img").getValue().toString());
                        }
                        else {
                            imagesView.add(dataSnapshot.child("profile_img2").getValue().toString());
                        }
                    }
                    if(dataSnapshot.hasChild("profile_img3")) {
                        if (dataSnapshot.child("profile_img3").getValue().toString().equals("no")){
                            imagesView.add(dataSnapshot.child("profile_img").getValue().toString());
                        }
                        else {
                            imagesView.add(dataSnapshot.child("profile_img3").getValue().toString());
                        }
                    }
                    if(dataSnapshot.hasChild("profile_img4")) {
                        if (dataSnapshot.child("profile_img4").getValue().toString().equals("no")){
                            imagesView.add(dataSnapshot.child("profile_img").getValue().toString());
                        }
                        else {
                            imagesView.add(dataSnapshot.child("profile_img4").getValue().toString());
                        }
                    }
                    if(dataSnapshot.hasChild("profile_img5")) {
                        if (dataSnapshot.child("profile_img5").getValue().toString().equals("no")){
                            imagesView.add(dataSnapshot.child("profile_img").getValue().toString());
                        }
                        else {
                            imagesView.add(dataSnapshot.child("profile_img5").getValue().toString());
                        }
                    }
                    if(dataSnapshot.hasChild("new_adv")) {
                        mCustomerDatabase.child(newString3).child("new_adv").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    imagesView_new.add(snapshot.getValue().toString());
                                }
                                init_new();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }) ;
                    }
                    if (dataSnapshot.hasChild("map_long") && dataSnapshot.hasChild("map_ard") ) {
                        maplong = Double.valueOf(dataSnapshot.child("map_long").getValue().toString());
                        map_ard = Double.valueOf(dataSnapshot.child("map_ard").getValue().toString());
                    }else if(dataSnapshot.hasChild("map_link")&&!(dataSnapshot.hasChild("map_long") && dataSnapshot.hasChild("map_ard") )){
                       map_link= dataSnapshot.child("map_link").getValue().toString();
                    }
                    else {
                        maplong = Double.valueOf(0);
                        map_ard = Double.valueOf(0);
                    }
                }
                init();

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

    private void init() {








        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new ViewPagerAbaterTwo(ObjectPreviewActivity.this, imagesView));


        //CirclePageIndicator indicator = (CirclePageIndicator)
        //findViewById(R.id.indicator);

        // indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        // indicator.setRadius(5 * density);

        NUM_PAGES =imagesView.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        /*swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);*/

        // Pager listener over indicator
       /* indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        }); */

        ImageButton leftNav = (ImageButton) findViewById(R.id.left_nav);
        ImageButton rightNav = (ImageButton) findViewById(R.id.right_nav);

// Images left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    mPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    mPager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mPager.getCurrentItem();
                tab++;
                mPager.setCurrentItem(tab);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        if (call != null && rate_s==11) {
            displayDialogRating();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (newString2 != null)
        {

            Intent i = new Intent(getApplicationContext(), MainChildActivity.class);
            i.putExtra("STRING_I_NEED2", newString);
            i.putExtra("STRING_I_NEED", newString2);
            if (newStringThree != 0  ) {
                i.putExtra("STRING_I_NEED_TWO", newStringThree);
                startActivity(i);
            }
            else {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }


        }
        else
        {

            Intent i = new Intent(getApplicationContext(), MainChildActivity.class);
            i.putExtra("STRING_I_NEED2", newString);
            //i.putExtra("STRING_I_NEED", newString2);
            if (newStringThree != 0) {
                i.putExtra("STRING_I_NEED_TWO", newStringThree);
                startActivity(i);
            }
            else {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }

        }

    }


    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newString2 != null)
                {

                    Intent i = new Intent(getApplicationContext(), MainChildActivity.class);
                    i.putExtra("STRING_I_NEED2", newString);
                    i.putExtra("STRING_I_NEED", newString2);
                    if (newStringThree != 0) {
                        i.putExtra("STRING_I_NEED_TWO", newStringThree);
                        startActivity(i);
                    }
                    else {
                        startActivity(new Intent(getApplicationContext() , MainActivity.class));
                    }


                }
                else
                {

                    Intent i = new Intent(getApplicationContext(), MainChildActivity.class);
                    i.putExtra("STRING_I_NEED2", newString);
                    //i.putExtra("STRING_I_NEED", newString2);
                    if (newStringThree != 0) {
                        i.putExtra("STRING_I_NEED_TWO", newStringThree);
                        startActivity(i);
                    }
                    else {
                        startActivity(new Intent(getApplicationContext() , MainActivity.class));
                    }

                }



            }
        });

    }
    private void removeOpj (String s) {



        if (newString2 != null)
        {
            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements")
                    .child(newString2);
            mCustomerDatabase.child(s).setValue(null);

            Intent i = new Intent(getApplicationContext(), MainChildActivity.class);
            i.putExtra("STRING_I_NEED2", newString);
            i.putExtra("STRING_I_NEED", newString2);
            i.putExtra("STRING_I_NEED_TWO", newStringThree);
            startActivity(i);
        }
        else
        {
            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newString).child("elements");
            mCustomerDatabase.child(s).setValue(null);


            Intent i = new Intent(getApplicationContext(), MainChildActivity.class);
            i.putExtra("STRING_I_NEED2", newString);
            //i.putExtra("STRING_I_NEED", newString2);
            i.putExtra("STRING_I_NEED_TWO", newStringThree);
            startActivity(i);
        }





    }
    private void transferObj (String s , String m) {
        HashMap map = new HashMap();

        mCustomerDatabase.child(newString3).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        map.put("name", dataSnapshot.child("name").getValue().toString());
                        map.put("address", dataSnapshot.child("address").getValue().toString());
                        map.put("telephone", dataSnapshot.child("telephone").getValue().toString());
                        map.put("gate", dataSnapshot.child("gate").getValue().toString());
                        map.put("deactive", dataSnapshot.child("deactive").getValue().toString());
                        map.put("profile_img", dataSnapshot.child("profile_img").getValue().toString());
                        map.put("profile_img2", dataSnapshot.child("profile_img2").getValue().toString());
                        map.put("profile_img3", dataSnapshot.child("profile_img3").getValue().toString());
                        map.put("profile_img4", dataSnapshot.child("profile_img4").getValue().toString());
                        map.put("profile_img5", dataSnapshot.child("profile_img5").getValue().toString());
                        map.put("profilesp", dataSnapshot.child("profilesp").getValue().toString());
                        map.put("rating", dataSnapshot.child("rating").getValue().toString());
                        map.put("special", dataSnapshot.child("special").getValue().toString());
                        map.put("website", dataSnapshot.child("website").getValue().toString());
                        map.put("kind", s);
                        map.put("speciality", m);

                        DatabaseReference mm = FirebaseDatabase.getInstance().getReference().child("all").child(s).child("elements")
                                .child(m).child(newString3) ;
                        mm.updateChildren(map);


                        Toast.makeText(ObjectPreviewActivity.this, "تم بنجاح", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



    }

    private void transferObj_sub (String s) {
        HashMap map = new HashMap();

        mCustomerDatabase.child(newString3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map.put("name", dataSnapshot.child("name").getValue().toString());
                map.put("address", dataSnapshot.child("address").getValue().toString());
                map.put("telephone", dataSnapshot.child("telephone").getValue().toString());
                map.put("gate", dataSnapshot.child("gate").getValue().toString());
                map.put("deactive", dataSnapshot.child("deactive").getValue().toString());
                map.put("profile_img", dataSnapshot.child("profile_img").getValue().toString());
                map.put("profile_img2", dataSnapshot.child("profile_img2").getValue().toString());
                map.put("profile_img3", dataSnapshot.child("profile_img3").getValue().toString());
                map.put("profile_img4", dataSnapshot.child("profile_img4").getValue().toString());
                map.put("profile_img5", dataSnapshot.child("profile_img5").getValue().toString());
                map.put("profilesp", dataSnapshot.child("profilesp").getValue().toString());
                map.put("rating", dataSnapshot.child("rating").getValue().toString());
                map.put("special", dataSnapshot.child("special").getValue().toString());
                map.put("website", dataSnapshot.child("website").getValue().toString());
                map.put("kind", s);


                DatabaseReference mm =FirebaseDatabase.getInstance().getReference().child("all").child(s)
                        .child("elements").child(newString3);

                mm.updateChildren(map);


                Toast.makeText(ObjectPreviewActivity.this, "تم بنجاح", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayDialog()
    {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("اختر الفروع")
                .setIcon(R.drawable.ic_send_white_24dp)
                .setView(R.layout.dialogetransferlayout)
                .create();
        d.show();


        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
        TextView kind_main = (TextView) d.findViewById(R.id.kind_main) ;
        TextView kind = (TextView) d.findViewById(R.id.kind) ;

        kind_main.setOnClickListener(new View.OnClickListener() {
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
                items.add("حفلات ومناسبات");


                final String[] list = getStringArray(items);

                AlertDialog sMatrial = new AlertDialog.Builder(ObjectPreviewActivity.this)
                        .setTitle("اختر")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                kind_main.setText(list[which]);
                                newStringz = list[which] ;
                            }
                        })
                        .show();
            }
        });
        kind.setOnClickListener(new View.OnClickListener() {
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

                    AlertDialog sMatrial = new AlertDialog.Builder(ObjectPreviewActivity.this)
                            .setTitle("اختر")
                            .setItems(list, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    kind.setText(list[which]);
                                    newStringzz = list[which] ;
                                }
                            })
                            .show();
                }
                else {
                    kind.setText("لا يوجد فروع");
                }

            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! newStringz.equals(" ")) {
                    if (newStringz.equals("كافيهات") || newStringz.equals("صيدليات") ||
                            newStringz.equals("موبيلات")|| newStringz.equals("حيوانات اليفة") || newStringz.equals("مكاتب محاماه")) {
                        transferObj_sub(newStringz );
                    }
                    else {
                        transferObj(newStringz, newStringzz);
                    }
                }
                else {
                    Toast.makeText(ObjectPreviewActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }
                d.hide();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayDialogRating()
    {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Give Your Rate")
                .setIcon(R.drawable.logoteb)
                .setView(R.layout.dialogeratinglayout)
                .setCancelable(false)
                .create();
        d.show();


        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
        RatingBar ratingBar_s= (RatingBar) d.findViewById(R.id.ratingBar);



        ratingBar_s.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                rate_s = rating ;

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rate_s != 11 && mCustomerDatabase != null) {
                    Toast.makeText(ObjectPreviewActivity.this, String.valueOf(rate_s), Toast.LENGTH_SHORT).show();

                    mCustomerDatabase.child(newString3).child("rate_list").push();
                    String kkey = mCustomerDatabase.child(newString3).child("rate_list").push().getKey().toString();

                    HashMap map_two = new HashMap();
                    map_two.put("rate", String.valueOf(rate_s));

                    mCustomerDatabase.child(newString3).child("rate_list").child(kkey).updateChildren(map_two);

                    d.hide();
                }else {
                    Toast.makeText(ObjectPreviewActivity.this, "ادخل التقييم", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    private void init_new() {

        mPager_new = (ViewPager) findViewById(R.id.pager_new);


        mPager_new.setAdapter(new ViewPagerAbaterThree(ObjectPreviewActivity.this, imagesView_new));
        mPager_new.setPageTransformer(true, new RotateUpTransformer());

        //CirclePageIndicator indicator = (CirclePageIndicator)
        //findViewById(R.id.indicator);

        // indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        // indicator.setRadius(5 * density);

        NUM_PAGES_new =imagesView_new.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage_new == NUM_PAGES_new) {
                    currentPage_new = 0;
                }
                mPager_new.setCurrentItem(currentPage_new++, true);
            }
        };

        setTimer(mPager_new , 4);

    }


    public void setTimer(final ViewPager myPager_new, int time){



        final Runnable Update = new Runnable() {

            int currentPage_new = 0 ;
            public void run() {
                if (currentPage_new == NUM_PAGES_new ) {
                    currentPage_new = 0;
                }
                myPager_new.setCurrentItem(currentPage_new++, true);
            }
        };

        swipeTimer_new = new Timer();
        swipeTimer_new.schedule(new TimerTask() {

            @Override
            public void run() {
                handler_new.post(Update);
            }
        }, 250, time*1000);
    }


}
