package com.kobtan.fahmy.hadayekelahram;

import android.content.Context;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;


public class ViewPagerAbater extends PagerAdapter {


    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private String s ;
    private ArrayList<String> kind ;


    public ViewPagerAbater(Context context,ArrayList<Integer> IMAGES , String s , ArrayList<String> kind) {
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


            imageView.setImageResource(IMAGES.get(position));

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


            imageView.setImageResource(IMAGES.get(position));

            view.addView(imageLayout, 0);

            return imageLayout;
        }
        else {
            imageLayout = inflater.inflate(R.layout.viewpager_item, view, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);


            imageView.setImageResource(IMAGES.get(position));

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
