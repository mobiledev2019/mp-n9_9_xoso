package com.dino.kqxs;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.dino.kqxs.fragments.DashboardFragment;
import com.dino.kqxs.fragments.HomeFragment;
import com.dino.kqxs.fragments.NotificationsFragment;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    public MainActivity() {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_notifications:
                fragment = new NotificationsFragment();
                loadFragment(fragment);
                return true;
        }
        return false;
    }
    };
    }

    //test git
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void loadViewModel(ViewModel viewModel) throws IOException {
        Document doc = Jsoup.connect("https://ketqua.net/").get();
        TextView textview = findViewById(R.id.textViewhtml);
        textview.setText("");
        String html = "";
        for (Element element : doc.select("result_tab_mb")) {
            if (element.hasText()) {
                html = html + element.text();
                break;
            }
        }
        System.out.println(html);
        textview.setText(Html.fromHtml(html));
        //textview.setText(html);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }
}
