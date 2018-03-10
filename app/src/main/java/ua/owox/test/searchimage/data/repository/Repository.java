package ua.owox.test.searchimage.data.repository;

import java.util.List;

import io.reactivex.Observable;
import ua.owox.test.searchimage.model.Image;


public interface Repository {

    Observable<List<Image>> get(int page);

    Observable<List<Image>> search(String query, int page);

}
