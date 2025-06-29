package me.krousant.trackerapi;

import me.krousant.trackerapi.event.listener.TrackerAPISettingsChangeListener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TrackerAPISettings
{
    private final Map<Option, Boolean> SETTINGS_MAP;
    private final Set<TrackerAPISettingsChangeListener> LISTENERS = new HashSet<>();

    private TrackerAPISettings()
    {
        SETTINGS_MAP = new HashMap<>();
        for(Option option : Option.values()) SETTINGS_MAP.put(option, false);
    }

    public static TrackerAPISettings build()
    {
        return new TrackerAPISettings();
    }

    public TrackerAPISettings set(Option option, boolean value)
    {
        if(SETTINGS_MAP.get(option) == value) return this;

        notifyChangeListeners(option, SETTINGS_MAP.get(option), value);
        SETTINGS_MAP.replace(option, value);
        return this;
    }

    public boolean get(Option option)
    {
        return SETTINGS_MAP.get(option);
    }

    public boolean registerChangeListener(TrackerAPISettingsChangeListener listener)
    {
        return LISTENERS.add(listener);
    }

    public boolean unregisterChangeListener(TrackerAPISettingsChangeListener listener)
    {
        return LISTENERS.remove(listener);
    }

    public void notifyChangeListeners(Option option, boolean oldValue, boolean newValue)
    {
        for(TrackerAPISettingsChangeListener listener : LISTENERS)
            listener.settingChanged(option, oldValue, newValue);
    }


    //All Values are false by default
    public enum Option
    {
        AUTO_TRACK_MOVEMENT, // <---- Currently only Players are supported
        AUTO_TRACK_WORLD_EXITS,
        ENABLE_TRACKER_COMPASS_MESSAGES,
        DESTROY_TRACKER_COMPASSES_ON_DESTROY,
        DROP_TRACKER_COMPASS_ON_DEATH,
        DROPPABLE_TRACKER_COMPASS,
        ENABLE_DEBUG_MODE,
        GIVE_TRACKER_COMPASS_ON_ADD,
        GIVE_TRACKER_COMPASS_ON_RESPAWN,
        REMOVE_TRACKER_COMPASS_ON_REMOVE,
        SAVE_LAST_TRACKED_LOCATION,
        TRACK_TARGET_ON_COMPASS_RECEIVE,
    }
}
