package com.pms.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder holder = null;
	private Camera mCamera = null;
	private Bitmap mBitmap = null;

	public CameraView(Context context) {
		super(context);
		holder = this.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置类型(必须)
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Camera.Parameters parameters = mCamera.getParameters();//设置相机参数
		parameters.setPictureFormat(PixelFormat.JPEG);// 设置图片格式
		parameters.setPreviewSize(250, 250);// 设置尺寸
		// parameters.setPictureSize(320, 480);//设置分辨率
		mCamera.setParameters(parameters);
		mCamera.startPreview();// 开始预览
	}

	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();// 启动服务
		try {
			mCamera.setPreviewDisplay(holder);// 设置预览
		} catch (IOException e) {
			mCamera.release();// 释放
			mCamera = null;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();// 停止预览
		mCamera = null;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mCamera != null) {
			mCamera.takePicture(null, null, pic);
		}
		return super.onKeyDown(keyCode, event);
	}

	// 拍照后输出图片
	public Camera.PictureCallback pic = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			File f = new File(android.os.Environment
					.getExternalStorageDirectory()
					+ "/pms.jpg");//SD卡中生成文件
			try {
				if (!f.exists()) {
					f.createNewFile();
				}
				BufferedOutputStream os = new BufferedOutputStream(
						new FileOutputStream(f));
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);//将数据转行成Bitmap图
				os.flush();
				os.close();
				Canvas canvas = holder.lockCanvas();//锁定画布
				canvas.drawBitmap(mBitmap, 0, 0, null);//绘画
				holder.unlockCanvasAndPost(canvas);//解除画布
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
