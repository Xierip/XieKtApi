package pl.xierip.xiektapi.extensions

import org.bukkit.ChatColor

/**
 * Created by xierip on 09.07.18.
 * Web: http://xierip.pl
 */
inline fun String.fixColors(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}