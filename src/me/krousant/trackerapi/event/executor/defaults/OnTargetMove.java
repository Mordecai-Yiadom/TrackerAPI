package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.EventExecutor;

public class OnTargetMove extends TrackerAPIEventExecutor
{
    public OnTargetMove(TrackerAPI instance)
    {
        super(instance, PlayerMoveEvent.getHandlerList(), EventPriority.NORMAL, true);
    }

    @Override
    public void execute(Listener listener, Event event)
    {
        PlayerMoveEvent playerMoveEvent  = (PlayerMoveEvent) event;

        Player player = playerMoveEvent.getPlayer();
        if(!API_INSTANCE.isTarget(player)) return;

        for(Tracker tracker : API_INSTANCE.getOnlineTrackers())
        {
            if(tracker.getTarget() == null) continue;
            if(tracker.getTarget().get() == null) continue;
            if(!tracker.getTarget().get().equals(player)) continue;

            if(API_INSTANCE.isInSameWorld(tracker.get(), player))
            {
                API_INSTANCE.compassManager().setTrackerCompassTarget(tracker, playerMoveEvent.getTo());
            }

        }
    }
}
