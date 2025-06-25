package me.krousant.trackerapi;

import me.krousant.trackerapi.event.listener.TrackerAPIEntityChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

public abstract class TrackerAPIEntity<T extends Entity> implements Serializable
{
    private UUID ID;
    private final LinkedList<TrackerAPIEntityChangeListener> CHANGE_LISTENERS;
    private boolean isNull;

    public TrackerAPIEntity(T entity)
    {
        if(entity  == null) throw new NullPointerException("Entity cannot be null.");
        ID = entity.getUniqueId();
        isNull = false;
        CHANGE_LISTENERS = new LinkedList<>();
    }

    public T get()
    {
        return (T) Bukkit.getEntity(ID);
    }

    public UUID id()
    {
        return ID;
    }

    public void addChangeListener(TrackerAPIEntityChangeListener listener)
    {
        CHANGE_LISTENERS.add(listener);
    }

    public void removeChangeListener(TrackerAPIEntityChangeListener listener)
    {
        CHANGE_LISTENERS.remove(listener);
    }

    protected void notifyChangeListeners(Object oldValue, Object newValue)
    {
        for(TrackerAPIEntityChangeListener listener : CHANGE_LISTENERS)
            listener.entityChanged(oldValue, newValue);
    }

    protected void notifyChangeListeners(Object... changes)
    {
        for(TrackerAPIEntityChangeListener listener : CHANGE_LISTENERS)
            listener.entityChanged(changes);
    }

    //TODO: Implement (internal) flags for TrackerAPIEntities || ex: NULL_FLAG,
    protected synchronized void setNull()
    {
        isNull = true;
    }

    public boolean isNull()
    {
        return isNull;
    }
}
