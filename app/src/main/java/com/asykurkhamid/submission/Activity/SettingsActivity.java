package com.asykurkhamid.submission.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.asykurkhamid.submission.ParentClass;
import com.asykurkhamid.submission.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends ParentClass {

    @BindView(R.id.tvLanguage)
    TextView tvLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setDefaultToolbar(true,getString(R.string.setting));

        tvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });
    }
}
