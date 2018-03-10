package ua.owox.test.searchimage.ui.search;


import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ua.owox.test.searchimage.data.repository.Repository;
import ua.owox.test.searchimage.ui.base.BasePresenter;

public class SearchPresenter<V extends SearchContract.View> extends BasePresenter<V> implements SearchContract.Presenter<V> {


    @Inject
    public SearchPresenter(Repository repository, CompositeDisposable compositeDisposable) {
        super(repository, compositeDisposable);
    }

    @Override
    public void loadImages(int page) {
        compositeDisposable.add(model.get(page)
                .doOnSubscribe(__ -> {
                    view.showProgress(true);
                    view.showEmpty(false);
                })
                .doOnTerminate(() -> view.showProgress(false))
                .doOnNext(images -> view.showEmpty(images.isEmpty()))
                .subscribe(
                        images -> view.onImagesLoadSuccess(images),
                        throwable -> {
                            Logger.d(throwable.getMessage());
                            view.showEmpty(true);
                        }
                ));
    }

    @Override
    public void loadNextPage(int page) {
        compositeDisposable.add(model.get(page)
                .subscribe(
                        images -> view.onImagesLoadSuccess(images),
                        throwable -> Logger.d(throwable.getMessage())
                ));
    }

    @Override
    public void searchImages(String query, int page) {
        compositeDisposable.add(model.search(query, page)
                .doOnSubscribe(__ -> {
                    view.showProgress(true);
                    view.showEmpty(false);
                })
                .doOnTerminate(() -> view.showProgress(false))
                .doOnNext(images -> view.showEmpty(images.isEmpty()))
                .subscribe(
                        searchImages -> view.onSearchSuccess(searchImages),
                        throwable -> {
                            Logger.d(throwable.getMessage());
                            view.showEmpty(true);
                        }
                ));
    }

    @Override
    public void loadNextSearchPage(String query, int page) {
        compositeDisposable.add(model.search(query, page)
                .subscribe(
                        searchImages -> view.onImagesLoadSuccess(searchImages),
                        throwable -> Logger.d(throwable.getMessage())
                ));
    }
}
