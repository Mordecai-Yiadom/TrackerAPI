package me.krousant.trackerapi.event.executor;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.EventExecutor;

public class AutoTrackMovementExecutor implements EventExecutor
{
    private TrackerAPI instance;
    @Override
    public void execute(Listener listener, Event event)
    {
        PlayerMoveEvent playerMoveEvent  = (PlayerMoveEvent) event;

        Player player = playerMoveEvent.getPlayer();
        if(!instance.isTarget(player)) return;

        for(Tracker tracker : instance.getTrackers())
        {
            if(tracker.get() == null) continue;

            if(instance.isInSameWorld(tracker.get(), player))
                instance.compassManager().setTrackerCompassTarget(tracker.get(), playerMoveEvent.getTo());

        }
    }
}
