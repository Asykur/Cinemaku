package com.asykurkhamid.submission.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asykurkhamid.submission.Adapter.SectionsPageAdapter;
import com.asykurkhamid.submission.Fragment.NowPlayingFragment;
import com.asykurkhamid.submission.Fragment.UpComingFragment;
import com.asykurkhamid.submission.ParentClass;
import com.asykurkhamid.submission.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ParentClass {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchtoolbars)
    Toolbar searchtollbar;
    @BindView(R.id.viewPagerMain)
    ViewPager viewPager;
    @BindView(R.id.tabMain)
    TabLayout tabMain;
    List<SearchListener> searchInterFace = new ArrayList<>();
    List<LoadDataListener> loadDataInterface = new ArrayList<>();

    private Menu search_menu;
    private MenuItem item_search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setSearchtollbar();
        setupViewPager(viewPager);
        tabMain.setupWithViewPager(viewPager);

    }

    SectionsPageAdapter mainAdapter = null;
    private void setupViewPager(ViewPager viewPager) {
        mainAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
        searchInterFace.add(nowPlayingFragment.getSearchIntance());
        loadDataInterface.add(nowPlayingFragment.getLoadDataInstance());
        mainAdapter.addFragment(nowPlayingFragment, getString(R.string.now_playing));

        UpComingFragment upComingFragment = new UpComingFragment();
        searchInterFace.add(upComingFragment.getSearchInstance());
        loadDataInterface.add(upComingFragment.getLoadDataInstance());
        mainAdapter.addFragment(upComingFragment, getString(R.string.up_coming));

        viewPager.setAdapter(mainAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabMain.setVisibility(View.VISIBLE);
                        break;
                    default:
                        tabMain.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //  inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //  set onclick masing-masing menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    circleReveal(R.id.searchtoolbars, 1, true, true);
                } else {
                    searchtollbar.setVisibility(View.VISIBLE);
                }
                item_search.expandActionView();
                break;

            case R.id.menuFavorite:
                Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
                startActivity(intent);
                break;

            case R.id.menuCall:
                String phoneNumber = getString(R.string.nope_ku);
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(phoneIntent);
                break;

            case R.id.menuSetting:
                Intent intentSet = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentSet);
                break;

            case R.id.menuAbout:
                Intent intentSetting = new Intent(this, AboutActivity.class);
                startActivity(intentSetting);
                break;

            default:
        }
        return super.onOptionsItemSelected(item);

    }

    public void setSearchtollbar() {
        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.search_menu);
            search_menu = searchtollbar.getMenu();
            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.searchtoolbars, 1, true, false);
                    else
                        searchtollbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);
            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbars, 1, true, false);
                    } else
                        searchtollbar.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });

            initSearchView();

        } else {
            Log.d("toolbar", "setSearchtollbar: NULL");
        }
    }

    public void initSearchView() {
        final SearchView searchView = (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard
        searchView.setSubmitButtonEnabled(false);

        // Change search close button image
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_close);

        // set hint and the text colors
        EditText txtSearch = (searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search..");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (viewPager != null && mainAdapter != null) {
                    searchInterFace.get(viewPager.getCurrentItem()).doSearch(editable.toString());
                } else {
                    Log.e("SEARCH",  "NULL OBJECT");
                }
            }
        });


        // set the cursor
        AutoCompleteTextView searchTextView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);
            }

        });
    }

    // circle animation
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(final int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        final View myView = findViewById(viewID);
        int width = myView.getWidth();
        if (posFromRight > 0)
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);
        int cx = width;
        int cy = myView.getHeight() / 2;
        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);
        anim.setDuration((long) 250);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                    if (viewPager != null && mainAdapter != null) {
                        loadDataInterface.get(viewPager.getCurrentItem()).loadData();
                    } else {
                        Log.e("SEARCH",  "NULL OBJECT");
                    }

                }
            }
        });

        // make the view visible and start the animation
        if (isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();
    }

    boolean pressedOnce = false;

    @Override
    public void onBackPressed() {
        if (pressedOnce) {
            super.onBackPressed();
            return;
        }
        this.pressedOnce = true;
        Toast.makeText(this, R.string.tap_again, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pressedOnce = false;
            }
        }, 2000);
    }

}
