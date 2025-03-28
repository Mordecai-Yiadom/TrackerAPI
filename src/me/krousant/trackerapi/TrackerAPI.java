package me.krousant.trackerapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


import java.util.UUID;

public class TrackerAPI
{
    private Plugin plugin;
    private TrackerAPISettings settings;
    private TrackingData trackingData;

    protected TrackerAPI(Plugin plugin, TrackerAPISettings settings)
    {
        this.plugin = plugin;
        this.settings = settings;
        trackingData = new TrackingData();
    }

    public TrackerAPISettings settings(){return settings;}
    public Plugin plugin(){return plugin;}

    //TRACKER METHODS
    public boolean addTracker(UUID tracker)
    {
        return trackingData.addTracker(tracker);
    }

    public boolean addTracker(Player tracker)
    {
        if(tracker == null) throw new NullPointerException("Player is null!");
        return trackingData.addTracker(tracker.getUniqueId());
    }

    public boolean removeTracker(UUID tracker)
    {
        return trackingData.removeTracker(tracker);
    }

    public boolean removeTracker(Player tracker)
    {
        if(tracker == null) throw new NullPointerException("Player is null!");
        return trackingData.removeTracker(tracker.getUniqueId());
    }


    //TARGET METHODS
    public boolean addTarget(UUID target)
    {
        return trackingData.addTarget(target);
    }

    public boolean addTarget(Entity target)
    {
        if(target == null) throw new NullPointerException("Entity is null!");
        return trackingData.addTarget(target.getUniqueId());
    }

    public boolean removeTarget(UUID target)
    {
        return trackingData.removeTarget(target);
    }

    public boolean removeTarget(Entity target)
    {
        if(target == null) throw new NullPointerException("Entity is null!");
        return trackingData.removeTarget(target.getUniqueId());
    }



    //Boolean Query Methods
    public boolean isTracker(UUID player) {return trackingData.isTracker(player);}
    //TODO add @NotNull to parameters
    public boolean isTracker(Player player)
    {
        if(player == null) throw new NullPointerException("Player is null!");
        return trackingData.isTracker(player.getUniqueId());
    }

    public boolean isTarget(UUID entity) {return trackingData.isTracker(entity);}
    //TODO add @NotNull to parameters
    public boolean isTarget(Entity entity)
    {
        if(entity == null) throw new NullPointerException("Entity is null!");
        return trackingData.isTracker(entity.getUniqueId());
    }

}
