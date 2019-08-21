package com.kobtan.fahmy.hadayekelahram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.view.ViewGroup.LayoutParams;



public class MainActivity extends AppCompatActivity {

    private GridView gridview;
    private ArrayList<String> kind = new ArrayList<>() ;
    private ArrayList<String> osNameList = new ArrayList<>() ;
    private ArrayList<String> osNameList_eng = new ArrayList<>() ;
    private ArrayList<Integer> osImages = new ArrayList<>() ;
    private ArrayList<Integer> osColors = new ArrayList<>() ;



    private Toolbar toolbar ;
    private DrawerLayout drawerLayout;
    private TextView title  , titlee;
    private Typeface typeface ;
    private CustomAdabter customAdabter ;
    private CustomAdabterBuy customAdabter_buy ;

    private LinearLayout all , buy ;
    private Button saveBtn ;
    private EditText nameEditText ;


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<String> imagesView  = new ArrayList<>();

    final Handler handler = new Handler();
    public Timer swipeTimer ;
    //private Button add ;
    private LinearLayout parent_linear ;
    private LinearLayout search ;
    private String newString ;
    private StringLocal stringLocal ;
    private TextView fast_text ;

    private String online = " ";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stringLocal = new StringLocal(MainActivity.this) ;
        setContentView(R.layout.activity_main);
        setContentView(R.layout.navigation_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        getSupportActionBar().setTitle(" ");

        parent_linear  = (LinearLayout) findViewById(R.id.parentt);

        swipeTimer = new Timer();

        //add  = (Button) findViewById(R.id.add);
        //add.setTypeface(typeface);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED3");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED3");
        }


        fast_text = (TextView) findViewById(R.id.search_fast) ;

