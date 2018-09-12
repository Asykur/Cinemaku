package com.asykurkhamid.submission.Activity;

import android.os.Bundle;

import com.asykurkhamid.submission.ParentClass;
import com.asykurkhamid.submission.R;

public class AboutActivity extends ParentClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setDefaultToolbar(true,getString(R.string.about));
    }
}
