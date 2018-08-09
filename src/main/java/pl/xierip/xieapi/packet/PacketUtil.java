package pl.xierip.xieapi.packet;

/**
 * @author Xieirp.
 */

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import java.util.Arrays;
import java.util.Collection;
import pl.xierip.xieapi.XieAPI;

public class PacketUtil {

  public static PacketContainer buildTeamPacket(String name, String display, int flag,
      String... members) {
    PacketContainer packet = XieAPI.getProtocolManager()
        .createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
    packet.getIntegers().write(0, flag);
    packet.getStrings().write(0, name).write(1, display);
    packet.getSpecificModifier(Collection.class).write(0, Arrays.asList(members));
    return packet;
  }

  public static PacketContainer buildTeamPacket(String name, String display, String prefix,
      String suffix, int flag, String... members) {
    PacketContainer packet = XieAPI.getProtocolManager()
        .createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
    packet.getIntegers().write(0, flag).write(1, flag);
    packet.getStrings().write(0, name).write(1, display).write(2, prefix).write(3, suffix);
    packet.getSpecificModifier(Collection.class).write(0, Arrays.asList(members));
    return packet;
  }
}
