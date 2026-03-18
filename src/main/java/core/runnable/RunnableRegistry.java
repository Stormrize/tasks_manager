package core.runnable;

import cli.Command;

import java.util.HashMap;
import java.util.Map;

public class RunnableRegistry {
    private static final Map<String, Runnable> runnableMap = new HashMap<>();

    static {
        runnableMap.put("space fact", new SpaceFact());
        runnableMap.put("random fact",new RandomFact());
        runnableMap.put("coding", new CodingChallange());
    }

    /**
     * Gibt den Befehl für den angegebenen Namen zurück.
     *
     * @param name Name des Befehls
     * @return {@link Command}-Instanz, oder {@code null}, wenn der Befehl nicht existiert
     */
    public static Runnable get(String name) {
        return runnableMap.get(name);
    }
}
