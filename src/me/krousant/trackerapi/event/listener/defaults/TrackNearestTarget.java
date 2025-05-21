package me.krousant.trackerapi.event.listener.defaults;

import me.krousant.trackerapi.Target;
import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrackNearestTarget implements CompassActionListener
{
    @Override
    public void actionPerformed(TrackerAPI instance, Event event)
    {
        Tracker tracker = instance.getPlayerAsTracker(((PlayerEvent)event).getPlayer());

        Target nearestTarget = null;
        double distanceToNearestTarget = Double.MAX_VALUE;
        double currdistance;

        for(Target target : instance.getTargets())
        {
            if(target.get() == null) continue;

            if(instance.isInSameWorld(target.get(), tracker.get()))
                currdistance = tracker.get().getLocation().distance(target.get().getLocation());
            else
            {
                if(target.getWorldExitLocation(target.get().getWorld()) == null) continue;
                currdistance = tracker.get().getLocation()
                        .distance(target.getWorldExitLocation(target.get().getWorld()));
            }


            if(currdistance < distanceToNearestTarget)
            {
                nearestTarget = target;
                distanceToNearestTarget = currdistance;
            }
        }

        if(nearestTarget == null)
        {
            instance.compassManager().sendCompassMessage(tracker,
                    ChatColor.RED + "Unable find entities to track");
            return;
        }

        tracker.setTarget(nearestTarget);
        instance.compassManager().setTrackerCompassTarget(tracker, nearestTarget.get().getLocation());
        instance.compassManager().sendCompassMessage(tracker,
                ChatColor.GREEN + "Now tracking " + nearestTarget.get().getName());
    }
}
