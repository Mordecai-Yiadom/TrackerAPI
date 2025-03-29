package me.krousant.trackerapi;

public interface TrackerAPISettingChangeListener
{
    void settingChanged(TrackerAPISettings.Option option, boolean oldValue, boolean newValue);
}
