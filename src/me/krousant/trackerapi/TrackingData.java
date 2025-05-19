package me.krousant.trackerapi;

import me.krousant.trackerapi.event.listener.TrackingDataChangeListener;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.*;

public class TrackingData implements Serializable
{
    /***********************************************************
     * Trackers can ONLY track ONE Target at a time
     *                       BUT
     * Targets can be tracked by MANY Trackers at a time
     * **********************************************************
     * Trackers can ONLY be Players
     *              while
     * Targets can be of type Entity
     *************************************************************/

    private Set<Tracker> TRACKERS;
    private Set<Target> TARGETS;
    private final LinkedList<TrackingDataChangeListener> CHANGE_LISTENERS;

    protected TrackingData()
    {
        TRACKERS = new HashSet<>();
        TARGETS = new HashSet<>();

        CHANGE_LISTENERS = new LinkedList<>();
    }

    protected boolean addTracker(Player tracker)
    {
        return true;
    }

    protected boolean removeTracker(Player tracker)
    {
        return true;
    }

    protected boolean addTarget(Entity target)
    {
        return true;
    }

    protected boolean removeTarget(Entity target)
    {
        return true;
    }

    protected Set<UUID> getTrackersFromTarget(UUID target)
    {
        Set<UUID> trackers = new HashSet<>();

        for(UUID tracker : TRACKING_MAP.keySet())
            if(TRACKING_MAP.get(tracker).equals(target)) trackers.add(tracker);

        return trackers;
    }

    protected Location getWorldExit(UUID target, World world)
    {
        return WORLD_EXITS.get(target).get(world);
    }

    protected Set<UUID> getTrackers()
    {
        return new HashSet<>(TRACKING_MAP.keySet());
    }

    protected Set<UUID> getTargets()
    {
        return new HashSet<>(TARGETS);
    }


    //Boolean utility methods
    protected boolean isTracker(Entity tracker)
    {
        return
    }

    protected boolean isTarget(Entity target)
    {
        return TARGETS.contains(target);
    }

    protected boolean hasTarget(UUID tracker)
    {
        return TRACKING_MAP.get(tracker) != null;
    }

    public boolean registerChangeListener(TrackingDataChangeListener listener)
    {
        return CHANGE_LISTENERS.add(listener);
    }

    public boolean unregisterChangeListener(TrackingDataChangeListener listener)
    {
        return CHANGE_LISTENERS.remove(listener);
    }
}
