package me.krousant.trackerapi.event.listener.defaults;

import me.krousant.trackerapi.Target;
import me.krousant.trackerapi.Tracker;
import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.TrackerAPISettings;
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

        TrackCurrentTarget.actionPerformed(instance,
                instance.getPlayerAsTracker(((PlayerInteractEvent) event).getPlayer()));
    }

    public static void actionPerformed(TrackerAPI instance, Tracker tracker)
    {

        if(tracker == null || instance == null) return;

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
        else if(instance.settings().get(TrackerAPISettings.Option.AUTO_TRACK_WORLD_EXITS))
            targetLocation = tracker.getTarget().getWorldExitLocation(tracker.get().getWorld());
        else targetLocation = null;

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
