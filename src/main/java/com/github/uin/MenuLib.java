package com.github.uin;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class MenuLib {

   private static final HashMap<UUID, AbstractMenu> openedUsers = Maps.newHashMap();
   private static MenuLib instance;
   private static JavaPlugin plugin;

   public MenuLib(JavaPlugin javaPlugin) {
      instance = this;
      plugin = javaPlugin;
      Bukkit.getPluginManager().registerEvents(new MenuListener(), javaPlugin);
   }

   public static MenuLib setup(JavaPlugin plugin) {
      return new MenuLib(plugin);
   }

   public static HashMap<UUID, AbstractMenu> getOpenedUsers() {
      return openedUsers;
   }

   public static MenuLib getInstance() {
      return instance;
   }

   public static JavaPlugin getPlugin() {
      return plugin;
   }
}
