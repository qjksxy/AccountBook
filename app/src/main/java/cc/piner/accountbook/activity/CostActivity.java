package cc.piner.accountbook.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import cc.piner.accountbook.R;
import cc.piner.accountbook.sqlite.MyDBDao;
import cc.piner.accountbook.sqlite.MyDBHelper;
import cc.piner.accountbook.thread.CostCalculatorThread;
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
    TextView costAddBtn, costView;
    MyDBHelper myDBHelper;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        myDBHelper = new MyDBHelper(this);
        handler = new MyHandler();
        initUI();
        new CostCalculatorThread(handler, myDBHelper).start();
    }

    private void initUI() {
        costAddBtn = findViewById(R.id.costAddBtn);
        costTitle = findViewById(R.id.costTitle);
        costMoney = findViewById(R.id.costMoney);
        costView = findViewById(R.id.costView);
        costAddBtn.setClickable(true);
        costAddBtn.setOnClickListener(v -> {
            String title = String.valueOf(costTitle.getText());
            String money = String.valueOf(costMoney.getText());
            if (money == null || money.equals("")) {
                Toast.makeText(this, "金额不能为空", Toast.LENGTH_SHORT).show();
            } else {
                double dMoney = Double.parseDouble(money);
                long lMoney = (long) (dMoney * 100);
                Calendar cal = Calendar.getInstance();
                long time = cal.getTimeInMillis();
                MyDBDao dao = new MyDBDao(myDBHelper);
                long costId = dao.insertCost(lMoney, time, title);
                costMoney.setText("");
                costTitle.setText("");
                new CostCalculatorThread(handler, myDBHelper).start();
                Cost cost = new Cost();
                cost.setTime(time);
                cost.setTitle(title);
                cost.setConsumption((int) lMoney);
                cost.setUserId(1);
                sendWeb(cost);
            }
        });
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
                if (statusCode != 200) {
                    Toast.makeText(CostActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(CostActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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
                default:
                    break;
            }
        }
    }
}



