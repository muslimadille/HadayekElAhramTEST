package com.kobtan.fahmy.hadayekelahram.News;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kobtan.fahmy.hadayekelahram.CustomListAdapterDoc;
import com.kobtan.fahmy.hadayekelahram.CustomListAdapterNews;
import com.kobtan.fahmy.hadayekelahram.CustomListAdapterTwo;
import com.kobtan.fahmy.hadayekelahram.MainActivity;
import com.kobtan.fahmy.hadayekelahram.MainChildActivity;
import com.kobtan.fahmy.hadayekelahram.MainSubActivity;
import com.kobtan.fahmy.hadayekelahram.ObjectPreviewActivity;
import com.kobtan.fahmy.hadayekelahram.R;
import com.kobtan.fahmy.hadayekelahram.ViewPagerAbaterDownload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class NewsActivity extends AppCompatActivity {


    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<String> itemTelephone = new ArrayList<>() ;
    private ArrayList<String> itemDetails = new ArrayList<>() ;
    private ArrayList<String> itemPostImages = new ArrayList<>() ;
    private ArrayList<String> itemLogoImages = new ArrayList<>() ;
    private ArrayList<String> itemaddress = new ArrayList<>() ;
    private ArrayList<String> itemdate = new ArrayList<>() ;
    private ArrayList<String> itemkey = new ArrayList<>() ;


    private Toolbar toolbar ;

    private TextView title ;
    private Typeface typeface ;
    public int newStringThree ;

    private CustomListAdapterNews adapter ;
    private DatabaseReference mCustomerDatabase;
    private TextView text_kind ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        FirebaseApp.initializeApp(NewsActivity.this);
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");
        backky();
        title = (TextView) findViewById(R.id.titlee) ;
        //title.setTypeface(typeface);
        list = (ListView) findViewById(R.id.list);




        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("news");


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view,
                                                final int position, long id) {

                        }
                    });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(NewsActivity.this, itemname.get(i), Toast.LENGTH_SHORT).show();
                //removeObject(i);
                return false;
            }
        });


        getUserInfo();


    }

    private void getUserInfo(){
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {

                        itemname.add(childSnapShot.getValue().toString());
                        itemTelephone.add(dataSnapshot.child("telephone").getValue().toString());
                        itemDetails.add(dataSnapshot.child("details").getValue().toString());
                        if (dataSnapshot.hasChild("post_img")) {
                            itemPostImages.add(dataSnapshot.child("post_img").getValue().toString());
                        }
                        else{
                            itemPostImages.add("waiting");
                        }
                        if (dataSnapshot.hasChild("address")) {
                            itemaddress.add(dataSnapshot.child("address").getValue().toString());
                        }
                        else{
                            itemaddress.add("address");
                        }
                        if (dataSnapshot.hasChild("date")) {
                            itemdate.add(dataSnapshot.child("date").getValue().toString());
                        }
                        else{
                            itemdate.add("1-2-2020");
                        }

                        itemLogoImages.add(dataSnapshot.child("profile_img").getValue().toString());


                        itemkey.add(dataSnapshot.getKey().toString()) ;

                    }

                }

                Collections.reverse(itemname);
                Collections.reverse(itemTelephone);
                Collections.reverse(itemDetails);
                Collections.reverse(itemPostImages);
                Collections.reverse(itemLogoImages);
                Collections.reverse(itemaddress);
                Collections.reverse(itemdate);
                Collections.reverse(itemkey);


                adapter = new CustomListAdapterNews(NewsActivity.this, itemname , itemTelephone, itemDetails , itemPostImages , itemLogoImages , itemaddress , itemdate , itemkey );
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
            Intent i = new Intent(getApplicationContext() , MainActivity.class);
            startActivity(i);
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

    public void removeObject (int pos) {

        final String[] list = new String[]{"مسح الاعلان", "الغاء"};
        new AlertDialog.Builder(NewsActivity.this)
                .setTitle("مسح")
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NewsActivity.this, list[which], Toast.LENGTH_SHORT).show();
                        if (list[which].equals("مسح الاعلان")) {
                            mCustomerDatabase.child(itemkey.get(pos)).setValue(null);
                            Intent i = new Intent(getApplicationContext(), NewsActivity.class);
                            startActivity(i);
                        }else if (list[which].equals("الغاء")) {

                        }
                        else {

                        }
                    }
                })
                .show();



    }

}
