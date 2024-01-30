package com.example.excercise2;


import android.app.DownloadManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Build;

import android.util.Log;

import androidx.annotation.RequiresApi;

import androidx.fragment.app.FragmentActivity;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.net.URL;

public class DownloadFileSegment implements Runnable {
    private int mFromByte = 0;
    private int mToByte = 0;
    private int mbytesRead = 0;
    private byte[] mFinalBytes = null;
    private Fragment1 mFragment1 = null;
    private Context mContext = null;
    private FragmentActivity mFragmentActivity = null;
    private DownloadManager mDownloadManager = null;
    private long mDownloadID = 0;

    DownloadFileSegment(Fragment1 fragment1, int fromByte, int toByte) {
        mFragment1 = fragment1;
        mContext = mFragment1.getContext();
        mFragmentActivity = mFragment1.getActivity();
        mFromByte = fromByte;
        mToByte = toByte;
        Log.e("From Byte: ", String.valueOf(mFromByte));
        Log.e("To Byte: ", String.valueOf(mToByte));
    }

    @Override
    public void run() {
        try (BufferedInputStream bIS = new BufferedInputStream(new URL("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf").openStream())) {
            byte dataBuffer[] = new byte[mToByte - mFromByte + 1];
            bIS.skip(mFromByte);
            mbytesRead = bIS.read(dataBuffer, 0, mToByte - mFromByte + 1);
            Log.e("bytes of Segment", String.valueOf(mbytesRead));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mFragment1.stitchDownloadsTogether(dataBuffer);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}




