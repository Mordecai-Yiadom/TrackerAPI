package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnTrackerDropCompass extends TrackerAPIEventExecutor
{
    public OnTrackerDropCompass(TrackerAPI instance)
    {
        super(instance, PlayerDropItemEvent.getHandlerList(), EventPriority.NORMAL, true);
    }

    @Override
    public void execute(Listener listener, Event e)
    {
        if(!(e instanceof PlayerDropItemEvent)) return;

        PlayerDropItemEvent event = (PlayerDropItemEvent) e;

        if(API_INSTANCE.isTracker(event.getPlayer()) &&
                API_INSTANCE.compassManager().isTrackerCompass(event.getItemDrop().getItemStack()))
            event.setCancelled(true);
    }
}
