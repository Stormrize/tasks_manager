package core.runnable;

import core.runnable.CodingChallange.CodingChallange;
import core.runnable.RandomFact.RandomFact;
import core.runnable.SpaceFact.SpaceFact;
import core.runnable.WallpapersChanger.*;
import java.util.HashMap;
import java.util.Map;

public class RunnableRegistry {
    private static final Map<String, Runnable> runnableMap = new HashMap<>();

    static {
        runnableMap.put("space fact", new SpaceFact());
        runnableMap.put("random fact",new RandomFact());
        runnableMap.put("coding", new CodingChallange());
        runnableMap.put("hello world", new HelloWorldTest());
        runnableMap.put("wallpaper", new WallpaperChanger());
    }

    public static Runnable get(String name) {
        return runnableMap.get(name);
    }
}
