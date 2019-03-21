package com.gwm.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 * @author gwm
 *
 */
public class FileUtils {
	private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
	private static String mDataRootPath = null;
	private final static String FOLDER_NAME = "/AndroidImage";

	private static FileUtils instance;
    private Context mContext;

    private FileUtils(Context context){
		mDataRootPath = context.getCacheDir().getPath();
		this.mContext = context;
	}
	public synchronized static FileUtils getInstance(Context context){
		if(instance == null){
			instance = new FileUtils(context);
		}
		return instance;
	}

	/**
	 * 获取系统sdk路径
	 * @return
	 */
	private String getStorageDirectory(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
				mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;
	}

	public Bitmap getBitmap(String fileName){
		return BitmapFactory.decodeFile(getStorageDirectory() + File.separator + fileName);
	}
	public boolean isFileExists(String fileName){
		return new File(getStorageDirectory() + File.separator + fileName).exists();
	}
	
	public long getFileSize(String fileName) {
		return new File(getStorageDirectory() + File.separator + fileName).length();
	}
	
	
	public void deleteFile() {
		File dirFile = new File(getStorageDirectory());
		if(! dirFile.exists()){
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}
		dirFile.delete();
	}

	/**
	 * 将文件转换成byte[]格式
	 * @param file
	 * @return
	 */
	public static byte[] getFileToByteArray(File file){
		ByteArrayOutputStream bos = null;
		FileInputStream fis = null;
		byte[] buffer = null;
		try {
			bos = new ByteArrayOutputStream();
			fis = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = fis.read(buf)) != -1){
				bos.write(buf, 0, len);
			}
			buffer = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
				bos = null;
				System.gc();
			}
			try {
				if(fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				fis = null;
				System.gc();
			}
		}
		return buffer;
	}
}
