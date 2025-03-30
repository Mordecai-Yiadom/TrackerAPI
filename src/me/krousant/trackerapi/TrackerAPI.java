package me.krousant.trackerapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class TrackerAPI extends TrackerAPICompassManager implements TrackerAPISettingChangeListener, Serializable
{
    private final Plugin plugin;
    private final TrackerAPISettings settings;
    private final TrackingData trackingData;
    private final UUID ID;

    protected TrackerAPI(Plugin plugin, TrackerAPISettings settings)
    {
        super();
        this.plugin = plugin;
        this.settings = settings;
        ID = UUID.randomUUID();
        trackingData = new TrackingData();
    }

    public TrackerAPISettings settings(){return settings;}
    public Plugin plugin(){return plugin;}
    public UUID id(){return ID;}

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


    public boolean setTargetForTracker(UUID tracker, UUID target)
    {
        if(tracker == null) throw new NullPointerException("Tracker UUID is null!");
        return trackingData.setTargetForTracker(tracker, target);
    }

    public boolean setTargetForTracker(Player tracker, Entity target)
    {
        if(tracker == null) throw new NullPointerException("Tracker Player is null!");
        return trackingData.setTargetForTracker(tracker.getUniqueId(), target.getUniqueId());
    }

    public boolean setWorldExit(UUID target, Location exit)
    {
        return trackingData.setWorldExit(target, exit);
    }

    public boolean setWorldExit(Player target, Location exit)
    {
        return trackingData.setWorldExit(target.getUniqueId(), exit);
    }

    public UUID getTargetFromTracker(UUID tracker)
    {
        return trackingData.getTargetFromTracker(tracker);
    }

    public UUID getTargetFromTracker(Player tracker)
    {
        return trackingData.getTargetFromTracker(tracker.getUniqueId());
    }

    public Set<UUID> getTrackersFromTarget(UUID target)
    {
        return trackingData.getTrackersFromTarget(target);
    }

    public Set<UUID> getTrackersFromTarget(Entity target)
    {
        return trackingData.getTrackersFromTarget(target.getUniqueId());
    }

    public Location getWorldExit(UUID target, World world)
    {
        return trackingData.getWorldExit(target, world);
    }

    public Location getWorldExit(Player target, World world)
    {
        return trackingData.getWorldExit(target.getUniqueId(), world);
    }

    public Set<UUID> getTrackers()
    {
        return trackingData.getTrackers();
    }

    public Set<UUID> getTargets()
    {
        return trackingData.getTargets();
    }


    //Boolean Query Methods
    public boolean isTracker(UUID player)
    {
        return trackingData.isTracker(player);
    }
    //TODO add @NotNull to parameters
    public boolean isTracker(Player player)
    {
        if(player == null) throw new NullPointerException("Player is null!");
        return trackingData.isTracker(player.getUniqueId());
    }

    public boolean isTarget(UUID entity)
    {
        return trackingData.isTracker(entity);
    }
    //TODO add @NotNull to parameters
    public boolean isTarget(Entity entity)
    {
        if(entity == null) throw new NullPointerException("Entity is null!");
        return trackingData.isTracker(entity.getUniqueId());
    }

    public boolean hasTarget(UUID tracker)
    {
        return trackingData.hasTarget(tracker);
    }

    public boolean hasTarget(Player tracker)
    {
        return trackingData.hasTarget(tracker.getUniqueId());
    }

    protected void destroy()
    {

    }

    @Override
    public void settingChanged(TrackerAPISettings.Option option, boolean oldValue, boolean newValue)
    {

    }

}
