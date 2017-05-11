package com.dainv.parrotjapanese.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.parrotjapanese.R;

import java.util.List;

/**
 * Created by dainv on 11/27/2015.
 */
public class AboutDialog extends DialogFragment {
    private final static String TAG = "AboutDialog";
    private final static String FB_APP_PACKAGE = "com.facebook.katana";
    private final static String GMAIL_APP_PACKAGE = "com.google.android.gm";
    private final static String TW_APP_PACKGE = "com.twitter.android";

    private Activity activity;
    private String versionName;

    private ImageView fbImg;
    private ImageView gmailImg;
    private ImageView twImg;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        activity = getActivity();
        versionName = "1.0.0";
        LayoutInflater inflater = activity.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_about, null);
        TextView title = (TextView)view.findViewById(R.id.abt_title);
        TextView msg = (TextView)view.findViewById(R.id.abt_message);

        fbImg = (ImageView)view.findViewById(R.id.abt_fb);
        gmailImg = (ImageView)view.findViewById(R.id.abt_gmail);
        twImg = (ImageView)view.findViewById(R.id.abt_tw);

        try {
            PackageManager manager = activity.getPackageManager();
            final PackageInfo packageInfo = manager.getPackageInfo(activity.getPackageName(), 0);
            versionName = packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException exp) {
            Log.v("AboutDialog", "EXCEPTION: " + exp.toString());
            exp.printStackTrace();
        }

        title.setText(R.string.app_name);
        msg.setText("version " + versionName);

        final String pkgURL;
        pkgURL = "http://play.google.com/store/apps/details?id=" + activity.getPackageName();
        final PackageManager pm = activity.getPackageManager();

        /** share via facebook */
        fbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** start sharing facebook intent */
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, pkgURL);

                boolean fbAppFound = false;
                List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                for (ResolveInfo info: matches) {
                    /**
                     * "com.facebook.katana" is the package name for Facebook app
                     * and "com.facebook.orca" is for FB Messenger app.
                     */
                    if (info.activityInfo.packageName.toLowerCase().startsWith(FB_APP_PACKAGE)) {
                        intent.setPackage(info.activityInfo.packageName);
                        fbAppFound = true;
                        break;
                    }
                }

                /**
                 * Facebook app is not found.
                 * Launch sharer from web browser
                 */
                if (!fbAppFound) {
                    String shareURL = "https://www.facebook.com/sharer/sharer.php?u=" + pkgURL;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shareURL));
                }
                try {
                    startActivity(intent);
                } catch (RuntimeException exp) {
                    Log.v(TAG, exp.toString());
                }
            }
        });

        /** share via Gmail */
        gmailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** start sharing Gmail intent */
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Get this app at following URL: " + pkgURL);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Learning Japanese application");

                boolean gmailAppFound = false;
                List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                for (ResolveInfo info: matches) {
                    /**
                     * "com.facebook.katana" is the package name for Facebook app
                     * and "com.facebook.orca" is for FB Messenger app.
                     */
                    if (info.activityInfo.packageName.contentEquals(GMAIL_APP_PACKAGE)) {
                        intent.setPackage(info.activityInfo.packageName);
                        final ActivityInfo activityInfo = info.activityInfo;
                        final ComponentName name = new ComponentName(
                                activityInfo.applicationInfo.packageName, activityInfo.name);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        intent.setComponent(name);
                        gmailAppFound = true;
                        break;
                    }
                }

                if (!gmailAppFound) {
                    String url = "https://mail.google.com/";
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                }
                startActivity(intent);
            }
        });

        twImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** start sharing Twitter intent */
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, pkgURL);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Learning Japanese application");

                PackageManager pm = v.getContext().getPackageManager();
                List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                for (final ResolveInfo app : matches)
                {
                    if (app.activityInfo.packageName.contentEquals(TW_APP_PACKGE))
                    {
                        final ActivityInfo activityInfo = app.activityInfo;
                        final ComponentName name = new ComponentName(
                                activityInfo.applicationInfo.packageName, activityInfo.name);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        intent.setComponent(name);
                        break;
                    } else {
                        String twURL = "https://twitter.com/";
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twURL));
                    }
                    startActivity(intent);
                }
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
