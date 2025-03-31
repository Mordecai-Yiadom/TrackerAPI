package me.krousant.trackerapi;

import me.krousant.trackerapi.event.action.CompassAction;
import me.krousant.trackerapi.event.action.NullAction;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public abstract class TrackerAPICompassManager
{
    private final Map<CompassAction, CompassActionListener> ACTION_MAP;
    private TrackerAPI API_INSTANCE;

    protected TrackerAPICompassManager()
    {
        ACTION_MAP = new HashMap<>();
        for(CompassAction action : CompassAction.values()) ACTION_MAP.put(action, NullAction.GET);
    }

    public void setCompassAction(CompassAction action, CompassActionListener listener)
    {
        if(listener == null) ACTION_MAP.put(action, NullAction.GET);
        else ACTION_MAP.put(action, listener);
    }

    public boolean giveTrackerCompass(Player player)
    {
        if(player == null) return false;
        player.getInventory().addItem(getTrackerCompass());
        return true;
    }

    public boolean removeTrackerCompass(Player player)
    {
        if(player == null) return false;

        ItemStack compass = getTrackerCompassFromInventory(player.getInventory());
        if(compass == null) return false;

        player.getInventory().remove(compass);
        return true;
    }

    public boolean hasTrackerCompass(Player tracker)
    {
        return getTrackerCompassFromInventory(tracker.getInventory()) != null;
    }

    public boolean isTrackerCompass(ItemStack itemStack)
    {
        try {if(!itemStack.getItemMeta().hasLore()) return false;}
        catch(NullPointerException ex) {return false;}
        return (itemStack.getItemMeta().getLore().contains(API_INSTANCE.id().toString())
                && itemStack.getType() == Material.COMPASS);
    }

    public ItemStack getTrackerCompassFromInventory(Inventory inventory)
    {
        for(ItemStack itemStack : inventory.getContents())
            if(isTrackerCompass(itemStack)) return itemStack;
        return null;
    }

    public ItemStack getTrackerCompass()
    {
        ItemStack compass = new ItemStack(Material.COMPASS);

        ItemMeta itemMeta = compass.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(API_INSTANCE.id().toString());
        itemMeta.setLore(lore);

        compass.setItemMeta(itemMeta);
        return compass;
    }

    public boolean setTrackerCompassTarget(Player tracker, Location location)
    {
        ItemStack compass = getTrackerCompassFromInventory(tracker.getInventory());
        if(compass == null) return false;

        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        compassMeta.setLodestone(location);

        compass.setItemMeta(compassMeta);
        return true;
    }
}
