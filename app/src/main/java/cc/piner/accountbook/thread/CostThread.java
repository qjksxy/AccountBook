package cc.piner.accountbook.thread;

import android.os.Handler;

import java.util.List;

import cc.piner.accountbook.sqlite.MyDBDao;
import cc.piner.accountbook.sqlite.MyDBHelper;
import cc.piner.accountbook.sqlite.pojo.Cost;
import cc.piner.accountbook.utils.DateUtil;
import cc.piner.accountbook.utils.HandlerUtil;

/**
 * 有关Cost相关计算的线程，将取代其他Cost相关线程类
 * <p>createDate 22-9-13</p>
 * <p>fileName   CostThread</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class CostThread extends Thread {
    Handler handler;
    MyDBHelper dbHelper;
    // 用于指派计算方法
    int method;
    public static final int GET_CONSUMPTION_TEXT = 10;
    public CostThread(Handler handler, MyDBHelper myDBHelper, int method) {
        // super();
        this.handler = handler;
        this.dbHelper = myDBHelper;
        this.method = method;
    }
    @Override
    public void run() {
        switch (method) {
            case GET_CONSUMPTION_TEXT:
                getConsumption();break;
        }
    }

    private void getConsumption() {
        long today = DateUtil.getTodayMS();
        long week = DateUtil.getWeekStartMS();
        long month = DateUtil.getMonthStartMS();
        MyDBDao myDBDao = new MyDBDao(dbHelper);
        List<Cost> dayCost = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{""+today}, null);
        long todaySum = 0;
        for (Cost cost : dayCost) {
            todaySum += cost.getCost();
        }
        StringBuilder str = new StringBuilder();
        str.append(String.format("今日消费：%7.2f元", todaySum / 100.0)).append('\n');

        List<Cost> weekCost = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{""+week}, null);
        long weekSum = 0;
        for (Cost cost : weekCost) {
            weekSum += cost.getCost();
        }
        str.append(String.format("本周消费：%7.2f元", weekSum / 100.0)).append('\n');

        List<Cost> monthCost = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{"" + month}, null);
        long monthSum = 0;
        for (Cost cost : monthCost) {
            monthSum += cost.getCost();
        }
        str.append(String.format("本月消费：%7.2f元", monthSum / 100.0));
        HandlerUtil.sendMsg(handler, str.toString(), HandlerUtil.MAIN_TEXT_VIEW, 0, 0);
    }

}
