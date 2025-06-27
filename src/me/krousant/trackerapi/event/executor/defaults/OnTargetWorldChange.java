package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.EventExecutor;

public class OnTargetWorldChange
{
    public static class Player extends TrackerAPIEventExecutor
    {
        public Player(TrackerAPI instance)
        {
            super(instance, PlayerPortalEvent.getHandlerList(), EventPriority.NORMAL, true);
        }

        @Override
        public void execute(Listener listener, Event event)
        {
            PlayerPortalEvent portalEvent  = (PlayerPortalEvent) event;

            org.bukkit.entity.Player target = portalEvent.getPlayer();
            if(!API_INSTANCE.isTarget(target)) return;

            API_INSTANCE.getEntityAsTarget(target)
                    .setWorldExitLocation(portalEvent.getFrom().getWorld(), portalEvent.getFrom());

            for(Tracker tracker : API_INSTANCE.getTrackers())
            {
                if(tracker.get() == null) continue;
                if(!tracker.getTarget().get().equals(target)) continue;

                if(API_INSTANCE.isInSameWorld(tracker.get(), portalEvent.getFrom()))
                {
                    API_INSTANCE.compassManager().setTrackerCompassTarget(tracker, portalEvent.getFrom());
                }
            }
        }
    }

    public static class Entity extends TrackerAPIEventExecutor
    {
        public Entity(TrackerAPI instance)
        {
            super(instance, EntityPortalEvent.getHandlerList(), EventPriority.NORMAL, true);
        }

        @Override
        public void execute(Listener listener, Event event)
        {
            EntityPortalEvent portalEvent  = (EntityPortalEvent) event;

            org.bukkit.entity.Entity target = portalEvent.getEntity();

            if(!API_INSTANCE.isTarget(target)) return;

            API_INSTANCE.getEntityAsTarget(target)
                    .setWorldExitLocation(portalEvent.getFrom().getWorld(), portalEvent.getFrom());

            for(Tracker tracker : API_INSTANCE.getTrackers())
            {
                if(tracker.get() == null) continue;
                if(!tracker.getTarget().get().equals(target)) continue;

                if(API_INSTANCE.isInSameWorld(tracker.get(), portalEvent.getFrom()))
                {
                    API_INSTANCE.compassManager().setTrackerCompassTarget(tracker, portalEvent.getFrom());
                }
            }
        }
    }

}
