package com.ssso_knrdist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ssso_knrdist.Utils.ApiCallingFlow;
import com.ssso_knrdist.Utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText input_mobilenum, input_paswrd, input_username, input_dateofbirth;
    RadioButton male, feMale;
    TextView link_signin;
    Button btn_reg;
    PrefManager prefManager;
    ApiCallingFlow apiCallingFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_mobilenum = findViewById(R.id.input_mobilenum);
        input_paswrd = findViewById(R.id.input_password);
        input_username = findViewById(R.id.input_name);
        input_dateofbirth = findViewById(R.id.input_dob);
        btn_reg = findViewById(R.id.btn_registration);
        male = findViewById(R.id.rb_reg_male);
        feMale = findViewById(R.id.rb_reg_female);
        prefManager = new PrefManager(this);
        link_signin = findViewById(R.id.link_signin);

        link_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
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

        final String password = input_paswrd.getText().toString();
        final String mobile = input_mobilenum.getText().toString();
;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("login response", "" + response);
                        apiCallingFlow.onSuccessResponse();
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            if (status.equals("success")) {
                                JSONObject json = object.getJSONObject("user_data");
                                String name = json.getString("name");
                                String phone = json.getString("phone");
                                String gender = json.getString("gender");
                                String dob = json.getString("dob");
                                String user_id = json.getString("user_id");

                                prefManager.storeValue(PrefManager.USER_ID, user_id);
                                prefManager.setUserId(user_id);
                                Log.i("loginuser", "" + prefManager.getUserId());

                                prefManager.storeValue(PrefManager.USERNAME, name);
                                prefManager.setUsername(name);

                                prefManager.storeValue(PrefManager.PHONE_NUMBER, phone);
                                prefManager.setPhoneNumber(phone);

                                Intent intent = new Intent(RegisterActivity.this, BottomPageActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            } else if (status.equals("error")) {
                                Toast.makeText(RegisterActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else if (status.equals("0")) {
                                Toast.makeText(RegisterActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else if (status.equals("error1")) {
                                Toast.makeText(RegisterActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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

        if (input_mobilenum.getText().toString().length() == 0)
        {
            input_mobilenum.setError("PhoneNumber is Required");
            input_mobilenum.requestFocus();
            flag = 1;
        }
        else if (input_mobilenum.getText().toString().length() != 10)
        {
            input_mobilenum.setError("phone number required 10 digits");
            input_mobilenum.requestFocus();
            flag = 1;
        }
        else if (input_paswrd.getText().toString().length() == 0) {
            input_paswrd.setError("Password not entered");
            input_paswrd.requestFocus();
            flag = 1;
        }
        else if (input_paswrd.getText().toString().length() < 8)
        {
            input_paswrd.setError("password requires atleast 8 characters");
            input_paswrd.requestFocus();
            flag = 1;
        }
        else if (input_dateofbirth.getText().toString().length() == 0)
        {
            input_dateofbirth.setError("dob is required");
            input_dateofbirth.requestFocus();
            flag = 1;
        }

        return flag;
    }
}
