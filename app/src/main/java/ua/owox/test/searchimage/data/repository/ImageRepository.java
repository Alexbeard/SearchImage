package ua.owox.test.searchimage.data.repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.owox.test.searchimage.data.source.ServerData;
import ua.owox.test.searchimage.model.Image;


public class ImageRepository implements Repository {

    ServerData server;

    public ImageRepository(ServerData server) {
        this.server = server;
    }

    @Override
    public Observable<List<Image>> get(int page) {
        return server.getPhotos(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Image>> search(String query, int page) {
        return server.search(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
