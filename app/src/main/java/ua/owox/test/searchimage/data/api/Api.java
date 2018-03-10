package ua.owox.test.searchimage.data.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.owox.test.searchimage.model.Image;
import ua.owox.test.searchimage.model.SearchResponse;

public interface Api {

    @GET("photos?per_page=20")
    Observable<List<Image>> getPhotos(@Query("page") int page);

    @GET("/search/photos?per_page=20")
    Observable<SearchResponse> search(@Query("query") String query, @Query("page") int page);

}
