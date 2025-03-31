package me.krousant.trackerapi.event.listener;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public interface TrackingDataChangeListener
{
    void trackerAdded(UUID tracker);
    void trackerRemoved(UUID tracker);

    void targetAdded(UUID tracker);
    void targetRemoved(UUID tracker);

    void worldExitChanged(UUID target, World world, Location oldLocation, Location newLocation);

    void trackerTargetChanged(UUID tracker, UUID oldTarget, UUID newTarget);


}
