package cn.edu.zstu.facedetection.Detection;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.DisplayMetrics;

import java.util.List;

/**
 * Created by qiupengxie on 2017/11/24.
 */

public class CameraUtil {
	/**
	 * 获取最佳预览大小
	 *
	 * @param parameters       相机参数
	 * @param screenResolution 屏幕宽高
	 * @return
	 */
	public static Point getBestCameraResolution(Camera.Parameters parameters,
	                                            Point screenResolution) {
		float tmp = 0f;
		float mindiff = 100f;
		float x_d_y = (float) screenResolution.x / (float) screenResolution.y;
		Camera.Size best = null;
		List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
		for (Camera.Size s : supportedPreviewSizes) {
			tmp = Math.abs(((float) s.width / (float) s.height) - x_d_y);
			if (tmp < mindiff) {
				mindiff = tmp;
				best = s;
			}
		}
		return new Point(best.width, best.height);
	}

	/**
	 * 获取屏幕宽度和高度，单位为px
	 *
	 * @return
	 */
	public static Point getScreenMetrics(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;
		return new Point(w_screen, h_screen);
	}
}
