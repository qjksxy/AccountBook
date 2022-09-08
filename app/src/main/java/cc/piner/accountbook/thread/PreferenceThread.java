package cc.piner.accountbook.thread;

import android.content.Context;
import android.os.Handler;

import java.util.Random;

import cc.piner.accountbook.utils.HandlerUtil;
import cc.piner.accountbook.utils.PreferencesUtil;

/**
 * <p>createDate 22-9-8</p>
 * <p>fileName   PreferenceThread</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class PreferenceThread extends Thread {
    Context context;
    Handler handler;
    public PreferenceThread(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {
        PreferencesUtil preferencesUtil = new PreferencesUtil();
        int userid = preferencesUtil.getIntPerference(context, PreferencesUtil.USER_ID_INT, 0);
        if (userid == 0) {
            userid = new Random().nextInt();
            preferencesUtil.saveIntPreference(context, PreferencesUtil.USER_ID_INT, userid);
        }
        HandlerUtil.sendMsg(handler, userid, HandlerUtil.COST_USER_ID, 0, 0);
    }
}
