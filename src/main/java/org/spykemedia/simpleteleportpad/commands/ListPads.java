package org.spykemedia.simpleteleportpad.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ListPads implements CommandExecutor {

    private final Map<String, Location> teleportPads;

    public ListPads(Map<String, Location> teleportPads) {
        this.teleportPads = teleportPads;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof org.bukkit.entity.Player)) {
            commandSender.sendMessage("Dit command kan alleen in-game");
            return true;
        }

        Player p = (Player) commandSender;
        p.sendMessage("Beschikbare pads:");

        for (String padName : teleportPads.keySet()) {
            p.sendMessage("- " + padName);
        }

        return true;
    }
}
