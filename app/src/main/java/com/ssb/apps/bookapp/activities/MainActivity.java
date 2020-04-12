package com.ssb.apps.bookapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.fragments.BlankFragment;
import com.ssb.apps.bookapp.fragments.FragmentDashboard;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.DialogAlertUtils;
import com.ssb.apps.bookapp.utils.DialogUniversalErrorUtils;
import com.ssb.apps.bookapp.utils.DialogUniversalSuccessUtils;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DialogAlertUtils.OnAlertDialogClickListener, DialogUniversalErrorUtils.OnErrorBtnClick,
        DialogUniversalSuccessUtils.OnSuccessOkBtnClick, View.OnClickListener {
    private static final String TAG = "MainActivity";
    NavigationView navMenu;
    Bundle mySavedInstanceState;
    private DialogAlertUtils dialogAlertUtils;
    DialogUniversalSuccessUtils dialogUniversalSuccessUtils;
    DialogUniversalErrorUtils dialogUniversalErrorUtils;
    DrawerLayout drawer;
    public Toolbar toolbar;
    ImageView img_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // IOUtils.setStatusBarColor(MainActivity.this);
        toolbar = findViewById(R.id.toolbar);
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
        dialogUniversalSuccessUtils = new DialogUniversalSuccessUtils(MainActivity.this);
        dialogUniversalSuccessUtils.setCallBack(this);
        dialogUniversalErrorUtils = new DialogUniversalErrorUtils(MainActivity.this);
        dialogUniversalErrorUtils.setCallBack(this);
        navMenu.setNavigationItemSelectedListener(this);
        loadFragment(new FragmentDashboard());

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

        }else{
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
        IOUtils.logout(this);
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_right, R.anim.exit_left,R.anim.enter_right, R.anim.exit_left).addToBackStack(null).replace(R.id.frame_container, fragment).commit();
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
    public void OnErrorClick() {
    }

    @Override
    public void OnSuccessClick() {
    }

}
