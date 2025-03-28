package me.krousant.trackerapi;

import org.bukkit.*;

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

    private Map<UUID, UUID> TRACKING_MAP;
    private Map<UUID, Map<World, Location>> WORLD_EXITS;
    private Set<UUID> TARGETS;

    protected TrackingData()
    {
        TRACKING_MAP = new HashMap<>();
        WORLD_EXITS = new HashMap<>();
        TARGETS = new HashSet<>();
    }

    protected boolean addTracker(UUID tracker)
    {
        return false;
    }

    protected boolean removeTracker(UUID tracker)
    {
        return false;
    }

    protected boolean addTarget(UUID target)
    {
        return false;
    }

    protected boolean removeTarget(UUID target)
    {
        return false;
    }

    protected boolean setTargetForTracker(UUID tracker, UUID target)
    {
        return false;
    }

    protected boolean setWorldExit(UUID target, Location exit)
    {
        return false;
    }


    //Utility Accessor Methods
    protected UUID getTargetFromTracker(UUID tracker)
    {
        return null;
    }

    protected UUID getTrackerFromTarget(UUID target)
    {
        return null;
    }

    protected Location getWorldExit(UUID target)
    {
        return null;
    }

    protected Set<UUID> getTrackers()
    {
        return null;
    }

    protected Set<UUID> getTargets()
    {
        return null;
    }


    //Boolean utility methods
    protected boolean isTracker(UUID tracker)
    {
        return TRACKING_MAP.containsKey(tracker);
    }

    protected boolean isTarget(UUID target)
    {
        return TARGETS.contains(target);
    }

    protected boolean hasTarget(UUID tracker)
    {
        return TRACKING_MAP.get(tracker) != null;
    }


}
