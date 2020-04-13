package com.ssb.apps.bookapp.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.ActivityLogin2;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.model.RoutModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


/**
 * Created by prateek on 07/05/2018.
 */

public class IOUtils {

    private static final String LOG_TAG = "LOG";
    public static final String SHARED_PREF_NAME = "BookApp";
    private static String TAG = "IOUtils";
    private static ProgressDialog proDialog = null;
    static DecimalFormat df2 = new DecimalFormat(".##");
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    private static List<Address> addresses = new ArrayList<>();
    public static String regexGSTIN = "^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$";


    public static boolean is_marshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void log(String LOG_TAG, String str) {
        if (str.length() > 4000) {
            Log.i(TAG, LOG_TAG + "->" + str.substring(0, 4000));
            log(TAG, str.substring(4000));
        } else
            Log.i(TAG, LOG_TAG + "->" + str);
    }

    public static void log(String str) {
        if (str.length() > 4000) {
            Log.i(TAG, str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i(TAG, str);
    }

    public static String getUniqueDeviceId(Context context) {
        String deviceId = "";
        return deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        return df.format(c.getTime());
    }


    public static <K, V> List<Object> convertHashmapToList(HashMap<K, V> map) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return map.values().stream().collect(Collectors.toList());
        }
        return null;
    }