        search  = (LinearLayout) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , SearchFullActivity.class));
            }
        });



        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");

        title = (TextView) findViewById(R.id.title) ;
        title.setTypeface(typeface);

        titlee = (TextView) findViewById(R.id.titlee) ;
        titlee.setTypeface(typeface);


        if (stringLocal.getUser() != null) {
            if (stringLocal.getUser().equals("en")) {
                titlee.setText("All Services");
                title.setText("Sell & Buy");
            }
            else {}
        }
        else {}

        all = (LinearLayout) findViewById(R.id.li_all) ;
        buy = (LinearLayout) findViewById(R.id.li_buy) ;

        //init("all");
        getData("all");


        gridview = (GridView) findViewById(R.id.customgrid);



        if (newString != null){


            getItemsBuy();

            //buy.setBackground(getResources().getDrawable(R.drawable.background_border_pager_main_change));
            //title.setTextColor(getResources().getColor(R.color.colorPrimary));

            //all.setBackground(getResources().getDrawable(R.drawable.background_border_pager_main));
            //titlee.setTextColor(getResources().getColor(R.color.colorAccent));

            toolbar.setBackgroundColor(getResources().getColor(R.color.four));
            gridview.setBackgroundColor(getResources().getColor(R.color.four));

            parent_linear.removeView(search);

            all.getLayoutParams().height = 140 ;
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) all.getLayoutParams();
            params.bottomMargin = 10 ;


            if (stringLocal.getUser() != null) {
                if (stringLocal.getUser().equals("en")) {
                    //add.setText("Add Advertising");
                }
                else {}
            }
            else {}
            all.setEnabled(true);

        }else {
            customAdabter =  new CustomAdabter(this, osNameList, osImages , osColors) ;
            gridview.setAdapter(customAdabter);

            getItemsAll();


            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            gridview.setBackgroundColor(getResources().getColor(R.color.colorBlowWhite));
            //all.setBackground(getResources().getDrawable(R.drawable.background_border_pager_main_change));
            //titlee.setTextColor(getResources().getColor(R.color.colorPrimary));

            all.getLayoutParams().height = LayoutParams.WRAP_CONTENT ;

            //buy.setBackground(getResources().getDrawable(R.drawable.background_border_pager_main));
            //title.setTextColor(getResources().getColor(R.color.colorAccent));


            //parent_linear.removeView(add);

            if (stringLocal.getUser() != null) {
                if (stringLocal.getUser().equals("en")) {
                    fast_text.setText("Fast Search");
                }
                else {}
            }
            else {}
            all.setEnabled(false);
        }


        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeTimer = new Timer();
                //init("all");
                getData("all");
                //parent_linear.removeView(add);
                parent_linear.addView(search);

                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                gridview.setBackgroundColor(getResources().getColor(R.color.colorBlowWhite));

                //all.setBackground(getResources().getDrawable(R.drawable.background_border_pager_main_change));
                //titlee.setTextColor(getResources().getColor(R.color.colorPrimary));

                all.getLayoutParams().height = LayoutParams.WRAP_CONTENT ;

                //buy.setBackground(getResources().getDrawable(R.drawable.background_border_pager_main));
                //title.setTextColor(getResources().getColor(R.color.colorAccent));
                getItemsAll() ;

                all.setEnabled(false);
                buy.setEnabled(true);

                if (stringLocal.getUser() != null) {
                    if (stringLocal.getUser().equals("en")) {
                        fast_text.setText("Fast Search");
                    }
                    else {}
                }
                else {}
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                swipeTimer = new Timer();
                //init("buy");
                getData("buy");
                parent_linear.removeView(search);
                //parent_linear.addView(add);


                all.getLayoutParams().height = 140 ;
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) all.getLayoutParams();
                params.bottomMargin = 10 ;


                toolbar.setBackgroundColor(getResources().getColor(R.color.four));
                gridview.setBackgroundColor(getResources().getColor(R.color.four));

                //buy.setBackground(getResources().getDrawable(R.drawable.background_border_pager_main_change));
                //title.setTextColor(getResources().getColor(R.color.colorPrimary));

                //all.setBackground(getResources().getDrawable(R.drawable.background_border_pager_main));
                //titlee.setTextColor(getResources().getColor(R.color.colorAccent));

                getItemsBuy() ;
                buy.setEnabled(false);
                all.setEnabled(true);

                if (stringLocal.getUser() != null) {
                    if (stringLocal.getUser().equals("en")) {
                      //  add.setText("Add Advertising");
                    }
                    else {}
                }
                else {}

                all.setEnabled(true);
            }
        });

        /*add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , NotePayActivity.class));
            }
        });*/

        //getData();
    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);

        View header = navigationView.getHeaderView(0);
        //TextView tv_email = (TextView)header.findViewById(R.id.tv_email);

        //tv_email.setText("Dr Ahmed Nabih");
        //circleImageView.setImageResource(R.drawable.drnabih);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        intentIcons();
    }

    public void intentIcons() {
        LinearLayout home , search , share , call , feedback , privacy , change_language , star , addyy , update , buys ;
        TextView home_k , search_k , share_k , call_k , feedback_k , privacy_k , change_language_k , star_k ;
        home = (LinearLayout) findViewById(R.id.home);
        search = (LinearLayout) findViewById(R.id.searchh);
        share = (LinearLayout) findViewById(R.id.share);
        call = (LinearLayout) findViewById(R.id.callus);
        feedback = (LinearLayout) findViewById(R.id.feedback);
        privacy = (LinearLayout) findViewById(R.id.privacy);
        change_language = (LinearLayout) findViewById(R.id.language);
        star = (LinearLayout) findViewById(R.id.star);
        addyy = (LinearLayout) findViewById(R.id.addy);
        update = (LinearLayout) findViewById(R.id.update);
        buys = (LinearLayout) findViewById(R.id.buys);

        //
        home_k = (TextView) findViewById(R.id.home_k) ;
        search_k = (TextView) findViewById(R.id.search_k) ;
        share_k = (TextView) findViewById(R.id.share_k) ;
        call_k = (TextView) findViewById(R.id.call_k) ;
        feedback_k = (TextView) findViewById(R.id.sugg_k) ;
        privacy_k = (TextView) findViewById(R.id.privacy_k) ;
        change_language_k = (TextView) findViewById(R.id.lang_k) ;
        star_k = (TextView) findViewById(R.id.fav_k) ;
        //

        if (stringLocal.getUser() != null) {
            if (stringLocal.getUser().equals("en")) {
                home_k.setText("Home");
                search_k.setText("Advanced Search");
                share_k.setText("Share");
                call_k.setText("Call us");
                feedback_k.setText("Feedback");
                privacy_k.setText("Privacy Policy");
                change_language_k.setText("Change Language");
                star_k.setText("Favorites");
            }
            else {}
        }
        else {}


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemsAll();
                drawerLayout.closeDrawers();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , SearchActivity.class));
                drawerLayout.closeDrawers();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
                drawerLayout.closeDrawers();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , AdvWithUsActivity.class));
                drawerLayout.closeDrawers();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                displayDialog();
                drawerLayout.closeDrawers();
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , PrivacyActivity.class));
                drawerLayout.closeDrawers();
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , FavouritsActvity.class));
                drawerLayout.closeDrawers();
            }
        });
       addyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , AddAdminActivity.class));
                drawerLayout.closeDrawers();
            }
        });
        buys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , AdminBuysActivity.class));
                drawerLayout.closeDrawers();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mmk = FirebaseDatabase.getInstance().getReference() ;



                mmk.child("current").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String online =dataSnapshot.getValue().toString();

                            try {
                                String currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                                if (currentVersion.equals(online)) {
                                    Toast.makeText(MainActivity.this, "لا يوجد تحديث", Toast.LENGTH_LONG).show();

                                }
                                else {
                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                    Toast.makeText(MainActivity.this, "جاري الذهاب للتحديث", Toast.LENGTH_LONG).show();
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }) ;



                drawerLayout.closeDrawers();
            }
        });
        change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] list = new String[]{"اللغة العربية", "اللغة الانجليزية"};
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Language")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, list[which], Toast.LENGTH_SHORT).show();
                                if (list[which].equals("اللغة العربية")) {
                                    drawerLayout.closeDrawers();
                                    stringLocal.storeUser("ar");
                                    startActivity(new Intent(getApplicationContext() , MainActivity.class));
                                }else if (list[which].equals("اللغة الانجليزية")) {
                                    drawerLayout.closeDrawers();
                                    stringLocal.storeUser("en");
                                    startActivity(new Intent(getApplicationContext() , MainActivity.class));
                                }
                                else {

                                }
                            }
                        })
                        .show();
                drawerLayout.closeDrawers();
            }
        });


    }


    private void  getItemsAll()   {



        osNameList = new ArrayList<>() ;
        osColors = new ArrayList<>() ;
        osImages = new ArrayList<>() ;

        osNameList.add("مطاعم");
        osNameList.add("تسوق");
        osNameList.add("موضة و ملابس");
        osNameList.add("مستشفيات");
        osNameList.add("دكتور خاص");
        osNameList.add("صيدليات");
        osNameList.add("معامل");
        osNameList.add("تعليمي");
        osNameList.add("جيم و رياضة");
        osNameList.add("اجهزة الكترونية") ;
        osNameList.add("عقارات");
        osNameList.add("مستلزمات اطفال") ;
        osNameList.add("سيارات");
        osNameList.add("موبيلات") ;
        osNameList.add("حيوانات اليفة") ;
        osNameList.add("صيانة منزلية") ;
        osNameList.add("اثاث منزلي") ;
        osNameList.add("كافيهات");
        osNameList.add("ماكينات الصرافة");
        osNameList.add("ترفيهي");
        osNameList.add("شركات الاتصالات") ;
        osNameList.add("خدمات حكومية");
        osNameList.add("مكاتب محاماه");

        osNameList.add("انضم الينا");
        osNameList.add("اعلن معنا");


        osColors.add(R.color.two);
        osColors.add(R.color.one);
        osColors.add(R.color.four);
        osColors.add(R.color.four);
        osColors.add(R.color.five);
        osColors.add(R.color.six);
        osColors.add(R.color.seven) ;
        osColors.add(R.color.colorPrimary);
        osColors.add(R.color.black);
        osColors.add(R.color.eleven) ;
        osColors.add(R.color.fourten);
        osColors.add(R.color.four) ;
        osColors.add(R.color.twelve);
        osColors.add(R.color.eighten);
        osColors.add(R.color.ninten);
        osColors.add(R.color.sixten) ;
        osColors.add(R.color.four) ;
        osColors.add(R.color.three);
        osColors.add(R.color.thirteen);
        osColors.add(R.color.twentythree);
        osColors.add(R.color.nine) ;
        osColors.add(R.color.twentythree);
        osColors.add(R.color.twenty);


        osColors.add(R.color.twentythree) ;
        osColors.add(R.color.twentyfour);


        osImages.add(R.drawable.restaurant);
        osImages.add(R.drawable.shopping);
        osImages.add(R.drawable.manwomen);
        osImages.add(R.drawable.hosbital_mn);
        osImages.add(R.drawable.medical);
        osImages.add(R.drawable.sydlyatt);
        osImages.add(R.drawable.maamel_mn) ;
        osImages.add(R.drawable.education_mn);
        osImages.add(R.drawable.gym_mn);
        osImages.add(R.drawable.elec_mnn) ;
        osImages.add(R.drawable.akarat_mn);
        osImages.add(R.drawable.carrycot) ;
        osImages.add(R.drawable.car);
        osImages.add(R.drawable.mobiles)  ;
        osImages.add(R.drawable.dog_mnn)  ;
        osImages.add(R.drawable.syana_mn) ;
        osImages.add(R.drawable.furniture_mnn)  ;
        osImages.add(R.drawable.coffe);
        osImages.add(R.drawable.atm_mnn);
        osImages.add(R.drawable.gamepad); //
        osImages.add(R.drawable.etisalat_mn)  ;
        osImages.add(R.drawable.hkomy_mn);
        osImages.add(R.drawable.laww)  ;


        osImages.add(R.drawable.andmelina_mn);
        osImages.add(R.drawable.aalnmaana_mn);

        customAdabter =  new CustomAdabter(this, osNameList, osImages , osColors) ;
        gridview.setAdapter(customAdabter);
    }

    private void  getItemsBuy() {
        osNameList = new ArrayList<>() ;
        osColors = new ArrayList<>() ;
        osImages = new ArrayList<>() ;

        osNameList.add("عقارات");
        osNameList.add("اضف إعلان");
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
        osNameList.add("ملابس اونلاين");// delete
         // delete




        osImages.add(R.drawable.akarat_b);
        osImages.add(R.drawable.adf);
        osImages.add(R.drawable.car_b);
        osImages.add(R.drawable.mobile_b);
        osImages.add(R.drawable.jops_b);
        osImages.add(R.drawable.asas);
        osImages.add(R.drawable.electri_b);
        osImages.add(R.drawable.syana_b);
        osImages.add(R.drawable.aghza_gyn);
        osImages.add(R.drawable.moda_b); //childertn
        osImages.add(R.drawable.dog_b);
        osImages.add(R.drawable.class_b); // dragat
        osImages.add(R.drawable.other_b);  // music
        osImages.add(R.drawable.online_b);



        osColors.add(R.color.one);
        osColors.add(R.color.thirteen);
        osColors.add(R.color.two);
        osColors.add(R.color.three);
        osColors.add(R.color.four);
        osColors.add(R.color.five);
        osColors.add(R.color.six);
        osColors.add(R.color.seven);
        osColors.add(R.color.eight);
        osColors.add(R.color.nine);
        osColors.add(R.color.ten);
        osColors.add(R.color.eleven);
        osColors.add(R.color.four);
        osColors.add(R.color.eight);


        customAdabter_buy =  new CustomAdabterBuy(this, osNameList, osImages , osColors) ;
        gridview.setAdapter(customAdabter_buy);

    }

    private void share () {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "حدايق الاهرام");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayDialog()
    {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Give Your Feedback")
                .setIcon(R.drawable.ic_send_white_24dp)
                .setView(R.layout.dialogefeedbacklayout)
                .create();
        d.show();


        saveBtn= (Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEditText= (EditText) d.findViewById(R.id.nameEditText);
                Toast.makeText(MainActivity.this, "شكرا لك", Toast.LENGTH_SHORT).show();
                d.hide();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    private void init(String s) {







        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new ViewPagerAbaterDownload(MainActivity.this, imagesView , s , kind ));
        mPager.setPageTransformer(true, new RotateUpTransformer());


        //CirclePageIndicator indicator = (CirclePageIndicator)
                //findViewById(R.id.indicator);

       // indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
       // indicator.setRadius(5 * density);

        NUM_PAGES =imagesView.size();
        //Toast.makeText(this, String.valueOf(NUM_PAGES), Toast.LENGTH_SHORT).show();

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

        setTimer(mPager , 4);



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

    }

    private void getData(String data_op) {

        imagesView = new ArrayList<>() ;
        kind = new ArrayList<>() ;


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("sponser").child("القائمة الرئيسية")
                .child("elements");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {

                        imagesView.add("gif");
                        //imagesView.add("logo");
                        if (dataSnapshot.hasChild("profile_img")) {
                            imagesView.add(dataSnapshot.child("profile_img").getValue().toString()); //

                        }
                        else {
                            imagesView.add("waiting") ;
                        }


                        kind.add("gif");
                        //kind.add("adv");
                        kind.add("pic");

                    }

                }
                init(data_op);
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
    protected void onResume() {
        super.onResume();
        swipeTimer = new Timer();
    }

    public void setTimer(final ViewPager myPager, int time){



        final Runnable Update = new Runnable() {

            int currentPage = 0 ;
            public void run() {
                if (currentPage == NUM_PAGES ) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 250, time*1000);
    }

    public void stopTimer(){
        //handler.removeCallbacks(null);
        swipeTimer.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();

        System.exit(0);
    }

    private void language_select () {
        StringLocal stringLocal = new StringLocal(MainActivity.this) ;

    }

}



