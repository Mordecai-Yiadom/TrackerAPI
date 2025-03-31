package me.krousant.trackerapi.event.listener;

import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.TrackerAPISettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class DefaultTrackingDataChangeListener implements TrackingDataChangeListener
{
    private TrackerAPI API_INSTANCE;

    public DefaultTrackingDataChangeListener(TrackerAPI apiInstance)
    {
        API_INSTANCE = apiInstance;
    }

    @Override
    public void trackerAdded(UUID tracker)
    {
        if(API_INSTANCE.settings().get(TrackerAPISettings.Option.GIVE_COMPASS_ON_ADD))
            API_INSTANCE.giveTrackerCompass(Bukkit.getPlayer(tracker));
    }

    @Override
    public void trackerRemoved(UUID tracker)
    {
        if(API_INSTANCE.settings().get(TrackerAPISettings.Option.REMOVE_COMPASS_ON_REMOVE))
            API_INSTANCE.removeTrackerCompass(Bukkit.getPlayer(tracker));
    }

    @Override
    public void targetAdded(UUID tracker)
    {}

    @Override
    public void targetRemoved(UUID tracker)
    {}

    @Override
    public void worldExitChanged(UUID target, World world, Location oldLocation, Location newLocation)
    {}

    @Override
    public void trackerTargetChanged(UUID tracker, UUID oldTarget, UUID newTarget)
    {}
}
