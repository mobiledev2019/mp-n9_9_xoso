package com.dino.kqxs.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;

import com.dino.kqxs.R;

import java.io.IOException;

public class DashboardFragment extends Fragment {
    private static final String TAG = "CHOITHU";
    private static final String URL = "https://www.minhngoc.net.vn/getkqxs/mien-bac.js/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadTask().execute(URL);
    }

    static class DownloadTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            Document document = null;
            try {
                document = Jsoup.connect(strings[0]).get();
                if (document != null) {
                    Elements kqElements = document.select("div.content > table > tbody > tr");
                    if (kqElements != null && kqElements.size() > 0) {
                        for (Element element : kqElements) {
                            Element giaikq = element.getElementsByTag("td").first();
                            Element sokq = element.getElementsByTag("td").first();
                            if (giaikq != null) {
                                String giai = giaikq.text();
                                Log.e(TAG, "doInBackground: " + giai);
                            }

                            if (sokq != null) {
                                String so = sokq.text();
                                Log.e(TAG, "doInBackground: " + so);
                            }
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
