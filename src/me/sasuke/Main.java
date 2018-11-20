package me.sasuke;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.sasuke.reflection.Hokage;
import me.sasuke.schedule.NinjaSchedule;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class Main extends JavaPlugin {

    protected static Plugin plugin;
    protected static Gson gson;
    protected static NinjaSchedule schedule;

    public static NinjaSchedule getSchedule() {
        return schedule;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Gson getGson() {
        return gson;
    }

    public void onEnable() {
        plugin = this;
        schedule = NinjaSchedule.getInstance(this);


        // Normalmente uso alguns adapters para locations e itemstacks...
        gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/mm/aa").enableComplexMapKeySerialization().create();


        Set<Class<?>> classes = Hokage.getClasses(getFile(), "me.sasuke");

        for (Class<?> classe : classes) {
            if (Listener.class.isAssignableFrom(classe)) {

            }
        }

    }

    public void onDisable() {

    }

}
