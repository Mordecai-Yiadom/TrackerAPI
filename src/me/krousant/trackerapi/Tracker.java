package me.krousant.trackerapi;

import org.bukkit.entity.Player;

import java.util.LinkedList;

public class Tracker extends TrackerAPIEntity<Player>
{
    private Target target;

    protected Tracker(Player player)
    {
        super(player);
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
}
