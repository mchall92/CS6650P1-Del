package Server;

import java.util.HashMap;

public class KeyValue {

    HashMap<String, String> map;

    public KeyValue() {
        map = new HashMap<>();
    }

    public boolean put(String key, String value) {
        if (key != null && key.length() > 0) {
            map.put(key, value);
            return true;
        }
        return false;
    }

    public String get(String key) {
        if (key != null && map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    public void delete(String key) {
        if (key != null && map.containsKey(key)) {
            map.remove(key);
        }
    }

    public boolean containsKey(String key) {
        return (key != null) && (key.length() > 0) && map.containsKey(key);
    }
}
