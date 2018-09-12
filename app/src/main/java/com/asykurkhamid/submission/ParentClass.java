package com.asykurkhamid.submission;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class ParentClass extends AppCompatActivity {

    private boolean mBackButtonEnable = true;
    private Toolbar toolbar;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void setDefaultToolbar(boolean backButtonEnable) {
        setDefaultToolbar(backButtonEnable, null);
    }


//    public void setMainToolbar(boolean search) {
//        toolbar = findViewById(R.id.tbParent);
//        setSupportActionBar(toolbar);
//        tvTitle = findViewById(R.id.tvTitleMovie);
//        View searchbtn = findViewById(R.id.imgSearch);
//
//        if (search) {
//            searchbtn.setVisibility(View.VISIBLE);
//            searchbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mBackButtonEnable)
//                        finish();
//                }
//            });
//        } else {
//            searchbtn.setVisibility(View.GONE);
//            searchbtn.setOnClickListener(null);
//        }
//    }

    public void setDefaultToolbar(boolean backButtonEnable, String title) {
        toolbar = findViewById(R.id.tbParent);
        setSupportActionBar(toolbar);
        tvTitle = findViewById(R.id.tvTitleToolbar);
        View backButton = findViewById(R.id.imgBack);

        if (TextUtils.isEmpty(title)) {
            tvTitle.setText("");
            tvTitle.setVisibility(View.VISIBLE);

        } else {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }

        if (backButtonEnable) {
            backButton.setVisibility(View.VISIBLE);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mBackButtonEnable)
                        finish();
                }
            });
        } else {
            backButton.setVisibility(View.GONE);
            backButton.setOnClickListener(null);
        }
    }
}
