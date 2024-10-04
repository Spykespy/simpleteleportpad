package org.spykemedia.simpleteleportpad.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.spykemedia.simpleteleportpad.helpers.TeleportPadNetwork;

public class LinkPads implements CommandExecutor {
    private final TeleportPadNetwork padNetwork;

    public LinkPads(TeleportPadNetwork padNetwork) {
        this.padNetwork = padNetwork;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof org.bukkit.entity.Player)) {
            commandSender.sendMessage("Dit command kan alleen in-game");
            return true;
        }

        if (strings.length != 2) {
            commandSender.sendMessage("Gebruik /linkpad <naam1> <naam2>");
            return false;
        }

        String pad1 = strings[0];
        String pad2 = strings[1];

        padNetwork.linkPads(pad1, pad2);
        commandSender.sendMessage("Paden gelinked.");

        return true;
    }
}
