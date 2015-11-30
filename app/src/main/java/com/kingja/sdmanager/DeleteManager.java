package com.kingja.sdmanager;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * 数据清理工具类
 */
public class DeleteManager {
/*=========================================内置存储===========================================*/

    /**
     * 清理内置存储(/data/data/[packagename]/cache)
     *
     * @param context
     */
    public static void deleteLocationCache(Context context) {
        deleteFilesByFile(context.getCacheDir(), false);
    }

    /**
     * 清理内置存储(/data/data/[packagename]/files)
     *
     * @param context
     */
    public static void deleteLocationFiles(Context context) {
        deleteFilesByFile(context.getFilesDir(), false);
    }

    /**
     * 清理内置存储(/data/data/[packagename]/databases)
     *
     * @param context
     */
    public static void deleteLocationDb(Context context) {
        deleteFilesByFile(new File("/data/data/"
                + context.getPackageName() + "/databases"), false);
    }

    /**
     * 清理内置存储(/data/data/[packagename]/shared_prefs)
     *
     * @param context
     */
    public static void deleteLocationSp(Context context) {
        deleteFilesByFile(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"), false);
    }

    /**
     * 清理指定文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        deleteFilesByFile(file, false);
    }
    /*=========================================SD卡存储===========================================*/


    /**
     * * 清理外置SD卡cache下的内容(/mnt/sdcard/android/data/[packagename]/cache)
     *
     * @param context
     */
    public static void deleteSdCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByFile(context.getExternalCacheDir(), false);
        }
    }

    /**
     * * 清理外置SD卡files下的内容(/mnt/sdcard/android/data/[packagename]/files)
     *
     * @param context
     */
    public static void deleteSdFiles(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByFile(context.getExternalFilesDir(null), false);
        }
    }


    /**
     * * 清理本应用所有的数据 * *
     *
     * @param context
     * @param files
     */
    public static void deleteAllData(Context context, File... files) {
        deleteLocationCache(context);
        deleteLocationFiles(context);
        deleteLocationDb(context);
        deleteLocationSp(context);
        deleteSdFiles(context);
        deleteSdCache(context);
        if (files == null) {
            return;
        }
        for (File file : files) {
            deleteFilesByFile(file, false);
        }
    }

    /**
     * 删除指定目录下的文件及文件夹(包括根目录)
     *
     * @param cacheFile
     * @param ifDeleteRootFile 是否删除根目录
     */
    private static void deleteFilesByFile(File cacheFile,
                                          boolean ifDeleteRootFile) {
        File[] files = cacheFile.listFiles();
        if (files.length > 0) {
            // 有文件或文件夹
            for (File file : files) {
                if (file.isDirectory() && file.listFiles().length > 0) {
                    // 是文件夹且下面还有目录
                    deleteFilesByFile(file, false);
                } else {
                    // 是文件或空文件夹
                    file.delete();
                }
            }
        }
        if (ifDeleteRootFile) {
            cacheFile.delete();
        }

    }

    /**
     * 获取文件大小字节
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return Math.round(size) + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }

}