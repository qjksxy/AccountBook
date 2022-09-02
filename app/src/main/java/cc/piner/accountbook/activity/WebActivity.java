package cc.piner.accountbook.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import cc.piner.accountbook.R;
import cc.piner.accountbook.web.ApiManage;
import cc.piner.accountbook.web.pojo.Data;
import cc.piner.accountbook.web.pojo.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebActivity extends AppCompatActivity {
    private static final String TAG = "WebActivity";
    TextView textView;
    Handler handler;
    Button userJsonBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        textView = findViewById(R.id.textView);
        userJsonBtn = findViewById(R.id.webUserJsonBtn);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String str = (String) msg.obj;
                textView.setText(str);
            }
        };

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.93.10.103:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiManage apiManage = retrofit.create(ApiManage.class);
        Call<User> blog = apiManage.getBlog();
        blog.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                Log.i(TAG, "onResponse: " + new Gson().toJson(body));
                Message obtain = Message.obtain();
                obtain.obj = new Gson().toJson(body);
                handler.sendMessage(obtain);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Message obtain = Message.obtain();
                obtain.obj = "new Gson().toJson(null);\n" + t.toString();
                handler.sendMessage(obtain);
            }
        });

        userJsonBtn.setOnClickListener(v -> {
            Call<Data> call = apiManage.uploadUser(new User(8, "小王", "12456"));
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    Data data = response.body();
                    Message obtain = Message.obtain();
                    obtain.obj = data.getDescription();
                    handler.sendMessage(obtain);
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    Message obtain = Message.obtain();
                    obtain.obj = t.toString();
                    handler.sendMessage(obtain);
                }
            });
        });
    }
}