package pl.xierip.xieapi.packet;

import com.comphenix.protocol.events.PacketContainer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.logging.Logging.LogType;

/**
 * @author Xierip on 24.11.2015.
 */
public class AsyncPacketThread extends BukkitRunnable {

  @Getter
  private static BlockingQueue<XiePacket> queue;

  public AsyncPacketThread() {
    queue = new ArrayBlockingQueue<>(16384);
  }

  public static void addPacket(Player player, PacketContainer packetContainer) {
    queue.add(new XiePacket(packetContainer, player));
  }

  public static void sendPackets() {
    queue.forEach(XiePacket::send);
    queue.clear();
  }

  @Override
  public void run() {
    while (true) {
      try {
        XiePacket take = queue.take();
        if (take != null) {
          take.send();
        }
      } catch (Exception e) {
        XieAPI.getLogging().log(LogType.EXCEPTION, "Error with send packet", e);
      }
    }
  }
}
