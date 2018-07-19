package pl.xierip.xieapi.objects;

import lombok.Data;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by xierip on 12.07.18.
 * Web: http://xierip.pl
 */
public
@Data
class ParticlePacket {
    private EnumParticle enumParticle;
    boolean distance;
    float offsetX;
    float offsetY;
    float offsetZ;
    float x;
    float y;
    float z;
    float particleData;
    int count;
    int[] data;

    public ParticlePacket(EnumParticle enumParticle, boolean distance, Location location, float offsetX, float offsetY, float offsetZ, float particleData, int count, int... data) {
        this.enumParticle = enumParticle;
        this.distance = distance;
        this.x = (float) location.getX();
        this.y = (float) location.getY();
        this.z = (float) location.getZ();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.particleData = particleData;
        this.count = count;
        this.data = data;
    }

    public PacketPlayOutWorldParticles toPacket() {
        return new PacketPlayOutWorldParticles(enumParticle, distance, x, y, z, offsetX, offsetY, offsetZ, particleData, count, data);
    }

    public void send(Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.networkManager.sendPacket(toPacket());
    }
}
