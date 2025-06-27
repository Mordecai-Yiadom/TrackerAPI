package me.krousant.trackerapi;

import me.krousant.trackerapi.event.executor.TrackerAPIEventExecutor;
import me.krousant.trackerapi.event.executor.defaults.*;
import me.krousant.trackerapi.event.listener.TrackerAPIChangeListener;
import me.krousant.trackerapi.event.listener.TrackerAPISettingsChangeListener;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.RegisteredListener;

import java.io.Serializable;
import java.util.*;

public class TrackerAPI implements TrackerAPISettingsChangeListener, Serializable
{
    private final Set<Tracker> TRACKERS;
    private final Set<Target> TARGETS;

    private final LinkedList<TrackerAPIChangeListener> CHANGE_LISTENERS;
    private final TrackerAPISettings settings;
    private final String ID;
    private final ArrayList<TrackerAPIEventExecutor> ACTIVE_EVENT_EXECUTORS;

    private TrackerAPICompassManager compassManager;

    protected TrackerAPI(TrackerAPISettings settings, TrackerAPICompassManager compassManager)
    {
        TRACKERS = new HashSet<>();
        TARGETS = new HashSet<>();

        this.settings = settings;
        ID = TrackerAPIManager.generateInstanceID();

        CHANGE_LISTENERS = new LinkedList<>();
        ACTIVE_EVENT_EXECUTORS = new ArrayList<>();

        if(compassManager == null)
            setCompassManager(new TrackerAPICompassManager(this, null,
                    null, null));
        else setCompassManager(compassManager);

        loadSettings();
        registerDefaultExecutors();
    }

    public TrackerAPISettings settings() {return settings;}
    public String id() {return ID;}
    public TrackerAPICompassManager compassManager() {return compassManager;}
    public void setCompassManager(TrackerAPICompassManager compassManager) {this.compassManager = compassManager;}


    /*******************
     TRACKER METHODS
     ******************/
    public boolean addTracker(Player player)
    {
        if(isTracker(player)) return false;
        Tracker tracker = new Tracker(this, player);

        TRACKERS.add(tracker);
        notifyTrackerAdded(tracker);
        return true;
    }

    public boolean removeTracker(Tracker tracker)
    {
        if(tracker == null) return false;
        if(!isTracker(tracker.id())) return false;

        TRACKERS.remove(tracker);

        notifyTrackerRemoved(tracker);
        tracker.setNull();

        return true;
    }

    public boolean removeTracker(Player player)
    {
        if(!isTracker(player)) return false;
        Tracker tracker = getPlayerAsTracker(player);

        if(tracker == null) return false;
        boolean removed = TRACKERS.remove(tracker);
        if(removed)
        {
            notifyTrackerRemoved(tracker);
            tracker.setNull();
        }
        return removed;
    }

