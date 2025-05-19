package me.krousant.trackerapi;

import org.bukkit.entity.Player;

import java.util.LinkedList;

public class Tracker extends TrackerAPIEntity<Player>
{
    private Target target;
    private final LinkedList<TrackerChangeListener> CHANGE_LISTENERS;

    protected Tracker(Player player)
    {
        super(player);
        CHANGE_LISTENERS = new LinkedList<>();
    }

    public Target getTarget()
    {
        return target;
    }

    public void setTarget(Target target)
    {
        notifyChangeListeners(target);
        this.target = target;
    }

    public void addChangeListener(TrackerChangeListener listener)
    {
        CHANGE_LISTENERS.add(listener);
    }

    public void removeChangeListener(TrackerChangeListener listener)
    {
        CHANGE_LISTENERS.remove(listener);
    }

    private void notifyChangeListeners(Target newTarget)
    {
        for(TrackerChangeListener listener : CHANGE_LISTENERS)
            listener.targetChanged(this.target, newTarget);
    }
}
