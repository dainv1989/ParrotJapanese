package com.dainv.parrotjapanese;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dainv on 17/07/14.
 */

public class AboutActivity extends AppCompatActivity {

    private ImageView imgShare;
    private ImageView imgVote;
    private ImageView imgHKApp;

    private TextView tvAppVersion;
    private String strAppVersion = "2.0";

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        imgShare = (ImageView)findViewById(R.id.imgAboutShare);
        imgVote = (ImageView)findViewById(R.id.imgAboutVote);
        imgHKApp = (ImageView)findViewById(R.id.imgHKApp);
        tvAppVersion = (TextView)findViewById(R.id.txtAboutVersion);

        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            strAppVersion = info.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        tvAppVersion.setText("version " + strAppVersion);

        imgHKApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * open Google Play market to rate app
                 * or start web browser if Google Play app is not available
                 */
                Uri uri = Uri.parse("market://details?id=com.dainv.hiragana");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e) {
                    Uri webUri = Uri.parse("http://play.google.com/store/apps/details?id=com.dainv.hiragana");
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, webUri);
                    startActivity(intentWeb);
                }
            }
        });

        imgVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * open Google Play market to rate app
                 * or start web browser if Google Play app is not available
                 */
                Uri uri = Uri.parse("market://details?id=" +
                        getApplicationContext().getPackageName());
                Intent intentMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intentMarket);
                }
                catch (ActivityNotFoundException e) {
                    Uri webUri = Uri.parse("http://play.google.com/store/apps/details?id=" +
                            getApplicationContext().getPackageName());
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, webUri);
                    startActivity(intentWeb);
                }
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = context.getResources().getString(R.string.share_content);
                String appUrl = " http://play.google.com/store/apps/details?id=" +
                        context.getPackageName();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/html");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText + appUrl);

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }
}
