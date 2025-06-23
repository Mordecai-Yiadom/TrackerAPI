package me.krousant.trackerapi.event.listener.defaults;

import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrackCurrentTarget implements CompassActionListener
{
    @Override
    public void actionPerformed(TrackerAPI instance, Event event)
    {
        if(!(event instanceof PlayerInteractEvent)) return;

        Tracker tracker = instance.getPlayerAsTracker(((PlayerInteractEvent) event).getPlayer());
        if(tracker == null) return;

        if(tracker.getTarget() == null)
        {
            instance.compassManager().sendCompassMessage(tracker,
                    ChatColor.RED + "Nothing to track");
            return;
        }

        if(tracker.getTarget().get() == null)
        {
            instance.compassManager().sendCompassMessage(tracker,
                    ChatColor.RED + "Unable to track current target");
            return;
        }

        Location targetLocation;

        if(instance.isInSameWorld(tracker.get(), tracker.getTarget().get()))
            targetLocation = tracker.getTarget().get().getLocation();
        else targetLocation = tracker.getTarget().getWorldExitLocation(tracker.get().getWorld());

        if(targetLocation == null)
        {
            instance.compassManager().sendCompassMessage(tracker,
                    ChatColor.RED + "Unable to track " + tracker.getTarget().get().getName());
            return;
        }

        instance.compassManager().setTrackerCompassTarget(tracker, targetLocation);
        instance.compassManager().sendCompassMessage(tracker,
                ChatColor.GREEN + "Now tracking " + tracker.getTarget().get().getName());
    }
}
