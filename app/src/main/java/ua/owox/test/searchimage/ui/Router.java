package ua.owox.test.searchimage.ui;

import android.widget.ImageView;

import ua.owox.test.searchimage.model.Image;

public interface Router {


    void goToDetail(ImageView imageView, Image image);

    void back();
}
