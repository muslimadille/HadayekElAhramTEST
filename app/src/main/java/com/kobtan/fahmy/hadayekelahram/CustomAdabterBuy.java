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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdabterBuy extends BaseAdapter {
    ArrayList<String> result;
    ArrayList<String> osNameList_eng = new ArrayList<>();
    ArrayList<Integer> imageId;
    ArrayList<Integer> colorID;
    private static LayoutInflater inflater=null;
    private final Activity context;
    private Typeface typeface ;
    private StringLocal stringLocal ;

    public CustomAdabterBuy(Activity context, ArrayList<String> osNameList, ArrayList<Integer> osImages , ArrayList<Integer> colorID) {

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

    public  void  getData () {
        osNameList_eng.add("Real estates");
        osNameList_eng.add("Add Advertise");
        osNameList_eng.add("Cars");
        osNameList_eng.add("Mobiles");
        osNameList_eng.add("Jops");
        osNameList_eng.add("Home Furniture");
        osNameList_eng.add("Electronics");
        osNameList_eng.add("Home Maintenance");
        osNameList_eng.add("Sports Devices");
        osNameList_eng.add("Fashion");
        osNameList_eng.add("Pets");
        osNameList_eng.add("Private classes");
        osNameList_eng.add("Others");
        osNameList_eng.add("Online Clothes");
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
        RelativeLayout os_rel;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout_buy, null);
        holder.os_text =(TextView) rowView.findViewById(R.id.os_texts);
        holder.os_text.setTypeface(typeface);
        holder.os_img =(ImageView) rowView.findViewById(R.id.os_images);
        holder.os_rel =(RelativeLayout) rowView.findViewById(R.id.rel);

        if (stringLocal.getUser() != null) {
            if (stringLocal.getUser().equals("en")) {
                holder.os_text.setText(osNameList_eng.get(position));
            }
            else {
                holder.os_text.setText(result.get(position));
            }
        }
        else {
            holder.os_text.setText(result.get(position));
        }



        if (holder.os_text.getText().toString().equals("Add Advertise")
                || holder.os_text.getText().toString().equals("اضف إعلان")) {

            GradientDrawable gradientDrawable = (GradientDrawable) holder.os_rel.getBackground().mutate();

            gradientDrawable.setColor(context.getResources().getColor(R.color.five));

        }
        holder.os_img.setImageResource(imageId.get(position));

        //GradientDrawable gradientDrawable = (GradientDrawable) holder.os_rel.getBackground().mutate();

        //gradientDrawable.setColor(context.getResources().getColor(colorID.get(position)));



        final String selecteditem = result.get(position) ;
        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (selecteditem.equals("اضف إعلان")){
                    Intent i = new Intent(context, NotePayActivity.class);
                    context.startActivity(i);
                }
                else
                {
                    Intent i = new Intent(context, BuySubActivity.class);
                    i.putExtra("STRING_I_NEED2", selecteditem);
                    i.putExtra("STRING_I_NEED", colorID.get(position));
                    context.startActivity(i);
                }





            }
        });

        return rowView;
    }




}
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                        img_one.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
                case GALLERY_REQUEST2:
                    selectedImageTwo = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageTwo);
                        img_two.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
                case GALLERY_REQUEST3:
                    selectedImageThree = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageThree);
                        img_three.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
                case GALLERY_REQUEST4:
                    selectedImageFour = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageFour);
                        img_four.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
                case GALLERY_REQUEST5:
                    selectedImageFive = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageFive);
                        img_five.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

     img_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST2);
            }
        });
        img_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST3);
            }
        });
        img_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST4);
            }
        });
        img_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST5);
            }
        });

    private final int GALLERY_REQUEST = 1 ;
    private final int GALLERY_REQUEST2 = 2 ;
    private final int GALLERY_REQUEST3 = 3 ;
    private final int GALLERY_REQUEST4 = 4 ;
    private final int GALLERY_REQUEST5 = 5 ;

        private Uri selectedImage , selectedImageTwo , selectedImageThree , selectedImageFour , selectedImageFive ;


*/