    public boolean isTracker(Tracker tracker)
    {
        if(tracker == null) return false;
        return tracker.apiInstance().equals(this) && TRACKERS.contains(tracker);
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

    public Tracker getPlayerAsTracker(Player player)
    {
        if(player == null) throw new NullPointerException("Player cannot be null.");

        for(Tracker tracker : TRACKERS)
            if(tracker.id().equals(player.getUniqueId()))
                return tracker;
        return null;
    }


    public Set<Tracker> getTrackers()
    {
        return (HashSet<Tracker>) ((HashSet<Tracker>) TRACKERS).clone();
    }

    public Set<Tracker> getOnlineTrackers()
    {
        Set<Tracker> onlineTrackers = new HashSet<>();
        for(Tracker tracker : TRACKERS)
            if(tracker.get() != null) onlineTrackers.add(tracker);

        return onlineTrackers;
    }


    /*******************
        TARGET METHODS
     ******************/
    public boolean addTarget(Entity entity)
    {
        if(isTarget(entity)) return false;
        Target target = new Target(this, entity);

        TARGETS.add(target);
        notifyTargetAdded(target);
        return true;
    }

    public boolean removeTarget(Target target)
    {
        if(target == null) return false;
        if(!isTracker(target.id())) return false;

        TARGETS.remove(target);

        notifyTargetRemoved(target);
        target.setNull();

        return true;
    }

    public boolean removeTarget(Entity entity)
    {
        if(!isTarget(entity)) return false;
        Target target = getEntityAsTarget(entity);

        if (target == null) return false;
        boolean removed = TARGETS.remove(target);
        if (removed)
        {
            for(Tracker tracker : getTrackers())
                if(tracker.getTarget().equals(target))
                    tracker.setTarget(null);

            notifyTargetRemoved(target);
            target.setNull();
        }
        return removed;
    }

    public boolean isTarget(Target target)
    {
        if(target == null) return false;
        return target.apiInstance().equals(this) && TARGETS.contains(target);
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

    public Target getEntityAsTarget(Entity entity)
    {
        if(entity == null) throw new NullPointerException("Entity cannot be null.");

        for(Target target : TARGETS)
            if(target.get().equals(entity))
                return target;
        return null;
    }


    public Set<Target> getTargets()
    {
        return (Set<Target>) ((HashSet<Target>) TARGETS).clone();
    }

    public boolean isInSameWorld(Entity e1, Entity e2)
    {
        if(e1 == null || e2 == null)
            throw new NullPointerException("Entities cannot be null");
        return e1.getWorld().equals(e2.getWorld());
    }

    public boolean isInSameWorld(Entity entity, Location location)
    {
        if(entity == null || location == null)
            throw new NullPointerException("Entity or Location cannot be null");
        return entity.getWorld().equals(location.getWorld());
    }


    protected void destroy()
    {
        for(TrackerAPIEventExecutor eventExecutor : ACTIVE_EVENT_EXECUTORS)
            eventExecutor.handlerList().unregister(eventExecutor.registeredListener());

        clearTrackers();
        clearTargets();

        notifyInstanceDestroyed();
    }

    public void clearTrackers()
    {
        for(Tracker tracker : getTrackers())
            removeTracker(tracker);
    }

    public void clearTargets()
    {
        for(Target target : getTargets())
            removeTarget(target);
    }

    public int trackerCount()
    {
        return TRACKERS.size();
    }

    public int targetCount()
    {
        return TARGETS.size();
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

    public void registerEventExecutor(TrackerAPIEventExecutor eventExecutor)
    {
        if(eventExecutor == null) return;

        eventExecutor.handlerList().register(eventExecutor.registeredListener());
        ACTIVE_EVENT_EXECUTORS.add(eventExecutor);
    }

    public void unregisterEventExecutor(TrackerAPIEventExecutor eventExecutor)
    {
        if(eventExecutor == null) return;

        eventExecutor.handlerList().unregister(eventExecutor.registeredListener());
        ACTIVE_EVENT_EXECUTORS.remove(eventExecutor);
    }

    public void unregisterEventExecutors(HandlerList handlerList)
    {
        if(handlerList == null) return;

        for(TrackerAPIEventExecutor executor : ACTIVE_EVENT_EXECUTORS.toArray(new TrackerAPIEventExecutor[0]))
            if(executor.handlerList().equals(handlerList))
            {
                executor.handlerList().unregister(executor.registeredListener());
                ACTIVE_EVENT_EXECUTORS.remove(executor);
            }
    }

    private void registerDefaultExecutors()
    {
        registerEventExecutor(new OnTrackerCompassClick(this));
    }

    private void loadSettings()
    {
        for(TrackerAPISettings.Option option : TrackerAPISettings.Option.values())
            settingChanged(option, false, this.settings.get(option));
    }

    @Override
    public void settingChanged(TrackerAPISettings.Option option, boolean oldValue, boolean newValue)
    {
        if(settings.get(TrackerAPISettings.Option.ENABLE_DEBUG_MODE))
            TrackerAPIPlugin.sendConsoleMessage(TrackerAPIPlugin.ConsoleMessageType.NEUTRAL,
                    "Settings have changed.", ID);

        switch(option)
        {
            case AUTO_TRACK_MOVEMENT:
                if(newValue) registerEventExecutor(new OnTargetMove(this));
                else unregisterEventExecutors(PlayerMoveEvent.getHandlerList());

            case AUTO_TRACK_WORLD_EXITS:
                if(newValue)
                {
                    registerEventExecutor(new OnTargetWorldChange.Entity(this));
                    registerEventExecutor(new OnTargetWorldChange.Player(this));
                }
                else
                {
                    unregisterEventExecutors(EntityPortalEvent.getHandlerList());
                    unregisterEventExecutors(PlayerPortalEvent.getHandlerList());
                }

            case DROPPABLE_TRACKER_COMPASS:
                if(!newValue) registerEventExecutor(new OnTrackerDropCompass(this));
                else unregisterEventExecutors(new OnTrackerDropCompass(this).handlerList());

            case DROP_TRACKER_COMPASS_ON_DEATH:
                if(!newValue) registerEventExecutor(new OnTrackerDeath(this));
                else unregisterEventExecutors(new OnTrackerDeath(this).handlerList());

            case GIVE_TRACKER_COMPASS_ON_RESPAWN:
                if(newValue) registerEventExecutor(new OnTrackerRespawn(this));
                else unregisterEventExecutors(new OnTrackerRespawn(this).handlerList());
        }
    }
}
