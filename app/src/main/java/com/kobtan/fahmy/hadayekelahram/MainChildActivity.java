package com.kobtan.fahmy.hadayekelahram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainChildActivity extends AppCompatActivity {


    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<Float> itemrate = new ArrayList<>() ;
    private ArrayList<String> itemaddress = new ArrayList<>();
    private ArrayList<String> itemimg = new ArrayList<>();
    private ArrayList<String> itemkey = new ArrayList<>();
    private ArrayList<String> itemtel = new ArrayList<>();
    private ArrayList<String> kind = new ArrayList<>();
    private Toolbar toolbar ;

    private TextView title ;
    private Typeface typeface ;
    private String newString , newStringtwo;
    public int newStringThree ;

    private CustomListAdapterTwo adapter ;
    private CustomListAdapterDoc adapter_doc ;
    private DatabaseReference mCustomerDatabase;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<Integer> imagesView  = new ArrayList<>();
    private TextView text_kind ;

    final Handler handler = new Handler();
    public Timer swipeTimer ;
    private FirebaseAuth mAuth;

    private Button saveBtn ;
    private EditText nameEditText ;
    private  ArrayList<Double> maplong = new ArrayList<>();
    private  ArrayList<Double> map_ard = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainChildActivity.this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main_child);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");
        backky();
        text_kind = (TextView) findViewById(R.id.three) ;
        text_kind.setTypeface(typeface);
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

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newStringThree= 0;
            } else {
                newStringThree= extras.getInt("STRING_I_NEED_TWO");
            }
        } else {
            newStringThree= (int) savedInstanceState.getSerializable("STRING_I_NEED_TWO");
        }




        if (newStringtwo != null) {
            if (newString != null) {
                if (newStringtwo.equals("اثاث منزلي")) {
                    if (newString.equals("نجف و تحف"))
                    {
                        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newStringtwo).child("elements").child("نجف و تحف");

                        getUserInfo();

                        title.setText(newString);


                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    final int position, long id) {

                                String selecteditem = itemkey.get(position);


                                Intent i = new Intent(getApplicationContext(), ObjectPreviewActivity.class);
                                i.putExtra("STRING_I_NEED", newStringtwo);
                                i.putExtra("STRING_I_NEED2", newString);
                                i.putExtra("STRING_I_NEED3", itemkey.get(position));
                                i.putExtra("STRING_I_NEED4", newStringThree);
                                startActivity(i);


                            }
                        });
                    }
                    else {
                        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newStringtwo).child("elements").child("اثاث");

                        getUserInfo();

                        title.setText(newString);


                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    final int position, long id) {

                                String selecteditem = itemkey.get(position);


                                Intent i = new Intent(getApplicationContext(), ObjectPreviewActivity.class);
                                i.putExtra("STRING_I_NEED", newStringtwo);
                                i.putExtra("STRING_I_NEED2", newString);
                                i.putExtra("STRING_I_NEED3", itemkey.get(position));
                                i.putExtra("STRING_I_NEED4", newStringThree);
                                startActivity(i);


                            }
                        });
                    }
                }
                else if (newStringtwo.equals("دكتور خاص"))
                {
                    mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newStringtwo).child("elements").child(newString);

                    getUserInfoDoc();

                    title.setText(newString);

                    list = (ListView) findViewById(R.id.list);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int position, long id) {

                            String selecteditem = itemkey.get(position);

                            Toast.makeText(MainChildActivity.this, "Here", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getApplicationContext(), ObjectPreviewActivity.class);
                            i.putExtra("STRING_I_NEED", newStringtwo);
                            i.putExtra("STRING_I_NEED2", newString);
                            i.putExtra("STRING_I_NEED3", itemkey.get(position));
                            i.putExtra("STRING_I_NEED4", newStringThree);
                            startActivity(i);


                        }
                    });
                }
                else {
                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newStringtwo).child("elements").child(newString);

                getUserInfo();

                title.setText(newString);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {

                        String selecteditem = itemkey.get(position);


                        Intent i = new Intent(getApplicationContext(), ObjectPreviewActivity.class);
                        i.putExtra("STRING_I_NEED", newStringtwo);
                        i.putExtra("STRING_I_NEED2", newString);
                        i.putExtra("STRING_I_NEED3", itemkey.get(position));
                        i.putExtra("STRING_I_NEED4", newStringThree);
                        startActivity(i);

                       //getMaps(itemkey.get(position)) ;


                    }
                });
            }
            }
            else{
                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all").child(newStringtwo).child("elements");
                getUserInfoTwo();

                title.setText(newStringtwo);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {

                        String selecteditem = itemkey.get(position);


                        Intent i = new Intent(getApplicationContext() , ObjectPreviewActivity.class);
                        i.putExtra("STRING_I_NEED", newStringtwo ) ;
                        i.putExtra("STRING_I_NEED3", itemkey.get(position) ) ;
                        i.putExtra("STRING_I_NEED4", newStringThree);
                        startActivity(i);



                    }
                });
            }
        }
        else {

        }





        init("all");


        text_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> items = new ArrayList<String>();

                items.add("البوابة الاولي");
                items.add("البوابة الثانية");
                items.add("البوابة الثالثة");
                items.add("البوابة الرابعة");
                items.add("بوابة احمس");
                items.add("بوابة حورس");
                items.add("جميع البوابات");



                final String[] list = getStringArray(items);

                AlertDialog sMatrial = new AlertDialog.Builder(MainChildActivity.this)
                        .setTitle("اختر البوابة")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                text_kind.setText(list[which]);
                                changeUserInfo(which+1);

                            }
                        })
                        .show();


            }
        });
    }

    private  void getMaps(String s){
        mCustomerDatabase.child(s).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("map_long") && dataSnapshot.hasChild("map_ard")) {
                    Toast.makeText(MainChildActivity.this, "This Have", Toast.LENGTH_SHORT).show();
                }
                else {
                    displayDialog(s);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayDialog(String s)
    {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Put Maps")
                .setIcon(R.drawable.ic_send_white_24dp)
                .setView(R.layout.dialogefeedbacklayout)
                .create();
        d.show();


        saveBtn= (Button) d.findViewById(R.id.saveBtn);
        nameEditText= (EditText) d.findViewById(R.id.nameEditText);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEditText.getText().toString().equals("")){
                    Toast.makeText(MainChildActivity.this, "لم تقم بادخال الارقام", Toast.LENGTH_SHORT).show();
                }else {
                    putMap(s , nameEditText.getText().toString());
                    d.hide();
                }

            }
        });

    }

    private void putMap(String s , String name){


            String firstWord = "";
            String secondWord = "";


            firstWord = name.substring(0, name.indexOf(","));

            secondWord = name.substring(name.indexOf(",")+2 , name.length() );


        Toast.makeText(this, firstWord + " - " + secondWord, Toast.LENGTH_LONG).show();

        if (firstWord == "" || secondWord == "") {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }else {
            HashMap map = new HashMap();
            map.put("map_long", firstWord);
            map.put("map_ard", secondWord );
            mCustomerDatabase.child(s).updateChildren(map) ;
        }




    }

    private void getUserInfo(){
        maplong = new ArrayList<>();
        map_ard = new ArrayList<>();
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {
                        String gateey = " ";
                        if (dataSnapshot.child("gate").getValue().toString().equals("1")){
                            gateey = "البوابة 1" ;
                        }else if (dataSnapshot.child("gate").getValue().toString().equals("2")){
                            gateey = "البوابة 2" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("3")){
                            gateey = "البوابة 3" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("4")){
                            gateey = "البوابة 4" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("5")){
                            gateey = "بوابة احمس" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("6")){
                            gateey = "بوابة حورس" ;
                        }

                            itemname.add(childSnapShot.getValue().toString());
                            itemimg.add(dataSnapshot.child("profile_img").getValue().toString());
                            itemrate.add(Float.valueOf(dataSnapshot.child("rating").getValue().toString()));
                            itemaddress.add(dataSnapshot.child("address").getValue().toString() + " - " + gateey);
                            itemtel.add(dataSnapshot.child("telephone").getValue().toString());
                            itemkey.add(dataSnapshot.getKey().toString()) ;
                            if (dataSnapshot.hasChild("map_long") && dataSnapshot.hasChild("map_ard") ) {
                                maplong.add(Double.valueOf(dataSnapshot.child("map_long").getValue().toString()));
                                map_ard.add(Double.valueOf(dataSnapshot.child("map_ard").getValue().toString()));
                            }
                            else {
                                maplong.add(Double.valueOf(0));
                                map_ard.add(Double.valueOf(0));
                            }
                            /*if (childSnapShot.getValue().toString().equals("ابو موسى")){
                                Toast.makeText(MainChildActivity.this, maplong + " - " + map_ard , Toast.LENGTH_LONG).show();
                            }*/

                    }
                    //Log.i("Ameeer" , childSnapShot.toString() ) ;

                }

                    adapter = new CustomListAdapterTwo(MainChildActivity.this, itemname , itemaddress, itemrate , itemimg , itemtel ,newStringThree , maplong , map_ard);
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
    private void getUserInfoTwo(){
        maplong = new ArrayList<>();
        map_ard = new ArrayList<>();
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {

                        String gateey = " ";
                        if (dataSnapshot.child("gate").getValue().toString().equals("1")){
                            gateey = "البوابة 1" ;
                        }else if (dataSnapshot.child("gate").getValue().toString().equals("2")){
                            gateey = "البوابة 2" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("3")){
                            gateey = "البوابة 3" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("4")){
                            gateey = "البوابة 4" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("5")){
                            gateey = "بوابة احمس" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("6")){
                            gateey = "بوابة حورس" ;
                        }

                        itemname.add(childSnapShot.getValue().toString());
                        itemimg.add(dataSnapshot.child("profile_img").getValue().toString());
                        itemrate.add(Float.valueOf(dataSnapshot.child("rating").getValue().toString()));
                        itemaddress.add(dataSnapshot.child("address").getValue().toString() + " - " + gateey);
                        itemtel.add(dataSnapshot.child("telephone").getValue().toString());
                        itemkey.add(dataSnapshot.getKey().toString()) ;

                        if (dataSnapshot.hasChild("map_long") && dataSnapshot.hasChild("map_ard") ) {
                            maplong.add(Double.valueOf(dataSnapshot.child("map_long").getValue().toString()));
                            map_ard.add(Double.valueOf(dataSnapshot.child("map_ard").getValue().toString()));
                        }
                        else {
                            maplong.add(Double.valueOf(0));
                            map_ard.add(Double.valueOf(0));
                        }
                    }
                    //Log.i("Ameeer" , childSnapShot.toString() ) ;

                }
                adapter = new CustomListAdapterTwo(MainChildActivity.this, itemname , itemaddress, itemrate , itemimg , itemtel ,newStringThree , maplong , map_ard);
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
    private void changeUserInfo(final int text){
        itemname = new ArrayList<String>();
        itemaddress = new ArrayList<String>();
        itemimg = new ArrayList<String>();
        itemtel = new ArrayList<String>();
        itemkey = new ArrayList<String>();
        itemrate = new ArrayList<Float>();
        maplong = new ArrayList<>();
        map_ard = new ArrayList<>();
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {
                        if (String.valueOf(text).equals("7") ) {
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
                        else if (dataSnapshot.child("gate").getValue().toString().equals(String.valueOf(text))) {

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
                if (newStringtwo.equals("دكتور خاص")) {

                    adapter_doc = new CustomListAdapterDoc(MainChildActivity.this, itemname , itemaddress, itemrate , itemimg , itemtel ,newStringThree);
                    list.setAdapter(adapter_doc);

                }else {
                    adapter = new CustomListAdapterTwo(MainChildActivity.this, itemname , itemaddress, itemrate , itemimg , itemtel ,newStringThree , maplong ,map_ard);
                    list.setAdapter(adapter);
                }
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
        if (newString != null){
                    Intent i = new Intent(getApplicationContext() , MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", newStringtwo);
                    i.putExtra("STRING_I_NEED_TWO", newStringThree);
                    startActivity(i);
            finish();

        }
        else
        {

                    Intent i = new Intent(getApplicationContext() , MainActivity.class);
                    startActivity(i);
            finish();

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


    private void getUserInfoDoc(){
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {
                        String gateey = " ";
                        if (dataSnapshot.child("gate").getValue().toString().equals("1")){
                            gateey = "البوابة 1" ;
                        }else if (dataSnapshot.child("gate").getValue().toString().equals("2")){
                            gateey = "البوابة 2" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("3")){
                            gateey = "البوابة 3" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("4")){
                            gateey = "البوابة 4" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("5")){
                            gateey = "بوابة احمس" ;
                        }
                        else if (dataSnapshot.child("gate").getValue().toString().equals("6")){
                            gateey = "بوابة حورس" ;
                        }

                        itemname.add(childSnapShot.getValue().toString());
                        itemimg.add(dataSnapshot.child("profile_img").getValue().toString());
                        itemrate.add(Float.valueOf(dataSnapshot.child("rating").getValue().toString()));
                        itemaddress.add(dataSnapshot.child("address").getValue().toString() + " - " + gateey);
                        itemtel.add(dataSnapshot.child("telephone").getValue().toString());
                        itemkey.add(dataSnapshot.getKey().toString()) ;

                    }
                    //Log.i("Ameeer" , childSnapShot.toString() ) ;
                }

                adapter_doc = new CustomListAdapterDoc(MainChildActivity.this, itemname , itemaddress, itemrate , itemimg , itemtel ,newStringThree);
                list.setAdapter(adapter_doc);
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



    private void init(String s) {


        imagesView = new ArrayList<>() ;
        kind = new ArrayList<>() ;

        if (newString != null) {
            if (newString.equals("مراكز صيانة")) {
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.one_el); //

                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.one_el); //




                kind.add("gif");
                kind.add("adv");
                kind.add("pic");

                kind.add("gif");
                kind.add("adv");
                kind.add("pic");
            }
            else if (newString.equals("نجف و تحف")) {
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.nadystores); //

                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.nadystores); //


                kind.add("gif");
                kind.add("adv");
                kind.add("pic");

                kind.add("gif");
                kind.add("adv");
                kind.add("pic");
            }
            else if (newString.equals("عيادات و مراكز طبية")) {
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.nadyy); //

                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.nadyy); //


                kind.add("gif");
                kind.add("adv");
                kind.add("pic");

                kind.add("gif");
                kind.add("adv");
                kind.add("pic");
            }
            else {
                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.logo);

                imagesView.add(R.drawable.logo);
                imagesView.add(R.drawable.logo);




                kind.add("gif");
                kind.add("adv");

                kind.add("gif");
                kind.add("adv");
            }
        }
        else {
            imagesView.add(R.drawable.logo);
            imagesView.add(R.drawable.logo);

            imagesView.add(R.drawable.logo);
            imagesView.add(R.drawable.logo);




            kind.add("gif");
            kind.add("adv");

            kind.add("gif");
            kind.add("adv");
        }








        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new ViewPagerAbater(MainChildActivity.this, imagesView , s , kind));
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

}
