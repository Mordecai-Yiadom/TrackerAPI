package me.krousant.trackerapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class Target extends TrackerAPIEntity<Entity>
{
    private final HashMap<UUID, Location> WORLD_EXITS;

    protected Target(Entity entity)
    {
        super(entity);
        WORLD_EXITS = new HashMap<>();
    }

    public Entity gets()
    {
        if(super.get() == null) return null;
        return (super.get().getType().equals(EntityType.PLAYER)) ? Bukkit.getPlayer(id()) : super.get();
    }

    public Location getWorldExitLocation(World world)
    {
        if(world == null) throw new NullPointerException("World cannot be null.");
        return WORLD_EXITS.get(world.getUID());
    }

    //TODO clean up error handling
    public void setWorldExitLocation(World world, Location location)
    {
        if(world == null) throw new NullPointerException("World cannot be null.");

        if(WORLD_EXITS.containsKey(world.getUID())) WORLD_EXITS.replace(world.getUID(), location);
        else WORLD_EXITS.put(world.getUID(), location);
    }
}
