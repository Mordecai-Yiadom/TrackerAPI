package me.krousant.trackerapi;

import me.krousant.trackerapi.event.action.CompassAction;
import me.krousant.trackerapi.event.action.NullCompassAction;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tracker extends TrackerAPIEntity<Player>
{
    private Target target;
    private final Map<CompassAction, CompassActionListener> COMPASS_ACTION_MAP;
    private final HashMap<UUID, Location> LAST_TRACKED_LOCATION;

    protected Tracker(Player player)
    {
        super(player);

        LAST_TRACKED_LOCATION = new HashMap<>();

        COMPASS_ACTION_MAP = new HashMap<>();
        for(CompassAction action : CompassAction.values())
            COMPASS_ACTION_MAP.put(action, NullCompassAction.INSTANCE);
    }

    public Target getTarget()
    {
        return target;
    }

    public void setTarget(Target target)
    {
        notifyChangeListeners(this.target, target);
        this.target = target;
    }

    public Location getLastTrackedLocation(World world)
    {
        if(world == null) return null;
        return LAST_TRACKED_LOCATION.get(world.getUID());
    }

    //TODO clean up error handling
    public void setLastTrackedLocation(World world, Location location)
    {
        if(world == null) throw new NullPointerException("World cannot be null.");

        if(LAST_TRACKED_LOCATION.containsKey(world.getUID())) LAST_TRACKED_LOCATION.replace(world.getUID(), location);
        else LAST_TRACKED_LOCATION.put(world.getUID(), location);
    }

    //TODO: Add better error handling
    public void setCompassAction(CompassAction action, CompassActionListener listener)
    {
        if(action == null) throw new NullPointerException("CompassAction cannot be null.");
        if(listener == null) COMPASS_ACTION_MAP.replace(action, NullCompassAction.INSTANCE);
        else COMPASS_ACTION_MAP.replace(action, listener);
    }

    public CompassActionListener getCompassAction(CompassAction action)
    {
        return COMPASS_ACTION_MAP.get(action);
    }
}
