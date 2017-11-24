package cn.edu.zstu.facedetection.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Chenlei on 2017/9/23.
 */
public class MyApplication extends Application {

	private static MyApplication sInstance;

	public MyApplication() {
		sInstance = this;
	}

	public static Context getContext() {
		return sInstance;
	}
}
