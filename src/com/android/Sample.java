package com.android.test;

import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

import android.os.PowerManager;
import android.os.storage.StorageManager;
import android.hardware.usb.UsbManager;

public class Sample extends Activity {
	private static final String TAG = "SampleTest";

	private PowerManager pm;
	private PowerManager.WakeLock wl;

	BroadcastReceiver mReceiver = new  BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (UsbManager.ACTION_USB_STATE.equals(action)) {
					StorageManager stoageManager = (StorageManager)
						context.getSystemService(Context.STORAGE_SERVICE);
					boolean connected =
						intent.getBooleanExtra(UsbManager.USB_CONNECTED, false);
					boolean UmsEnable = stoageManager.isUsbMassStorageEnabled();
					Log.d(TAG, "connected = " + connected + " UMS status " + UmsEnable);
					if (connected && !UmsEnable) {
						Log.d(TAG, "enableUsbMassStorage now");
						stoageManager.enableUsbMassStorage();
					} else if (!connected && UmsEnable) {
						Log.d(TAG, "disableUsbMassStorage now");
						stoageManager.disableUsbMassStorage();
					}
				}
			}
		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Allocate screen lock : keep screen on
		pm = (PowerManager) this.getSystemService(this.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
				    PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		wl.acquire();

		IntentFilter filter = new IntentFilter();
		filter.addAction(UsbManager.ACTION_USB_STATE);
		registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// StorageTest.TestMassStorage();
		// AudioRecord.StartTest();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		unregisterReceiver(mReceiver);

		// Release screen lock
		wl.release();
	}
}