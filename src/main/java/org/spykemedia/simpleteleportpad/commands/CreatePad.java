package org.spykemedia.simpleteleportpad.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Location;
import org.spykemedia.simpleteleportpad.SimpleTeleportPad;
import org.spykemedia.simpleteleportpad.helpers.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreatePad implements CommandExecutor {

    private final Database database;

    public CreatePad(Database database) {
        this.database = database;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Dit command kan alleen in-game");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length != 1){
            player.sendMessage("Gebruik /createpad <naam>");
            return false;
        }

        String padName = args[0];
        Location location = player.getLocation();

        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO teleportpads (name,world,x,y,z) VALUES (?,?,?,?,?)");

            ps.setString(1, padName);
            ps.setString(2, location.getWorld().getName());
            ps.setInt(3, location.getBlockX());
            ps.setInt(4, location.getBlockY());
            ps.setInt(5, location.getBlockZ());
            ps.executeUpdate();
            ps.close();

            player.sendMessage("Je pad " + padName + " is gemaakt!");


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;

    }
}
