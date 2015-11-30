package com.kingja.sdmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean("hasLogin", false).commit();

        String getCacheDir = getCacheDir().getAbsolutePath();
        String getFilesDir = getFilesDir().getAbsolutePath();
        String getExternalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String getExternalCacheDir = getExternalCacheDir().getAbsolutePath();
        String getExternalFilesDir = getExternalFilesDir(null).getAbsolutePath();
        TextView tv_1 = (TextView) findViewById(R.id.tv_1);
        TextView tv_2 = (TextView) findViewById(R.id.tv_2);
        TextView tv_3 = (TextView) findViewById(R.id.tv_3);
        TextView tv_4 = (TextView) findViewById(R.id.tv_4);
        TextView tv_5 = (TextView) findViewById(R.id.tv_5);
        TextView tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_1.setText(getCacheDir);
        tv_2.setText(getFilesDir);
        tv_3.setText(getExternalStorageDirectory);
        tv_4.setText(getExternalCacheDir);
        tv_5.setText(getExternalFilesDir);

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            tv_6.setText("有外置SD卡");
        } else {
            tv_6.setText("无外置SD卡");
        }
        /*=================================新建文件===========================================*/
        File file1 = new File(getCacheDir, "getCacheDir()");
        File file2 = new File(getFilesDir, "getFilesDir()");
        File file3 = new File(getExternalStorageDirectory, "getExternalStorageDirectory()");
        File file4 = new File(getExternalCacheDir, "getExternalCacheDir()");
        File file5 = new File(getExternalFilesDir, "getExternalFilesDir(null)");
        File file6 = getDir("myDb", Context.MODE_PRIVATE);
        Log.i(TAG, "getCacheDir: " + file1.mkdirs());
        Log.i(TAG, "getFilesDir: " + file2.mkdirs());
        Log.i(TAG, "getExternalStorageDirectory: " + file3.mkdirs());
        Log.i(TAG, "getExternalCacheDir: " + file4.mkdirs());
        Log.i(TAG, "getExternalFilesDir: " + file5.mkdirs());
        Log.i(TAG, "myDb=: " + file6.getAbsolutePath());
        Log.i(TAG, "myDb: " + file6.mkdirs());
    }

    /**
     * 删除内置cache
     * @param view
     */
    public void onDeleteCache(View view) {
        DeleteManager.deleteLocationCache(this);
    }
    /**
     * 删除内置files
     * @param view
     */
    public void onDeleteFiles(View view) {
        DeleteManager.deleteLocationFiles(this);
    }
    /**
     * 删除SD卡cache
     * @param view
     */
    public void onDeleteSdCache(View view) {
        DeleteManager.deleteSdFiles(this);
    }
    /**
     * 删除SD卡files
     * @param view
     */
    public void onDeleteSdFiles(View view) {
        DeleteManager.deleteSdCache(this);
    }
}
