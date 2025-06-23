package me.krousant.trackerapi;

import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

import java.util.UUID;

public class TrackerAPIPlugin extends JavaPlugin
{
    private static Plugin PLUGIN;

    @Override
    public void onEnable()
    {
        sendConsoleMessage(ConsoleMessageType.SUCCESS, "TrackerAPI has been enabled.", null);
        PLUGIN = Bukkit.getPluginManager().getPlugin("TrackerAPI");
    }

    @Override
    public void onDisable()
    {
        TrackerAPIManager.clearInstances();
        sendConsoleMessage(ConsoleMessageType.NEUTRAL, "TrackerAPI has been disabled.", null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return true;
    }

    //Convenience Methods(s) for Plugin
    public static void sendConsoleMessage(ConsoleMessageType messageType, String message, String instanceID)
    {
        if(message == null) return;

        String id = (instanceID == null) ? "" : "|ID: " + instanceID + "|";

        Bukkit.getConsoleSender()
                .sendMessage(String.format("%s[TrackerAPI] %s (%s): %s", messageType.color, id, messageType.prefix, message));
    }

    public enum ConsoleMessageType
    {
        ERROR(ChatColor.RED, "Error"),
        SUCCESS(ChatColor.GREEN, "Success"),
        NEUTRAL(ChatColor.GRAY, ""),
        WARNING(ChatColor.YELLOW, "Warning");

        private ChatColor color;
        private String prefix;

        ConsoleMessageType(ChatColor color, String prefix)
        {
            this.color = color;
            this.prefix = prefix;
        }
    }

    protected static void saveInstance()
    {

    }

    public static Plugin plugin()
    {
        return PLUGIN;
    }
}
