package me.krousant.trackerapi;

import me.krousant.trackerapi.event.action.CompassAction;
import me.krousant.trackerapi.event.action.NullCompassAction;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import me.krousant.trackerapi.event.listener.TrackerAPIChangeListener;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TrackerAPICompassManager implements TrackerAPIChangeListener
{
    private TrackerAPI API_INSTANCE;
    private TrackerCompassValidator compassValidator;
    private ItemStack genericCompass;

    private final ItemStack DEFAULT_GENERIC_COMPASS;

    public TrackerAPICompassManager(TrackerAPI instance, TrackerCompassValidator compassValidator,
                                    ItemStack genericCompass)
    {
        if(instance == null) throw new NullPointerException("TrackerAPI cannot be null.");
        API_INSTANCE = instance;

        DEFAULT_GENERIC_COMPASS = createDefaultGenericTrackerCompass();

        if(compassValidator == null) this.compassValidator = new DefaultTrackerCompassValidator(instance);
        else this.compassValidator = compassValidator;

        if(genericCompass == null) this.genericCompass = DEFAULT_GENERIC_COMPASS;
        else this.genericCompass = genericCompass;

        API_INSTANCE.addChangeListener(this);
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

    public boolean setTrackerCompassTarget(Tracker tracker, Location location)
    {
        if(tracker.get() == null) return false;

        ItemStack compass = getTrackerCompassFromInventory(tracker.get().getInventory());
        if(compass == null) return false;

        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        compassMeta.setLodestoneTracked(false);
        compassMeta.setLodestone(location);

        compass.setItemMeta(compassMeta);
        return true;
    }

    public void sendCompassMessage(Tracker tracker, String message)
    {
        if(tracker.get() == null || message == null ||
                !API_INSTANCE.settings().get(TrackerAPISettings.Option.ENABLE_COMPASS_MESSAGES)) return;

        tracker.get().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
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

    @Override
    public void trackerAdded(Tracker tracker)
    {
        if(API_INSTANCE.settings().get(TrackerAPISettings.Option.GIVE_COMPASS_ON_ADD))
        {
            Player trackerPlayer = tracker.get();
            if(trackerPlayer == null) return;

            if(!hasTrackerCompass(trackerPlayer))
                giveTrackerCompass(trackerPlayer);
        }
    }

    @Override
    public void trackerRemoved(Tracker tracker)
    {
        if(API_INSTANCE.settings().get(TrackerAPISettings.Option.REMOVE_COMPASS_ON_REMOVE))
        {
            Player trackerPlayer = tracker.get();
            if(trackerPlayer == null) return;

            if(hasTrackerCompass(trackerPlayer))
                removeTrackerCompass(trackerPlayer);
        }
    }

    @Override
    public void targetAdded(Target target)
    {}

    @Override
    public void targetRemoved(Target target)
    {
        for(Tracker tracker : API_INSTANCE.getTrackers())
            if(tracker.getTarget().equals(target))
                tracker.setTarget(null);
    }

    @Override
    public void instanceDestroyed()
    {}

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
            if(itemStack == null) return false;
            if(itemStack.getType().equals(Material.COMPASS) && itemStack.hasItemMeta())
            {
                if(itemStack.getItemMeta().hasLore())
                {
                    if(itemStack.getItemMeta().getLore().contains(instance.id().toString()))
                        return true;
                }
            }
            return false;
        }
    }
}
