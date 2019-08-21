package com.kobtan.fahmy.hadayekelahram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
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

public class EditMyAdvActivity extends AppCompatActivity {


    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<String> itemprice = new ArrayList<>() ;
    private ArrayList<String> itemtel = new ArrayList<>();
    private ArrayList<String> itemdate = new ArrayList<>();
    private ArrayList<String> itemimg = new ArrayList<>();
    private ArrayList<String> itemcalls = new ArrayList<>();
    private ArrayList<String> itemmsgs = new ArrayList<>();
    private ArrayList<String> itemviews = new ArrayList<>();
    private ArrayList<String> itemkind = new ArrayList<>();

    private ArrayList<String> itemkey = new ArrayList<>();
    private ArrayList<String> itemkeyMember = new ArrayList<>();

    private Typeface typeface ;

    private CustomListAdapterBuyTwo adapter ;
    private DatabaseReference mCustomerDatabase;

    private FirebaseAuth mAuth;
    private StringCurrentUserPref currentUserPref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_adv);
        mAuth = FirebaseAuth.getInstance();
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");
        backky();

        currentUserPref = new StringCurrentUserPref(this);


        list = (ListView) findViewById(R.id.list);



        if (currentUserPref.getUser() != null) {

            if (FirebaseDatabase.getInstance().getReference().child("members").child("adv") == null) {
                Toast.makeText(this, "لا يوجد اعلانات حاليا", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("members").child(currentUserPref.getUser().toString()).child("adv");


            getUserInfo();


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {

                    String selecteditem = itemkey.get(position);

                    Toast.makeText(EditMyAdvActivity.this, "Here + " + position , Toast.LENGTH_SHORT).show();
                }
            });




        }
        }
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

                            itemviews.add(dataSnapshot.child("views").getValue().toString());
                            itemcalls.add(dataSnapshot.child("calls").getValue().toString());
                            itemmsgs.add(dataSnapshot.child("msgs").getValue().toString());
                            itemkind.add(dataSnapshot.child("kind").getValue().toString());


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
                adapter = new CustomListAdapterBuyTwo(EditMyAdvActivity.this, itemname , itemprice, itemimg , itemtel , itemdate,itemcalls , itemmsgs , itemviews );
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

    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , MemberPanelAdvActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MemberPanelAdvActivity.class));
    }

    private void removeOpj (String s , int pos) {
        DatabaseReference mBuy = FirebaseDatabase.getInstance().getReference().child("buy").child(itemkind.get(pos)).child(s);
        mCustomerDatabase.child(s).setValue(null);

        mBuy.setValue(null) ;
        itemname.remove(pos) ;

        startActivity(new Intent(getApplicationContext() , EditMyAdvActivity.class));



    }

    public void options_o (int position) {
        ArrayList<String> items = new ArrayList<String>();

        items.add("مسح الاعلان") ;
        items.add("تمييز الاعلان") ;
        items.add("تعديل الإعلان") ;




        MaterialDialog matrial = new MaterialDialog.Builder(EditMyAdvActivity.this)
                .title("الخيارات")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text != null) {

                            if (text == "مسح الاعلان" ) {
                                removeOpj(itemkey.get(position) , position);
                            }
                            if (text == "تمييز الاعلان" ) {
                                startActivity(new Intent(getApplicationContext() , AdvWithUsActivity.class));
                            }
                            if (text == "تعديل الإعلان" ) {
                                Intent i  = new Intent(getApplicationContext() , EditDetailsAdvActivity.class);
                                i.putExtra("STRING_I_NEED" , itemkey.get(position)) ;
                                i.putExtra("STRING_I_NEED2" , itemkind.get(position)) ;
                                startActivity(i);
                            }

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
