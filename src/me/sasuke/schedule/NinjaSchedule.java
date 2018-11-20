package me.sasuke.schedule;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

/*
                    (AIVSO - EM BETA!!)
       Lembre-se este experimento está em fase de testes!
       Porfavor, reporte bugs e o mais importante ,
       NÃO USE ISSO COMO UMA ALTERNATIVA DEFINITIVA PARA
       O SISTEMA ORIGINAL DO SPIGOT.

 */

public class NinjaSchedule {


    private Plugin plugin;
    private boolean actived;

    private HashMap<NinjaTask, UpdateType> tasks;

    private NinjaSchedule(Plugin plugin) {
        this.plugin = plugin;
        this.actived = true;
        this.tasks = new HashMap<>();
    }

    public static NinjaSchedule getInstance(Plugin plugin) {
        return new NinjaSchedule(plugin);
    }

    public NinjaTask newSyncNinjaTask(NinjaTask task , UpdateType updateType){
        tasks.put(task , updateType);
        return task;
    }
    
    public BukkitTask startMainTask() {
        BukkitTask task = new BukkitRunnable() {
            public void run() {

                for (NinjaTask ninjaTask : tasks.keySet()) {
                    if(tasks.get(ninjaTask).Elapsed()){
                        ninjaTask.run();
                    }
                }
                
                if (!actived) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1L);

        return task;
    }

    private Plugin getPlugin() {
        return plugin;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public interface NinjaTask {
        public void run();
    }

}
