package com.kobtan.fahmy.hadayekelahram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class CustomAdabter extends BaseAdapter {
    ArrayList<String> result;
    ArrayList<String> result_en = new ArrayList<>();
    ArrayList<Integer> imageId;
    ArrayList<Integer> colorID;
    private static LayoutInflater inflater=null;
    private final Activity context;
    private Typeface typeface ;
    private StringLocal stringLocal ;

    public CustomAdabter(Activity context, ArrayList<String> osNameList, ArrayList<Integer> osImages , ArrayList<Integer> colorID) {

        // TODO Auto-generated constructor stub

        stringLocal = new StringLocal(context) ;
        getData();

        result=osNameList;
        this.context = context;
        imageId=osImages;
        this.colorID = colorID;
        typeface = Typeface.createFromAsset(this.context.getAssets() , "font.ttf");
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);






    }

    public void  getData() {
        result_en.add("Restaurants");
        result_en.add("Saratn ElSady");
        result_en.add("Shopping") ;
        result_en.add("Fashion & Clothes");
        result_en.add("Hospitals");
        result_en.add("Doctors");
        result_en.add("Pharmacies");
        result_en.add("Laboratories");
        result_en.add("Educational");

        result_en.add("Festivals"); //new 1

        result_en.add("Gym & Sports");
        result_en.add("Electronics");
        result_en.add("Real estates");
        result_en.add("Babies");
        result_en.add("Cars");
        result_en.add("Mobiles");
        result_en.add("Pets");
        result_en.add("Home Maintenance");
        result_en.add("Home Furniture");
        result_en.add("Cafes");
        result_en.add("ATM");
        result_en.add("Entertainment");
        result_en.add("Tele Companies");
        result_en.add("Government Services");
        result_en.add("Law Offices");

        result_en.add("Join us");
        result_en.add("Advertise with us");
    };

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
        RelativeLayout os_rel;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout, null);
        holder.os_text =(TextView) rowView.findViewById(R.id.os_texts);
        holder.os_text.setTypeface(typeface);
        holder.os_img =(ImageView) rowView.findViewById(R.id.os_images);
        holder.os_rel =(RelativeLayout) rowView.findViewById(R.id.rel);



        if (stringLocal.getUser() != null) {
            if (stringLocal.getUser().equals("en")) {
                holder.os_text.setText(result_en.get(position));
            }
            else {
                holder.os_text.setText(result.get(position));
            }
        }
        else {
            holder.os_text.setText(result.get(position));
        }





        holder.os_img.setImageResource(imageId.get(position));

        GradientDrawable gradientDrawable = (GradientDrawable) holder.os_rel.getBackground().mutate();

        gradientDrawable.setColor(context.getResources().getColor(colorID.get(position)));



        final String selecteditem = result.get(position) ;
        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                if (selecteditem.equals("تسوق"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("مطاعم"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("مستشفيات"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("مستلزمات اطفال"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("شركات الاتصالات"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("دكتور خاص"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("معامل"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("موضة و ملابس"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("جيم و رياضة"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("اجهزة الكترونية"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("ترفيهي"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("سيارات"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("تعليمي"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("حفلات ومناسبات"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("ماكينات الصرافة"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("عقارات"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("خدمات حكومية"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("صيانة منزلية"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
               /* else if (selecteditem.equals("حيوانات اليفة"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }*/
                else if (selecteditem.equals("اثاث منزلي"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("عروض وخصومات"))
                {
                    Intent i = new Intent(context, MainSubActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                    context.startActivity(i);
                }
                else if (selecteditem.equals("اضف إعلان")) {
                    Intent i = new Intent(context, NotePayActivity.class);
                    i.putExtra("STRING_I_NEED", selecteditem);
                    context.startActivity(i);
                }
                else if (selecteditem.equals("انضم الينا")) {
                    Intent i = new Intent(context, DoWithUsActivity.class);
                    context.startActivity(i);
                }
                else if (selecteditem.equals("اعلن معنا")) {
                    Intent i = new Intent(context, AdvWithUsActivity.class);
                    context.startActivity(i);
                }
                else if (selecteditem.equals("حملة سرطان الثدى")) {
                    Intent i = new Intent(context, SignToSaratnActivity.class);
                    context.startActivity(i);
                }
                else
                    {
                        Intent i = new Intent(context, MainChildActivity.class);
                        i.putExtra("STRING_I_NEED2", selecteditem);
                        i.putExtra("STRING_I_NEED_TWO", colorID.get(position));
                        context.startActivity(i);
                }



            }
        });

        return rowView;
    }




}