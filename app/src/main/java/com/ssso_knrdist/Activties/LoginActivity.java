package com.ssso_knrdist.Activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ssso_knrdist.Utils.PrefManager;
import com.ssso_knrdist.R;
import com.ssso_knrdist.Utils.ApiCallingFlow;
import com.ssso_knrdist.Utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
EditText input_mobilenum,input_paswrd;
TextView link_signup;
Button btn_login;
    PrefManager prefManager;
    ApiCallingFlow apiCallingFlow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_mobilenum=findViewById(R.id.input_mobilenum);
        input_paswrd=findViewById(R.id.input_password);
        btn_login=findViewById(R.id.btn_login);
        prefManager = new PrefManager(this);
        link_signup=findViewById(R.id.link_signup);


        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int verify = validate();
                if (verify == 0) {
                    api();
                }
            }
        });


    }

    private void api() {
        RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.relative_layout);

        apiCallingFlow = new ApiCallingFlow(this, parentLayout, true) {
            @Override
            public void callCurrentApiHere() {
                api();
            }
        };
        if (apiCallingFlow.getNetworkState()) {
            loginData();
        }
    }

    private void loginData() {
        final String password = input_paswrd.getText().toString();
        final String mobile = input_mobilenum.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("login response", "" + response);
                        apiCallingFlow.onSuccessResponse();
                        try {
                            JSONObject object = new JSONObject(response);

                            String status = object.getString("status");

                            if (status.equals("success")) {
                                JSONObject json = object.getJSONObject("user_profile");

                                String name = json.getString("name");
                                String email = json.getString("email");
                                String phone = json.getString("mobile");
                                String user_id = json.getString("user_id");
                                prefManager.storeValue(PrefManager.USER_ID, user_id);
                                prefManager.setUserId(user_id);
                                Log.i("loginuser", "" + prefManager.getUserId());
                                prefManager.storeValue(PrefManager.USERNAME, name);
                                prefManager.setUsername(name);
                                prefManager.storeValue(PrefManager.EMAIL_ID, email);
                                prefManager.setEmailId(email);
                                prefManager.storeValue(PrefManager.PHONE_NUMBER, phone);
                                prefManager.setPhoneNumber(phone);
                                prefManager.storeValue(prefManager.APP_USER_LOGIN, true);


                                    Intent intent = new Intent(LoginActivity.this, BottomPageActivity.class);
                                    startActivity(intent);
                                    finish();


                                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(LoginActivity.this, AddressActivity.class);
//                                startActivity(intent);
//                                finish();
                            } else if (status.equals("error")) {
                                Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else if (status.equals("0")) {
                                Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else if (status.equals("error1")) {
                                Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            }else if (status.equals("error2")) {
                                Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("android", "_--------------Login Response----------------" + response);
                        // Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        apiCallingFlow.onErrorResponse();

                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.i("An", "_-------------Error--------------------" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", mobile);
                map.put("password", password);


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private int validate() {
        int flag = 0;

        if (input_mobilenum.getText().toString().length() == 0) {
            input_mobilenum.setError("PhoneNumber is Required");
            input_mobilenum.requestFocus();
            flag = 1;
        } else if (input_mobilenum.getText().toString().length() != 10) {
            input_mobilenum.setError("phone number required 10 digits");
            input_mobilenum.requestFocus();
            flag = 1;
        } else if (input_paswrd.getText().toString().length() == 0) {
            input_paswrd.setError("Password not entered");
            input_paswrd.requestFocus();
            flag = 1;
        } else if (input_paswrd.getText().toString().length() < 8) {
            input_paswrd.setError("password requires atleast 8 characters");
            input_paswrd.requestFocus();
            flag = 1;
        }
        return flag;
    }
}