package ua.owox.test.searchimage.data.source;

import java.util.List;

import io.reactivex.Observable;
import ua.owox.test.searchimage.model.Image;

public interface ServerData {

    Observable<List<Image>> getPhotos(int page);

    Observable<List<Image>> search(String query, int page);

}
