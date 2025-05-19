package me.krousant.trackerapi;

import me.krousant.trackerapi.event.listener.DefaultTrackingDataChangeListener;
import me.krousant.trackerapi.event.listener.TrackerAPISettingsChangeListener;
import me.krousant.trackerapi.event.listener.TrackingDataChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TrackerAPI extends TrackerAPICompassManager implements TrackerAPISettingsChangeListener, Serializable
{
    private Set<Tracker> TRACKERS;
    private Set<Target> TARGETS;

    private final TrackerAPISettings settings;
    private final UUID ID;

    protected TrackerAPI(TrackerAPISettings settings)
    {
        super();
        API_INSTANCE = this;

        TRACKERS = new HashSet<>();
        TARGETS = new HashSet<>();

        this.settings = settings;
        ID = UUID.randomUUID();
    }

    public TrackerAPISettings settings() {return settings;}
    public UUID id() {return ID;}


    /*******************
     TRACKER METHODS
     ******************/
    public boolean addTracker(Player player)
    {
        Tracker tracker;

        try {tracker = new Tracker(player);}
        catch(NullPointerException ex){return false;}

        return TRACKERS.add(tracker);
    }

    public boolean removeTracker(Tracker tracker)
    {
        if(tracker == null) return false;
        return TRACKERS.remove(tracker);
    }

    public boolean isTracker(Player player)
    {
        if(player == null) return false;

        for(Tracker tracker : TRACKERS)
            if(tracker.id().equals(player.getUniqueId())) return true;
        return false;
    }

    public boolean isTracker(UUID id)
    {
        if(id == null) return false;

        for(Tracker tracker : TRACKERS)
            if(tracker.id().equals(id)) return true;
        return false;
    }

    public Set<Tracker> getTrackers()
    {
        return (HashSet<Tracker>) ((HashSet<Tracker>) TRACKERS).clone();
    }


    /*******************
        TARGET METHODS
     ******************/
    public boolean addTarget(Entity entity)
    {
        Target target;

        try {target = new Target(entity);}
        catch(NullPointerException ex){return false;}

        return TARGETS.add(target);
    }

    public boolean removeTarget(Target target)
    {
        if(target == null) return false;
        return TARGETS.remove(target);
    }

    public boolean isTarget(Entity entity)
    {
        if(entity == null) return false;

        for(Target target : TARGETS)
            if(target.id().equals(entity.getUniqueId())) return true;
        return false;
    }

    public boolean isTarget(UUID id)
    {
        if(id == null) return false;

        for(Target target : TARGETS)
            if(target.id().equals(id)) return true;
        return false;
    }

    public Set<Target> getTargets()
    {
        return (HashSet<Target>) ((HashSet<Target>) TARGETS).clone();
    }



    protected void destroy()
    {}


    @Override
    public void settingChanged(TrackerAPISettings.Option option, boolean oldValue, boolean newValue)
    {
        if(settings.get(TrackerAPISettings.Option.ENABLE_DEBUG_MODE))
            TrackerAPIPlugin.sendConsoleMessage(TrackerAPIPlugin.ConsoleMessageType.NEUTRAL,
                    "Settings have changed.", ID);
    }
}
