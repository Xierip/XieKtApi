package pl.xierip.xieapi.particles;

import lombok.Data;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

/**
 * Created by xierip on 12.07.18. Web: http://xierip.pl
 */
public
@Data
class ParticleData {

  boolean distance;
  float offsetX;
  float offsetY;
  float offsetZ;
  float particleData;
  int count;
  int[] data;
  private EnumParticle enumParticle;

  public ParticleData(EnumParticle enumParticle, boolean distance, float offsetX, float offsetY,
      float offsetZ, float particleData, int count, int... data) {
    this.enumParticle = enumParticle;
    this.distance = distance;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.offsetZ = offsetZ;
    this.particleData = particleData;
    this.count = count;
    this.data = data;
  }

  public PacketPlayOutWorldParticles toPacket(float x, float y, float z) {
    return new PacketPlayOutWorldParticles(enumParticle, distance, x, y, z, offsetX, offsetY,
        offsetZ, particleData, count, data);
  }
}
