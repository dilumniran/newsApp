package com.example.a247news.util;

import android.util.Log;

import com.example.a247news.database.TinyDB;
import com.example.a247news.object.User;


public class SessionManager {
    private static final String TAG = SessionManager.class.getName();

    private static volatile SessionManager sessionManagerInstance;
    private static final String USER_DATA = "user_data";

    private TinyDB mTinyDB;

    private SessionManager() {
        mTinyDB = new TinyDB(ContextManager.getInstance());
        //Prevent form the reflection api.
        if (sessionManagerInstance != null) {
            Log.d(TAG, "SessionManager:_12 ");
            mTinyDB = new TinyDB(ContextManager.getInstance());
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SessionManager getInstance() {
        //Double check locking pattern
        if (sessionManagerInstance == null) { //Check for the first time
            synchronized (SessionManager.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (sessionManagerInstance == null)
                    sessionManagerInstance = new SessionManager();
            }
        }
        return sessionManagerInstance;
    }

    /**
     * @param userData User information data
     */
    public void saveUerInfo(User userData){
        mTinyDB.putObject(USER_DATA,userData);
    }

    /**
     * @return User information data
     */
    public User getUerInfo(){
        return mTinyDB.getObject(USER_DATA, User.class);
    }

    /**
     * remove all saved the values from shared preference
     */
    public void logOut(){
        saveUerInfo(null);
    }
}
