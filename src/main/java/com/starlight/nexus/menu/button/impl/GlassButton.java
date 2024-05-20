package com.starlight.nexus.menu.button.impl;

import com.starlight.nexus.menu.button.Button;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
public class GlassButton extends Button {

    private int data;

    @Override
    public String getName(Player var1) {
        return translate("&7");
    }

    @Override
    public List<String> getDescription(Player player) {
        return null;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.STAINED_GLASS_PANE;
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte) data;
    }

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

}