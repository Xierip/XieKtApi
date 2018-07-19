package pl.xierip.xieapi.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.utils.StringUtil;

/**
 * @author Xierip on 24.11.2015.
 */
public class AsyncPreLoginListener implements Listener {
    @EventHandler
    private void onEvent(final AsyncPlayerPreLoginEvent event) {
        if (XieAPI.isDisabling()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, StringUtil.fixColors("&4Serwer jest restartowany!"));
        }
    }
}
