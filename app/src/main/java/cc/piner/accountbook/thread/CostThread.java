package cc.piner.accountbook.thread;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    Context context;
    // 用于指派计算方法
    int method;
    public static final int GET_CONSUMPTION_TEXT = 10;
    public static final int GET_HISTORICAL_RECORD = 11;
    public CostThread(Handler handler, MyDBHelper myDBHelper, Context context, int method) {
        // super();
        this.handler = handler;
        this.dbHelper = myDBHelper;
        this.context = context;
        this.method = method;
    }
    @Override
    public void run() {
        switch (method) {
            case GET_CONSUMPTION_TEXT:
                getConsumption();break;
            case GET_HISTORICAL_RECORD:
                getHistoricalRecord();

        }
    }

    // 获取日、周、月消费金额
    private void getConsumption() {
        // 从设置中检索已经保存的周计划和月计划的值
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        String weekTargetString = sharedPreferences.getString("week", "");
        String monthTargetString = sharedPreferences.getString("month", "");
        long weekTarget = 0, monthTarget = 0;
        boolean hasWeekTarget = false, hasMonthTarget = false;
        if(weekTargetString != null && !weekTargetString.equals("")) {
            weekTarget = Long.parseLong(weekTargetString);
            hasWeekTarget = true;
        }
        if(monthTargetString != null && !monthTargetString.equals("")) {
            monthTarget = Long.parseLong(monthTargetString);
            hasMonthTarget = true;
        }

        long today = DateUtil.getTodayMS();
        long week = DateUtil.getWeekStartMS();
        long month = DateUtil.getMonthStartMS();
        MyDBDao myDBDao = new MyDBDao(dbHelper);
        // 计算日消费
        List<Cost> dayCost = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{""+today}, null);
        long todaySum = 0;
        for (Cost cost : dayCost) {
            todaySum += cost.getCost();
        }


        // 计算周消费
        List<Cost> weekCost = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{""+week}, null);
        long weekSum = 0;
        for (Cost cost : weekCost) {
            weekSum += cost.getCost();
        }

        // 计算月消费
        List<Cost> monthCost = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{"" + month}, null);
        long monthSum = 0;
        for (Cost cost : monthCost) {
            monthSum += cost.getCost();
        }

        // 获取当日消费额度  当日消费额度 = min(周消费额度/本周剩余天数, 月消费额度/本月剩余天数)
        long dayTarget = -1;
        if (hasWeekTarget) {
            dayTarget = weekTarget * 100 / DateUtil.getWeekRemainDays();
        }
        if (hasMonthTarget) {
            long tempDayTarget = monthTarget * 100 / DateUtil.getMonthRemainDays();
            if (tempDayTarget < dayTarget || dayTarget < 0) {
                dayTarget = tempDayTarget;
            }
        }
        // 连接字符串
        StringBuilder str = new StringBuilder();
        str.append(String.format("今日消费：%7.2f", todaySum / 100.0));
        if (dayTarget > 0) {
            str.append(String.format("/%.2f", dayTarget / 100.0));
        }
        str.append('\n');
        str.append(String.format("本周消费：%7.2f", weekSum / 100.0));
        if (hasWeekTarget) {
            str.append("/").append(weekTargetString);
        }
        str.append('\n');
        str.append(String.format("本月消费：%7.2f", monthSum / 100.0));
        if (hasMonthTarget) {
            str.append("/").append(monthTargetString);
        }
        HandlerUtil.sendMsg(handler, str.toString(), HandlerUtil.MAIN_TEXT_VIEW, 0, 0);
    }

    //
    private void getHistoricalRecord() {
        long month = DateUtil.getMonthStartMS();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        MyDBDao myDBDao = new MyDBDao(dbHelper);
        StringBuilder stringBuilder = new StringBuilder();
        // List<Cost> costs = myDBDao.queryCost(
        //         MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{"" + month}, MyDBHelper.COST_TIME_COLUMN + " DESC LIMIT 50");
        List<Cost> costs = myDBDao.queryCost(
                MyDBHelper.COST_TIME_COLUMN + " > ?", new String[]{"0"}, MyDBHelper.COST_TIME_COLUMN + " DESC LIMIT 50");
        long monthSum = 0;
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < costs.size(); i++) {
            String str = costs.get(i).getDesc();
            map.put(str, map.containsKey(str) ? map.get(str)+1 : 1);

            if (i != 0) {
                stringBuilder.append('\n');
            }
            Cost cost = costs.get(i);
            monthSum += cost.getCost();
            String date = format.format(cost.getTime());
            double dCost = cost.getCost() / 100.0;
            stringBuilder.append(String.format("%s %6.2f：%s", date, dCost, cost.getDesc()));
        }
        HandlerUtil.sendMsg(handler, stringBuilder.toString(), HandlerUtil.MONTH_COST_LIST, (int) monthSum, 0);

        for (int i = 0; i < 3; i++) {
            int max = 0;
            String maxStr = null;
            for (String s : map.keySet()) {
                if (map.get(s) > max) {
                    maxStr = s;
                    max = map.get(s);
                }
            }
            if (maxStr != null) {
                map.remove(maxStr);
            }
            result.add(maxStr);
        }
        HandlerUtil.sendMsg(handler, result, HandlerUtil.COST_HINT_TEXT, 0, 0);
    }
}
