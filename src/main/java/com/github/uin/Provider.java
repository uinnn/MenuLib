package com.github.uin;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public interface Provider {

   <T extends AbstractMenu> T menu();

   static <T extends AbstractMenu> T menu(Class<T> clazz) {
      return clazz.cast(AbstractMenu.class);
   }


   // base
   String title();
   int size();
   boolean opened();
   Player opener();
   Inventory inventory();
   Inventory openerInventory();
   Inventory toInventory();

   void setTitle(String title);
   void setSize(int size);
   void setOpened(boolean opened);
   void setOpener(Player opener);
   void setInventory(Inventory inventory);

   // properties
   Map<String, MenuProperty> menuProperties();
   boolean hasProperty(String key);
   MenuProperty getProperty(String key);
   void removeProperty(String key);
   void insertProperty(String key, MenuProperty property);

   // items
   Map<Integer, UseableItem> itemsMap();
   int amountOfItems();
   boolean containsItem(int slot);
   Optional<UseableItem> itemIn(int slot);
   Set<Integer> slotsOfItems();
   Collection<UseableItem> items();
   void addItem(int slot, UseableItem item);
   void removeItem(int slot);

   // handlers
   Map<HandlerType, List<InvokableMethod>> handlers();
   List<InvokableMethod> initializeHandlers();
   List<InvokableMethod> finalizeHandlers();
   void runInitializeHandlers();
   void runFinalizeHandlers();
   void setHandlers(Class<?> clazz, Object instance);

   // others
   void defaultOpen(Player player);
   void defaultClose(Player player);
   void open(Player player);
   void close(Player player);

}
