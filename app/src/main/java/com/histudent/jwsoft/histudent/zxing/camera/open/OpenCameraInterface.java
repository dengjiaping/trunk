/*
 *  Copyright(c) 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.histudent.jwsoft.histudent.zxing.camera.open;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;

import com.histudent.jwsoft.histudent.HTApplication;

public class OpenCameraInterface {

	private static final String TAG = OpenCameraInterface.class.getName();

	/**
	 * Opens the requested camera with {@link Camera#open(int)}, if one exists.
	 * 
	 * @param cameraId
	 *            camera ID of the camera to use. A negative value means
	 *            "no preference"
	 * @return handle to {@link Camera} that was opened
	 */
	public static Camera open(int cameraId) {

		int numCameras = Camera.getNumberOfCameras();
		if (numCameras == 0) {
			Log.w(TAG, "No cameras!");
			return null;
		}

		boolean explicitRequest = cameraId >= 0;

		if (!explicitRequest) {
			// Select a camera if no explicit camera requested
			int index = 0;
			while (index < numCameras) {
				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				Camera.getCameraInfo(index, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
					break;
				}
				index++;
			}

			cameraId = index;
		}

		Camera camera;
		if (cameraId < numCameras) {
			Log.i(TAG, "Opening camera #" + cameraId);

			camera = Camera.open(cameraId);
			setCameraDisplayOrientation(cameraId,camera);
		} else {
			if (explicitRequest) {
				Log.w(TAG, "Requested camera does not exist: " + cameraId);
				camera = null;
			} else {
				Log.i(TAG, "No camera facing back; returning camera #0");
				camera = Camera.open(0);
				setCameraDisplayOrientation(0,camera);
			}
		}

		return camera;
	}


	public static void setCameraDisplayOrientation(
												   int cameraId, android.hardware.Camera camera) {
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);

		WindowManager manager = (WindowManager) HTApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
		int rotation = manager.getDefaultDisplay().getRotation();
		int degrees = 0;
		Log.i(TAG, "rotation"+rotation);
		Log.i(TAG, "info"+info.facing+"   "+info.orientation);
		switch (rotation) {
			case Surface.ROTATION_0: degrees = 0; break;
			case Surface.ROTATION_90: degrees = 90; break;
			case Surface.ROTATION_180: degrees = 180; break;
			case Surface.ROTATION_270: degrees = 270; break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		} else {  // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}


	/**
	 * Opens a rear-facing camera with {@link Camera#open(int)}, if one exists,
	 * or opens camera 0.
	 * 
	 * @return handle to {@link Camera} that was opened
	 */
	public static Camera open() {
		return open(-1);
	}

}
