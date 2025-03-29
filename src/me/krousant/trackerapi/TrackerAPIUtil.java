package me.krousant.trackerapi;

import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class TrackerAPIUtil
{
    protected static ItemStack DEFAULT_TRACKER_COMPASS;

    static
    {
        DEFAULT_TRACKER_COMPASS = new ItemStack(Material.COMPASS);
        CompassMeta itemMeta = (CompassMeta) DEFAULT_TRACKER_COMPASS.getItemMeta();
        itemMeta.setLodestoneTracked(true);
        itemMeta.getDisplayName();
    }
}
