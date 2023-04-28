package com.example.sim.category;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sim.BaseActivity;
import com.example.sim.ChangeImageActivity;
import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.dto.category.CategoryCreateDTO;
import com.example.sim.dto.category.CategoryItemDTO;
import com.example.sim.service.CategoryNetwork;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.oginotihiro.cropview.CropView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    public static int SELECT_IMAGE_RESULT = 300;
    private ImageView avatar_image;
    private CategoryCreateDTO categoryTest=null;
    private Uri selectCropImage ;
    private TextInputLayout name_TextLayout;
    private TextInputLayout priority_TextLayout;
    private TextInputLayout desc_TextLayout;

    // Activityies
    Intent mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        avatar_image = findViewById(R.id.my_avatar);
        Button selectAvatar_btn = findViewById(R.id.btn_SelectFoto);
        Button addCategory_btn = findViewById(R.id.btn_Category_Add);
        name_TextLayout = findViewById(R.id.name_textField);
        priority_TextLayout = findViewById(R.id.priority_textField);
        desc_TextLayout = findViewById(R.id.description_textField);

        //Додаванна Активіті на яку перейду
        mainActivity = new Intent(this, MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        TextInputLayout arr[] = {name_TextLayout,name_TextLayout,name_TextLayout,};


        //Додати категорію
        addCategory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("-----Відправка данних на сервер. Створення нової Категорії");
                categoryTest = new CategoryCreateDTO();

                // Create DTO
                categoryTest.setName(name_TextLayout.getEditText().getText().toString());
                categoryTest.setImageBase64(uriGetBase64(selectCropImage));
                categoryTest.setPriority(Integer.parseInt(priority_TextLayout.getEditText().getText().toString()));
                categoryTest.setDescription(desc_TextLayout.getEditText().getText().toString());

                requestServer(categoryTest);
            }
        });

        //Обрати фото
        selectAvatar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(view);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_RESULT) {
            selectCropImage = data.getParcelableExtra("croppedUri");
            avatar_image.setImageURI(selectCropImage);
        }
    }

    void requestServer(CategoryCreateDTO model) {
        CategoryNetwork
                .getInstance()
                .getJsonApi()
                .addCategory(model)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response)  {
                        if (response.isSuccessful())
                        {
                            System.out.println("----Category Added successfuly");
                            startActivity(mainActivity);
                            finish();
                        }
                        else{
                            System.out.println("Server erroe");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("----Category added Error");

                    }
                });
    }

    public  void selectImage (View view)
    {
        Intent intent = new Intent(this, ChangeImageActivity.class);
        startActivityForResult(intent,SELECT_IMAGE_RESULT);
    }

    private String uriGetBase64(Uri uri) {
        try {
            Bitmap bitmap=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch(IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] byteArr = bytes.toByteArray();
            return Base64.encodeToString(byteArr, Base64.DEFAULT);

        } catch(Exception ex) {
            return null;
        }
    }
}

//TODO: Added Validation fields and click Button "Додати"