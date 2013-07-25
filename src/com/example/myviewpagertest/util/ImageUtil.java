package com.example.myviewpagertest.util;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

public class ImageUtil {
	static String Image_CACHE_PATH =  "MyViewPager/.cache";// �����ļ�Ŀ¼
	
	private static ImageUtil instance;

    private String localImageFolder;

    private ImageUtil() {
        File path = getCachePath();
        if (path == null) {
            return;
        }
        this.localImageFolder = path.toString() + File.separator;
    }
    
    public static synchronized ImageUtil getInstance() {
        if (instance == null) {
            instance = new ImageUtil();
        }
        return instance;
    }
    
    /**
     * ��ʾͼƬ
     * 
     * @param activity
     * @param handler
     * @param view
     * @param imageUrl
     */
    public void setImage(Activity activity, Handler handler, ImageView view, String imageUrl) {
        if (imageUrl == null || imageUrl.length() == 0) {
            return;
        }
        Bitmap bitmap = null;
        SoftReference<Bitmap> softObject = null;
        String savePath = localImageFolder + URLEncoder.encode(imageUrl.trim());
        ImageCache instance = ImageCache.getInstance();
        if (instance != null) {// �ӻ����ȡͼƬ
            softObject = instance.get(savePath);
        }

        if (softObject != null) {
            bitmap = softObject.get();
        }
        if (bitmap == null) {// ����û�У��ʹӱ��ػ������ȡ
                ImageThreadPool.execute(new DownloadRunnable(activity, handler, view, imageUrl, savePath));
        } else {
            view.setImageBitmap(bitmap);
        }
    }
    
	 /**
     * ��ȡͼƬ�����ļ���
     */
    public static File getCachePath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
            File file = new File(sdDir + File.separator + Image_CACHE_PATH);
            if (!file.isDirectory()) { // Ŀ¼������
                boolean ret = file.mkdirs(); // ����Ŀ¼
                if (!ret) {
                    return null;
                }
            }
            return file;
        } else {
            return null;
        }

    }
    
    private class DownloadRunnable implements Runnable {

        private Activity activity;
        private Handler handler;
        private ImageView view;
        private String imageUrl;
        private String savePath;
        private int resId;

        public DownloadRunnable(Activity activity, Handler handler, ImageView view, String imageUrl, String savePath) {
            this.activity = activity;
            this.handler = handler;
            this.view = view;
            this.imageUrl = imageUrl;
            this.savePath = savePath;
        }

        @Override
        public void run() {
			Bitmap bitmap = null;
			//���ؼ���
			try {
				if ((new File(savePath)).exists()) {
					bitmap = BitmapFactory.decodeFile(savePath);
				}
			} catch (Exception e) {
				// Console.e(TAG, "���ر���ͼƬ�ڴ����" + e.getMessage());
				return;
			} catch (OutOfMemoryError e) {
				// Console.e(TAG, "���ر���ͼƬ�ڴ����" + e.getMessage());
				return;
			}

			if (bitmap != null) {// �����д�ͼƬ, ����Ҫ���غʹ洢
				savePath = null;// ��Ϊ����Ҫ�洢����filePath��Ҫ����Ϊ��
				refreshUI(activity, view, bitmap, savePath);
				
			} else {// ͨ����·�첽����ͼƬ
				bitmap = download(imageUrl, handler);
				if (bitmap == null) {
					File file = new File(savePath);
					if (file.exists()) {
						file.delete();
					}
					if (resId > 0) {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								view.setImageResource(resId);
							}
						});
					}
					return;
				}
				refreshUI(activity, view, bitmap, savePath);
			}
		}

    }

    // 1 ˢ��UI
    // 2 �洢ͼƬ������
    private void refreshUI(Activity activity, final ImageView view, final Bitmap bitmap, String savePath) {
        if (bitmap != null) {
            ImageCache instance = ImageCache.getInstance();
            if (instance != null) {
                instance.put(savePath, new SoftReference<Bitmap>(bitmap));
            }
            
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                        view.setImageBitmap(bitmap);          
                }
            });
            if (savePath != null) {
                saveBitmap(bitmap, savePath);
            }
        } 
    }
    
    private Bitmap download(String imageUrl, Handler handler) {
        imageUrl = imageUrl.trim();
        URL m;
        InputStream i = null;
        Bitmap d = null;
        HttpGet httpRequest = null;
        try {          
                m = new URL(imageUrl);
	            try {
	                httpRequest = new HttpGet(m.toURI());
	            } catch (URISyntaxException e) {
	                
	            }
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
	
	            HttpEntity entity = response.getEntity();
	            // �ƺ��ؼ�������һ��
	            BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
	            InputStream instream = bufHttpEntity.getContent();
	            d = BitmapFactory.decodeStream(instream);
        } catch (OutOfMemoryError e) {
        } catch (Exception e) {
        } finally {
            if (i != null) {
                try {
                    i.close();
                } catch (IOException e) {
                }
            }
        }
        return d;

    }
    
    public void saveBitmap(Bitmap bitmap, String pathPath) {
        // Console.d(TAG, "save path: " + pathPath);
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        File file = new File(pathPath);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 70, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {   
                e.printStackTrace();
        } catch (IOException e) {   
                e.printStackTrace();     
        } catch (IllegalStateException e) {
                e.printStackTrace();
        }

    }

}
