package com.example.excercise2;

import android.content.Context;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFile implements Runnable {
    private Fragment1 mFragment = null;
    private Context mContext = null;
    private FragmentActivity mFragmentActivity = null;

    DownloadFile(Fragment1 fragment){
        mFragment = fragment;
        mContext = fragment.getContext();
        mFragmentActivity = fragment.getActivity();
    }

    public void run() {
        int numberOfThreads = 4;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url1 = new URL("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            int totalLength = httpURLConnection.getContentLength();
            Log.e("Total Length: ", String.valueOf(totalLength));
            int remainder = totalLength % numberOfThreads;
            Log.e("Remainder: ", String.valueOf(remainder));
            int lengthPerThread = totalLength / numberOfThreads;
            Log.e("Length Per Thread: ", String.valueOf(lengthPerThread));
            int mMaxLengthOfFragment = lengthPerThread + remainder;
            Log.e("Max Length Per Thread", String.valueOf(mMaxLengthOfFragment));
            httpURLConnection.getInputStream().close();

            for (int i = 0; i < numberOfThreads; i++) {
                Thread t;
                DownloadFileSegment dfs;
                if (i < (numberOfThreads - 1)) {
                    t = new Thread(new DownloadFileSegment(mFragment, lengthPerThread * i, (lengthPerThread * i) + (lengthPerThread - 1)));
                } else {
                    t = new Thread(new DownloadFileSegment(mFragment, lengthPerThread * i, (lengthPerThread * i) + (lengthPerThread - 1) + remainder));
                }
                t.start();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
