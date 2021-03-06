package ua.owox.test.searchimage.ui.detail;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ua.owox.test.searchimage.data.repository.Repository;
import ua.owox.test.searchimage.ui.base.BasePresenter;


public class DetailPresenter<V extends DetailContract.View> extends BasePresenter<V> implements DetailContract.Presenter<V> {

    @Inject
    public DetailPresenter(Repository repository, CompositeDisposable compositeDisposable) {
        super(repository, compositeDisposable);
    }

}
