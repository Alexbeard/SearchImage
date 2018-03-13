package ua.owox.test.searchimage.ui.detail;

import android.graphics.Bitmap;

import ua.owox.test.searchimage.ui.base.IPresenter;
import ua.owox.test.searchimage.ui.base.IView;

public interface DetailContract {

    interface View extends IView {
    }

    interface Presenter<V extends View> extends IPresenter<V> {
    }
}
