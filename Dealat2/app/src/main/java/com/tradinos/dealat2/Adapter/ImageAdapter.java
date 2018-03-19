package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageDecoder;
import com.tradinos.dealat2.View.MasterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class ImageAdapter extends BaseAdapter {

    private List<Image> images, selectedImages = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, List<Image> images) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.images = images;
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public Image getItem(int i) {
        return this.images.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<Image> getSelectedImages() {
        return this.selectedImages;
    }

    public void addCapturedImage(Image image) {
        image.select();
        this.images.add(0, image);
        this.selectedImages.add(image);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = this.inflater.inflate(R.layout.row_image, null);
        view.setTag(i);
        //view.setLayoutParams(new FrameLayout.LayoutParams(220, 220));


        ImageView imageView = view.findViewById(R.id.imageView);
        final ImageView imageViewCheck = view.findViewById(R.id.imageViewCheck);

        imageView.setImageBitmap(null);
        ImageGetter task = new ImageGetter(imageView);
        task.execute(getItem(i).getPath());

        if (getItem(i).isSelected())
            imageViewCheck.setVisibility(View.VISIBLE);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Image clickedImage = images.get(Integer.parseInt(view.getTag().toString()));

                if (clickedImage.isSelected()) {
                    clickedImage.unselect();
                    selectedImages.remove(clickedImage);
                    imageViewCheck.setVisibility(View.INVISIBLE);
                } else {
                    if (Image.ImageCounter >= Image.MAX_IMAGES) {
                        ((MasterActivity) context).showMessageInToast(R.string.toastMaxImages);
                        return;
                    }

                    clickedImage.select();
                    selectedImages.add(clickedImage);
                    imageViewCheck.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }


    public class ImageGetter extends AsyncTask<String, Void, Bitmap> {
        private ImageView iv;

        public ImageGetter(ImageView v) {
            iv = v;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return new ImageDecoder().decodeSmallImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            iv.setImageBitmap(result);
        }
    }
}
