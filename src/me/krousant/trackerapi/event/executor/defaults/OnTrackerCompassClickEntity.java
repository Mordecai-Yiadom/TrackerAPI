package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class OnTrackerCompassClickEntity extends TrackerAPIEventExecutor
{
    public OnTrackerCompassClickEntity(TrackerAPI instance) {super(instance);}

    @Override
    public void execute(Listener listener, Event event)
    {
        if(!(event instanceof PlayerInteractEntityEvent)) return;
    }
}
