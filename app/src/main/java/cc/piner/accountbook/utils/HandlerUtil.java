package cc.piner.accountbook.utils;

import android.os.Handler;
import android.os.Message;

/**
 * <p>createDate 22-9-4</p>
 * <p>fileName   HandlerUtil</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class HandlerUtil {
    public static final int MAIN_TEXT_VIEW = 1001;
    public static final int MONTH_COST_LIST = 1002;
    /**
     * 自动生成Message并调用handler.sendMessage(message)方法
     *
     * @param handler
     * @param obj     message.obj
     * @param what    message.what
     * @param arg1    message.arg1
     * @param arg2    message.arg2
     */
    public static void sendMsg(Handler handler, Object obj, int what, int arg1, int arg2) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = obj;
        message.arg1 = arg1;
        message.arg2 = arg2;
        handler.sendMessage(message);
    }
}
