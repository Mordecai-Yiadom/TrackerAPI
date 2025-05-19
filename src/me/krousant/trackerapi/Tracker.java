package me.krousant.trackerapi;

import org.bukkit.entity.Player;

public class Tracker extends TrackerAPIEntity<Player>
{
    private Target target;

    public Tracker(Player player)
    {
        super(player);
    }

    public Target getTarget()
    {
        return target;
    }

    public void setTarget(Target target)
    {
        this.target = target;
    }
}
