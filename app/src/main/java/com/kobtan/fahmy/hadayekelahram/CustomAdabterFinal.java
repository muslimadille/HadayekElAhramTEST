package com.kobtan.fahmy.hadayekelahram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdabterFinal extends BaseAdapter {
    ArrayList<String> result;
    ArrayList<Integer> imageId;
    private static LayoutInflater inflater=null;
    private final Activity context;
    private Typeface typeface ;
    private final int color_dd ;
    private String elementone ;

    public CustomAdabterFinal(Activity context, ArrayList<String> osNameList, ArrayList<Integer> osImages , int color_dd  , String elementone) {

        // TODO Auto-generated constructor stub
        result=osNameList;
        this.context = context;
        imageId=osImages;
        this.color_dd = color_dd;
        this.elementone = elementone;
        typeface = Typeface.createFromAsset(this.context.getAssets() , "font.ttf");
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView os_text;
        ImageView os_img;
        LinearLayout os_rel;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout_sub, null);
        holder.os_text =(TextView) rowView.findViewById(R.id.os_texts);
        holder.os_text.setTypeface(typeface);
        holder.os_img =(ImageView) rowView.findViewById(R.id.img);
        holder.os_rel =(LinearLayout) rowView.findViewById(R.id.os_rel);


        if (result.get(position).length() >= 10) {
            holder.os_text.setTextSize(12);
            holder.os_text.setText(result.get(position));
        }
        else {
            holder.os_text.setText(result.get(position));
        }


        holder.os_img.setImageResource(imageId.get(position));

        holder.os_rel.setBackgroundColor(context.getResources().getColor(color_dd));

        //GradientDrawable gradientDrawable = (GradientDrawable) holder.os_rel.getBackground().mutate();

        //gradientDrawable.setColor(context.getResources().getColor(colorID.get(position)));



        final String selecteditem = result.get(position) ;
        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                    Intent i = new Intent(context, MainChildActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED2", elementone);
                    i.putExtra("STRING_I_NEED_TWO", color_dd);
                    context.startActivity(i);


            }
        });

        return rowView;
    }


}