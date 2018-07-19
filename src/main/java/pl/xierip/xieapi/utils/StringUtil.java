package pl.xierip.xieapi.utils;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by Xierip on 2016-06-25.
 * DarkElite.pl ©
 */
public class StringUtil {
    public static Pattern REPLACE_ALL_PATTERN = Pattern.compile("(?<!&)&([0-9a-fk-orA-FK-OR])");
    public static Pattern REPLACE_COLOR_PATTERN = Pattern.compile("(?<!&)&([0-9a-fA-F])");
    public static Pattern REPLACE_FORMAT_PATTERN = Pattern.compile("(?<!&)&([l-orL-OR])");
    public static Pattern REPLACE_MAGIC_PATTERN = Pattern.compile("(?<!&)&([Kk])");
    public static Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
    public static Pattern VANILLA_COLOR_PATTERN = Pattern.compile("§+[0-9A-Fa-f]");
    public static Pattern VANILLA_FORMAT_PATTERN = Pattern.compile("§+[L-ORl-or]");
    public static Pattern VANILLA_MAGIC_PATTERN = Pattern.compile("§+[Kk]");
    public static Pattern VANILLA_PATTERN = Pattern.compile("§+[0-9A-FK-ORa-fk-or]?");

    public static boolean contains(final String word, final String... strings) {
        for (final String string : strings) {
            if (string.equalsIgnoreCase(word))
                return true;
        }
        return false;
    }

    public static String escapeSpecialRegexChars(final String str) {
        return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
    }

    public static String fixColors(final String string) {
        return replace(replace(ChatColor.translateAlternateColorCodes('&', string), ">>", "\u00bb"), "<<", "\u00ab");
    }

    public static List<String> fixColors(final List<String> strings) {
        return strings.stream().map(StringUtil::fixColors).collect(toList());
    }

    public static boolean isAlphaNumeric(final String s) {
        return s.matches("^[a-zA-Z0-9_]*$");
    }

    public static boolean isAlphaNumericWithSpace(final String s) {
        return s.matches("^[a-zA-Z0-9\\-\\s]+$");
    }

    public static String normalizeString(final String str) {
        final String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static List<String> replace(final List<String> list, String... replace) {
        return list.stream().map(s -> replace(s, replace)).collect(Collectors.toList());
    }


    public static String replace(String text, final Map<String, String> map) {
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            text = replace(text, entry.getKey(), entry.getValue());
        }
        return text;
    }

    public static String replace(String text, String... replace) {
        if (replace.length == 0) {
            return text;
        }
        if (replace.length % 2 != 0) {
            replace = Arrays.copyOfRange(replace, 0, replace.length - 1);
        }
        for (int i = 0; i < replace.length; i = i + 2) {
            text = replace(text, replace[i], replace[i + 1]);
        }
        return text;
    }

    public static String replace(final String text, final String searchString, final String replacement) {
        if (text == null || text.isEmpty() || searchString.isEmpty() || replacement == null) {
            return text;
        }
        int start = 0;
        int max = -1;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        final int replacedLength = searchString.length();
        int increase = replacement.length() - replacedLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        final StringBuilder sb = new StringBuilder(text.length() + increase);
        while (end != -1) {
            sb.append(text.substring(start, end)).append(replacement);
            start = end + replacedLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        sb.append(text.substring(start));
        return sb.toString();
    }

    public static TextComponent[] replaceStringToTextComponent(final String string, final String from, final TextComponent to) {
        final String from1 = escapeSpecialRegexChars(from);
        if (string.contains(from)) {
            final List<TextComponent> list = new ArrayList<>();
            final String[] split = string.split(from1);
            list.add(new TextComponent(fixColors(split[0])));
            if (split.length == 2) {
                list.add(to);
                list.add(new TextComponent(fixColors(ChatColor.getLastColors(split[0]) + split[1])));
                return list.toArray(new TextComponent[list.size()]);
            }
            for (int i = 1; i < split.length; i++) {
                list.add(to);

                list.add(new TextComponent(fixColors(ChatColor.getLastColors(split[i - 1]) + split[i])));
            }
            return list.toArray(new TextComponent[list.size()]);
        } else {
            return new TextComponent[]{new TextComponent(StringUtil.fixColors(string))};
        }
    }

    public static String stringColors(final String input, final Pattern pattern) {
        if (input == null) {
            return null;
        }
        return pattern.matcher(input).replaceAll("");
    }

    public static String stripColors(final String string) {
        return ChatColor.stripColor(string);
    }

    public static List<String> stripColors(final List<String> strings) {
        return strings.stream().map(StringUtil::stripColors).collect(toList());
    }
}
