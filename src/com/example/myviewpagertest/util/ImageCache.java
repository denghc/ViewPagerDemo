package com.example.myviewpagertest.util;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Set;

import android.graphics.Bitmap;

public class ImageCache extends LinkedHashMap<String, SoftReference<Bitmap>>{
	private static final long serialVersionUID = -1286987934608859578L;

    private int mMaxsize = 20;
    private static ImageCache cache;

    private ImageCache(int maxsize) {
        super();
        mMaxsize = maxsize;
    }

    private ImageCache() {
        super();
    }

    public static synchronized ImageCache getInstance(int maxEntries) {
        if (cache == null) {
            cache = new ImageCache(maxEntries);
        }
        return cache;
    }

    public static synchronized ImageCache getInstance() {
        if (cache == null) {
            cache = new ImageCache();
        }
        return cache;
    }

    public synchronized boolean hasCacheObject(Object key) {
        return get(key) != null;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<String, SoftReference<Bitmap>> eldest) {
        return size() > mMaxsize;
    }

    public synchronized SoftReference<Bitmap> put(String key, Bitmap value) {
        return super.put(key, new SoftReference<Bitmap>(value));
    }

    @Override
    public synchronized SoftReference<Bitmap> get(Object key) {
        return super.get(key);
    }

    public synchronized boolean hasKey(Object key) {
        Set<String> keySet = this.keySet();
        for (String k : keySet) {
            if (k.equals(key))
                return true;
        }
        return false;
    }
}
