package com.kobtan.fahmy.hadayekelahram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CustomListAdapterBuyTwo extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<String> itemprice;
    private final ArrayList<String> itemimg;
    private final ArrayList<String> itemtel;
    private final ArrayList<String> itemdate;
    private final ArrayList<String> itemcalls;
    private final ArrayList<String> itemmsgs;
    private final ArrayList<String> itemviews;
    private Typeface typeface ;


    public CustomListAdapterBuyTwo(Activity context, ArrayList<String> itemname, ArrayList<String> itemprice , ArrayList<String> itemimg , ArrayList<String> itemtel , ArrayList<String> itemdate , ArrayList<String> itemcalls
            ,ArrayList<String> itemmsgs ,  ArrayList<String> itemviews   ) {
        super(context, R.layout.itemlisttwo, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemprice=itemprice;
        this.itemdate=itemdate;
        this.itemimg  = itemimg ;
        this.itemtel  = itemtel ;
        this.itemcalls  = itemcalls ;
        this.itemmsgs  = itemmsgs ;
        this.itemviews  = itemviews ;


        typeface = Typeface.createFromAsset(this.context.getAssets() , "font.ttf");
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.itemlistedit, null, true);


        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.item2);
        TextView txtTelephone = (TextView) rowView.findViewById(R.id.item3);
        TextView txtDate = (TextView) rowView.findViewById(R.id.item4);
        TextView txtViews = (TextView) rowView.findViewById(R.id.item5);
        TextView txtCalls = (TextView) rowView.findViewById(R.id.item6);
        TextView txtMsgs = (TextView) rowView.findViewById(R.id.item7);

        ImageView img = (ImageView) rowView.findViewById(R.id.icon);

        Button options  = (Button) rowView.findViewById(R.id.options) ;


        txtPrice.setTypeface(typeface);
        txtTitle.setTypeface(typeface);


        txtTitle.setText(itemname.get(position));
        txtPrice.setText(itemprice.get(position) + " ج.م ");
        txtTelephone.setText("التليفون: " + itemtel.get(position));
        txtDate.setText(itemdate.get(position));

        txtViews.setText(itemviews.get(position));
        txtCalls.setText(itemcalls.get(position));
        txtMsgs.setText(itemmsgs.get(position));


        if (itemimg.get(position).equals("waiting")) {
            img.setImageResource(R.drawable.logopng);
        } else {
            Glide.with(context)
                    .load(itemimg.get(position))
                    .placeholder(R.drawable.logopng)
                    .apply(new RequestOptions().override(300, 250))
                    .into(img);
        }

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof EditMyAdvActivity){
                    ((EditMyAdvActivity)context).options_o(position);
                }
                if(context instanceof AdminBuysActivity){
                    ((AdminBuysActivity)context).options_o(position);
                }
            }
        });


        return rowView;
    }





}