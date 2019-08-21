package com.kobtan.fahmy.hadayekelahram;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BuySubActivity extends AppCompatActivity {

    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<String> itemprice = new ArrayList<>() ;
    private ArrayList<String> itemtel = new ArrayList<>();
    private ArrayList<String> itemdate = new ArrayList<>();
    private ArrayList<String> itemimg = new ArrayList<>();
    private ArrayList<String> itemgate = new ArrayList<>();

    private ArrayList<String> kind = new ArrayList<>();

    private ArrayList<String> itemkey = new ArrayList<>();
    private ArrayList<String> itemkeyMember = new ArrayList<>();

    private TextView title ;
    private Typeface typeface ;
    private String newString , newStringtwo;

    private CustomListAdapterBuy adapter ;
    private DatabaseReference mCustomerDatabase;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<Integer> imagesView  = new ArrayList<>();

    final Handler handler = new Handler();
    public Timer swipeTimer ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sub);
        mAuth = FirebaseAuth.getInstance();
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");
        backky();

        title = (TextView) findViewById(R.id.titlee) ;
        title.setTypeface(typeface);
        list = (ListView) findViewById(R.id.list);

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




        if (newStringtwo != null) {

                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("buy").child(newStringtwo);


                getUserInfo();


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {

                        String selecteditem = itemkey.get(position);


                        Intent i = new Intent(getApplicationContext() , AdvObjectPreviewActivity.class);
                        i.putExtra("STRING_I_NEED", newStringtwo ) ;
                        i.putExtra("STRING_I_NEED3", itemkey.get(position) ) ;
                        i.putExtra("STRING_I_NEED2", itemkeyMember.get(position) ) ;
                        startActivity(i);


                    }
                });

            }


        title.setText(newStringtwo);

        init("buy");


    }

    private void getUserInfo(){
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {
                        if (dataSnapshot.child("active").getValue().toString().equals("no")) {

                        } else {


                            itemname.add(childSnapShot.getValue().toString());
                            itemtel.add(dataSnapshot.child("telephone").getValue().toString());
                            itemprice.add(dataSnapshot.child("price").getValue().toString());
                            itemdate.add(dataSnapshot.child("date").getValue().toString());
                            itemgate.add(dataSnapshot.child("gate").getValue().toString());
                            itemkeyMember.add(dataSnapshot.child("memberID").getValue().toString());


                            if (dataSnapshot.hasChild("img_0")) {
                                itemimg.add(dataSnapshot.child("img_0").getValue().toString());
                            } else {
                                itemimg.add("waiting");
                            }
                            itemkey.add(dataSnapshot.getKey().toString());

                        }
                        //Log.i("Ameeer" , childSnapShot.toString() ) ;
                    }
                }
                adapter = new CustomListAdapterBuy(BuySubActivity.this, itemname , itemprice, itemimg , itemtel , itemdate, itemgate, itemkey);
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
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("STRING_I_NEED3", "buy");
        startActivity(i);
        finish();
    }

    private void init(String s) {


        imagesView = new ArrayList<>();
        kind = new ArrayList<>();

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);


        kind.add("pic");
        kind.add("pic");
        kind.add("pic");
        kind.add("pic");


        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new ViewPagerAbater(BuySubActivity.this, imagesView , s , kind));
        mPager.setPageTransformer(true, new RotateUpTransformer());


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

        setTimer(mPager , 5);


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
    public void setTimer(final ViewPager myPager, int time){
        final int size = 4 ;


        final Runnable Update = new Runnable() {
            int NUM_PAGES =size;
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
    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("STRING_I_NEED3", "buy");
                startActivity(i);
                finish();
            }
        });

    }


}
