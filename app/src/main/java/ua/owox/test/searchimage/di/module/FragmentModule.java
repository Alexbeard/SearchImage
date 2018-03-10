package ua.owox.test.searchimage.di.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ua.owox.test.searchimage.data.repository.Repository;
import ua.owox.test.searchimage.di.PerFragment;
import ua.owox.test.searchimage.ui.detail.DetailContract;
import ua.owox.test.searchimage.ui.detail.DetailPresenter;
import ua.owox.test.searchimage.ui.search.SearchContract;
import ua.owox.test.searchimage.ui.search.SearchPresenter;


@Module
public class FragmentModule {


    @Provides
    @PerFragment
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    @PerFragment
    SearchContract.Presenter<SearchContract.View> provideSearchPresenter(Repository repository, CompositeDisposable compositeDisposable) {
        return new SearchPresenter<>(repository, compositeDisposable);
    }

    @Provides
    @PerFragment
    DetailContract.Presenter<DetailContract.View> provideDetailPresenter(Repository repository, CompositeDisposable compositeDisposable) {
        return new DetailPresenter<>(repository, compositeDisposable);
    }

}
