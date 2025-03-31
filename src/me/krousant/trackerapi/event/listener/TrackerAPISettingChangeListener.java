package me.krousant.trackerapi.event.listener;

import me.krousant.trackerapi.TrackerAPISettings;

public interface TrackerAPISettingChangeListener
{
    void settingChanged(TrackerAPISettings.Option option, boolean oldValue, boolean newValue);
}
