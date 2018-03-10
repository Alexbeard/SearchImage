package ua.owox.test.searchimage.ui.search;

import java.util.List;

import ua.owox.test.searchimage.model.Image;
import ua.owox.test.searchimage.ui.base.IPresenter;
import ua.owox.test.searchimage.ui.base.IView;
import ua.owox.test.searchimage.ui.base.LoadingView;

public interface SearchContract {

    interface View extends IView, LoadingView {
        void onImagesLoadSuccess(List<Image> images);

        void onSearchSuccess(List<Image> images);

        void showEmpty(boolean show);
    }

    interface Presenter<V extends View> extends IPresenter<V> {
        void loadImages(int page);

        void searchImages(String query , int page);

        void loadNextPage(int page);

        void loadNextSearchPage(String query, int page);
    }
}
