package com.zhbj.zhbj.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		if (JPushInterface.ACTION_NOTIFICATION_OPENED
				.equals(intent.getAction())) {

			Toast.makeText(context, "�����ʹ���", Toast.LENGTH_SHORT).show();
			/*
			 * //���Զ����Activity Intent i = new Intent(context,
			 * TestActivity.class); i.putExtras(bundle);
			 * i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
			 * Intent.FLAG_ACTIVITY_CLEAR_TOP ); context.startActivity(i);
			 */
		}
	}
}
