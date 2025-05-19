package me.krousant.trackerapi;

import org.bukkit.entity.*;

import java.util.UUID;

public abstract class TrackerAPIEntity<T extends Entity>
{
    private UUID ID;
    private T entity;

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
