package pl.xierip.xiektapi.extensions

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Created by xierip on 09.07.18.
 * Web: http://xierip.pl
 */
inline fun CommandSender.sendColoredMessage(message: String) {
    this.sendMessage(message.fixColors())
}

inline fun Player.giveOrDropItem(itemStack: ItemStack) {
    this.giveOrDropItem(itemStack, this.location)
}

inline fun Player.giveOrDropItem(itemStack: ItemStack, location: Location) {
    this.inventory.addItem(itemStack).forEach { _, item -> location.world.dropItem(location, item) }
}

inline fun Player.giveOrDropItems(itemStacks: List<ItemStack>) {
    this.giveOrDropItems(itemStacks, this.location)
}

inline fun Player.giveOrDropItems(itemStacks: List<ItemStack>, location: Location) {
    for (itemStack in itemStacks) {
        itemStack.let { this.giveOrDropItem(itemStack, location) }
    }
}

inline fun Player.hasSpace(): Boolean {
    for (storageContent in this.inventory.storageContents) {
        if (storageContent == null) return true
        else if (storageContent.type == Material.AIR) return true
    }
    return false
}