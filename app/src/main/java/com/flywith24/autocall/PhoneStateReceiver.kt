package com.flywith24.autocall

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import org.greenrobot.eventbus.EventBus


/**
 * @author yyz (杨云召)
 * @date   2020/4/10
 * time   11:44
 * description
 */
class PhoneStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals("android.intent.action.PHONE_STATE")) {
            val tm =
                context!!.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
            tm.listen(object : PhoneStateListener() {
                override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                    when (state) {
                        //挂断
                        TelephonyManager.CALL_STATE_IDLE -> {
                            EventBus.getDefault().post("")
                        }
                    }
                    super.onCallStateChanged(state, phoneNumber)
                }
            }, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }
}