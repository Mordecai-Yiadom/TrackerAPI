package me.krousant.trackerapi;

import me.krousant.trackerapi.event.action.CompassAction;
import me.krousant.trackerapi.event.action.NullCompassAction;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public abstract class TrackerAPICompassManager
{
    private TrackerAPI API_INSTANCE;
    private TrackerCompassValidator compassValidator;
    private ItemStack genericCompass;

    private final ItemStack DEFAULT_GENERIC_COMPASS;

    public TrackerAPICompassManager(TrackerAPI instance, TrackerCompassValidator compassValidator,
                                    ItemStack genericCompass)
    {
        if(instance == null) throw new NullPointerException("TrackerAPI cannot be null.");
        this.API_INSTANCE = instance;

        DEFAULT_GENERIC_COMPASS = createDefaultGenericTrackerCompass();

        if(compassValidator == null) this.compassValidator = new DefaultTrackerCompassValidator(instance);
        else this.compassValidator = compassValidator;

        if(genericCompass == null) this.genericCompass = DEFAULT_GENERIC_COMPASS;
        else this.genericCompass = genericCompass;
    }


    public boolean giveTrackerCompass(Player player)
    {
        if(player == null) throw new NullPointerException("Player cannot be null");

        player.getInventory().addItem(DEFAULT_GENERIC_COMPASS);
        return true;
    }

    public boolean removeTrackerCompass(Player player)
    {
        if(player == null) throw new NullPointerException("Player cannot be null.");

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
        return compassValidator.isValidTrackerCompass(itemStack);
    }

    public ItemStack getTrackerCompassFromInventory(Inventory inventory)
    {
        for(ItemStack itemStack : inventory.getContents())
            if(isTrackerCompass(itemStack)) return itemStack;
        return null;
    }

    public ItemStack getTrackerCompass()
    {
        return DEFAULT_GENERIC_COMPASS.clone();
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

    private ItemStack createDefaultGenericTrackerCompass()
    {
        ItemStack compass = new ItemStack(Material.COMPASS);

        List<String> lore = new ArrayList<>();
        lore.add(API_INSTANCE.id().toString());

        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        compassMeta.setDisplayName(ChatColor.GOLD + "Tracker Compass");
        compassMeta.setLore(lore);
        compassMeta.setLodestoneTracked(true);

        compass.setItemMeta(compassMeta);

        return compass;
    }

    private static class DefaultTrackerCompassValidator implements TrackerCompassValidator
    {
        private TrackerAPI instance;

        private DefaultTrackerCompassValidator(TrackerAPI instance)
        {
            this.instance = instance;
        }

        @Override
        public boolean isValidTrackerCompass(ItemStack itemStack)
        {
            if(itemStack == null) throw new NullPointerException("ItemStack cannot be null.");
            if(itemStack.getType().equals(Material.COMPASS) && itemStack.hasItemMeta())
            {
                if(itemStack.getItemMeta().hasLore())
                {
                    try
                    {
                        if(itemStack.getItemMeta().getLore().getFirst().equals(instance.id().toString()))
                            return true;
                    }
                    catch(NoSuchElementException ex){}
                }
            }
            return false;
        }
    }
}
