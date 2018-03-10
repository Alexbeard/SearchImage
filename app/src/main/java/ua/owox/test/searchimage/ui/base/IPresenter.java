package ua.owox.test.searchimage.ui.base;

public interface IPresenter<V extends IView> {

    void bindView(V view);

    void unbindView();
}
