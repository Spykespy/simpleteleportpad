package org.spykemedia.simpleteleportpad.helpers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.spykemedia.simpleteleportpad.SimpleTeleportPad;

import java.util.HashMap;
import java.util.Map;

public class EnergySystem {
    private final Map<String, Integer> padEnergy = new HashMap<>();
    private final int maxEnergy = 100;
    private final int rechargeRate = 1;
    private final SimpleTeleportPad plugin;


    public EnergySystem(SimpleTeleportPad plugin) {
        this.plugin = plugin;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<String, Integer> entry : padEnergy.entrySet()) {
                    String padName = entry.getKey();
                    int currentEnergy = entry.getValue();

                    if (currentEnergy < maxEnergy) {
                        padEnergy.put(padName, currentEnergy + rechargeRate);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public boolean canTeleport(String padName) {
        return padEnergy.getOrDefault(padName, 0) >= maxEnergy;
    }

    public void useEnergy(String padName) {
        padEnergy.put(padName, padEnergy.get(padName) - 10);
    }

    public void addPad(String padName) {
        padEnergy.put(padName, maxEnergy);
    }

    public void showEnergy(Player p, String padName){
        int energy = padEnergy.getOrDefault(padName, 0);
        p.sendMessage("Energy: " + energy);
    }

}
