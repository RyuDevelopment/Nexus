package com.starlight.nexus.util

import com.starlight.nexus.menu.Menu
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.entity.Player
import java.util.*
import java.util.function.Predicate

class ConversationBuilder {

    private val text = arrayOfNulls<String>(4)

    var menu: Menu? = null
    var async: Boolean = false
    var predicate: Predicate<Array<String>>? = null

    lateinit var position: Location

    fun withText(slot: Int, text: String): ConversationBuilder {
        if (slot in 0..3) {
            this.text[slot] = text
        }
        return this
    }

    fun executeAsync(): ConversationBuilder {
        this.async = true
        return this
    }

    fun withFallBackMenu(menu: Menu): ConversationBuilder {
        this.menu = menu
        return this
    }

    fun onFinish(predicate: Predicate<Array<String>>): ConversationBuilder {
        this.predicate = predicate
        return this
    }

    fun build(player: Player) {
        if (predicate == null) {
            return
        }

        if (menu != null) {
            player.closeInventory()
        }

        position = Location(player.world, player.location.x, 255.0, player.location.z)

        val signBlock = position.block
        signBlock.type = Material.WALL_SIGN

        val signState = signBlock.state as Sign
        signState.update()

        player.sendSignChange(position, text)

        cache[player.uniqueId] = this
    }

    companion object {
        val cache = HashMap<UUID, ConversationBuilder>()
        val NBT_FORMAT = "{\"text\":\"%s\"}"
    }
}