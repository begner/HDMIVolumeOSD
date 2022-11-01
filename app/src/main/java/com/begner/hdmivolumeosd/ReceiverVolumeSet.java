package com.begner.hdmivolumeosd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverVolumeSet extends BroadcastReceiver {

    Context pcontext;
    OSD osd;

    public void setOSD(OSD osd) {
        this.osd = osd;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        pcontext = context;
        //check the intent something like:
        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
            int newVolume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
            int oldVolume = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);

            if (newVolume != oldVolume) {
//              Toast.makeText(pcontext ,"newVolume" +newVolume + " oldVolume" + oldVolume, Toast.LENGTH_SHORT).show();
                System.out.println("In onReceive newVolume = " +newVolume);
                // osd.updateView(newVolume, oldVolume, 60);
            }
        }
    }
}