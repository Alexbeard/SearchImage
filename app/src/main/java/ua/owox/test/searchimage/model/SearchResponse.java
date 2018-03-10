package ua.owox.test.searchimage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @SerializedName("results")
    @Expose
    private List<Image> searchResults;

    public List<Image> getSearchResults() {
        return searchResults;
    }
}
