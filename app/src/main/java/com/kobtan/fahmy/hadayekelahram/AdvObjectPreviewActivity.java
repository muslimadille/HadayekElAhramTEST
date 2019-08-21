package com.kobtan.fahmy.hadayekelahram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class AdvObjectPreviewActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<String> imagesView  = new ArrayList<>();
    private Toolbar toolbar ;
    private DatabaseReference mCustomerDatabase ;

    private TextView name , price , date , telephone , views  , address , details , person_name ;
    private String newString , newString2 , newString3 ;
    private ImageView img_call ;
    private ImageView img_flag  , img_sms , img_share;
    private DatabaseReference current_user_db , current_user_db_views , current_user_db_Two ;
    private int views_start , msgs_start , calls_start;
    private String memberID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_object_preview);
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



        name = (TextView) findViewById(R.id.name) ;
        price = (TextView) findViewById(R.id.price) ;
        date = (TextView) findViewById(R.id.date) ;
        telephone = (TextView) findViewById(R.id.telephone) ;
        views = (TextView) findViewById(R.id.views) ;
        address = (TextView) findViewById(R.id.gate) ;
        details = (TextView) findViewById(R.id.details) ;
        person_name = (TextView) findViewById(R.id.person_name) ;



        img_call = (ImageView) findViewById(R.id.call) ;
        img_flag = (ImageView) findViewById(R.id.flag) ;
        img_sms = (ImageView) findViewById(R.id.sms) ;
        img_share = (ImageView) findViewById(R.id.share) ;

        if (newString != null)
        {
                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("buy").child(newString);

                getUserInfo();
        }
        else
        {
            Toast.makeText(this, "خطأ", Toast.LENGTH_SHORT).show();
        }

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = telephone.getText().toString();
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);

                Toast.makeText(getApplicationContext(), "Calling", Toast.LENGTH_SHORT).show();
                callAdv();

            }
        });

        img_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:"+telephone.getText().toString());
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "The SMS text");
                startActivity(it);

                Toast.makeText(AdvObjectPreviewActivity.this, "Messaging now !", Toast.LENGTH_SHORT).show();
                msgsAdv();
            }
        });
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               share();
            }
        });


        img_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> items = new ArrayList<String>();

                items.add("خطأ فى السعر / الصورة / الفئة") ;
                items.add("إعلان غير لائق") ;
                items.add(" تم بيع السلعة") ;
                items.add(" نصب / إحتيال") ;
                items.add(" بائع غير لائق") ;
                items.add("سبب أخر") ;




                final String[] list = getStringArray(items);

                AlertDialog sMatrial = new AlertDialog.Builder(AdvObjectPreviewActivity.this)
                        .setTitle("الابلاغ عن هذا الاعلان")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                flagAdv(list[which]);
                            }
                        })
                        .show();

                sMatrial.getWindow().setLayout(300, Toolbar.LayoutParams.WRAP_CONTENT);
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
                    }
                    if (dataSnapshot.hasChild("price")) {

                        price.setText("السعر: " + dataSnapshot.child("price").getValue().toString() + " ج.م ");
                    }
                    if (dataSnapshot.hasChild("date")) {

                        date.setText(dataSnapshot.child("date").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("telephone")) {

                        telephone.setText("رقم الهاتف: "+dataSnapshot.child("telephone").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("views")) {

                        views.setText("عدد المشاهدات: "+dataSnapshot.child("views").getValue().toString());
                        views_start = Integer.valueOf(dataSnapshot.child("views").getValue().toString()) ;
                    }
                    if (dataSnapshot.hasChild("gate")) {

                        address.setText("العنوان: "+ dataSnapshot.child("gate").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("details")) {

                        details.setText("تفاصيل الاعلان: "+dataSnapshot.child("details").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("msgs")) {

                        msgs_start = Integer.valueOf(dataSnapshot.child("msgs").getValue().toString()) ;
                    }
                    if (dataSnapshot.hasChild("calls")) {

                        calls_start = Integer.valueOf(dataSnapshot.child("calls").getValue().toString()) ;
                    }
                    if (dataSnapshot.hasChild("memberID")) {

                        memberID = dataSnapshot.child("memberID").getValue().toString() ;
                    }



                    if (dataSnapshot.hasChild("img_0"))
                    {
                        imagesView.add(dataSnapshot.child("img_0").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("img_1"))
                    {
                        imagesView.add(dataSnapshot.child("img_1").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("img_2"))
                    {
                        imagesView.add(dataSnapshot.child("img_2").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("img_3"))
                    {
                        imagesView.add(dataSnapshot.child("img_3").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("img_4"))
                    {
                        imagesView.add(dataSnapshot.child("img_4").getValue().toString());
                    }
                }
                init();
                viewsAdv();
                person();

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


        mPager.setAdapter(new ViewPagerAbaterTwo(AdvObjectPreviewActivity.this, imagesView));


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void flagAdv(String kind) {

        FirebaseDatabase.getInstance().getReference().child("buy").child(newString).child(newString3).child("reports").push() ;
        String key = FirebaseDatabase.getInstance().getReference().child("buy").child(newString).child(newString3).child("reports").push().getKey();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("buy").child(newString).child(newString3).child("reports").child(key);

        HashMap map = new HashMap();
        map.put("kind", kind);
        map.put("date", getdate() );


        current_user_db.updateChildren(map);


        FirebaseDatabase.getInstance().getReference().child("members").child(newString2).child("adv").child(newString3).child("reports").push() ;
        String keyTwo = FirebaseDatabase.getInstance().getReference().child("members").child(newString2).child("adv").child(newString3).child("reports").push().getKey();
        current_user_db_Two = FirebaseDatabase.getInstance().getReference().child("members").child(newString2).child("adv").child(newString3).child("reports").child(key);

        HashMap mapTwo = new HashMap();
        mapTwo.put("kind", kind);
        mapTwo.put("date", getdate() );


        current_user_db_Two.updateChildren(mapTwo);


    }
    private void viewsAdv() {

        String value = String.valueOf(views_start+1) ;
        FirebaseDatabase.getInstance().getReference().child("buy").child(newString).child(newString3).child("views").setValue(value);

        FirebaseDatabase.getInstance().getReference().child("members").child(newString2).child("adv").child(newString3).child("views").setValue(value).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AdvObjectPreviewActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callAdv() {

        String value = String.valueOf(calls_start+1) ;
        FirebaseDatabase.getInstance().getReference().child("buy").child(newString).child(newString3).child("calls").getRef().setValue(value);

        FirebaseDatabase.getInstance().getReference().child("members").child(newString2).child("adv").child(newString3).child("calls").getRef().setValue(value);

    }
    private void msgsAdv() {

        String value = String.valueOf(msgs_start+1) ;
        FirebaseDatabase.getInstance().getReference().child("buy").child(newString).child(newString3).child("msgs").getRef().setValue(value);

        FirebaseDatabase.getInstance().getReference().child("members").child(newString2).child("adv").child(newString3).child("msgs").getRef().setValue(value);

    }

    private void person() {



        FirebaseDatabase.getInstance().getReference().child("members").child(newString2).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                person_name.setText("اسم المعلن: "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String getdate () {
        String date = (DateFormat.format(" التاريخ "+" dd-MM-yyyy", new java.util.Date()).toString());
        return date;
    }

    private void share () {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "حدايق الاهرام");
            String shareMessage= "\nLet me recommend you this application\n\n" + name.getText().toString();
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
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
