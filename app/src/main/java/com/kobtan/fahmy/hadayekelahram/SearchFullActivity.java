package com.kobtan.fahmy.hadayekelahram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;

public class SearchFullActivity extends AppCompatActivity {


    private EditText search_txt ;
    private ImageButton search_btn ;


    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<Float> itemrate = new ArrayList<>() ;
    private ArrayList<String> itemaddress = new ArrayList<>();
    private ArrayList<String> itemimg = new ArrayList<>();
    private ArrayList<String> itemkey = new ArrayList<>();
    private ArrayList<String> itemtel = new ArrayList<>();

    private ArrayList<String> itemsubb = new ArrayList<>();
    private ArrayList<String> itemsubb_subb = new ArrayList<>();
    private Toolbar toolbar ;


    private ProgressDialog pd ;
    private Typeface typeface ;

    private CustomListAdapterTwo adapter ;
    private DatabaseReference mCustomerDatabase;

    private  ArrayList<Double> maplong = new ArrayList<>();
    private  ArrayList<Double> map_ard = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_full);
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");
        list = (ListView) findViewById(R.id.list);
        search_txt = (EditText) findViewById(R.id.search_field) ;
        search_btn = (ImageButton) findViewById(R.id.search_btn) ;

        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("all");


        backky();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SearchFullActivity.this);
                pd.setMessage("جاري البحث");
                pd.setCancelable(false);
                pd.show();
                String s = search_txt.getText().toString() ;
                itemname = new ArrayList<>() ;
                itemrate = new ArrayList<>() ;
                itemaddress = new ArrayList<>();
                itemimg = new ArrayList<>();
                itemkey = new ArrayList<>();
                itemtel = new ArrayList<>();
                itemsubb = new ArrayList<>();
                itemsubb_subb = new ArrayList<>();
                if (!s.equals("")) {
                    if (s.length()>1){
                    if (s.charAt(s.length() - 1) == 'ي' || s.charAt(s.length() - 1) ==  'ى' || s.charAt(s.length() - 1) == 'ة' || s.charAt(s.length() - 1) == 'ه') {
                        //Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
                        s = s.substring(0, s.length() - 1);
                        Toast.makeText(SearchFullActivity.this, String.valueOf(s.length()), Toast.LENGTH_SHORT).show();
                    }
                    }
                }
                getUserInfo(s.toLowerCase());

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

        //        String selecteditem = itemkey.get(position);


                Intent i = new Intent(getApplicationContext() , ObjectPreviewActivity.class);
                i.putExtra("STRING_I_NEED", itemsubb.get(position)) ;
                if (! itemsubb_subb.get(position).equals("no")){
                    i.putExtra("STRING_I_NEED2", itemsubb_subb.get(position) ) ;
                }

                i.putExtra("STRING_I_NEED3", itemkey.get(position) ) ;
                startActivity(i);



            }
        });

    }

    private void getUserInfo(String s){

        maplong = new ArrayList<>();
        map_ard = new ArrayList<>();

        //Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        mCustomerDatabase.orderByChild("name").startAt(s);
        mCustomerDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Toast.makeText(SearchFullActivity.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                            if (messageSnapshot.child("sub").getValue().toString().equals("no")) {
                                for (DataSnapshot childshot : messageSnapshot.child("elements").getChildren()) {
                                    if (childshot.hasChild("name")) {


                                    String name = (String) childshot.child("name").getValue().toString().toLowerCase();
                                    if (s.length() >= 3) {

                                        if (name.startsWith(s) || name.contains(s)) {
                                            itemname.add(childshot.child("name").getValue().toString());
                                            itemimg.add(childshot.child("profile_img").getValue().toString());
                                            itemtel.add(childshot.child("telephone").getValue().toString());
                                            itemaddress.add(childshot.child("address").getValue().toString());
                                            itemrate.add(Float.valueOf(childshot.child("rating").getValue().toString()));

                                            itemkey.add(childshot.getKey().toString());


                                            itemsubb.add(messageSnapshot.getKey().toString());
                                            itemsubb_subb.add("no") ;

                                            if (childshot.hasChild("map_long") && childshot.hasChild("map_ard")) {
                                                maplong.add(Double.valueOf(childshot.child("map_long").getValue().toString()));
                                                map_ard.add(Double.valueOf(childshot.child("map_ard").getValue().toString()));
                                            } else {
                                                maplong.add(Double.valueOf(0));
                                                map_ard.add(Double.valueOf(0));
                                            }
                                        }
                                    } else {

                                        if (name.startsWith(s)) {
                                            itemname.add(childshot.child("name").getValue().toString());
                                            itemimg.add(childshot.child("profile_img").getValue().toString());
                                            itemtel.add(childshot.child("telephone").getValue().toString());
                                            itemaddress.add(childshot.child("address").getValue().toString());
                                            itemrate.add(Float.valueOf(childshot.child("rating").getValue().toString()));
                                            if (childshot.hasChild("map_long") && childshot.hasChild("map_ard")) {
                                                maplong.add(Double.valueOf(childshot.child("map_long").getValue().toString()));
                                                map_ard.add(Double.valueOf(childshot.child("map_ard").getValue().toString()));

                                            } else {
                                                maplong.add(Double.valueOf(0));
                                                map_ard.add(Double.valueOf(0));
                                            }
                                            itemkey.add(childshot.getKey().toString());


                                            itemsubb.add(messageSnapshot.getKey().toString());
                                            itemsubb_subb.add("no") ;
                                        }
                                    }


                                }
                                }
                            }
                            else if(messageSnapshot.child("sub").getValue().toString().equals("yes")) {
                                for (DataSnapshot childshot : messageSnapshot.child("elements").getChildren()) {
                                    for (DataSnapshot childshot_two : childshot.getChildren()) {

                                        if (childshot_two.hasChild("name")) {



                                            String name = (String) childshot_two.child("name").getValue().toString().toLowerCase();
                                            if (s.length() >= 3) {

                                                if (name.startsWith(s) || name.contains(s)) {
                                                    itemname.add(childshot_two.child("name").getValue().toString());
                                                    itemimg.add(childshot_two.child("profile_img").getValue().toString());
                                                    itemtel.add(childshot_two.child("telephone").getValue().toString());
                                                    itemaddress.add(childshot_two.child("address").getValue().toString());
                                                    itemrate.add(Float.valueOf(childshot_two.child("rating").getValue().toString()));

                                                    itemkey.add(childshot_two.getKey().toString()) ;
                                                    itemsubb.add(messageSnapshot.getKey().toString());
                                                    itemsubb_subb.add(childshot.getKey().toString()) ;

                                                    if (childshot.hasChild("map_long") && childshot.hasChild("map_ard") ) {
                                                        maplong.add(Double.valueOf(childshot.child("map_long").getValue().toString()));
                                                        map_ard.add(Double.valueOf(childshot.child("map_ard").getValue().toString()));
                                                    }

                                                    else {
                                                        maplong.add(Double.valueOf(0));
                                                        map_ard.add(Double.valueOf(0));
                                                    }
                                                }
                                            } else {

                                                if (name.startsWith(s)) {
                                                    itemname.add(childshot_two.child("name").getValue().toString());
                                                    itemimg.add(childshot_two.child("profile_img").getValue().toString());
                                                    itemtel.add(childshot_two.child("telephone").getValue().toString());
                                                    itemaddress.add(childshot_two.child("address").getValue().toString());
                                                    itemrate.add(Float.valueOf(childshot_two.child("rating").getValue().toString()));
                                                    if (childshot.hasChild("map_long") && childshot.hasChild("map_ard") ) {
                                                        maplong.add(Double.valueOf(childshot.child("map_long").getValue().toString()));
                                                        map_ard.add(Double.valueOf(childshot.child("map_ard").getValue().toString()));
                                                    }

                                                    else {
                                                        maplong.add(Double.valueOf(0));
                                                        map_ard.add(Double.valueOf(0));
                                                    }
                                                    itemkey.add(childshot_two.getKey().toString()) ;
                                                    itemsubb.add(messageSnapshot.getKey().toString());
                                                    itemsubb_subb.add(childshot.getKey().toString()) ;
                                                }
                                            }
                                        }
                                    }


                                }
                            }
                        }

                        adapter = new CustomListAdapterTwo(SearchFullActivity.this, itemname , itemaddress, itemrate , itemimg , itemtel ,R.color.colorPrimary , maplong , map_ard);
                        list.setAdapter(adapter);
                        pd.cancel();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
    }
    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backky) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }
}
