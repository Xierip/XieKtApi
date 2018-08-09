package pl.xierip.xieapi.tab;

import lombok.Getter;

/**
 * Created by Xierip on 19.09.2017. Web: http://xierip.pl
 */
public class TabTexture {

  @Getter
  private final String value, signature;

  public TabTexture(String value, String signature) {
    this.value = value;
    this.signature = signature;
  }
}
