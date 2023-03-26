package com.kobtan.fahmy.hadayekelahram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class CustomListAdapterBuy extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<String> itemprice;
    private final ArrayList<String> itemimg;
    private final ArrayList<String> itemtel;
    private final ArrayList<String> itemdate;
    private final ArrayList<String> itemgate;
    private Typeface typeface ;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private final ArrayList<String> itemkey;
    private int test ;
    Set<String> colorsSet ;

    public CustomListAdapterBuy(Activity context, ArrayList<String> itemname, ArrayList<String> itemprice , ArrayList<String> itemimg , ArrayList<String> itemtel , ArrayList<String> itemdate , ArrayList<String> itemgate , ArrayList<String> itemkey) {
        super(context, R.layout.itemlisttwo, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemprice=itemprice;
        this.itemdate=itemdate;
        this.itemimg  = itemimg ;
        this.itemtel  = itemtel ;
        this.itemgate  = itemgate ;
        this.itemkey  = itemkey ;



        // Initialize the SharedPreference object
        this.mSharedPreferences = context.getSharedPreferences( "mmkk" , context.MODE_PRIVATE);
        //this.mSharedPreferences = context.getSharedPreferences("colors_list", Activity.MODE_PRIVATE);

        // Get SharedPreferences editor
        this.mEditor = mSharedPreferences.edit();

        typeface = Typeface.createFromAsset(this.context.getAssets() , "font.ttf");

        // Read the values from SharedPreferences
        colorsSet = mSharedPreferences.getStringSet(
                context.getResources().getString(R.string.sp_key_colors),
                null
        );
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.itemlistbuy, null,true);
        test = 0 ;


        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.item2);
        TextView txtTelephone = (TextView) rowView.findViewById(R.id.item3);
        TextView txtDate = (TextView) rowView.findViewById(R.id.item4);
        TextView txtBwaba = (TextView) rowView.findViewById(R.id.item5);
        ImageView img = (ImageView) rowView.findViewById(R.id.icon);
        final ImageView img_fav = (ImageView) rowView.findViewById(R.id.textView17);

        ImageView call_btn = (ImageView) rowView.findViewById(R.id.textView16) ;


        img_fav.setImageResource(R.drawable.stargreen);

        if (colorsSet != null ){

            for(String color : colorsSet){
                if (itemkey.get(position).equals(color)) {
                    img_fav.setImageResource(R.drawable.starblack);
                    test = 1 ;
                    break;
                }
            }
        }



        txtPrice.setTypeface(typeface);
        txtTitle.setTypeface(typeface);


        txtTitle.setText(itemname.get(position));
        txtPrice.setText(itemprice.get(position)+" ج.م " );
        txtTelephone.setText(itemtel.get(position));
        txtDate.setText(itemdate.get(position));
        txtBwaba.setText(itemgate.get(position));


        if (itemimg.get(position).equals("waiting")){
            img.setImageResource(R.drawable.logopng);
        }else {
            Glide.with(context)
                    .load(itemimg.get(position))
                    .placeholder(R.drawable.logopng)
                    .apply(new RequestOptions().override(300, 250))
                    .into(img);
        }

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = itemtel.get(position);
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                context.startActivity(surf);

                Toast.makeText(context, "Calling", Toast.LENGTH_SHORT).show();
            }
        });




       /* img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (test == 0) {
                    //ArrayList<String> colorsList = new ArrayList<>();

                    if (colorsSet != null){

                    }
                    else {
                        colorsSet = new HashSet<>();
                    }
                    // add all the colors to the Set
                    colorsSet.add(itemkey.get(position));



                    // Put an ArrayList to SharedPreferences
                    mEditor.putStringSet(
                            context.getResources().getString(R.string.sp_key_colors),
                            colorsSet
                    );
                    mEditor.apply();

                    img_fav.setImageResource(R.drawable.starblack);
                    test = 1 ;

                }
                else if (test == 1) {


                    // add all the colors to the Set
                    colorsSet.remove(itemkey.get(position));



                    // Put an ArrayList to SharedPreferences
                    mEditor.putStringSet(
                            context.getResources().getString(R.string.sp_key_colors),
                            colorsSet
                    );
                    mEditor.apply();

                    img_fav.setImageResource(R.drawable.stargreen);
                    test = 0 ;
                }
                else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        return rowView;


    }
    
}