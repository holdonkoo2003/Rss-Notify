package com.laithnurie.baka.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.laithnurie.baka.MangaMenu;
import com.laithnurie.baka.RssApp;


/**
 * Created by laithnurie on 17/05/2013.
 */

public class SmsListener extends BroadcastReceiver {


	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
			SmsMessage[] msgs = null;
			String msg_from;
			String msgBody;
			if (bundle != null) {
				//---retrieve the SMS message received---
				try {
					Object[] pdus = (Object[]) bundle.get("pdus");
					msgs = new SmsMessage[pdus.length];
					for (int i = 0; i < msgs.length; i++) {
						msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						msg_from = msgs[i].getOriginatingAddress();
						msgBody = msgs[i].getMessageBody();

						Log.v("sms", "from " + msg_from);
						Log.v("sms", "text " + msgBody);

						Toast.makeText(context, msgBody, Toast.LENGTH_LONG).show();

						if (msg_from.contentEquals("+447826521789")) {

							Toast.makeText(context, "android " + msgBody, Toast.LENGTH_LONG).show();
							LocationProvider lp = new LocationProvider();
							lp.getNetworkLocation(RssApp.getCurrentActivity());
						}
					}
				} catch (Exception e) {
					Log.e("Exception caught", e.getMessage());
				}
			}
		}
	}
}
