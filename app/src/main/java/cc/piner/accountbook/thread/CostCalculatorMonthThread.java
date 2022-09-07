package cc.piner.accountbook.thread;

import android.os.Handler;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.List;

import cc.piner.accountbook.sqlite.MyDBDao;
import cc.piner.accountbook.sqlite.MyDBHelper;
import cc.piner.accountbook.sqlite.pojo.Cost;
import cc.piner.accountbook.utils.DateUtil;
import cc.piner.accountbook.utils.HandlerUtil;

/**
 * <p>createDate 22-3-12</p>
 * <p>fileName   CalculatorMonthCostThread</p>
 * <p>展示月消费记录</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class CostCalculatorMonthThread extends Thread {
    private static final String TAG = "CalculatorMonthCostThre";
    Handler handler;
    MyDBHelper dbHelper;

    public CostCalculatorMonthThread(Handler handler, MyDBHelper dbHelper) {
        this.handler = handler;
        this.dbHelper = dbHelper;
        Log.w(TAG, "CalculatorMonthCostThread: constructor running", null);
    }

    @Override
    public void run() {
        long month = DateUtil.getMonthStartMS();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        MyDBDao myDBDao = new MyDBDao(dbHelper);
        StringBuilder str = new StringBuilder();
        List<Cost> costs = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{"" + month}, MyDBHelper.COST_TIME_COLUMN + " DESC");
        long monthSum = 0;
        for (int i = 0; i < costs.size(); i++) {
            if (i != 0) {
                str.append('\n');
            }
            Cost cost = costs.get(i);
            monthSum += cost.getCost();
            String date = format.format(cost.getTime());
            double dCost = cost.getCost() / 100.0;
            str.append(String.format("%s %6.2f：%s", date, dCost, cost.getDesc()));
        }
        HandlerUtil.sendMsg(handler, str.toString(), HandlerUtil.MONTH_COST_LIST, (int) monthSum, 0);
    }
}
