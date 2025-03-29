package me.krousant.trackerapi;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TrackerAPISettings
{
    private Map<Option, Boolean> SETTINGS_MAP;
    private final Set<TrackerAPISettingChangeListener> LISTENERS = new HashSet<>();

    //THIS CANNOT BE CHANGED
    private final ItemStack DEFAULT_TRACKER_COMPASS;

    private TrackerAPISettings(ItemStack compass)
    {
        if(compass == null) DEFAULT_TRACKER_COMPASS = TrackerAPIUtil.DEFAULT_TRACKER_COMPASS;
        else DEFAULT_TRACKER_COMPASS = compass;

        SETTINGS_MAP = new HashMap<>();
        for(Option option : Option.values()) SETTINGS_MAP.put(option, false);
    }

    private static TrackerAPISettings build(ItemStack defaultTrackerCompass)
    {
        return new TrackerAPISettings(defaultTrackerCompass);
    }

    public TrackerAPISettings set(Option option, boolean value)
    {
        if(SETTINGS_MAP.get(option) == value) return this;

        notifyChangeListeners(option, SETTINGS_MAP.get(option), value);
        SETTINGS_MAP.replace(option, value);
        return this;
    }

    public ItemStack getDefaultTrackerCompass()
    {
        return DEFAULT_TRACKER_COMPASS;
    }

    public boolean registerChangeListener(TrackerAPISettingChangeListener listener)
    {
        return LISTENERS.add(listener);
    }

    public boolean unregisterChangeListener(TrackerAPISettingChangeListener listener)
    {
        return LISTENERS.remove(listener);
    }

    public void notifyChangeListeners(Option option, boolean oldValue, boolean newValue)
    {
        for(TrackerAPISettingChangeListener listener : LISTENERS)
            listener.settingChanged(option, oldValue, newValue);
    }


    //All Values are false by default
    public enum Option
    {
        AUTO_TRACK_MOVEMENT,
        AUTO_TRACK_WORLD_EXITS,
        DROP_COMPASS_ON_DEATH,
        DROPPABLE_COMPASS,
        ENABLE_DEBUG_MODE,
        DESTROY_COMPASSES_WHEN_FINISHED
    }
}
