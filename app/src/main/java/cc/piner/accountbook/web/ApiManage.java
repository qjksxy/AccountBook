package cc.piner.accountbook.web;

import cc.piner.accountbook.web.pojo.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiManage {
    @GET("hello.do")
    Call<User> getBlog();
}
