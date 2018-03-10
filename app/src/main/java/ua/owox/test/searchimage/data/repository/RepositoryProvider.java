package ua.owox.test.searchimage.data.repository;


import ua.owox.test.searchimage.data.source.ServerData;


public class RepositoryProvider {

    private static Repository sImageRepository;

    public static Repository getImageRepository(ServerData server) {
        if (sImageRepository == null) {
            sImageRepository = new ImageRepository(server);
        }
        return sImageRepository;
    }
}
