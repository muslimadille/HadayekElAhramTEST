package com.kobtan.fahmy.hadayekelahram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CustomListAdapterTwo extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<String> itemprice;
    private final ArrayList<Float> itemrate;
    private final ArrayList<String> itemimg;
    private final ArrayList<String> itemtel;
    private Typeface typeface ;
    private int colorIdd ;
    private ArrayList<Double> long_map=  new ArrayList<>()  ;
    private ArrayList<Double> ard_map =  new ArrayList<>()  ;
    private ArrayList<String> map_link =  new ArrayList<>();


    public CustomListAdapterTwo(Activity context, ArrayList<String> itemname, ArrayList<String> itemprice , ArrayList<Float> itemrate ,ArrayList<String> itemimg , ArrayList<String> itemtel , int colorIdd  , ArrayList<Double>  long_map , ArrayList<Double>  ard_map,ArrayList<String> map_link) {
        super(context, R.layout.itemlisttwo, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemprice=itemprice;
        this.itemrate=itemrate;
        this.itemimg  = itemimg ;
        this.itemtel  = itemtel ;
        this.colorIdd  = colorIdd ;
        this.long_map = long_map ;
        this.map_link=map_link;
        this.ard_map = ard_map ;

        typeface = Typeface.createFromAsset(this.context.getAssets() , "font.ttf");
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.itemlisttwo, null,true);



        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.item2);
        TextView txtTelephone = (TextView) rowView.findViewById(R.id.item3);
        ImageView img = (ImageView) rowView.findViewById(R.id.icon);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.rating);
        ImageView call_btn = (ImageView) rowView.findViewById(R.id.textView16) ;
        ImageView map_btn = (ImageView) rowView.findViewById(R.id.textView17) ;
        LinearLayout linearLayout_l = (LinearLayout) rowView.findViewById(R.id.liner) ;

        txtPrice.setTypeface(typeface);
        txtTitle.setTypeface(typeface);


        GradientDrawable drawable = (GradientDrawable)linearLayout_l.getBackground();
        drawable.setStroke(3, context.getResources().getColor(colorIdd));

        txtTitle.setText(itemname.get(position));
        txtTitle.setTextColor(context.getResources().getColor(colorIdd));


        txtPrice.setText(  "العنوان: " + itemprice.get(position));
        ratingBar.setRating(itemrate.get(position));
        txtTelephone.setText(itemtel.get(position));
        if (itemimg.get(position).equals("waiting")){
            img.setImageResource(R.drawable.logopng);
        }else {
            Glide.with(context)
                    .load(itemimg.get(position))
                    .placeholder(R.drawable.logo)
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
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(long_map.get(position)>0.0&&ard_map.get(position)>0.0){
                    Intent i =  new Intent(context , MapsActivity.class) ;
                    i.putExtra("STRING_I_NEED" , long_map.get(position)) ;
                    i.putExtra("STRING_I_NEED2" , ard_map.get(position)) ;
                    context.startActivity(i);
                }else{
                    if(!map_link.isEmpty()){
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(map_link.get(position)));
                        context.startActivity(intent);
                    }

                }
            }
        });

        return rowView;


    }
}