package com.gwm.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;


/**
 * Assets目录工具类，用于访问各种Assets目录下的文件
 * @author asus1
 *
 */
public class AssetsUtil {
	private Context context;
	private AssetManager assets;

	public AssetsUtil(Context context) {
		this.context = context;
		assets = context.getAssets();
	}
	/**
	 * 安装assets目录下的文件
	 * @param fileName 文件名称
	 */
	public void installapk(String fileName) {
		try {
			InputStream stream = assets.open(fileName);
			if (stream == null) {
				Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
				return;
			}

			String folder = Environment.getExternalStorageDirectory().getPath()+"/sm/";
			File f = new File(folder);
			if (!f.exists()) {
				f.mkdir();
			}
			String apkPath = Environment.getExternalStorageDirectory().getPath()+"/sm/test.apk";
			File file = new File(apkPath);
			if(!file.exists())
				file.createNewFile();
			writeStreamToFile(stream, file);
			installApk(apkPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 执行具体的静默安装assets目录下的文件，需要手机ROOT。
	 * @param fileName
	 */
	public boolean install(String fileName) {
		boolean result = false;
		DataOutputStream dataOutputStream = null;
		BufferedReader errorStream = null;
		try {
			// 申请su权限
			Process process = Runtime.getRuntime().exec("su");
			dataOutputStream = new DataOutputStream(process.getOutputStream());
			// 执行pm install命令
			String path = "file:///android_asset/" + fileName;
			String command = "pm install -r " + path + "\n";
			dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
			dataOutputStream.flush();
			dataOutputStream.writeBytes("exit\n");
			dataOutputStream.flush();
			process.waitFor();
			errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String msg = "";
			String line;
			// 读取命令的执行结果
			while ((line = errorStream.readLine()) != null) {
				msg += line;
			}
			Log.d("TAG", "install msg is " + msg);
			// 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
			if (!msg.contains("Failure")) {
				result = true;
			}
		} catch (Exception e) {
			Log.e("TAG", e.getMessage(), e);
		} finally {
			try {
				if (dataOutputStream != null) {
					dataOutputStream.close();
				}
				if (errorStream != null) {
					errorStream.close();
				}
			} catch (IOException e) {
				Log.e("TAG", e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 * 加载assets目录下的网页
	 * @param webView
	 * @param fileName
	 */
	public void loadWebPage(WebView webView, String fileName){
		webView.loadUrl("file:///android_asset/"+fileName);
	}
	private void writeStreamToFile(InputStream stream, File file) {
		try {
			OutputStream output = null;
			try {
				output = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				try {
					final byte[] buffer = new byte[1024];
					int read;

					while ((read = stream.read(buffer)) != -1)
						output.write(buffer, 0, read);
					output.flush();
				} finally {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void installApk(String apkPath) {
        AppUtils.getInstance(context).installApk(apkPath);
	}
}
