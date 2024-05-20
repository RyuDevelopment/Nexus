package com.starlight.nexus.command.data.parameter.impl

import com.starlight.nexus.command.data.parameter.ParameterType
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BooleanParameterType : ParameterType<Boolean?> {

    private val map: MutableMap<String, Boolean> = mutableMapOf()

    init {
        map["on"] = true
        map["off"] = false

        map["yes"] = true
        map["no"] = false

        map["true"] = true
        map["false"] = false
    }

    override fun transform(sender: CommandSender, source: String): Boolean? {
        if (!this.map.containsKey(source.toLowerCase())) {
            sender.sendMessage("${ChatColor.RED} $source is not a valid boolean.")
            return null
        }

        return this.map[source.toLowerCase()]
    }

    override fun tabComplete(sender: Player, flags: Set<String>, source: String): List<String> {
        return ArrayList(this.map.keys)
    }

}