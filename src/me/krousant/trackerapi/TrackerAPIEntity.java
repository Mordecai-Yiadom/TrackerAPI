package me.krousant.trackerapi;

import org.bukkit.entity.*;

import java.io.Serializable;
import java.util.UUID;

public abstract class TrackerAPIEntity<T extends Entity> implements Serializable
{
    private UUID ID;
    private transient T entity;

    public TrackerAPIEntity(T entity)
    {
        if(entity  == null) throw new NullPointerException("Entity cannot be null.");
        ID = entity.getUniqueId();
    }

    public T get()
    {
        return entity;
    }

    public UUID id()
    {
        return ID;
    }
}
