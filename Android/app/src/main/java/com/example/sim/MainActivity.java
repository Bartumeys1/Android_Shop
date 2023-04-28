package com.example.sim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.application.HomeApplication;
import com.example.sim.category.CategoriesAdapter;
import com.example.sim.dto.category.CategoryItemDTO;
import com.example.sim.service.CategoryNetwork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private CategoriesAdapter adapter;
    private RecyclerView rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageView iv = findViewById(R.id.imageView);
//        String url = "https://pv016.allin.ml/images/1.jpg";
//        Glide.with(this)
//                .load(url)
//                .apply(new RequestOptions().override(600))
//                .into(iv);

        rc = findViewById(R.id.rcvCategories);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        rc.setAdapter(new CategoriesAdapter(new ArrayList<>(), null,null));
        requestServer();
    }

    void requestServer() {
        CategoryNetwork
                .getInstance()
                .getJsonApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        List<CategoryItemDTO> data = response.body();
                        adapter = new CategoriesAdapter(data,
                                MainActivity.this::onClickByItemEdit,
                                MainActivity.this::onClickByItemDelete);
                        rc.setAdapter(adapter);
                        //int a=5;
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable t) {

                    }
                });
    }

    private void onClickByItemDelete(CategoryItemDTO item){
        Toast.makeText(HomeApplication.getAppContext(),"Delete item Id: "+item.getId(),Toast.LENGTH_LONG).show();
    }
    private void onClickByItemEdit(CategoryItemDTO item){
        Toast.makeText(HomeApplication.getAppContext(),"Edit item name: ",Toast.LENGTH_LONG).show();
    }
}