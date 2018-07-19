package pl.xierip.xieapi.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xierip on 24.11.2015.
 */
class Tag {
    @Getter
    @Setter
    private Map<String, String> sended;

    public Tag() {
        sended = new HashMap<>();
    }

    public String get(final String nick) {
        return sended.get(nick);
    }

    public boolean isSet(final String nick) {
        return sended.containsKey(nick);
    }

    public String set(final String nick, final String tag) {
        if (sended == null) {
            sended = new HashMap<>();
        }
        sended.put(nick, tag);
        return tag;
    }
}
