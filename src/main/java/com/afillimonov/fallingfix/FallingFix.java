package com.afillimonov.fallingfix;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FallingFix extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onFallBlock(EntityChangeBlockEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType() == EntityType.FALLING_BLOCK && e.getTo() == Material.AIR) {
            World world = e.getBlock().getWorld();
            double x = e.getBlock().getLocation().getX();
            double y = e.getBlock().getLocation().getY();
            double z = e.getBlock().getLocation().getZ();
            Material block = e.getBlock().getType();
            e.getBlock().setType(Material.AIR);

            while (y > 0.0D) {
                Location loc = new Location(world, x, y, z);
                if (loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.WATER && loc.getBlock().getType() != Material.LAVA) {
                    loc.setY(y + 1.0D);
                    loc.getBlock().setType(block);
                    break;
                }
                y--;
            }

            e.setCancelled(true);
        }
    }
}
