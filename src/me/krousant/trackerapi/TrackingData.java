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

    private final Map<UUID, UUID> TRACKING_MAP;
    private final Map<UUID, Map<World, Location>> WORLD_EXITS;
    private final Set<UUID> TARGETS;

    protected TrackingData()
    {
        TRACKING_MAP = new HashMap<>();
        WORLD_EXITS = new HashMap<>();
        TARGETS = new HashSet<>();
    }

    protected boolean addTracker(UUID tracker)
    {
        if(isTracker(tracker)) return false;
        TRACKING_MAP.put(tracker, null);
        return true;
    }

    protected boolean removeTracker(UUID tracker)
    {
        if(!isTracker(tracker)) return false;
        TRACKING_MAP.remove(tracker);
        return true;
    }

    protected boolean addTarget(UUID target)
    {
        if(isTracker(target)) return false;
        TARGETS.add(target);
        WORLD_EXITS.put(target, new HashMap<>());
        return true;
    }

    protected boolean removeTarget(UUID target)
    {
        if(!isTracker(target)) return false;
        TARGETS.remove(target);
        WORLD_EXITS.remove(target);

        for(UUID tracker : TRACKING_MAP.keySet())
            if(TRACKING_MAP.get(tracker).equals(target))
                TRACKING_MAP.replace(tracker, null);

        return true;
    }

    protected boolean setTargetForTracker(UUID tracker, UUID target)
    {
        if(isTracker(tracker) && isTarget(target))
        {
            TRACKING_MAP.replace(tracker, target);
            return true;
        }
        return false;
    }

    protected boolean setWorldExit(UUID target, Location exit)
    {
        if(!isTarget(target)) return false;
        WORLD_EXITS.get(target).replace(exit.getWorld(), exit);
        return true;
    }


    //Utility Accessor Methods
    protected UUID getTargetFromTracker(UUID tracker)
    {
        return TRACKING_MAP.get(tracker);
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
