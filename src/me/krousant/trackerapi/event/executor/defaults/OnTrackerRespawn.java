package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class OnTrackerRespawn extends TrackerAPIEventExecutor
{
    public OnTrackerRespawn(TrackerAPI instance)
    {
        super(instance, PlayerRespawnEvent.getHandlerList(), EventPriority.NORMAL, true);
    }

    @Override
    public void execute(Listener listener, Event e)
    {
        if(!(e instanceof PlayerRespawnEvent)) return;

        PlayerRespawnEvent event = (PlayerRespawnEvent) e;
        Tracker tracker = API_INSTANCE.getPlayerAsTracker(event.getPlayer());

        if(tracker == null && event.getRespawnReason() != PlayerRespawnEvent.RespawnReason.DEATH) return;

        if(!API_INSTANCE.compassManager().hasTrackerCompass(tracker.get()))
            API_INSTANCE.compassManager().giveTrackerCompass(tracker.get());
    }
}
