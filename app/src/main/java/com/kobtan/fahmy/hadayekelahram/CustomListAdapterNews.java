package com.kobtan.fahmy.hadayekelahram;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomListAdapterNews extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemkey;
    private final ArrayList<String> itemname;
    private final ArrayList<String> itemTelephone;
    private final ArrayList<String> itemDetails;
    private final ArrayList<String> itempostImages;
    private final ArrayList<String> itemlogoImages;
    private final ArrayList<String> itemaddress;
    private final ArrayList<String> itemdate;
    private Typeface typeface ;
    private int test ;
    Set<String> colorsSet ;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;



    public CustomListAdapterNews(Activity context, ArrayList<String> itemname,ArrayList<String> itemTelephone ,ArrayList<String> itemDetails, ArrayList<String> itempostImages , ArrayList<String> itemlogoImages, ArrayList<String> itemaddress , ArrayList<String> itemdate ,ArrayList<String> itemkey ) {
        super(context, R.layout.itemlisttwo, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemTelephone=itemTelephone;
        this.itemDetails=itemDetails;
        this.itempostImages  = itempostImages ;
        this.itemlogoImages  = itemlogoImages ;
        this.itemaddress  = itemaddress ;
        this.itemdate  = itemdate ;

        this.itemkey  = itemkey ;


        typeface = Typeface.createFromAsset(this.context.getAssets() , "font.ttf");

        // Initialize the SharedPreference object
        this.mSharedPreferences = context.getSharedPreferences( "mmkk" , context.MODE_PRIVATE);
        //this.mSharedPreferences = context.getSharedPreferences("colors_list", Activity.MODE_PRIVATE);

        // Get SharedPreferences editor
        this.mEditor = mSharedPreferences.edit();

        // Read the values from SharedPreferences
        colorsSet = mSharedPreferences.getStringSet(
                context.getResources().getString(R.string.sp_key_colors),
                null
        );
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.itemlistnews, null,true);
        test = 0 ;


        TextView text_name = (TextView) rowView.findViewById(R.id.item_name);
        TextView text_telephone = (TextView) rowView.findViewById(R.id.item_telephone);
        TextView text_details = (TextView) rowView.findViewById(R.id.item_details);
        TextView text_address = (TextView) rowView.findViewById(R.id.item_address);
        TextView text_date = (TextView) rowView.findViewById(R.id.item_date);
        CircleImageView img_icon = (CircleImageView) rowView.findViewById(R.id.user_img);
        ImageView img_post = (ImageView) rowView.findViewById(R.id.item_post);
        ImageView img_share = (ImageView) rowView.findViewById(R.id.sharee);
        ImageView img_call = (ImageView) rowView.findViewById(R.id.callee);
        ImageView img_fav = (ImageView) rowView.findViewById(R.id.star);

        text_name.setTypeface(typeface);
        text_telephone.setTypeface(typeface);
        text_details.setTypeface(typeface);
        text_address.setTypeface(typeface);
        text_date.setTypeface(typeface);

        text_name.setText(itemname.get(position));
        //text_telephone.setText(  "اتصل من هنا : " + itemTelephone.get(position));
        //text_details.setText(  "العرض: " + itemDetails.get(position));
        //text_address.setText(  "العنوان: " + itemaddress.get(position));
        text_date.setText(  "التاريخ: " + itemdate.get(position));

        text_details.setText(Html.fromHtml("العرض: " + "<font color=#0816A6>" + itemDetails.get(position) ));
        text_details.setTypeface(null, Typeface.BOLD);
        text_address.setText(Html.fromHtml("العنوان: " + "<font color=red>" + itemaddress.get(position) ));
        //text_date.setText(Html.fromHtml("التاريخ: " + "<font color=red>" + itemdate.get(position) ));
        text_telephone.setText(Html.fromHtml("اتصل من هنا : " + "<font color=red>" + itemTelephone.get(position) ));


        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "حدايق الاهرام");
                    String shareMessage= "\nLet me recommend you this ad\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        text_telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = itemTelephone.get(position);
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                context.startActivity(surf);

                Toast.makeText(context, "Calling Now ....", Toast.LENGTH_SHORT).show();
            }
        });
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = itemTelephone.get(position);
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                context.startActivity(surf);

                Toast.makeText(context, "Calling Now ....", Toast.LENGTH_SHORT).show();
            }
        });

        if (itemlogoImages.get(position).equals("waiting")){
            img_icon.setImageResource(R.drawable.logopng);
        }else {
            Glide.with(context)
                    .load(itemlogoImages.get(position))
                    .placeholder(R.drawable.logo)
                    .apply(new RequestOptions().override(300, 250))
                    .into(img_icon);
        }


        if (itempostImages.get(position).equals("waiting")){
            img_post.setImageResource(R.drawable.logopng);
        }else {
            Glide.with(context)
                    .load(itempostImages.get(position))
                    .placeholder(R.drawable.logo)
                    .apply(new RequestOptions().override(300, 250))
                    .into(img_post);
        }


        if (colorsSet != null ){

            for(String color : colorsSet){
                if (itemkey.get(position).equals(color)) {
                    img_fav.setImageResource(R.drawable.starblack);
                    test = 1 ;
                    break;
                }
            }
        }

        img_fav.setOnClickListener(new View.OnClickListener() {
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
        });


        return rowView;


    }
}