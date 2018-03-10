package ua.owox.test.searchimage.data.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ua.owox.test.searchimage.BuildConfig;


public class ApiKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .addHeader("Accept-Version", "v1")
                .addHeader("Authorization", "Client-ID " + BuildConfig.API_KEY);
        Request request = builder.build();

        return chain.proceed(request);
    }
}
