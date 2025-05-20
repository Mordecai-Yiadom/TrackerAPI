package me.krousant.trackerapi;

import me.krousant.trackerapi.event.listener.TrackerAPIChangeListener;
import me.krousant.trackerapi.event.listener.TrackerAPISettingsChangeListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

public class TrackerAPI implements TrackerAPISettingsChangeListener, Serializable
{
    private final Set<Tracker> TRACKERS;
    private final Set<Target> TARGETS;

    private final LinkedList<TrackerAPIChangeListener> CHANGE_LISTENERS;
    private final TrackerAPISettings settings;
    private final UUID ID;

    protected TrackerAPI(TrackerAPISettings settings)
    {
        TRACKERS = new HashSet<>();
        TARGETS = new HashSet<>();

        this.settings = settings;
        ID = UUID.randomUUID();

        CHANGE_LISTENERS = new LinkedList<>();
    }

    public TrackerAPISettings settings() {return settings;}
    public UUID id() {return ID;}


    /*******************
     TRACKER METHODS
     ******************/
    public boolean addTracker(Player player)
    {
        Tracker tracker;

        try {tracker = new Tracker(player);}
        catch(NullPointerException ex){return false;}

        boolean added = TRACKERS.add(tracker);
        if(added) notifyTrackerAdded(tracker);
        return added;
    }

    public boolean removeTracker(Tracker tracker)
    {
        if(tracker == null) return false;

        boolean removed = TRACKERS.add(tracker);
        if(removed) notifyTrackerRemoved(tracker);
        return removed;
    }

    public boolean isTracker(Player player)
    {
        if(player == null) return false;

        for(Tracker tracker : TRACKERS)
            if(tracker.id().equals(player.getUniqueId())) return true;
        return false;
    }

    public boolean isTracker(UUID id)
    {
        if(id == null) return false;

        for(Tracker tracker : TRACKERS)
            if(tracker.id().equals(id)) return true;
        return false;
    }

    public Set<Tracker> getTrackers()
    {
        return (HashSet<Tracker>) ((HashSet<Tracker>) TRACKERS).clone();
    }


    /*******************
        TARGET METHODS
     ******************/
    public boolean addTarget(Entity entity)
    {
        Target target;

        try {target = new Target(entity);}
        catch(NullPointerException ex){return false;}

        boolean added = TARGETS.add(target);
        if(added) notifyTargetAdded(target);

        return added;
    }

    public boolean removeTarget(Target target)
    {
        if(target == null) return false;

        boolean removed = TARGETS.add(target);
        if(removed) notifyTargetRemoved(target);

        return removed;
    }

    public boolean isTarget(Entity entity)
    {
        if(entity == null) return false;

        for(Target target : TARGETS)
            if(target.id().equals(entity.getUniqueId())) return true;
        return false;
    }

    public boolean isTarget(UUID id)
    {
        if(id == null) return false;

        for(Target target : TARGETS)
            if(target.id().equals(id)) return true;
        return false;
    }

    public Set<Target> getTargets()
    {
        return (HashSet<Target>) ((HashSet<Target>) TARGETS).clone();
    }


    protected void destroy()
    {
        notifyInstanceDestroyed();
    }

    public void addChangeListener(TrackerAPIChangeListener listener)
    {
        CHANGE_LISTENERS.add(listener);
    }

    public void removeChangeListener(TrackerAPIChangeListener listener)
    {
        CHANGE_LISTENERS.remove(listener);
    }

    private void notifyTrackerAdded(Tracker tracker)
    {
        for(TrackerAPIChangeListener listener : CHANGE_LISTENERS)
            listener.trackerAdded(tracker);
    }

    private void notifyTrackerRemoved(Tracker tracker)
    {
        for(TrackerAPIChangeListener listener : CHANGE_LISTENERS)
            listener.trackerRemoved(tracker);
    }

    private void notifyTargetAdded(Target target)
    {
        for(TrackerAPIChangeListener listener : CHANGE_LISTENERS)
            listener.targetAdded(target);
    }

    private void notifyTargetRemoved(Target target)
    {
        for(TrackerAPIChangeListener listener : CHANGE_LISTENERS)
            listener.targetRemoved(target);
    }

    private void notifyInstanceDestroyed()
    {
        for(TrackerAPIChangeListener listener : CHANGE_LISTENERS)
            listener.instanceDestroyed();
    }


    @Override
    public void settingChanged(TrackerAPISettings.Option option, boolean oldValue, boolean newValue)
    {
        if(settings.get(TrackerAPISettings.Option.ENABLE_DEBUG_MODE))
            TrackerAPIPlugin.sendConsoleMessage(TrackerAPIPlugin.ConsoleMessageType.NEUTRAL,
                    "Settings have changed.", ID);
    }
}
