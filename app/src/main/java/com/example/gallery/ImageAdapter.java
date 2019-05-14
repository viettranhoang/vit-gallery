package com.example.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Image> mImages;
    private OnImageListener mListener;

    public void setListener(OnImageListener listener) {
        mListener = listener;
    }

    public ImageAdapter(ArrayList<Image> images) {
        mImages = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item_layout,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mImages.get(i));
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private ImageView mImageSmall;
        private TextView mTextDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            registerEvents();
        }

        private void registerEvents() {
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void initViews(View itemView) {
            mImageSmall = itemView.findViewById(R.id.image_crop);
            mTextDate = itemView.findViewById(R.id.text_create_date);
        }

        public void bindData(Image image) {
            mTextDate.setText(image.getDate());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(image.getPath()));
                mImageSmall.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
//            Glide.with(mContext)
//                    .load(image.getPath())
//                    .into(mImageSmall);

        }

        @Override
        public boolean onLongClick(View v) {
            mListener.onLongClickListener(mImages.get(getAdapterPosition()));
            return true;
        }

        @Override
        public void onClick(View v) {
            mListener.onClickListener(mImages.get(getAdapterPosition()));
        }
    }

    public void update(ArrayList<Image> images) {
        mImages.addAll(images);
        notifyDataSetChanged();
    }

    public interface OnImageListener {
        void onLongClickListener(Image image);

        void onClickListener(Image image);
    }
}
