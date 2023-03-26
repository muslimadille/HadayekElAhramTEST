package com.kobtan.fahmy.hadayekelahram;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ameermahmoud on 09/05/17.
 */

public class StringCurrentUserPref {

    // private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 1;

    // Sharedpref file name
    private static final String PREF_NAMEE = "hadayek";

    // All Shared Preferences Keys
    private static final String KEY_USER_IDD = "VarHadayek";



    // Constructor
    public StringCurrentUserPref(Context context) {
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
