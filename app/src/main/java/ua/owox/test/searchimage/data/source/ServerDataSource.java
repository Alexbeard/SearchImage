package ua.owox.test.searchimage.data.source;

import java.util.List;

import io.reactivex.Observable;
import ua.owox.test.searchimage.data.api.Api;
import ua.owox.test.searchimage.model.Image;
import ua.owox.test.searchimage.model.SearchResponse;

public class ServerDataSource implements ServerData {

    private Api api;

    public ServerDataSource(Api api) {
        this.api = api;
    }


    @Override
    public Observable<List<Image>> getPhotos(int page) {
        return api.getPhotos(page);
    }

    @Override
    public Observable<List<Image>> search(String query, int page) {
        return api.search(query, page)
                .map(SearchResponse::getSearchResults);
    }
}
