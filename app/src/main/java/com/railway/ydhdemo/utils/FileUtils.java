package com.railway.ydhdemo.utils;

import android.os.Environment;

import com.railway.ydhdemo.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class FileUtils {

    public static File createFile() {
        File file = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            LogUtils.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ydh.apk");
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ydh.apk");
        } else {
            LogUtils.e(App.getContext().getCacheDir().getAbsolutePath() + "/ydh.apk");
            file = new File(App.getContext().getCacheDir().getAbsolutePath() + "/ydh.apk");
        }
        return file;
    }

    public static void writeFile2Disk(ResponseBody response, File file, FileLoadInterface httpCallBack) {
        long currentLength = 0;
        OutputStream os = null;
        InputStream is = response.byteStream();
        long totalLength = response.contentLength();
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                httpCallBack.onLoading(currentLength, totalLength);
            } // httpCallBack.onLoading(currentLength,totalLength,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeFile2Disk(Response<ResponseBody> response, File file, FileLoadInterface httpCallBack) {
        long currentLength = 0;
        OutputStream os = null;
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                httpCallBack.onLoading(currentLength, totalLength);
            } // httpCallBack.onLoading(currentLength,totalLength,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface FileLoadInterface {
        void onLoading(long current, long total);
    }
}
