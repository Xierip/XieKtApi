package pl.xierip.xieapi.thread;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.bukkit.entity.Player;
import pl.xierip.xieapi.objects.XiePacket;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.enums.LogType;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Xierip on 24.11.2015.
 */
public class AsyncPacketThread extends Thread {
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
                if (take != null)
                    take.send();
            } catch (Exception e) {
                XieAPI.getLogging().log(LogType.EXCEPTION, "Error with send packet", e);
            }
        }
    }
}
