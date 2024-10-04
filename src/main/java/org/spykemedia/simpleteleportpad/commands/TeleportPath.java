package org.spykemedia.simpleteleportpad.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.spykemedia.simpleteleportpad.helpers.PathFinding;
import org.spykemedia.simpleteleportpad.helpers.TeleportPadNetwork;

import java.util.List;
import java.util.Map;

public class TeleportPath implements CommandExecutor {

    private final TeleportPadNetwork padNetwork;
    private final PathFinding pathFinding;
    private final Map<String, Location> teleportPads;

    public TeleportPath(TeleportPadNetwork padNetwork, PathFinding pathFinding, Map<String, Location> teleportPads) {
        this.padNetwork = padNetwork;
        this.pathFinding = pathFinding;
        this.teleportPads = teleportPads;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof org.bukkit.entity.Player)) {
            commandSender.sendMessage("Dit command kan alleen in-game");
            return true;
        }

        Player p = (Player) commandSender;
        if (strings.length != 2) {
            p.sendMessage("Gebruik /path <naam1> <naam2>");
        }

        String startPad = strings[0];
        String endPad = strings[1];

        List<String> path = pathFinding.findShortestPath(startPad, endPad, padNetwork.getNetwork());

        if (path == null) {
            p.sendMessage("Geen pad gevonden.");
            return true;
        }

        p.sendMessage("Teleporteren via: "+ String.join(" > ", path));

        // Logica toevoegen om locatie van het

        String finalPath = path.get(path.size() - 1);
        Location endPadLocation = teleportPads.get(finalPath);

        p.teleport(endPadLocation);

        return false;
    }
}
