package com.ssso_knrdist.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public static final String APP_USER_LOGIN = "login";


    public static final String PREF_NAME = "nadk";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String EMAIL_ID = "email_id";
    public static final String USERNAME = "name";
    public static final String USER_ID = "user_id";
    public static final String SERVICE_ID = "service_id";
    public static final String SAMITHI_ID = "service_id";
    public static final String SAMITHI_service_ID = "service_id";
    public static final String DOB = "dob";



    public PrefManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public void storeValue(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, object.toString());
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        }
        editor.apply();
    }

    public String getUserId() {
        return pref.getString(USER_ID, "");
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public String getString(String key) {
        return pref.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public int getInteger(String key) {
        return pref.getInt(key, 0);
    }

    public void setEmailId(String emailId) {
        editor.putString(EMAIL_ID, emailId);
        editor.commit();
    }


    public String getEmailId() {
        return pref.getString(EMAIL_ID, "");
    }

    public void setServiceId(String serviceId) {
        editor.putString(SERVICE_ID, serviceId);
        editor.commit();
    }


    public String getServiceId() {
        return pref.getString(SERVICE_ID, "");
    }

 public void setSAMITHI_service_ID(String samithi_service_id) {
        editor.putString(SAMITHI_service_ID, samithi_service_id);
        editor.commit();
    }


    public String getSAMITHI_service_ID() {
        return pref.getString(SAMITHI_service_ID, "");
    }







    public void setSamithiId(String samithiId) {
        editor.putString(SAMITHI_ID, samithiId);
        editor.commit();
    }


    public String getSamithiId() {
        return pref.getString(SAMITHI_ID, "");
    }



    public void setUsername(String username) {
        editor.putString(USERNAME, username);

        editor.commit();
    }

    public String getUsername() {
        return pref.getString(USERNAME, "");
    }
  public void setDob(String dob) {
        editor.putString(DOB, dob);

        editor.commit();
    }

    public String getDob() {
        return pref.getString(DOB, "");

    }


    public void setPhoneNumber(String phoneNumber) {
        editor.putString(PHONE_NUMBER, phoneNumber);
        editor.commit();
    }

    public String getPhoneNumber() {
        return pref.getString(PHONE_NUMBER, "");
    }




    public void logout() {
        editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}