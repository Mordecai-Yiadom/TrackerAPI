package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.action.CompassAction;
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

        PlayerInteractEntityEvent entityEvent = (PlayerInteractEntityEvent) event;

        Tracker tracker = API_INSTANCE.getPlayerAsTracker(entityEvent.getPlayer());
        if(tracker == null) return;

        if(tracker.getTarget().equals(entityEvent.getRightClicked())
                && API_INSTANCE.compassManager().isTrackerCompass(tracker.get().getItemInUse()))
            tracker.getCompassAction(CompassAction.RIGHT_CLICK_ENTITY).actionPerformed(API_INSTANCE, event);
    }
}
