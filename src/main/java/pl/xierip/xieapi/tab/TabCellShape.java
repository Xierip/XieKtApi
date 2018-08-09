package pl.xierip.xieapi.tab;

import lombok.Getter;

/**
 * Created by Xierip on 19.09.2017. Web: http://xierip.pl
 */
public class TabCellShape {

  @Getter
  private final int ping;
  @Getter
  private final int slot;
  @Getter
  private final TabTexture texture;

  public TabCellShape(final int ping, final int slot, final TabTexture texture) {
    this.ping = ping;
    this.slot = slot;
    this.texture = texture;
  }
}
