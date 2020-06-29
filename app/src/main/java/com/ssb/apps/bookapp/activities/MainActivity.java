package com.ssb.apps.bookapp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.ActivityMainBinding;
import com.ssb.apps.bookapp.fragments.BlankFragment;
import com.ssb.apps.bookapp.fragments.FragmentBucket;
import com.ssb.apps.bookapp.fragments.FragmentDashboard;
import com.ssb.apps.bookapp.fragments.FragmentProfile;
import com.ssb.apps.bookapp.model.BookInfoModel;
import com.ssb.apps.bookapp.model.StatusModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.DialogAlertUtils;
import com.ssb.apps.bookapp.utils.IOUtils;

import org.json.JSONObject;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DialogAlertUtils.OnAlertDialogClickListener, View.OnClickListener, PaymentResultListener {
    private static final String TAG = "MainActivity";
    private BottomSheetBehavior sheetBehavior;
    ActivityMainBinding binding;
    NavigationView navMenu;
    Bundle mySavedInstanceState;
    private DialogAlertUtils dialogAlertUtils;
    DrawerLayout drawer;
    public Toolbar toolbar;
    ImageView img_notification;
    AppBarLayout appBarLayout;
    ApiInterface apiService;
    public TextView tv_writer;
    public ImageView userPic;
    public static AutoCompleteTextView searchBox;
    public RelativeLayout relSearch;
    BookInfoModel data;
    String key;
    ImageView item1,item2,item3,item4;
    final int sdk = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        // IOUtils.setStatusBarColor(MainActivity.this);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        setSupportActionBar(toolbar);
        navMenu = findViewById(R.id.nav_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mySavedInstanceState = savedInstanceState;
        dialogAlertUtils = new DialogAlertUtils(this);
        dialogAlertUtils.setCallBack(this);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_drawer);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.et_user_name);

        nav_user.setText(BookApp.cache.readString(MainActivity.this, Constant.USER_NAME, ""));


        // Toolbar :: Transparent
        relSearch = toolbar.findViewById(R.id.relSearch);
        searchBox = toolbar.findViewById(R.id.search_box);
        userPic = toolbar.findViewById(R.id.my_custom_icon);
        tv_writer = toolbar.findViewById(R.id.tv_register);


        navMenu.setNavigationItemSelectedListener(this);
        loadFragment(new FragmentDashboard());
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("razoer_pay_call"));

        sheetBehavior = BottomSheetBehavior.from(binding.appbar.bottomSheet);
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (80 * scale + 0.5f);
        sheetBehavior.setPeekHeight(pixels);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        binding.appbar.linMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //btn_bottom_sheet.setText("Close sheet");
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    //  btn_bottom_sheet.setText("Expand sheet");
                }
            }
        });
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                binding.appbar.imageArrow.setRotation(slideOffset * 180);
                // CommonUtils.runAnimationAgain(getActivity(),binding.secondary.rvUpdates);
            }
        });

    }

    /*@SuppressLint("ResourceAsColor")
    private void checkToggle() {
        if(tapBarMenu.isOpened()){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                tapBarMenu.setBackgroundColor(R.color.colorAccent);
        }else{
                tapBarMenu.setBackgroundColor(R.color.black);
            }
            *//*if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                tapBarMenu.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.color.colorAccent) );
            } else {
                tapBarMenu.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorAccent));
            }*//*
        }
    }*/

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            data = (BookInfoModel) intent.getSerializableExtra("data");
            key = intent.getStringExtra("key");
            startPayment();

        }
    };


    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();
        co.setKeyID(key);
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "" + data.getBookInfo().getBookInfoData().getBookName());
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");

            options.put("amount", "" + data.getBookInfo().getBookInfoData().getBookPrice() + "00");

            JSONObject preFill = new JSONObject();
            preFill.put("email",
                    BookApp.cache.readString(activity, Constant.EMAIL, "").isEmpty() ?
                            "test@razorpay.com" : BookApp.cache.readString(activity, Constant.EMAIL, ""));
            preFill.put("contact", BookApp.cache.readString(activity, Constant.MOBILE, "").isEmpty() ?
                    "" : BookApp.cache.readString(activity, Constant.MOBILE, ""));

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "startPayment: error " + e.getMessage());
            IOUtils.showAlertDialog(this, getString(R.string.error_message), "" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            showCloseAlert();
        }
    }

    private void showCloseAlert() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.str_exit))
                .setMessage(getString(R.string.str_are_sure))
                .setPositiveButton(getString(R.string.str_yes),
                        (dialog, which) -> {
                            // Perform Action & Dismiss dialog
                            dialog.dismiss();
                            finish();
                        }).setNegativeButton(getString(R.string.str_no), (dialog, which) -> {
            // Do nothing
            dialog.dismiss();
        }).create().show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_ic_logout) {
            dialogAlertUtils.showDialog(getString(R.string.nav_menu_logout), getString(R.string.app_logout));
        } else if (id == R.id.menu_dashboard) {
            loadFragment(new FragmentDashboard());
        } else if (id == R.id.menu_ic_bucket) {
            loadFragment(new FragmentBucket());
        } else if (id == R.id.menu_profile) {
            loadFragment(new FragmentProfile());
        } else {
            loadFragment(new BlankFragment());
        }

        if (id != R.id.menu_ic_logout)
            setTitleText(item.getTitle().toString());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnYesClick() {
        //Log.e(TAG, "onClick: session logout");
        if (BookApp.cache.readBoolean(this, Constant.ISLOGINFB, false))
            LoginManager.getInstance().logOut();

        IOUtils.logout(this);
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_right, R.anim.exit_left).addToBackStack(null).replace(R.id.frame_container, fragment).commit();
    }

    public void loadFragment(Fragment fragment, Fragment currentFragment) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, fragment).remove(currentFragment).commit();
    }

    public void setTitleText(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title.toUpperCase());
        }
    }


    @Override
    public void OnNoClick() {
        dialogAlertUtils.dismissDialog();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.e(TAG, "onPaymentSuccess: sucess " + s);
        paymentCall(s);
        // IOUtils.showAlertDialog(this, getString(R.string.str_success), this.getString(R.string.str_success_book_purchase));

    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG, "onPaymentError: error " + s);
        IOUtils.showAlertDialog(this, this.getString(R.string.error_message), this.getString(R.string.str_fail));

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("razoer_pay_call"));
    }

    private void paymentCall(String trnas) {
        if (IOUtils.isConnected(MainActivity.this)) {
            IOUtils.hideKeyBoard(MainActivity.this);
            //show progress dialog
            IOUtils.startLoadingView(MainActivity.this);

            Call<StatusModel> call = apiService.callPurchaseAPI(BookApp.cache.readString(MainActivity.this, Constant.USERID, ""),
                    BookApp.cache.readString(MainActivity.this, Constant.TOKEN, ""), "1",
                    data.getBookInfo().getBookInfoData().getBookId(), trnas);
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            data.getBookInfo().getBookInfoData().setHasUserPaid(true);
                            IOUtils.showSnackBar(MainActivity.this, "" + response.body().getMsg());
                            Intent intent = new Intent("book_parchaes");
                            intent.putExtra("data", data);
                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(MainActivity.this);
                            } else {
                                IOUtils.showAlertDialog(MainActivity.this, getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<StatusModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(MainActivity.this, getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(MainActivity.this, getString(R.string.err_internet));

    }
}
