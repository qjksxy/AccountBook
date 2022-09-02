package cc.piner.accountbook.web;

import cc.piner.accountbook.web.pojo.Data;
import cc.piner.accountbook.web.pojo.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiManage {
    @GET("hello.do")
    Call<User> getBlog();

    @POST("user.json")
    Call<Data> uploadUser(@Body User user);
}
