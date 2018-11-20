package me.sasuke;

import org.bukkit.Bukkit;

// Just a simple logger, gonna implement some other things in the future
public class Utils {

  public static void log(String str) {
    Bukkit.getConsoleSender().sendMessage("§3[LOG] §f" + str);
  }
  public static void error(String str) {
    Bukkit.getConsoleSender().sendMessage("§c[ERROR] §f" + str);
  }

}
