package com.kobtan.fahmy.hadayekelahram;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;


public class ViewPagerAbaterDownload extends PagerAdapter {


    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private String s ;
    private ArrayList<String> kind ;


    public ViewPagerAbaterDownload(Context context, ArrayList<String> IMAGES , String s , ArrayList<String> kind) {
        this.context = context;
        this.IMAGES=IMAGES;
        this.kind = kind;
        inflater = LayoutInflater.from(context);
        this.s = s ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = null;

        if (kind.get(position).equals("adv")) {
            imageLayout = inflater.inflate(R.layout.viewpager_item_custom, view, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);


            imageView.setImageResource(R.drawable.logo_custom);

            view.addView(imageLayout, 0);

            return imageLayout;
        }
        else if (kind.get(position).equals("gif")) {
           // if (position == IMAGES.size()-1) {
                if (s.equals("buy")) {
                    imageLayout = inflater.inflate(R.layout.viewpager_item_gif, view, false);

                    assert imageLayout != null;

                    view.addView(imageLayout, 0);

                    return imageLayout;
                }
                else  {
                   // imageLayout = inflater.inflate(R.layout.viewpager_item_gif_two, view, false);
                    imageLayout = inflater.inflate(R.layout.viewpager_item_gif, view, false);
                    assert imageLayout != null;


                    view.addView(imageLayout, 0);

                    return imageLayout;
                }

            //}
        }
        else if (kind.get(position).equals("pic")) {
            imageLayout = inflater.inflate(R.layout.viewpager_item, view, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);

            if (IMAGES.get(position).equals("waiting")) {
                imageView.setImageResource(R.drawable.logo_custom);
            }else {
                Glide.with(context)
                        .load(IMAGES.get(position))
                        .placeholder(R.drawable.logo_custom)
                        //.apply(new RequestOptions().override(300, 250))
                        .into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (context instanceof MainActivity) {
                           // ((MainActivity)context).removeOpj(position);
                        }
                        if (context instanceof MainSubActivity) {
                           // ((MainSubActivity)context).removeOpj(position);
                        }
                        if (context instanceof MainChildActivity) {
                           // ((MainChildActivity)context).removeOpj(position);
                        }
                    }
                });
                //imageView.setImageResource(IMAGES.get(position));
            }
            view.addView(imageLayout, 0);

            return imageLayout;
        }
        else if (kind.get(position).equals("no")) {
            return null ;
        }
        else {
            imageLayout = inflater.inflate(R.layout.viewpager_item, view, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);


            imageView.setImageResource(R.drawable.logo_custom);

            view.addView(imageLayout, 0);

            return imageLayout;
        }


    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }



}
