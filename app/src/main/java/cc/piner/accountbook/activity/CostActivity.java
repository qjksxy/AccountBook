package cc.piner.accountbook.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.Calendar;
import java.util.List;

import cc.piner.accountbook.R;
import cc.piner.accountbook.sqlite.MyDBDao;
import cc.piner.accountbook.sqlite.MyDBHelper;
import cc.piner.accountbook.thread.CostCalculatorMonthThread;
import cc.piner.accountbook.thread.CostCalculatorThread;
import cc.piner.accountbook.thread.PreferenceThread;
import cc.piner.accountbook.utils.HandlerUtil;
import cc.piner.accountbook.web.ApiManage;
import cc.piner.accountbook.web.pojo.Cost;
import cc.piner.accountbook.web.pojo.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CostActivity extends AppCompatActivity {

    EditText costTitle, costMoney;
    TextView costAddBtn, costView, costListView;
    TextView costHint1, costHint2, costHint3;
    TitleBar titleBar;
    MyDBHelper myDBHelper;
    Handler handler;
    final String[] hint = {"不准点！", "说了不准点！", "再点就坏了！", "坏了，赔钱！", "呜呜呜呜呜，不可以(⋟﹏⋞)"};
    int hintIndex = 0;
    int userid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        myDBHelper = new MyDBHelper(this);
        handler = new MyHandler();
        initUI();
        new PreferenceThread(this, handler).start();
    }

    private void initUI() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        costAddBtn = findViewById(R.id.costAddBtn);
        costTitle = findViewById(R.id.costTitle);
        costMoney = findViewById(R.id.costMoney);
        costView = findViewById(R.id.costView);
        costListView = findViewById(R.id.costListView);
        costHint1 = findViewById(R.id.costHint1);
        costHint2 = findViewById(R.id.costHint2);
        costHint3 = findViewById(R.id.costHint3);
        costHint1.setOnClickListener(new MyListener());
        costHint2.setOnClickListener(new MyListener());
        costHint3.setOnClickListener(new MyListener());
        costAddBtn.setClickable(true);
        costAddBtn.setOnClickListener(v -> {
            String title = String.valueOf(costTitle.getText());
            String money = String.valueOf(costMoney.getText());
            if (money.equals("")) {
                Toast.makeText(this, "金额不能为空", Toast.LENGTH_SHORT).show();
            } else {
                double dMoney = 0;
                try {
                    dMoney = Double.parseDouble(money);
                } catch (Exception e) {
                    Toast.makeText(this, "金额填写错误，请检查", Toast.LENGTH_SHORT).show();
                }
                long lMoney = (long) (dMoney * 100);
                if (lMoney != 0) {
                    Calendar cal = Calendar.getInstance();
                    long time = cal.getTimeInMillis();
                    MyDBDao dao = new MyDBDao(myDBHelper);
                    long costId = dao.insertCost(lMoney, time, title);
                    costMoney.setText("");
                    costTitle.setText("");
                    new CostCalculatorThread(handler, myDBHelper).start();
                    new CostCalculatorMonthThread(handler, myDBHelper).start();

                    Cost cost = new Cost();
                    cost.setTime(time);
                    cost.setTitle(title);
                    cost.setConsumption((int) lMoney);
                    cost.setUserid(userid);
                    sendWeb(cost);
                }
            }
        });
        titleBar = findViewById(R.id.titleBar);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                Toast.makeText(CostActivity.this, "大懒虫还没有实现这个功能，请期待后续版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                String str = hint[hintIndex++];
                if (hintIndex == hint.length) {
                    hintIndex--;
                }
                Toast.makeText(CostActivity.this, str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                Toast.makeText(CostActivity.this, "大懒虫还没有实现这个功能，请期待后续版本", Toast.LENGTH_SHORT).show();
            }
        });
        new CostCalculatorThread(handler, myDBHelper).start();
        new CostCalculatorMonthThread(handler, myDBHelper).start();
    }

    private void sendWeb(Cost cost) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.93.10.103:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiManage apiManage = retrofit.create(ApiManage.class);
        Call<Data> dataCall = apiManage.uploadCost(cost);
        dataCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data body = response.body();
                int statusCode = body.getStatusCode();
                String description = body.getDescription();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
            }
        });
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case HandlerUtil.MAIN_TEXT_VIEW:
                    costView.setText((String) msg.obj);
                    break;
                case HandlerUtil.MONTH_COST_LIST:
                    String str = (String) msg.obj;
                    if (str != null && !str.equals("")) {
                        costListView.setText(str);
                    }
                    break;
                case HandlerUtil.COST_HINT_TEXT:
                    List<String> lists = (List<String>) msg.obj;
                    if (!lists.isEmpty()) {
                        costHint1.setText(lists.get(0));
                    }
                    if (lists.size() > 1) {
                        costHint2.setText(lists.get(1));
                    }
                    if (lists.size() > 2) {
                        costHint3.setText(lists.get(2));
                    }
                    break;
                case HandlerUtil.COST_USER_ID:
                    userid = (int) msg.obj;
                    break;
                default:
                    break;
            }
        }
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            costTitle.setText(tv.getText());
            costMoney.requestFocusFromTouch();
        }
    }
}



