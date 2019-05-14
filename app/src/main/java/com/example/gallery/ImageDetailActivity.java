package com.example.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class ImageDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private int mPosition;
    private Image mImage;
    private ImageView mImageDetail;
    private Button mButtonUpdate;
    private EditText mTextDate;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        getIncomingIntent();
        initViews();
        registerEvents();
    }

    private void registerEvents() {
        mButtonUpdate.setOnClickListener(this);
    }

    private void initViews() {
        mImageDetail = findViewById(R.id.image_detail);
        mButtonUpdate = findViewById(R.id.button_update);
        mTextDate = findViewById(R.id.text_date);


        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(mImage.getPath()));
            mImageDetail.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mTextDate.setHint(mImage.getDate());

    }

    private void getIncomingIntent() {
        mIntent = getIntent();
        mImage = (Image) mIntent.getSerializableExtra("image");
        mPosition = mIntent.getIntExtra("position", 0);


    }

    public static Intent getIntent(Context context) {
        return new Intent(context, ImageDetailActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update: {
                handleUpdate();
            }

        }

    }

    private void handleUpdate() {
        if (mTextDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "This field must be not empty!", Toast.LENGTH_SHORT).show();
        } else {
            mImage.setDate(mTextDate.getText().toString());
            mIntent.putExtra("image_back", mImage);
            mIntent.putExtra("pos", mPosition);
            setResult(1, mIntent);
            Toast.makeText(this, "Update successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
