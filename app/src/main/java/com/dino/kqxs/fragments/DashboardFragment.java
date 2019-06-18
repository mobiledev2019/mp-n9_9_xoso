package com.dino.kqxs.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dino.kqxs.R;

import java.io.IOException;

public class DashboardFragment extends Fragment {
    private static final String TAG = "CHOITHU";
    private static final String URL = "https://www.minhngoc.net.vn/getkqxs/mien-bac.js/";

    static String text;
    public TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dashboard_fragment, container, false);
        super.onCreate(savedInstanceState);
        new DownloadTask().execute(URL);
        textView = v.findViewById(R.id.textView);
        textView.setText(text);
        return v;
    }

    static class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Document document = null;
            text = "";
            try {
                document = Jsoup.connect(strings[0]).get();
                if (document != null) {
                    Elements kqElements = document.select("div.content > table > tbody > tr");
                    if (kqElements != null && kqElements.size() > 0) {
                        for (Element element : kqElements) {
                            Elements giaikq = element.getElementsByTag("td");
                            if (giaikq != null) {
                                String giai = giaikq.text();
                                text = text + giai + "\n";
                                Log.e(TAG, "doInBackground: " + text);
                            }
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return text;
        }
    }
}
