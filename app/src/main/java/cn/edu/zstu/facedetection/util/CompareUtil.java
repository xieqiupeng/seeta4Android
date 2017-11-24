package cn.edu.zstu.facedetection.util;

import android.graphics.Bitmap;
import android.os.Environment;

import seetaface.XUtils;

/**
 * Created by qiupengxie on 2017/11/24.
 */

public class CompareUtil {
	//
	private static String PATH_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";
	private static String PATH_IMG1 = PATH_FOLDER + "Image/IMAGE.jpg";
	private static String PATH_IMG2 = PATH_FOLDER + "/Download/0_1_1.jpg";
	//
	private Bitmap bitmap;

	public void loadPictureOne() {
		bitmap = XUtils.getScaledBitmap(PATH_IMG1, 600);
		loadPicture(bitmap);
	}

	public void loadPictureTwo() {
		bitmap = XUtils.getScaledBitmap(PATH_IMG1, 600);
		loadPicture(bitmap);
	}

	private void loadPicture(Bitmap bm) {
	}
}
