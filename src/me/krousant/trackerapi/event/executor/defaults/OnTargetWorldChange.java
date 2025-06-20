package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.EventExecutor;

public class OnTargetWorldChange extends TrackerAPIEventExecutor
{
    public OnTargetWorldChange(TrackerAPI instance){super(instance);}

    @Override
    public void execute(Listener listener, Event event)
    {
        PlayerPortalEvent portalEvent  = (PlayerPortalEvent) event;

        Player player = portalEvent.getPlayer();
        if(!API_INSTANCE.isTarget(player)) return;

        for(Tracker tracker : API_INSTANCE.getTrackers())
        {
            if(tracker.get() == null) continue;
            if(!tracker.getTarget().get().equals(player)) continue;

            if(API_INSTANCE.isInSameWorld(tracker.get(), portalEvent.getFrom()))
            {
                API_INSTANCE.compassManager().setTrackerCompassTarget(tracker, portalEvent.getFrom());
                API_INSTANCE.getEntityAsTarget(portalEvent.getPlayer())
                        .setWorldExitLocation(portalEvent.getFrom().getWorld(), portalEvent.getFrom());
            }
        }
    }
}
