package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.action.CompassAction;
import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnTrackerCompassClick extends TrackerAPIEventExecutor
{
    public OnTrackerCompassClick(TrackerAPI instance)
    {
        super(instance, PlayerInteractEvent.getHandlerList(), EventPriority.NORMAL, false);
    }

    @Override
    public void execute(Listener listener, Event event)
    {
        if(!(event instanceof PlayerInteractEvent) ) return;
        PlayerInteractEvent playerInteractEvent = (PlayerInteractEvent) event;

        Tracker tracker = API_INSTANCE.getPlayerAsTracker(playerInteractEvent.getPlayer());
        if(tracker == null) return;
        //if(tracker.isNull()) return;

        if(!API_INSTANCE.compassManager().isTrackerCompass(playerInteractEvent.getItem())) return;

        switch(playerInteractEvent.getAction())
        {
            case LEFT_CLICK_AIR:
                tracker.getCompassAction(CompassAction.LEFT_CLICK_AIR).actionPerformed(API_INSTANCE, event);
                break;

            case LEFT_CLICK_BLOCK:
                tracker.getCompassAction(CompassAction.LEFT_CLICK_BLOCK).actionPerformed(API_INSTANCE, event);
                break;

            case RIGHT_CLICK_AIR:
                tracker.getCompassAction(CompassAction.RIGHT_CLICK_AIR).actionPerformed(API_INSTANCE, event);
                break;

            case RIGHT_CLICK_BLOCK:
                tracker.getCompassAction(CompassAction.RIGHT_CLICK_BLOCK).actionPerformed(API_INSTANCE, event);
                break;
        }

    }
}
