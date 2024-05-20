package com.starlight.nexus.util.nms.particle

import org.bukkit.Color
import org.bukkit.Location

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

data class ParticleMeta(val location: Location, val particle: Any) {

    constructor(
        location: Location,
        particle: Any,
        deltaX: Float,
        deltaY: Float,
        deltaZ: Float,
        speed: Float,
        amount: Int
    ) : this(location, particle) {
        this.offsetX = deltaX
        this.offsetY = deltaY
        this.offsetZ = deltaZ
        this.speed = speed
        this.amount = amount
    }

    constructor(
        location: Location,
        particle: Any,
        color: Color,
        speed: Float,
        amount: Int
    ) : this(location, particle) {
        this.offsetX = com.starlight.nexus.util.Color.convertColorValue(color.red.toDouble()).toFloat()
        this.offsetY = com.starlight.nexus.util.Color.convertColorValue(color.green.toDouble()).toFloat()
        this.offsetZ = com.starlight.nexus.util.Color.convertColorValue(color.blue.toDouble()).toFloat()
        this.speed = speed
        this.amount = amount
    }

    var offsetX = 0.0F
    var offsetY = 0.0F
    var offsetZ = 0.0F
    var speed = 1.0F
    var amount = 1

}