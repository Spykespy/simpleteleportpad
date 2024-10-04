package org.spykemedia.simpleteleportpad;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.spykemedia.simpleteleportpad.commands.*;
import org.spykemedia.simpleteleportpad.helpers.Database;
import org.spykemedia.simpleteleportpad.helpers.EnergySystem;
import org.spykemedia.simpleteleportpad.helpers.PathFinding;
import org.spykemedia.simpleteleportpad.helpers.TeleportPadNetwork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SimpleTeleportPad extends JavaPlugin implements Listener {

    private Database database;
    private Map<String,Location> teleportPads  = new HashMap<>();
    private EnergySystem energySystem;
    private TeleportPadNetwork padNetwork;
    private PathFinding pathFinding;

    @Override
    public void onEnable() {

        database = new Database();

        try {
            database.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            getLogger().severe("Verbinding met DB mislukt.");
            return;
        }

        loadTeleportPads();
        startParticleEffectTask();
        energySystem = new EnergySystem(this);

        //Registeren events en commands
        this.getCommand("createpad").setExecutor(new CreatePad(database));
        this.getCommand("pad").setExecutor(new GotoPad(teleportPads, energySystem));
        this.getCommand("listpads").setExecutor(new ListPads(teleportPads));
        this.getCommand("linkpad").setExecutor(new LinkPads(padNetwork));
        this.getCommand("path").setExecutor(new TeleportPath(padNetwork, pathFinding, teleportPads));


        Bukkit.getPluginManager().registerEvents(this, this);

        getLogger().info("Enabled");
    }

    public void onDisable() {
        try {
            database.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getLogger().info("Disabled");
    }

    public void loadTeleportPads(){
        try{
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM teleportpads");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String name = rs.getString("name");
                String world = rs.getString("world");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");

                Location loc = new Location(Bukkit.getWorld(world), x, y, z);

                teleportPads.put(name, loc);
                getLogger().info("Pad " + name + " geladen.");

            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startParticleEffectTask(){
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for(Location location : teleportPads.values()){
                location.getWorld().spawnParticle(Particle.FLAME, location.add(0.5, 1, 0.5), 10, 0.5, 0.5, 0.5, 0);
                location.subtract(0.5, 1, 0.5);
            }
        }, 0L, 20L);
    }

}

