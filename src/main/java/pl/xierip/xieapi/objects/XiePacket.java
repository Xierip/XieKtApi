package pl.xierip.xieapi.objects;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.bukkit.entity.Player;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.enums.LogType;

/**
 * @author Xierip on 24.11.2015.
 */
public class XiePacket {
    @Getter
    private PacketContainer packetContainer;
    @Getter
    private Player player;

    public XiePacket(PacketContainer packetContainer, Player player) {
        this.packetContainer = packetContainer;
        this.player = player;
    }

    public void send() {
        try {
            XieAPI.getProtocolManager().sendServerPacket(this.player, this.packetContainer);
        } catch (Exception e) {
            XieAPI.getLogging().log(LogType.EXCEPTION, "Error with send packet", e);
        }
        this.player = null;
        this.packetContainer = null;
    }
}
