package pl.xierip.xieapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Xierip on 2016-06-26.
 * DarkElite.pl ©
 */
public class StorageUtils {
    public static List<String> getListStringFromString(final String string) {
        final List<String> list = new ArrayList<>();
        if (string == null || string.equals("") || string.equals("NULL")) {
            return list;
        } else {
            return CollectionsUtil.deserializeList(string);
        }
    }

    public static List<UUID> getListUUIDFromString(final String string) {
        if (string == null || string.equals("") || string.equals("NULL")) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(CollectionsUtil.deserializeList(string).stream().map(UUID::fromString).collect(Collectors.toList()));
        }
    }
}
