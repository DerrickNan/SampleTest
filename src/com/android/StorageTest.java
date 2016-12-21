package com.android.test;

import android.util.Log;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class StorageTest {
	private static final String TAG = "StorageTest";

	public static void StartTest() {
		TestStorageSpace();
	}

	private static void TestStorageSpace() {
		Log.d(TAG, "TestStorageSpace in");
		try {
			String MEDIA_DIR = Environment.getMediaStorageDirectory().toString();
			StatFs stat = new StatFs(MEDIA_DIR);
			Log.i(TAG, "MEDIA_DIR = " + MEDIA_DIR +
			      " Block=" + stat.getAvailableBlocks() + " BlockSize=" + stat.getBlockSize());

			String EXTERNAL_DIR = Environment.getExternalStorageDirectory().toString();
			StatFs stat1 = new StatFs(EXTERNAL_DIR);
			Log.i(TAG, "EXTERNAL_DIR = " + EXTERNAL_DIR +
			      " Block=" + stat1.getAvailableBlocks() + " BlockSize=" + stat1.getBlockSize());

			String FileName = EXTERNAL_DIR + "/StoragePath.txt";
			File mTestFile = new File(FileName.toString());
			OutputStream mTestOutput = new FileOutputStream(mTestFile);
			if (null != mTestOutput) {
				byte[] dir = FileName.getBytes();
				mTestOutput.write(dir, 0, dir.length);
				mTestOutput.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "Exception : " + e);
		}
		Log.d(TAG, "TestStorageSpace out");
	}
}
