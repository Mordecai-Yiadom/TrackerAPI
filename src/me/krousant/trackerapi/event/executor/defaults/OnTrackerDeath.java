package me.krousant.trackerapi.event.executor.defaults;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class OnTrackerDeath extends TrackerAPIEventExecutor
{

    public OnTrackerDeath(TrackerAPI instance)
    {
        super(instance, PlayerDeathEvent.getHandlerList(), EventPriority.NORMAL, true);
    }

    @Override
    public void execute(Listener listener, Event e)
    {
        if(!(e instanceof PlayerDeathEvent)) return;

        PlayerDeathEvent event = (PlayerDeathEvent) e;

        Tracker tracker = API_INSTANCE.getPlayerAsTracker(event.getEntity());

        if(tracker == null) return;

        ItemStack[] drops = event.getDrops().toArray(new ItemStack[0]);

        for(ItemStack itemStack : drops)
        {
            if(API_INSTANCE.compassManager().isTrackerCompass(itemStack))
                event.getDrops().remove(itemStack);
        }
    }
}
