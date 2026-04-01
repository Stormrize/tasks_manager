package core.runnable.WallpapersChanger;

import storage.Data;

public class WallpaperHelper implements Runnable {
    public  void run() {
        Data.deleteWallpapersURL();
        Data.saveWallpapersURL("space", 2);
    }
}