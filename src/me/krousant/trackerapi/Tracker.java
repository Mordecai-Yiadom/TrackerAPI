package me.krousant.trackerapi;

import me.krousant.trackerapi.event.action.CompassAction;
import me.krousant.trackerapi.event.action.NullCompassAction;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Tracker extends TrackerAPIEntity<Player>
{
    private Target target;
    private final Map<CompassAction, CompassActionListener> COMPASS_ACTION_MAP;

    protected Tracker(Player player)
    {
        super(player);

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
