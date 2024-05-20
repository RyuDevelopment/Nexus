package com.starlight.nexus.menu.button.impl;

import com.starlight.nexus.menu.button.Button;
import com.starlight.nexus.util.Callback;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BooleanButton extends Button {

    private boolean confirm;
    private Callback<Boolean> callback;

    public void clicked(Player player, int i, ClickType clickType) {

        if (this.confirm) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20.0F, 0.1F);
        } else {
            player.playSound(player.getLocation(), Sound.DIG_GRAVEL, 20.0F, 0.1F);
        }

        player.closeInventory();

        this.callback.callback(this.confirm);
    }

    @Override
    public String getName(Player player) {
        return this.confirm ? ChatColor.GREEN + "Confirm" : ChatColor.RED + "Cancel";
    }

    @Override
    public List<String> getDescription(Player player) {
        return new ArrayList();
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte)(this.confirm ? 5 : 14);
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.WOOL;
    }}
