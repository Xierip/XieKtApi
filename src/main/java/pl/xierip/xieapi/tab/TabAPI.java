package pl.xierip.xieapi.tab;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import org.bukkit.entity.Player;
import pl.xierip.xieapi.utils.StringUtil;

/**
 * Created by xierip on 23.08.17. Web: http://xierip.pl
 */
public class TabAPI {

  @Getter
  private static PacketContainer headerAndFooterPacket;
  @Getter
  private static TabCellShape[] tabCellShapeMap;
  @Getter
  private static Map<UUID, TabList> tabLists = new ConcurrentHashMap<>();

  public static void clearTabLists() {
    for (TabList tabList : tabLists.values()) {
      try {
        ProtocolLibrary.getProtocolManager()
            .sendServerPacket(tabList.getOwner(), tabList.getRemovePacket());
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  public static TabList createOrGetTabListForPlayer(final Player player) {
    if (tabLists.containsKey(player.getUniqueId())) {
      return tabLists.get(player.getUniqueId());
    }
    final TabList list = new TabList(player);
    tabLists.put(player.getUniqueId(), list);
    return list;
  }

  public static Map<Player, PacketContainer> disable() {
    Map<Player, PacketContainer> map = new HashMap<>();
    for (TabList tabList : tabLists.values()) {
      Player owner = tabList.getOwner();
      if (owner == null || !owner.isOnline()) {
        continue;
      }
      PacketContainer removePacket = tabList.getRemovePacket();
      if (removePacket == null) {
        continue;
      }
      map.put(owner, removePacket);
    }
    tabLists.clear();
    return map;
  }

  public static TabList getPlayerTabList(final Player player) {
    return tabLists.getOrDefault(player.getUniqueId(), null);
  }

  public static void loadData(final TabCellShape[] tabCellShapeMap, String header, String footer) {
    TabAPI.tabCellShapeMap = tabCellShapeMap;
    headerAndFooterPacket = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
    headerAndFooterPacket.getModifier().writeDefaults();
    headerAndFooterPacket.getChatComponents()
        .write(0, WrappedChatComponent.fromText(StringUtil.fixColors(header)));
    headerAndFooterPacket.getChatComponents()
        .write(1, WrappedChatComponent.fromText(StringUtil.fixColors(footer)));
  }

  public static void quitPlayer(final Player player) {
    TabList tabList = tabLists.remove(player.getUniqueId());
    if (tabList == null) {
      return;
    }
    tabList.clear();
  }
}
