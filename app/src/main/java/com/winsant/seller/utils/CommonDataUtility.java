package com.winsant.seller.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.winsant.seller.ui.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Developer on 2/10/2017.
 */

public class CommonDataUtility {

    /**
     * Determine if the device is a tablet (i.e. it has a large screen).
     *
     * @param activity The calling activity.
     */
    public static boolean isTablet(Activity activity) {
        return (activity.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean checkConnection(Activity activity) {
        ConnectivityManager
                cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static Typeface setRobotoMediumTypeFace(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    public static Typeface setRobotoRegularTypeFace(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    public static Typeface setRobotoBoldTypeFace(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    public static Typeface setHelveticaNeueHvTypeFace(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeueHv.ttf");
    }

    public static Typeface setHelveticaNeueMedTypeFace(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeueMed.ttf");
    }

    public static Typeface setHelveticaNeueTypeFace(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeue.ttf");
    }

    public static float convertSpToPixels(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static String getPreferenceString(String key) {
        return MyApplication.getInstance().getPreferenceUtility().getString(key);
    }

    public static void setPreferenceString(String key, String value) {
        MyApplication.getInstance().getPreferenceUtility().setString(key, value);
    }

    public static void clearData() {
        MyApplication.getInstance().getPreferenceUtility().clearData();
    }

    public static void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static Bitmap decodeFile(File f, int WIDTH, int HIGHT) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {

            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                //Read byte from input stream

                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;

                //Write byte from output stream
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

//    public static void setBadgeCount(Context context, MenuItem itemCart, String count) {
//
//        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
//
//        BadgeDrawable badge;
//
//        // Reuse drawable if possible
//        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
//        if (reuse != null && reuse instanceof BadgeDrawable) {
//            badge = (BadgeDrawable) reuse;
//        } else {
//            badge = new BadgeDrawable(context);
//        }
//
//        badge.setCount(count);
//        icon.mutate();
//        icon.setDrawableByLayerId(R.id.ic_badge, badge);
//    }
//
//    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
//        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
//    }
//
//    public static double clamp(double value, double low, double high) {
//        return Math.min(Math.max(value, low), high);
//    }
//
//    public static List<Icon> getIcons() {
//        List<Icon> icons = new ArrayList<>();
//        icons.add(new Icon(R.drawable.ico_wishlist_selected_svg, R.drawable.ico_wishlist_normal_svg, IconType.Heart));
//        return icons;
//    }

    public static Drawable resizeDrawable(Context context, Drawable drawable, int width, int height) {
        Bitmap bitmap = getBitmap(drawable, width, height);
        return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));
    }

    public static Bitmap getBitmap(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawableCompat) {
            return getBitmap((VectorDrawableCompat) drawable, width, height);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable, width, height);
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private static Bitmap getBitmap(VectorDrawableCompat vectorDrawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}

