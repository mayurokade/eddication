package com.ssb.apps.bookapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.ActivityLogin2Binding;
import com.ssb.apps.bookapp.databinding.ActivityLoginBinding;
import com.ssb.apps.bookapp.databinding.LayoutOtpBinding;
import com.ssb.apps.bookapp.model.RoutModel;
import com.ssb.apps.bookapp.model.UserDetailsModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin2 extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = ActivityLogin2.class.getName();
    ActivityLogin2Binding binding;
    LayoutOtpBinding otpBinding;
    ActivityLoginBinding loginBinding;
    private ViewPagerAdapter adapter;
    TextView txtLoginWithNum;
    EditText etNum;
    String id, name, email, birthday, gender;
    CallbackManager callbackManager;
    ApiInterface apiService;
    final int sdk = android.os.Build.VERSION.SDK_INT;
    private String mobileNo = "";
    String token = "";
    PinEntryEditText txt_pin_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_login2);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login2);
        if (BookApp.cache.readString(this, Constant.URL, "").isEmpty()) {
            Constant.BASE_URL = "https://ssbdesignapps.in/";
            Log.e(TAG, "onCreate: base url if " + Constant.BASE_URL);
            apiService = ApiClient.getClient().create(ApiInterface.class);
            loadRoutApi();
        } else {
            Constant.BASE_URL = "" + BookApp.cache.readString(this, Constant.URL, "");
            Log.e(TAG, "onCreate: base url else " + Constant.BASE_URL);
        }


        token = FirebaseInstanceId.getInstance().getToken();
        BookApp.cache.writeString(this, Constant.TOKEN, token);
        adapter = new ViewPagerAdapter();
        binding.viewPagerVertical.setAdapter(adapter);
        binding.viewPagerVertical.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                /*if (position == 1)
                    layoutEditMobile.setVisibility(View.VISIBLE);
                else
                    layoutEditMobile.setVisibility(View.GONE);*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        callbackManager = CallbackManager.Factory.create();


    }

    private void loadRoutApi() {
        if (IOUtils.isConnected(this)) {
            IOUtils.hideKeyBoard(this);
            //show progress dialog
            IOUtils.startLoadingView(this);


            Call<RoutModel> call = apiService.getBaseUrl("EddApPLicaT");
            call.enqueue(new Callback<RoutModel>() {
                @Override
                public void onResponse(Call<RoutModel> call, Response<RoutModel> response) {
                    Log.e(TAG, "onResponse: call " + call.request());
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            IOUtils.stopLoading();
                            BookApp.cache.writeString(ActivityLogin2.this, Constant.URL, response.body().getBaseUrl());
                            Constant.BASE_URL = response.body().getBaseUrl();
                        } else {
                            IOUtils.stopLoadingView();
                            IOUtils.showAlertDialog(ActivityLogin2.this, getString(R.string.error_message), getString(R.string.msg_contact_admin));// show alert dialog if invalid credential
                        }
                    }
                }

                @Override
                public void onFailure(Call<RoutModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(ActivityLogin2.this, getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });
        } else {
            IOUtils.showSnackBar(this, getString(R.string.err_internet));
        }
    }

    @Override
    public void onClick(View v) {

    }


    class ViewPagerAdapter extends PagerAdapter {
        private EditText inputRegistrationId, inputMobile, inputOtp;

        LayoutInflater layoutInflater;
        View view;

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }


        public ViewPagerAdapter() {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LinearLayout linOtp, linOtpSelected, linFacebook;
            LoginButton loginButton;
            TextView tvOtpTitle, tvOtpTitle1;
            final String[] strOTP = {""};
            if (position == 0) {
                view = layoutInflater.inflate(R.layout.activity_login, container, false);
                txtLoginWithNum = view.findViewById(R.id.txtLoginWithNum);
                linFacebook = view.findViewById(R.id.linFacebook);
                txtLoginWithNum.setOnClickListener(v -> {
                    binding.viewPagerVertical.setCurrentItem(1);
                    overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
                });

                loginButton = view.findViewById(R.id.login_button);
                List<String> permissionNeeds = Arrays.asList("user_photos", "email",
                        "user_birthday", "public_profile", "AccessToken");
               /* loginButton.registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {@Override
                        public void onSuccess(LoginResult loginResult) {

                            System.out.println("onSuccess");

                            String accessToken = loginResult.getAccessToken()
                                    .getToken();
                            Log.e("accessToken", accessToken);

                            GraphRequest request = GraphRequest.newMeRequest(
                                    loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {@Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {

                                        Log.i("LoginActivity",
                                                response.toString());
                                        try {
                                            id = object.getString("id");
                                            try {
                                                URL profile_pic = new URL(
                                                        "http://graph.facebook.com/" + id + "/picture?type=large");
                                                Log.e("profile_pic",
                                                        profile_pic + "");

                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }
                                            name = object.getString("name");
                                            email = object.getString("email");
                                            gender = object.getString("gender");
                                            birthday = object.getString("birthday");

                                            Log.e(TAG, "onCompleted: name "+name+"/n email "+email +"/n gender "+gender+"/n bday "+birthday );
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields",
                                    "id,name,email,gender, birthday");
                            request.setParameters(parameters);
                            request.executeAsync();
                        }

                            @Override
                            public void onCancel() {
                                System.out.println("onCancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                System.out.println("onError");
                                Log.v("LoginActivity", exception.getCause().toString());
                            }
                        });*/
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(TAG, "onSuccess: User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "onCancel: Login attempt canceled.");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Log.e(TAG, "onError: Login attempt failed.");
                    }
                });

                linFacebook.setOnClickListener(v -> {
                    loginButton.performClick();
                });

            }
            if (position == 1) {
                view = layoutInflater.inflate(R.layout.layout_otp, container, false);
                etNum = view.findViewById(R.id.etNum);
                linOtp = view.findViewById(R.id.linOpt);
                tvOtpTitle1 = view.findViewById(R.id.tvOtpTitle);
                etNum.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }


                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (etNum.getText().toString().length() > 9) {
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                linOtp.setBackgroundDrawable(ContextCompat.getDrawable(ActivityLogin2.this, R.drawable.rounded_corner_selected));
                                tvOtpTitle1.setTextColor(getResources().getColor(R.color.black));
                            } else {
                                linOtp.setBackground(ContextCompat.getDrawable(ActivityLogin2.this, R.drawable.rounded_corner_selected));
                                tvOtpTitle1.setTextColor(ContextCompat.getColor(ActivityLogin2.this, R.color.black));
                            }
                            linOtp.setPadding(40, 20, 40, 20);
                            mobileNo = etNum.getText().toString();

                        } else {
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                linOtp.setBackgroundDrawable(ContextCompat.getDrawable(ActivityLogin2.this, R.drawable.rounded_corner_unselected));
                                tvOtpTitle1.setTextColor(getResources().getColor(R.color.main_color_grey_900));
                            } else {
                                linOtp.setBackground(ContextCompat.getDrawable(ActivityLogin2.this, R.drawable.rounded_corner_unselected));
                                tvOtpTitle1.setTextColor(ContextCompat.getColor(ActivityLogin2.this, R.color.main_color_grey_900));
                            }

                            linOtp.setPadding(40, 20, 40, 20);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                linOtp.setOnClickListener(v -> {
                    IOUtils.hideKeyBoard(ActivityLogin2.this);
                    mobileNo = etNum.getText().toString();
                    if (mobileNo.length() > 9) {
                        apiService = ApiClient.getClient().create(ApiInterface.class);
                        String res = checkNumberApi();
                    } else {
                        IOUtils.showSnackBar(ActivityLogin2.this, getString(R.string.enter_register_number));
                    }

                    /**/
                });

            }

            if (position == 2) {
                view = layoutInflater.inflate(R.layout.layout_otp_pin, container, false);
                linOtpSelected = view.findViewById(R.id.linOpt);
                tvOtpTitle = view.findViewById(R.id.tvOtpTitle);
                txt_pin_entry = view.findViewById(R.id.txt_pin_entry);
                txt_pin_entry.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (txt_pin_entry.getText().toString().length() > 4) {

                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                linOtpSelected.setBackgroundDrawable(ContextCompat.getDrawable(ActivityLogin2.this, R.drawable.rounded_corner_selected));
                                tvOtpTitle.setTextColor(getResources().getColor(R.color.black));
                            } else {
                                linOtpSelected.setBackground(ContextCompat.getDrawable(ActivityLogin2.this, R.drawable.rounded_corner_selected));
                                tvOtpTitle.setTextColor(ContextCompat.getColor(ActivityLogin2.this, R.color.black));
                            }
                            linOtpSelected.setPadding(40, 20, 40, 20);
                        } else {
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                linOtpSelected.setBackgroundDrawable(ContextCompat.getDrawable(ActivityLogin2.this, R.drawable.rounded_corner_unselected));
                                tvOtpTitle.setTextColor(getResources().getColor(R.color.main_color_grey_900));
                            } else {
                                linOtpSelected.setBackground(ContextCompat.getDrawable(ActivityLogin2.this, R.drawable.rounded_corner_unselected));
                                tvOtpTitle.setTextColor(ContextCompat.getColor(ActivityLogin2.this, R.color.main_color_grey_900));
                            }

                            linOtpSelected.setPadding(40, 20, 40, 20);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                txt_pin_entry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                    @Override
                    public void onPinEntered(CharSequence str) {

                    }
                });
                /*txt_pin_entry.setOnClickListener(v->{
                    IOUtils.showKeyBoard(ActivityLogin2.this);
                });*/

                txt_pin_entry.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        IOUtils.showKeyBoard(ActivityLogin2.this);
                        return false;
                    }
                });
                linOtpSelected.setOnClickListener(v -> {
                    if (txt_pin_entry.getText().toString().length() > 4) {
                        apiCheckOTP(txt_pin_entry.getText().toString());
                    } else {
                        IOUtils.showSnackBar(ActivityLogin2.this, getString(R.string.enterotp));
                        txt_pin_entry.setText(null);
                    }
                });


            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }

    private void apiCheckOTP(String txt_pin_entry) {
        if (IOUtils.isConnected(this)) {
            IOUtils.hideKeyBoard(this);
            //show progress dialog
            IOUtils.startLoadingView(this);


            Call<UserDetailsModel> call = apiService.getUserDetails(mobileNo, txt_pin_entry, "" + token);
            call.enqueue(new Callback<UserDetailsModel>() {
                @Override
                public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                    Log.e(TAG, "onResponse: call " + call.request());
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            IOUtils.stopLoading();
                            Log.e(TAG, "onResponse: user id " + response.body().getShopData().getUserId());
                            BookApp.cache.writeString(ActivityLogin2.this, Constant.USERID, "" + response.body().getShopData().getUserId());
                            BookApp.cache.writeString(ActivityLogin2.this, Constant.USER_NAME, "" + response.body().getShopData().getUserName());
                            BookApp.cache.writeString(ActivityLogin2.this, Constant.MOBILE, "" + response.body().getShopData().getUserMobile());
                            BookApp.cache.writeString(ActivityLogin2.this, Constant.TOKEN, "" + response.body().getShopData().getUserToken());
                            BookApp.cache.writeBoolean(ActivityLogin2.this, Constant.ISLOGIN, true);
                            startActivity(new Intent(ActivityLogin2.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
                        } else {
                            IOUtils.stopLoadingView();
                            IOUtils.showAlertDialog(ActivityLogin2.this, response.body().getMsg(), getString(R.string.msg_contact_admin));// show alert dialog if invalid credential
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(ActivityLogin2.this, getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });
        } else {
            IOUtils.showSnackBar(this, getString(R.string.err_internet));
        }
    }

    private String checkNumberApi() {
        final String[] otp = {""};
        if (IOUtils.isConnected(this)) {
            IOUtils.hideKeyBoard(this);
            //show progress dialog
            IOUtils.startLoadingView(this);


            Call<RoutModel> call = apiService.getAuthriseMobile(mobileNo);
            call.enqueue(new Callback<RoutModel>() {
                @Override
                public void onResponse(Call<RoutModel> call, Response<RoutModel> response) {
                    Log.e(TAG, "onResponse: call " + call.request());
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            IOUtils.stopLoading();
                            Toast.makeText(ActivityLogin2.this, "" + response.body().getOpt(), Toast.LENGTH_SHORT).show();
                            otp[0] = "" + response.body().getOpt();
                            binding.viewPagerVertical.setCurrentItem(2);
                            overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
                            IOUtils.showKeyBoard(ActivityLogin2.this);
                        } else {
                            IOUtils.stopLoadingView();
                            IOUtils.showAlertDialog(ActivityLogin2.this, response.body().getMsg(), getString(R.string.msg_contact_admin));// show alert dialog if invalid credential
                        }
                    }
                }

                @Override
                public void onFailure(Call<RoutModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(ActivityLogin2.this, getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                    t.printStackTrace();
                }
            });
        } else {
            IOUtils.showSnackBar(this, getString(R.string.err_internet));
        }
        return otp[0];
    }

    @Override
    public void onBackPressed() {

        if (binding.viewPagerVertical.getCurrentItem() == 0) {
            super.onBackPressed();
            overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
        }
        if (binding.viewPagerVertical.getCurrentItem() == 1) {
            binding.viewPagerVertical.setCurrentItem(0);
            etNum.setText("");
            overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
        }
        if (binding.viewPagerVertical.getCurrentItem() == 2) {
            //showQuitSignupDialog();
            binding.viewPagerVertical.setCurrentItem(1);
            txt_pin_entry.setText("");
            overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
