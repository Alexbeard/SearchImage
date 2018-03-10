package ua.owox.test.searchimage.data.source;

import ua.owox.test.searchimage.data.api.Api;


public class ServerDataProvider {

    private static ServerData sServerData;

    public static ServerData getServerData(Api api) {
        if (sServerData == null) {
            sServerData = new ServerDataSource(api);
        }
        return sServerData;
    }
}
