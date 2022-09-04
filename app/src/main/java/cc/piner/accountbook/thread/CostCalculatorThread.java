package cc.piner.accountbook.thread;

import android.os.Handler;

import java.util.List;

import cc.piner.accountbook.sqlite.MyDBDao;
import cc.piner.accountbook.sqlite.MyDBHelper;
import cc.piner.accountbook.sqlite.pojo.Cost;
import cc.piner.accountbook.utils.DateUtil;
import cc.piner.accountbook.utils.HandlerUtil;

/**
 * <p>createDate 22-9-4</p>
 * <p>fileName   CostCalculatorThread</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class CostCalculatorThread extends Thread {
    MyDBHelper dbHelper;
    Handler handler;
    public CostCalculatorThread(Handler handler, MyDBHelper dbHelper) {
        this.handler = handler;
        this.dbHelper = dbHelper;
    }

    @Override
    public void run() {
        long today = DateUtil.getTodayMS();
        long month = DateUtil.getMonthStartMS();
        MyDBDao myDBDao = new MyDBDao(dbHelper);
        List<Cost> costs1 = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{""+today}, null);
        long todaySum = 0;
        for (Cost cost : costs1) {
            todaySum += cost.getCost();
        }
        String str = String.format("今日消费：%s元", todaySum / 100.0);
        List<Cost> costs2 = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{"" + month}, null);
        long monthSum = 0;
        for (Cost cost : costs2) {
            monthSum += cost.getCost();
        }
        str += "\n" + String.format("本月消费：%s元", monthSum / 100.0);
        HandlerUtil.sendMsg(handler, str, HandlerUtil.MAIN_TEXT_VIEW, 0, 0);
    }

}
