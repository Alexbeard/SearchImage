package ua.owox.test.searchimage.ui.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ua.owox.test.searchimage.data.repository.Repository;


public class BasePresenter<V extends IView> implements IPresenter<V> {


    protected final CompositeDisposable compositeDisposable;
    protected volatile V view;
    protected Repository model;


    @Inject
    public BasePresenter(Repository repository, CompositeDisposable compositeDisposable) {
        this.model = repository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void bindView(V view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        compositeDisposable.dispose();
        this.view = null;
    }
}
