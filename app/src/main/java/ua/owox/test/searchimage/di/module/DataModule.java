package ua.owox.test.searchimage.di.module;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.owox.test.searchimage.BuildConfig;
import ua.owox.test.searchimage.data.api.Api;
import ua.owox.test.searchimage.data.api.ApiKeyInterceptor;
import ua.owox.test.searchimage.data.repository.Repository;
import ua.owox.test.searchimage.data.repository.RepositoryProvider;
import ua.owox.test.searchimage.data.source.ServerData;
import ua.owox.test.searchimage.data.source.ServerDataProvider;

@Module
public class DataModule {

    Context context;

    public DataModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Repository provideImageRepository(ServerData server) {
        return RepositoryProvider.getImageRepository(server);
    }

    @Provides
    @Singleton
    ServerData provideServerData(Api api) {
        return ServerDataProvider.getServerData(api);
    }


    @Provides
    @Singleton
    Api createService(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor())
                .addInterceptor(new ChuckInterceptor(context))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
