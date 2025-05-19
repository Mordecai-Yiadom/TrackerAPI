package me.krousant.trackerapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.io.Serializable;
import java.util.UUID;

public abstract class TrackerAPIEntity<T extends Entity> implements Serializable
{
    private UUID ID;

    public TrackerAPIEntity(T entity)
    {
        if(entity  == null) throw new NullPointerException("Entity cannot be null.");
        ID = entity.getUniqueId();
    }

    public T get()
    {
        return (T) Bukkit.getEntity(ID);
    }

    public UUID id()
    {
        return ID;
    }
}
