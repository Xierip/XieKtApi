package pl.xierip.xieapi.tab;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.ChatColor;


/**
 * Created by xierip on 23.08.17. Web: http://xierip.pl
 */
public class TabCell {

  private final TabCellShape tabCellShape;
  private WrappedGameProfile profile;
  private String text;
  @Getter
  private boolean updatedText = false, send = false;

  public TabCell(final TabCellShape tabCellShape) {
    this.tabCellShape = tabCellShape;
    this.text = getNullSlot(tabCellShape.getSlot());
    profile = new WrappedGameProfile(UUID.randomUUID(),
        "!!!!!!!!!!!!!!" + (tabCellShape.getSlot() < 10 ? "0" : "") + tabCellShape.getSlot());
    profile.getProperties().put("textures",
        new WrappedSignedProperty("textures", tabCellShape.getTexture().getValue(),
            tabCellShape.getTexture().getSignature()));
    new PlayerInfoData(profile, tabCellShape.getPing(), EnumWrappers.NativeGameMode.NOT_SET,
        WrappedChatComponent.fromText(""));
  }

  private static String getNullSlot(final int slot) {
    return slot >= 10 ? "§" + String.valueOf(slot / 10) + "§" + String.valueOf(slot % 10)
        : "§" + String.valueOf(slot);
  }

  public PlayerInfoData getPlayerInfoData() {
    return new PlayerInfoData(profile, tabCellShape.getPing(), EnumWrappers.NativeGameMode.NOT_SET,
        WrappedChatComponent.fromText(text));
  }

  public TabCell setText(final String text) {
    String newText =
        getNullSlot(tabCellShape.getSlot()) + ChatColor.translateAlternateColorCodes('&', text);
    if (this.text.equals(newText)) {
      return this;
    }
    this.text = newText;
    updatedText = false;
    return this;
  }
}
