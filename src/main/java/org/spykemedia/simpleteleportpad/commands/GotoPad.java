package org.spykemedia.simpleteleportpad.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.spykemedia.simpleteleportpad.helpers.EnergySystem;

import java.util.Map;

public class GotoPad implements CommandExecutor {
    private final Map<String, Location> teleportPads;
    private final EnergySystem energySystem;

    public GotoPad(Map<String, Location> teleportPads, EnergySystem energySystem) {
        this.teleportPads = teleportPads;
        this.energySystem = energySystem;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof org.bukkit.entity.Player)) {
            commandSender.sendMessage("Dit command kan alleen in-game");
            return true;
        }

        Player p = (Player) commandSender;
        if (strings.length != 1) {
            p.sendMessage("Gebruik /pad <naam>");
            return false;
        }

        String padName = strings[0];
        Location padLocation = teleportPads.get(padName);

        if (padLocation != null) {
            if (!energySystem.canTeleport(padName)) {
                p.sendMessage("Pad heeft te weinig energie.");
                return true;
            }

            p.teleport(padLocation);
            energySystem.useEnergy(padName);
            p.sendMessage("Je bent geteleporteerd!");


        } else {
            p.sendMessage("Geen pad gevonden met deze naam.");
        }
        return false;
    }
}
