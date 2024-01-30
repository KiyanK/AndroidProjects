package com.example.excercise2;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;

import android.app.DownloadManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;

import android.webkit.CookieManager;
import android.webkit.URLUtil;

import java.io.IOException;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new Fragment1());
        fragmentTransaction.commit();
    }

    public void downloadFile(View view) throws IOException {
        URL url1 = new URL("https://uwaterloo.ca/onbase/sites/ca.onbase/files/uploads/files/sampleunsecuredpdf.pdf");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(String.valueOf(url1)));
        String title = URLUtil.guessFileName(String.valueOf(url1), null, null);
        request.setTitle(title);
        request.setDescription("Downloading file please wait...");
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, title);
        String cookie = CookieManager.getInstance().getCookie(String.valueOf(url1));
        request.addRequestHeader("cookie", cookie);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }
}
