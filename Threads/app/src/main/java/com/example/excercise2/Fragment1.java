package com.example.excercise2;


import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.math.BigInteger;


public class Fragment1 extends Fragment {

    private Button download;
    private TextView TextView;
    private BigInteger urlByteSize = new BigInteger("1");
    private Fragment1 fragment1 = this;
    private static int NumberOfDownloads = 0;
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void stitchDownloadsTogether(byte[] byteArray) throws IOException {
        outputStream.write(byteArray);
        NumberOfDownloads++;

        if (NumberOfDownloads == 4) {
            try {
                File outputFile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)), "test.pdf");
                OutputStream outStream = new FileOutputStream(outputFile, true);
                outputStream.writeTo(outStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                outputStream.close();
            }

        }

    }



        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        download = (Button) view.findViewById(R.id.button);
        download.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                NumberOfDownloads = 0;
                Thread t = new Thread(new DownloadFile(fragment1));
                t.start();
            }
        });
        return view;
    }
}
