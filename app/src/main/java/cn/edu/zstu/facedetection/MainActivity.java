package cn.edu.zstu.facedetection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.edu.zstu.R;
import cn.edu.zstu.facedetection.Detection.AssetUtil;
import cn.edu.zstu.facedetection.Detection.FaceDetector;
import cn.edu.zstu.facedetection.Detection.MessageEvent;
import cn.edu.zstu.facedetection.cameraPreview.CameraTexturePreview;

/**
 * Created by Chenlei on 2017/04/28.
 */
public class MainActivity extends Activity implements
		CompoundButton.OnCheckedChangeListener {
	static final String TAG = "MainActivity";
	public static final boolean DEBUG = true;

	private SurfaceView mFaceSurface;
	private FaceDetector mFacetector;
	private ToggleButton mSwitch;
	private RelativeLayout mSwitchLayout;
	private boolean mIsDisplay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		if (DEBUG) Log.d(TAG, "[onCreate()]");
		//
		init();
		initRSwitch();
		EventBus.getDefault().register(this);
		//
		AssetUtil.copyAssetToCache(this);
		mFacetector = FaceDetector.getInstance();
		mFacetector.setUIThreadInterface(mFaceSurface);
		//
		EventBus.getDefault().post("start");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (DEBUG) Log.d(TAG, "[onPause()]");
		CameraTexturePreview.closeCamera();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (DEBUG) Log.d(TAG, "[onResume()]");
		CameraTexturePreview.openCamera();
	}

	private void init() {
		mFaceSurface = (SurfaceView) findViewById(R.id.sfv_face);
		mFaceSurface.setZOrderOnTop(true);
		mFaceSurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}

	public void initRSwitch() {
		mSwitchLayout = (RelativeLayout) findViewById(R.id.det_layout);
		mSwitch = (ToggleButton) findViewById(R.id.swt);
		mSwitch.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
			case R.id.swt:
				if (!isChecked) {
					mFacetector.stopDetector();
				} else {
					mFacetector.startDetector();
				}
				break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (mIsDisplay) {
				mSwitchLayout.setVisibility(View.INVISIBLE);
				mIsDisplay = false;
			} else {
				mSwitchLayout.setVisibility(View.VISIBLE);
				mIsDisplay = true;
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Subscribe
	public void onEventMainThread(MessageEvent event) {
		if (event.message.equals("start")) {
			mFacetector.startDetector();
		} else if (event.message.equals("stop")) {
//			mSwitch.setChecked(false);
			mFacetector.stopDetector();
			startActivity(new Intent(MainActivity.this, CompareActivity.class));
			Log.d("123123", event.message);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
