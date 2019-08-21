package com.kobtan.fahmy.hadayekelahram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminBuysActivity extends AppCompatActivity {


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
    private ArrayList<String> itemSub = new ArrayList<>();
    private ArrayList<String> itemactive = new ArrayList<>();

    private ArrayList<String> itemkey = new ArrayList<>();
    private ArrayList<String> itemkeyMember = new ArrayList<>();

    private Typeface typeface ;

    private CustomListAdapterBuyThree adapter ;
    private DatabaseReference mCustomerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_adv);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");
        backky();



        list = (ListView) findViewById(R.id.list);



                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("buy");


                getUserInfo();


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {

                        String selecteditem = itemkey.get(position);

                        Toast.makeText(AdminBuysActivity.this, "Here + " + position , Toast.LENGTH_SHORT).show();
                    }
                });




            }




    private void getUserInfo(){
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshots, String s) {
                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {

                    for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {

                            itemname.add(childSnapShot.getValue().toString());
                            itemSub.add(dataSnapshots.getKey().toString());
                            itemtel.add(dataSnapshot.child("telephone").getValue().toString());
                            itemprice.add(dataSnapshot.child("price").getValue().toString());
                            itemdate.add(dataSnapshot.child("date").getValue().toString());

                            itemviews.add(dataSnapshot.child("views").getValue().toString());
                            itemcalls.add(dataSnapshot.child("calls").getValue().toString());
                            itemmsgs.add(dataSnapshot.child("msgs").getValue().toString());
                            itemkind.add(dataSnapshot.child("kind").getValue().toString());
                            itemactive.add(dataSnapshot.child("active").getValue().toString());


                            if (dataSnapshot.hasChild("img_0")) {
                                itemimg.add(dataSnapshot.child("img_0").getValue().toString());
                            } else {
                                itemimg.add("waiting");
                            }
                            itemkey.add(dataSnapshot.getKey().toString());

                        //Log.i("Ameeer" , childSnapShot.toString() ) ;
                    }
                }
            }
                adapter = new CustomListAdapterBuyThree(AdminBuysActivity.this, itemname , itemprice, itemimg , itemtel , itemdate,itemcalls , itemmsgs , itemviews , itemactive );
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
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
    }

    private void removeOpj (String s , int pos) {

        mCustomerDatabase.child(itemSub.get(pos)).child(s).setValue(null);

        itemname.remove(pos) ;

        startActivity(new Intent(getApplicationContext() , AdminBuysActivity.class));

    }
    private void activeOpj (String s , int pos) {

        Toast.makeText(this, itemSub.get(pos), Toast.LENGTH_SHORT).show();

        DatabaseReference activeDatabase  = FirebaseDatabase.getInstance().getReference().child("buy").child(itemSub.get(pos)).child(s);

        HashMap hashMap = new HashMap() ;
        if (itemactive.get(pos).equals("no")) {
            hashMap.put("active" , "yes") ;
        }else {
            hashMap.put("active" , "no") ;
        }

        activeDatabase.updateChildren(hashMap);

        startActivity(new Intent(getApplicationContext() , AdminBuysActivity.class));

    }

    public void options_o (int position) {
        ArrayList<String> items = new ArrayList<String>();

        items.add("مسح الاعلان") ;
        items.add("تفعيل او عدم تفعيل الاعلان") ;

        MaterialDialog matrial = new MaterialDialog.Builder(AdminBuysActivity.this)
                .title("الخيارات")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text != null) {

                            if (text == "مسح الاعلان" ) {
                                //removeOpj(itemkey.get(position) , position);
                            }
                            if (text == "تفعيل او عدم تفعيل الاعلان" ) {
                                activeOpj(itemkey.get(position) , position);
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