    public static String getOrderDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }


    public static String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(c.getTime());
    }


    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(c.getTime());
    }

    public static String getCurrentDateForSurvey() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getUniqueId(String key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSS");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return key + Long.parseLong(sdf.format(timestamp));
    }

    @SuppressWarnings("deprecation")
    public static void mySnackBar(Context mContext, String message, View view) {

        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setDuration(Snackbar.LENGTH_SHORT)
                .setActionTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        // Change the text message color
        (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        (snackbar.getView()).getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        View mySbView = snackbar.getView();
        mySbView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.black));


        TextView textView = mySbView.findViewById(com.google.android.material.R.id.snackbar_text);
        // We can apply the property of TextView
        textView.setTextColor(mContext.getResources().getColor(R.color.white));
        textView.setSingleLine(false);
        textView.setTextSize(14f);
        textView.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        snackbar.show();
    }


    @SuppressWarnings("deprecation")
    public static void mySnackBarInternet(Context mContext, View view) {
        Snackbar.make(view, mContext.getString(R.string.msg_internet_connection), Snackbar.LENGTH_LONG)
                .setActionTextColor(mContext.getResources().getColor(R.color.black))
                .show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startLoadingView(Context context) {
        try {
            if (context != null) {
                if (proDialog == null)
                    proDialog = ProgressDialog.show(context, "", "", true, false);

                proDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                //  proDialog.setProgressDrawable(context.getDrawable(R.drawable.progress_drawable));
                proDialog.setContentView(R.layout.progress_loader);
                proDialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLoadingView() {
        try {
            if (proDialog != null)
                proDialog.dismiss();
            proDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startLoadingPleaseWait(Context mContext) {
        try {

            if (mContext != null) {
                if (proDialog == null)
                    proDialog = ProgressDialog.show(mContext, null, mContext.getString(R.string.msg_please_Wait));
                proDialog.setCancelable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startLoading(Context context, String message) {
        try {
            if (context != null) {
                if (proDialog == null)
                    proDialog = ProgressDialog.show(context, null, message);
                proDialog.setCancelable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLoading() {
        try {
            if (proDialog != null)
                proDialog.dismiss();
            proDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInternetPresent(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            @SuppressLint("MissingPermission") NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static String getCurrentDateForRegistration() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        return df.format(c);

    }

    public static boolean isInternetConnectivity(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();

        return networkinfo == null ? false : true;
    }

    public static void hideKeyBoard(Context context, EditText myEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }

    public static void hideKeyBoard(Context context, AutoCompleteTextView autoCompleteTextView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
    }

    public static void hideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (((Activity) context).getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void myToast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void setupUI(View view, final Context mContext) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    IOUtils.hideKeyBoard(mContext);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView, mContext);
            }
        }
    }

    public static String getSHA256Hash(String password, String salt) {
        String data = password.trim() + salt;
        String result = null;
        MessageDigest digest1 = null;
        try {
            digest1 = MessageDigest.getInstance("SHA-256");
            digest1.reset();
            byte[] hash = digest1.digest(data.getBytes(Charset.forName("UTF-16LE")));
            result = android.util.Base64.encodeToString(hash, android.util.Base64.DEFAULT);

            //  Log.e("result", result);

            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String formatStringToDecimalPlaces(Double value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(value);
    }

    private static long removeLastNDigits(long x, long n) {
        return (long) (x / Math.pow(10, n));
    }


    public static boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }


    public static boolean isGSTINValid(String gstin) {


        CharSequence inputStr = gstin;

        Pattern pattern = Pattern.compile(regexGSTIN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }


    public static String getFormattedDateHistory(String input_Date) {
        String output = "";

        if (!input_Date.equals("")) {
            //2018-10-05T00:00:00
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:MM:SS");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = dateFormat.parse(input_Date);
            } catch (ParseException e) {
                e.printStackTrace();
                output = "";
            }
            output = outputFormat.format(date);
        }
        return output;
    }

    public static String getFormattedCustDOB(String input_Date) {
        String output = "";

        if (!input_Date.equals("")) {
            //2018-10-05T00:00:00
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(input_Date);
            } catch (ParseException e) {
                e.printStackTrace();
                output = "";
            }
            output = outputFormat.format(date);
        }
        return output;
    }


    public static String getFormattedDateOrderHistory(String input_Date) {
        String output = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date date = null;
        try {
            date = dateFormat.parse(input_Date);
        } catch (ParseException e) {
            e.printStackTrace();
            output = "";
        }

        output = outputFormat.format(date);
        return output;
    }


    public static String getFormattedInvoiceDateOrderHistory(String input_Date) {
        String output = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(input_Date);
        } catch (ParseException e) {
            e.printStackTrace();
            output = "";
        }

        output = outputFormat.format(date);
        return output;
    }

    public static int getRandomNumber(int max, int min) {
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;
    }


    public static void changeLanguage(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static void setExpandedChild(ExpandableListView expListView, List<?> listDataHeader) {
        for (Object data : listDataHeader) expListView.expandGroup(listDataHeader.indexOf(data));
    }

    public static void loadImage(Context context,ImageView view, String url) {

        Glide.with(context)
                .load(BookApp.cache.readString(context, Constant.URL,"")+"/"+url)
                .error(R.drawable.coverpaeg)
                .into(view);
    }




    public static void setfilter(EditText editText) {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                if (source.equals("")) { // for backspace
                    return source;
                }
                if (source.toString().matches("[a-zA-Z ]+")) {
                    return source;
                }

                if (source.toString().matches(".*\\d+.*")) {
                    Log.e("", true + "");
                }

                return "";
            }
        };

        editText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(50)});

    }

    public static void setGSTINfilter(EditText editText) {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                if (source.equals("")) { // for backspace
                    return source;
                }
                if (source.toString().matches("[a-zA-Z0-9]+")) {
                    return source;
                }
                return "";
            }
        };

        editText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(15)});

    }


    public static void redirectURL(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);

    }

    public static void checkAppHeapMemory() {

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        Log.e("memory", "maxMemory:" + Long.toString(maxMemory));
    }

    public static String loadDataFromAssets(String inFile, Context context) {
        String tContents = "";

        try {
            InputStream stream = context.getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
            tContents = tContents.replace("\n", "").replace("\r", "");
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }


    public static String toCamelCase(String s) {
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts) {
            if (part != null && part.trim().length() > 0)
                camelCaseString = camelCaseString + toProperCase(part);
            else
                camelCaseString = camelCaseString + part + " ";
        }
        return camelCaseString;
    }


    public static double getDiscountPriceByProduct(double price, double discount_ratio) {
        double netPrice = 0.0f;

        netPrice = price - (price * (discount_ratio / 100));

        return netPrice;
    }


    public static String getScreenSize(AppCompatActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return "Width" + " " + width + " " + "height" + " " + height;
    }


    public static String getDeviceDpi(AppCompatActivity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float value = activity.getResources().getDisplayMetrics().density;
        return "Dpi " + value + "  density:" + (activity.getResources().getDisplayMetrics().density);
    }

    public static double getGSTPrice(double price, double gst_percent) {
        double gstPrice = 0.0f;
        gstPrice = price * (gst_percent / 100);
        return gstPrice;
    }

    public static String formatPrice(double value) {
        DecimalFormat formatter;
        if (value <= 99999)
            formatter = new DecimalFormat("###,###,##0.00");
        else
            formatter = new DecimalFormat("#,##,##,###.00");

        return formatter.format(value);
    }


    public static String toProperCase(String s) {
        String temp = s.trim();
        String spaces = "";
        if (temp.length() != s.length()) {
            int startCharIndex = s.charAt(temp.indexOf(0));
            spaces = s.substring(0, startCharIndex);
        }
        temp = temp.substring(0, 1).toUpperCase() +
                spaces + temp.substring(1).toLowerCase() + " ";
        return temp;
    }

    public static String capitalize(String inputString) {
        return inputString.substring(0, 1).toUpperCase() + inputString.substring(1);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Boolean isActivityRunning(Class activityClass, Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
    }

    public static String getAddress(Context context, Location mCurrentLocation) {
        Log.i(LOG_TAG, "getAddress: latitude1------- " + BookApp.lat);
        Log.i(LOG_TAG, "getAddress: longi------- " + BookApp.longi);
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses.size() > 0) {

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            return address;

            //  Toast.makeText(context, "address:" + address + "\ncity:" + city, Toast.LENGTH_SHORT).show();
        }

        return context.getString(R.string.str_error);
    }


    public static boolean checkPermissions(Context context, String permission) {
        int permissionState = ActivityCompat.checkSelfPermission(context,
                /*Manifest.permission.ACCESS_FINE_LOCATION*/permission);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public static String getAddress(Context context) {
        Log.i(LOG_TAG, "getAddress: latitude1------- " + BookApp.lat);
        Log.i(LOG_TAG, "getAddress: longi------- " + BookApp.longi);
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(BookApp.lat, BookApp.longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses.size() > 0) {

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            return address;

            //  Toast.makeText(context, "address:" + address + "\ncity:" + city, Toast.LENGTH_SHORT).show();
        }

        return context.getString(R.string.str_error);
    }

    public static boolean isEmailAgreeta(String email) {
        return email.contains("@agreeta.com");
    }




    public static void showTreatmentCalendarView(Context BASE_CONTEXT, final TextView imageButton, String strStartDate) {
        final String[] selected_date = new String[1];
        final String[] display_date = new String[1];
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(BASE_CONTEXT, R.style.AppThemeDialog,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker,
                                          int selectedyear, int selectedmonth, int selectedday) {

                        selectedmonth = selectedmonth + 1;
                        if (selectedday < 10) {
                            selected_date[0] = selectedyear + "/" + selectedmonth + "/" + "0" + selectedday;
                            display_date[0] = "0" + selectedday + "/" + selectedmonth + "/" + selectedyear;
                        }
                        if (selectedmonth < 10) {
                            selected_date[0] = selectedyear + "/" + "0" + selectedmonth + "/" + selectedday;
                            display_date[0] = selectedday + "/" + "0" + selectedmonth + "/"
                                    + selectedyear;
                        }
                        if (selectedmonth < 10 && selectedday < 10) {
                            selected_date[0] = selectedyear + "/0" + selectedmonth + "/0" + selectedday;
                            display_date[0] = "0" + selectedday + "/0"
                                    + selectedmonth + "/" + selectedyear;
                        }
                        if (selectedmonth > 9 && selectedday > 9) {
                            selected_date[0] = selectedyear + "/" + selectedmonth + "/" + selectedday;
                            display_date[0] = "" + selectedday + "/" + selectedmonth + "/" + selectedyear;
                        }
                        imageButton.setText(display_date[0].trim());
                    }
                }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (strStartDate != null) {
            try {
                Date date = df.parse(strStartDate);
                mDatePicker.getDatePicker().setMinDate(date.getTime());// TODO: used to hide previous date,month and year
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        mDatePicker.show();

        mDatePicker.getDatePicker().setMaxDate(new Date().getTime());

        mDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE, BASE_CONTEXT.getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    imageButton.setText("");
                }
            }
        });
    }





    public static String dateConversion(String fromDateFormat, String toDateFormat, String convertDate) {
        String date = convertDate;
        try {

            SimpleDateFormat spf = new SimpleDateFormat(fromDateFormat);
            Date newDate = spf.parse(date);
            spf = new SimpleDateFormat(toDateFormat);
            date = spf.format(newDate);
            System.out.println(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }










    public static boolean compareDate2(String date_1, String date_2, boolean flagEqualOrGreater, SimpleDateFormat formatter1) {
        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Date date1 = formatter.parse(date_1);

            Date date2 = formatter.parse(date_2);

            if (flagEqualOrGreater) {
                return date2.compareTo(date1) >= 0;
            } else {
                return date2.compareTo(date1) <= 0;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    public static boolean checkNotNullEmpty(String param1) {
        return (param1 != null && !param1.isEmpty());
    }


    public static void setStatusBarColor(Activity activity) {
        Window window = activity.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        }
    }

    public static void logout(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        prefsEditor.apply();
        BookApp.cache.clearAllPreference(context);

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, ActivityLogin2.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static void showSnackBar(Activity context, String msg) {
        //CommonUtils.hideKeyboard(context);
        View parentLayout = context.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.darkgreen));
        snackbar.show();
    }
    public static void errorShowSnackBar(Activity context, String msg) {
        //CommonUtils.hideKeyboard(context);
        View parentLayout = context.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.red));
        snackbar.show();
    }

    /**
     * Alert message
     *
     * @param mContext
     * @param title
     * @param message
     */
    public static void showAlertDialog(Context mContext, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(mContext.getString(R.string.str_ok), (dialog, which) -> {
                    // continue with delete
                    dialog.cancel();
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        dialog.show();

        //.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
    }

    public static String getAppVersion(Context context) {
        String version = null;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    public static void showKeyBoard(Context context ) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
}
