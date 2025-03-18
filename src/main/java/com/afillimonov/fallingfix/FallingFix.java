package com.afillimonov.fallingfix;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
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
            Material fallingBlockMaterial = e.getBlock().getType();

            e.getBlock().setType(Material.AIR);

            while (y > 0.0D) {
                Location loc = new Location(world, x, y, z);
                Block blockBelow = loc.getBlock();

                if (blockBelow.getType() == Material.AIR || blockBelow.isLiquid()) {
                    y--;
                    continue;
                }
                if (isBreakable(blockBelow)) {
                    blockBelow.setType(fallingBlockMaterial);
                    break;
                }
                loc.setY(y + 1.0D);
                loc.getBlock().setType(fallingBlockMaterial);
                break;
            }
            e.setCancelled(true);
        }
    }
    private boolean isBreakable(Block block) {
        return block.isPassable() || block.isEmpty() || block.isLiquid();
    }
}
