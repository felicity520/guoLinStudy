package com.ryd.gyy.guolinstudy.Util;

import android.content.Context;
import android.os.Build;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StoragePathUtils {

//调用：
//     String path = StoragePathUtils.getUPath(mContext, true);
// Log.e("StoragePathUtils", "onCreate u path : " + path );

    private static final String TAG = "StoragePathUtils";

    /**
     * 获取SD的路径
     *
     * @param mContext
     * @param is_removale
     * @return
     */
    public static String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        StorageVolume[] storageVolumes;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            storageVolumes = (StorageVolume[]) getVolumeList.invoke(mStorageManager);

            int i = 0;
            for (StorageVolume storageVolume : storageVolumes) {
                String path = (String) getPath.invoke(storageVolume);
                Log.i(TAG, "all: " + (i++) + path);
                boolean removable = (Boolean) isRemovable.invoke(storageVolume);

                if (is_removale == removable) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (storageVolume.getDescription(mContext).equalsIgnoreCase("SD")) {
                            Log.i(TAG, "SD: " + path);
                            return path;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取U盘的路径
     *
     * @param mContext
     * @param is_removale
     * @return
     */
    public static String getUPath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        StorageVolume[] storageVolumes;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            storageVolumes = (StorageVolume[]) getVolumeList.invoke(mStorageManager);

            int i = 0;
            for (StorageVolume storageVolume : storageVolumes) {
                String path = (String) getPath.invoke(storageVolume);
                Log.i(TAG, "path: " + path);
                boolean removable = (Boolean) isRemovable.invoke(storageVolume);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    U盘的名称
                    Log.e(TAG, "getUPath: " + storageVolume.getDescription(mContext));
                }
                if (is_removale == removable) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //USB是U盘的名称
                        if (storageVolume.getDescription(mContext).equalsIgnoreCase("USB")) {
                            Log.i("ggg", "U: " + path);
                            return path;
                        }
                    }
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

}

