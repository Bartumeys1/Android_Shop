package com.example.sim.network;

import com.example.sim.dto.user.ProfileDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/user/{id}")
    public Call<Void> getById(@Path("id") int id);
    @POST("api/user/updateProfile")
    public Call<Void> updateProfile(@Body ProfileDTO model);
}
