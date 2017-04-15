package com.aisautocare.mobile.service;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.activity.ConfirmOrderActivity;
import com.aisautocare.mobile.activity.WaitOrderActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aisautocare.mobile.activity.MainActivity;
import com.aisautocare.mobile.app.Config;
import com.aisautocare.mobile.util.NotificationUtils;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");
            String type = data.getString("type");
            Log.i("Type", "tyipe : " + type);
            if (!type.toLowerCase().contains("go")){
                String title = data.getString("title");
                String message = data.getString("message");
                boolean isBackground = data.getBoolean("is_background");
                String imageUrl = data.getString("image");
                String timestamp = data.getString("timestamp");
                JSONObject payload = data.getJSONObject("payload");
                String uidBengkel = data.getString("uid_bengkel");
                JSONObject bengkel = data.getJSONObject("bengkel");
                JSONArray service = data.getJSONArray("service");
                JSONArray serviceType = data.getJSONArray("service_type");
                JSONObject review = data.getJSONObject("review");
                JSONObject order = data.getJSONObject("order");


                Log.e(TAG, "data: " + data);
                Log.e(TAG, "title: " + title);
                Log.e(TAG, "message: " + message);
                Log.e(TAG, "isBackground: " + isBackground);
                Log.e(TAG, "payload: " + payload.toString());
                Log.e(TAG, "imageUrl: " + imageUrl);
                Log.e(TAG, "timestamp: " + timestamp);


                GlobalVar.orderLat = Double.valueOf(order.getString("latitude"));
                GlobalVar.orderLon = Double.valueOf(order.getString("longitude"));
                GlobalVar.bengkelLat = Double.valueOf(bengkel.getString("latitude"));
                GlobalVar.bengkelLon = Double.valueOf(bengkel.getString("longitude"));
                Intent pushNotification = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
                pushNotification.putExtra("message", message);
                pushNotification .putExtra("idBengkel", bengkel.getString("id"));
                GlobalVar.bengkelID= Integer.valueOf(bengkel.getString("id"));
                pushNotification .putExtra("latBengkel", bengkel.getString("latitude"));
                pushNotification .putExtra("lonBengkel", bengkel.getString("longitude"));
                pushNotification .putExtra("namaBengkel", bengkel.getString("name"));
                pushNotification .putExtra("alamatBengkel", bengkel.getString("address"));
                pushNotification .putExtra("hpBengkel", bengkel.getString("phone"));
                pushNotification .putExtra("namaLayanan", serviceType.getJSONObject(0).getString("name") + " " + serviceType.getJSONObject(0).getString("sub"));
                pushNotification .putExtra("hargaLayanan", serviceType.getJSONObject(0).getString("price") );
                pushNotification .putExtra("uidBengkel", uidBengkel );
                pushNotification .putExtra("hj", service.getJSONObject(0).getString("hj") );
                pushNotification .putExtra("hb", service.getJSONObject(0).getString("hb"));
                pushNotification .putExtra("jp", service.getJSONObject(0).getString("jp") );
                pushNotification .putExtra("bd", service.getJSONObject(0).getString("bd"));
                System.out.println("Rating dari get string ");
                pushNotification.putExtra("rating", review.getString("rating"));
                pushNotification.putExtra("jumlah_review", review.getString("post"));

                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                    // app is in foreground, broadcast the push message

                    // play notification sound
                    NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                    notificationUtils.playNotificationSound();
                    Log.e(TAG,  "NOTIFIKASI DITERIMA AKAN DIARAHKAN KE CONFIRM ORDER");
                    pushNotification.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(pushNotification);
                } else {
                    // app is in background, show the notification in notification tray
                    Intent resultIntent = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
                    resultIntent.putExtra("message", message);

                    // check for image attachment
                    if (TextUtils.isEmpty(imageUrl)) {
                        showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                    } else {
                        // image is present, show notification with image
                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                    }
                }
            }else{
                Log.e("Confirm Go", "Masuk ke Konfirm go");
                GlobalVar.statusBerangkat = true;
            }










        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
