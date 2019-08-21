package com.kobtan.fahmy.hadayekelahram;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ameermahmoud on 09/05/17.
 */

public class StringLocal {

    // private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 28;

    // Sharedpref file name
    private static final String PREF_NAMEE = "hadayek_language";

    // All Shared Preferences Keys
    private static final String KEY_USER_IDD = "VarHadayek_langugae";



    // Constructor
    public StringLocal(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAMEE, Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public void storeUser(String user) {
        // editor.clear();
        editor.commit();
        // editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_IDD , user);
      /*  editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_TYPE , user.getType());
        editor.putString(KEY_USER_PHONE , user.getPhone());
        editor.putString(KEY_USER_EMAIL , user.getEmail());*/
        editor.commit();


        //   Log.e(TAG, "User is stored in shared preferences. " + user.getName() + " ," + user.getType() );
    }

    public String getUser() {

        if (pref.getString(KEY_USER_IDD, null ) != null) {
            String id ;
            id = pref.getString(KEY_USER_IDD , null );
           /* name = pref.getString(KEY_USER_NAME, null);
            type = pref.getString(KEY_USER_TYPE, null);
            phone = pref.getString(KEY_USER_PHONE, null);
            email = pref.getString(KEY_USER_EMAIL, null);*/
            // get elkeyyy

        /*    UserModel user = new UserModel();
            user.setId(id);
            user.setName(name);
            user.setType(type);
            user.setPhone(phone);
            user.setName(email);
            return user;*/
           // String user = id ;

            return  id ;
        }
        return null;
    }
    public void clear() {
        // FirebaseMessaging.getInstance().unsubscribeFromTopic("room_"+getUser().getId());
        editor.clear();
        editor.commit();
        /*Intent intent = new Intent(_context, LoginActivity.class);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        _context.startActivity(mainIntent);*/
    }
}

/*
private void init(String s) {


        imagesView = new ArrayList<>() ;
        kind = new ArrayList<>() ;


        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.one_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.two_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.three_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.four_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.five_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.six_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.seven_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.thirteen_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.nine_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.ten_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.eleven_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.twelve_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.eight_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.fourten_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.fiveten_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.sixten_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.seventen_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.eighten_el); //

        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.logo);
        imagesView.add(R.drawable.nineten_el); //


        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");

        kind.add("gif");
        kind.add("adv");
        kind.add("pic");



        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new ViewPagerAbater(MainActivity.this, imagesView , s , kind));
        mPager.setPageTransformer(true, new RotateUpTransformer());


        //CirclePageIndicator indicator = (CirclePageIndicator)
                //findViewById(R.id.indicator);

       // indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
       // indicator.setRadius(5 * density);

        NUM_PAGES =imagesView.size();
        //Toast.makeText(this, String.valueOf(NUM_PAGES), Toast.LENGTH_SHORT).show();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        setTimer(mPager , 4);


        // Pager listener over indicator


    }


 */
