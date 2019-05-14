package com.example.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ImageAdapter.OnImageListener, View.OnClickListener {
    private ArrayList<Image> mImages;
    private ImageAdapter mAdapter;
    private RecyclerView mRecyclerImage;
    private DatabaseHelper mDatabaseHelper;
    private Button mButtonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        initRecycler();
    }

    private void initViews() {
        mRecyclerImage = findViewById(R.id.recycler_image);
        mButtonAdd = findViewById(R.id.button_add);
        mButtonAdd.setOnClickListener(this);
    }

    private void initRecycler() {
        mAdapter = new ImageAdapter(mImages);
        mAdapter.setListener(this);
        mRecyclerImage.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerImage.setAdapter(mAdapter);
    }

    private void initData() {
        mDatabaseHelper = new DatabaseHelper(this);
        mImages = mDatabaseHelper.getAllImage();
//        mImages.add(new Image("12/12/1997", R.drawable.temp));
//        mImages.add(new Image("11/11/1997", R.drawable.fifth_principle));
//        mImages.add(new Image("11/12/1997", R.drawable.fourth_pr));
//        mImages.add(new Image("13/12/1997", R.drawable.fist_principle));
//        mImages.add(new Image("14/12/1997", R.drawable.logo));
//        mImages.add(new Image("15/12/1997", R.drawable.second_principle));
//        mImages.add(new Image("16/12/1997", R.drawable.third_principle));
    }

    @Override
    public void onLongClickListener(Image image) {
        int index = mImages.indexOf(image);
        mImages.remove(image);
        mAdapter.notifyItemRemoved(index);
        mDatabaseHelper.deleteImage(image);
    }

    @Override
    public void onClickListener(Image image) {
        Intent intent = ImageDetailActivity.getIntent(this);
        intent.putExtra("image", image);
        intent.putExtra("position", mImages.indexOf(image));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.hasExtra("image_back")) {
                int pos = data.getIntExtra("position", 0);
                Image image = (Image) data.getSerializableExtra("image_back");
                mImages.remove(pos);
                mImages.add(pos, image);
                mAdapter.notifyItemChanged(pos);
                mDatabaseHelper.updateImage(image);
            }

        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Image image = new Image("1/1/2000", uri.toString());
            mImages.add(image);
            mAdapter.notifyDataSetChanged();
            mDatabaseHelper.addImage(image);

        }
    }

    @Override
    public void onClick(View v) {
        handleAdd();
    }

    private void handleAdd() {
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        intent.setType("image/*");

//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }


}
