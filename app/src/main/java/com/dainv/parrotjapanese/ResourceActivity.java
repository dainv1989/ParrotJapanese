package com.dainv.parrotjapanese;

import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResourceActivity extends AppCompatActivity {
    private String[] resLogo;
    private String[] resTitle;
    private String[] resDesc;
    private String[] resSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        /* set toolbar title */
        Resources res = getResources();
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setTitle(res.getString(R.string.str_btnResource));

        resLogo = res.getStringArray(R.array.res_logo_name);
        resTitle = res.getStringArray(R.array.res_title);
        resDesc = res.getStringArray(R.array.res_desc);
        resSite = res.getStringArray(R.array.res_site);

        LinearLayout resLayout[] = new LinearLayout[resTitle.length];
        TextView tvResTitle;
        TextView tvResDesc;
        TextView tvResSite;
        ImageView ivResLogo;
        int resId = 0;
        int resLogoId = 0;
        String packageName = getPackageName();

        for (int i = 0; i < resTitle.length; i++) {
            resId = res.getIdentifier("resource" + i, "id", packageName);
            if (resId > 0) {
                resLayout[i] = (LinearLayout)findViewById(resId);
                tvResTitle = (TextView)resLayout[i].findViewById(R.id.res_title);
                tvResDesc = (TextView)resLayout[i].findViewById(R.id.res_desc);
                tvResSite = (TextView)resLayout[i].findViewById(R.id.res_site);
                ivResLogo = (ImageView)resLayout[i].findViewById(R.id.res_logo);

                resLogoId = res.getIdentifier(resLogo[i], "drawable", packageName);
                if (resLogoId > 0) {
                    ivResLogo.setImageResource(resLogoId);
                } else {
                    ivResLogo.setImageResource(R.drawable.parrot);
                }
                tvResTitle.setText(resTitle[i]);
                tvResDesc.setText(resDesc[i]);
                tvResSite.setText("site: " + resSite[i]);
            }
        }
    }
}
