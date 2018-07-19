package pl.xierip.xieapi.objects;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.enums.LogType;
import pl.xierip.xieapi.utils.InventoryUtil;
import pl.xierip.xieapi.utils.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Xierip on 17.08.2015.
 */
public
@Data
class EditingInventory {
    private XieCallback callback;
    private Config config;
    private UUID editor;
    private Inventory inventory;
    private Inventory inventoryUpdate = null;
    private List<ItemStack> itemStacks;
    private String path;

    public EditingInventory(final Player editor, final Inventory inventory, final List<ItemStack> itemStacks, final String path, final Config config, final Inventory inventoryUpdate, final XieCallback callback) {
        this.editor = editor.getUniqueId();
        this.inventory = inventory;
        this.itemStacks = itemStacks;
        this.path = path;
        this.config = config;
        this.inventoryUpdate = inventoryUpdate;
        this.callback = callback;
        this.itemStacks.forEach(this.inventory::addItem);
        editor.openInventory(this.inventory);
    }

    public EditingInventory(final Player editor, final Inventory inventory, final List<ItemStack> itemStacks, final String path, final Config config, final Inventory inventoryUpdate) {
        this.editor = editor.getUniqueId();
        this.inventory = inventory;
        this.itemStacks = itemStacks;
        this.path = path;
        this.config = config;
        this.inventoryUpdate = inventoryUpdate;
        this.itemStacks.forEach(this.inventory::addItem);
        editor.openInventory(this.inventory);
    }

    public EditingInventory(final Player editor, final Inventory inventory, final List<ItemStack> itemStacks, final String path, final Config config) {
        this.editor = editor.getUniqueId();
        this.inventory = inventory;
        this.itemStacks = itemStacks;
        this.path = path;
        this.config = config;
        this.itemStacks.forEach(this.inventory::addItem);
        editor.openInventory(this.inventory);
    }

    public void update() {
        this.itemStacks.clear();
        this.itemStacks.addAll(Arrays.stream(this.inventory.getContents()).filter(itemStack -> itemStack != null).collect(Collectors.toList()));
        this.config.set(this.path, this.itemStacks);
        this.config.save();
        if (this.inventoryUpdate != null) {
            if (this.inventoryUpdate.getSize() < InventoryUtil.getSize(this.itemStacks.size())) {
                this.inventoryUpdate.clear();
                XieAPI.getLogging().log(LogType.INFO, "Cannot update items inventory (" + StringUtil.stripColors(this.getInventoryUpdate().getTitle()) + ")");
                return;
            } else {
                this.inventoryUpdate.clear();
                for (int i = 0; i < this.itemStacks.size(); i++) {
                    inventoryUpdate.setItem(i, this.itemStacks.get(i));
                }
            }
        }
        if (callback != null) {
            try {
                callback.call();
            } catch (final Exception exception) {
                XieAPI.getLogging().log(LogType.EXCEPTION, "Error with execute callback in inventory manager", exception);
            }
        }
    }
}
