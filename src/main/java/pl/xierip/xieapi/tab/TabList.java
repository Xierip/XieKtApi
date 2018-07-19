package pl.xierip.xieapi.tab;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xierip on 19.09.2017.
 * Web: http://xierip.pl
 */
public class TabList {
    @Getter
    private Player owner;
    private boolean send = false;
    private TabCell[] tabCells = new TabCell[80];

    public TabList(final Player player) {
        this.owner = player;
        for (int i = 0; i < TabAPI.getTabCellShapeMap().length; i++) {
            tabCells[i] = new TabCell(TabAPI.getTabCellShapeMap()[i]);
        }
    }

    public void clear() {
        owner = null;
        tabCells = null;
    }

    public PacketContainer getHeaderAndFooterPacket() {
        return TabAPI.getHeaderAndFooterPacket();
    }

    public PacketContainer getRemovePacket() {
        if (!send) return null;
        final List<PlayerInfoData> playerInfoDataList = new ArrayList<>();
        for (final TabCell cell : tabCells) {
            playerInfoDataList.add(cell.getPlayerInfoData());
        }
        send = false;
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packetContainer.getModifier().writeDefaults();
        packetContainer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        packetContainer.getPlayerInfoDataLists().write(0, playerInfoDataList);
        return packetContainer;

    }

    public PacketContainer getSendPacket() {
        if (send) return null;
        final List<PlayerInfoData> playerInfoDataList = new ArrayList<>();
        for (final TabCell cell : tabCells) {
            playerInfoDataList.add(cell.getPlayerInfoData());
        }
        send = true;
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packetContainer.getModifier().writeDefaults();
        packetContainer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        packetContainer.getPlayerInfoDataLists().write(0, playerInfoDataList);
        return packetContainer;
    }

    public PacketContainer getSlotUpdatePacket(int slot) {
        if (!send) return null;
        if (tabCells.length <= slot) return null;
        final List<PlayerInfoData> playerInfoDataList = new ArrayList<>();
        playerInfoDataList.add(tabCells[slot].getPlayerInfoData());
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packetContainer.getModifier().writeDefaults();
        packetContainer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);
        packetContainer.getPlayerInfoDataLists().write(0, playerInfoDataList);
        return packetContainer;
    }

    public PacketContainer getUpdatePacket() {
        if (!send) return null;
        final List<PlayerInfoData> playerInfoDataList = new ArrayList<>();
        for (final TabCell cell : tabCells) {
            if (cell.isUpdatedText()) continue;
            playerInfoDataList.add(cell.getPlayerInfoData());
        }
        if (playerInfoDataList.isEmpty()) return null;

        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packetContainer.getModifier().writeDefaults();
        packetContainer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);
        packetContainer.getPlayerInfoDataLists().write(0, playerInfoDataList);
        return packetContainer;
    }

    public TabCell setSlot(final int slot, final String text) {
        if (slot > 79 || slot < 0) return null;
        final TabCell tabCell = tabCells[slot];
        tabCell.setText(text);
        return tabCell;
    }
}
