/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Description:   CameraTexturePreview.java
 * Author:        Lei Chen <lei.chen@shanjutech.com>
 * Date:          05/19/2017
 *
 * Copyright (C) 2015-2017 The ZSTU SmartHome Project
 */

package cn.edu.zstu.facedetection.cameraPreview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import cn.edu.zstu.facedetection.Detection.FaceDetector;

@SuppressLint("NewApi")
public class CameraTexturePreview extends TextureView implements TextureView.SurfaceTextureListener {
	private final String TAG = "CameraTexturePreview";
	private boolean DEBUG = true;
	private static SurfaceTexture mSurfaceTexture = null;
	private static boolean mCamHasOpened = false;
	private static boolean mSurfaceTextureAvailable = false;
	public static Matrix mScaleMatrix = new Matrix();
	public static int mSurfaceHeight;
	public static int mSurfaceWidth;

	private static Context context = null;

	public CameraTexturePreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setSurfaceTextureListener(this);
		CameraTexturePreview.context = context.getApplicationContext();
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
	                                      int height) {
		if (DEBUG)
			Log.i(TAG, "onSurfaceTextureAvailable()");

		mSurfaceTextureAvailable = true;
		if (mCamHasOpened) {
			startPreview(surface);
		}
		mSurfaceTexture = surface;
		mSurfaceHeight = height;
		mSurfaceWidth = width;
		mScaleMatrix.setScale(width / (float) CameraWrapper.IMAGE_WIDTH, height / (float) CameraWrapper.IMAGE_HEIGHT);
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
	                                        int height) {
		if (DEBUG)
			Log.i(TAG, "onSurfaceTextureSizeChanged()");

		mSurfaceHeight = height;
		mSurfaceWidth = width;
		mScaleMatrix.setScale(width / (float) CameraWrapper.IMAGE_WIDTH, height / (float) CameraWrapper.IMAGE_HEIGHT);
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		if (DEBUG)
			Log.i(TAG, "onSurfaceTextureDestroyed()");

		if (FaceDetector.getInstance().getIsStarted())
			FaceDetector.getInstance().stopDetector();

		mCamHasOpened = false;
		mSurfaceTextureAvailable = false;
		mSurfaceTexture = null;
		closeCamera();
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//		Log.i(TAG, "onSurfaceTextureUpdated()");  
	}

	private static CameraWrapper.CamOpenOverCallback mCamOpenOverCallback = new CameraWrapper.CamOpenOverCallback() {
		@Override
		public void cameraHasOpened() {
			mCamHasOpened = true;
			if (mSurfaceTextureAvailable && (mSurfaceTexture != null)) {
				startPreview(mSurfaceTexture);
				mScaleMatrix.setScale(mSurfaceWidth / (float) CameraWrapper.IMAGE_WIDTH,
						mSurfaceHeight / (float) CameraWrapper.IMAGE_HEIGHT);
			}
		}
	};

	public static void openCamera() {
		Thread openThread = new Thread() {
			@Override
			public void run() {
				CameraWrapper.getInstance(context).doOpenCamera(mCamOpenOverCallback);
			}
		};
		openThread.start();
	}

	private static void startPreview(SurfaceTexture surface) {
		CameraWrapper.getInstance(context).doStartPreview(surface);
	}

	public static void closeCamera() {
		CameraWrapper.getInstance(context).doStopCamera();
	}

	public void switchCamera() {
		closeCamera();
		CameraWrapper.getInstance(context).switchCameraId();
		openCamera();
	}
}
