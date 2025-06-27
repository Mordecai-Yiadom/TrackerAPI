package me.krousant.trackerapi.event.listener.defaults;

import me.krousant.trackerapi.Target;
import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.TrackerAPISettings;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrackNearestTarget implements CompassActionListener
{
    @Override
    public void actionPerformed(TrackerAPI instance, Event event)
    {
        if(!(event instanceof PlayerEvent)) return;

        Tracker tracker = instance.getPlayerAsTracker(((PlayerEvent)event).getPlayer());

        if(tracker == null) return;

        Target nearestTarget = null;
        double nearestDistance = Double.MAX_VALUE;
        double currdistance;

        for(Target target : instance.getTargets())
        {
            if(target.get() == null || target.id().equals(tracker.id())) continue;

            if(instance.isInSameWorld(target.get(), tracker.get()))
                currdistance = tracker.get().getLocation().distance(target.get().getLocation());

            else if(!instance.settings().get(TrackerAPISettings.Option.AUTO_TRACK_WORLD_EXITS)
                    || target.getWorldExitLocation(tracker.get().getWorld()) == null)
                continue;

            else
                currdistance = tracker.get().getLocation()
                        .distance(target.getWorldExitLocation(tracker.get().getWorld()));

            if(currdistance < nearestDistance)
            {
                nearestTarget = target;
                nearestDistance = currdistance;
            }
        }

        if(nearestTarget == null)
        {
            instance.compassManager().sendCompassMessage(tracker,
                    ChatColor.RED + "Unable find entities to track");
            return;
        }

        tracker.setTarget(nearestTarget);
        TrackCurrentTarget.actionPerformed(instance, tracker);
    }
}
