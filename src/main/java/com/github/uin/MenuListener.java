package com.github.uin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Optional;

public class MenuListener implements Listener {

   @EventHandler
   public void onMenuOpen(InventoryOpenEvent e) {
      AbstractMenu menu = MenuLib.getOpenedUsers().get(e.getPlayer().getUniqueId());
      if (menu == null)
         return;
      menu.runInitializeHandlers();
   }


   @EventHandler
   public void onMenuClick(InventoryClickEvent e) {
      Player p = (Player) e.getWhoClicked();
      if (e.getClickedInventory() != p.getOpenInventory().getTopInventory())
         return;
      Menu menu = MenuLib.getOpenedUsers().get(p.getUniqueId()).menu();
      if (menu == null)
         return;
      e.setCancelled(true);
      if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || e.getAction() == InventoryAction.NOTHING || e.getAction() == InventoryAction.SWAP_WITH_CURSOR || e.getAction() == InventoryAction.UNKNOWN)
         return;
      if (e.getRawSlot() < 0 || e.getRawSlot() > menu.size())
         return;
      menu.setCurrentSlot(e.getRawSlot());
      menu.setCurrentRow(e.getRawSlot() / 9);
      menu.setCurrentColumn(e.getRawSlot() / 6);
      Optional<UseableItem> currentItem = menu.itemIn(e.getRawSlot());
      menu.setCurrentItem(currentItem.orElse(null));
      currentItem.ifPresent(item -> item.run(e));
      p.updateInventory();
   }


   @EventHandler
   public void onMenuClose(InventoryCloseEvent e) {
      Menu menu = (Menu) MenuLib.getOpenedUsers().get(e.getPlayer().getUniqueId());
      if (menu == null)
         return;
      Player p = (Player) e.getPlayer();
      menu.runFinalizeHandlers();
      menu.defaultClose(p);
      if (!menu.isCloseable()) {
         Bukkit.getScheduler().runTaskLater(MenuLib.getPlugin(),
               () -> p.openInventory(e.getInventory()), 1);
      }
   }

}
