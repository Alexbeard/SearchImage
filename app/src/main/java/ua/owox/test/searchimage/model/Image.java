package ua.owox.test.searchimage.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

    @SerializedName("urls")
    @Expose
    private Urls urls;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("user")
    @Expose
    private User user;

    protected Image(Parcel in) {
        this.urls = in.readParcelable(Urls.class.getClassLoader());
        this.likes = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public Urls getUrls() {
        return urls;
    }

    public int getLikes() {
        return likes;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.urls, flags);
        dest.writeInt(this.likes);
        dest.writeParcelable(this.user, flags);
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}