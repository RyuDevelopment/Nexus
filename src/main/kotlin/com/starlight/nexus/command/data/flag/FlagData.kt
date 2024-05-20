package com.starlight.nexus.command.data.flag

import com.starlight.nexus.command.data.Data

data class FlagData(
    val names: List<String>,
    val description: String,
    val defaultValue: Boolean,
    val methodIndex: Int
) : Data