package com.github.uin;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractMenu implements Provider {

   private final Map<String, MenuProperty> menuProperties;
   private final Map<HandlerType, List<InvokableMethod>> handlers;
   private final Map<Integer, UseableItem> itemsMap;
   private String title;
   private int size;
   private Player opener;
   private Inventory inventory;
   private boolean opened;


   public AbstractMenu(String title, int size) {
      this.title = title;
      this.size = size;
      this.opened = false;
      this.menuProperties = Maps.newHashMap();
      this.handlers = Maps.newHashMap();
      this.itemsMap = Maps.newHashMap();
   }


   @Override
   @SuppressWarnings("unchecked")
   public <T extends AbstractMenu> T menu() {
      return (T) Provider.menu(getClass());
   }

   @Override
   public String title() {
      return title;
   }

   @Override
   public int size() {
      return size;
   }

   @Override
   public boolean opened() {
      return opened;
   }

   @Override
   public Player opener() {
      return opener;
   }

   @Override
   public Inventory inventory() {
      return inventory;
   }

   @Override
   public Inventory openerInventory() {
      return opened ? opener.getOpenInventory().getTopInventory() : null;
   }

   @Override
   public Inventory toInventory() {
      Inventory inventory = Bukkit.createInventory(null, size, title);
      itemsMap.forEach(this::addItem);
      return inventory;
   }

   @Override
   public void setTitle(String title) {
      this.title = title;
   }

   @Override
   public void setSize(int size) {
      this.size = size;
   }

   @Override
   public void setOpened(boolean opened) {
      this.opened = opened;
   }

   @Override
   public void setOpener(Player opener) {
      this.opener = opener;
   }

   @Override
   public void setInventory(Inventory inventory) {
      this.inventory = inventory;
   }

   @Override
   public Map<String, MenuProperty> menuProperties() {
      return menuProperties;
   }

   @Override
   public boolean hasProperty(String key) {
      return menuProperties.containsKey(key);
   }

   @Override
   public MenuProperty getProperty(String key) {
      return menuProperties().get(key);
   }

   @Override
   public void removeProperty(String key) {
      menuProperties.remove(key);
   }

   @Override
   public void insertProperty(String key, MenuProperty property) {
      menuProperties.put(key, property);
   }

   @Override
   public Map<Integer, UseableItem> itemsMap() {
      return itemsMap;
   }

   @Override
   public int amountOfItems() {
      return itemsMap.size();
   }

   @Override
   public boolean containsItem(int slot) {
      return itemsMap.containsKey(slot);
   }

   @Override
   public Optional<UseableItem> itemIn(int slot) {
      return Optional.ofNullable(itemsMap.get(slot));
   }

   @Override
   public Set<Integer> slotsOfItems() {
      return Collections.unmodifiableSet(itemsMap.keySet());
   }

   @Override
   public Collection<UseableItem> items() {
      return Collections.unmodifiableCollection(itemsMap.values());
   }

   @Override
   public void addItem(int slot, UseableItem item) {
      itemsMap.put(slot, item);
      if (inventory != null)
         inventory.setItem(slot, item);
      if (openerInventory() != null)
         openerInventory().setItem(slot, item);
   }

   @Override
   public void removeItem(int slot) {
      itemsMap.remove(slot);
      if (inventory != null)
         inventory.setItem(slot, null);
      if (openerInventory() != null)
         openerInventory().setItem(slot, null);
   }

   @Override
   public Map<HandlerType, List<InvokableMethod>> handlers() {
      return handlers;
   }

   @Override
   public List<InvokableMethod> initializeHandlers() {
      return handlers.get(HandlerType.INITIALIZE);
   }

   @Override
   public List<InvokableMethod> finalizeHandlers() {
      return handlers.get(HandlerType.FINALIZE);
   }

   @Override
   public void runInitializeHandlers() {
      initializeHandlers().forEach(method -> method.invoke(this));
   }

   @Override
   public void runFinalizeHandlers() {
      finalizeHandlers().forEach(method -> method.invoke(this));
   }

   @Override
   public void setHandlers(Class<?> clazz, Object instance) {
      Validate.notNull(clazz, "Class cannot be null");
      Validate.notNull(instance, "Instance cannot be null");
      List<InvokableMethod> initializeHandlers = new ArrayList<>();
      List<InvokableMethod> finalizeHandlers = new ArrayList<>();
      for (Method method : clazz.getDeclaredMethods()) {
         if (!method.isAnnotationPresent(MenuHandler.class))
            continue;
         MenuHandler annotation = method.getAnnotation(MenuHandler.class);
         InvokableMethod invokableMethod = new InvokableMethod(method, instance);
         if (annotation.type() == HandlerType.ALL) {
            initializeHandlers.add(invokableMethod);
            finalizeHandlers.add(invokableMethod);
         } else if (annotation.type() == HandlerType.INITIALIZE) {
            initializeHandlers.add(invokableMethod);
         } else if (annotation.type() == HandlerType.FINALIZE) {
            finalizeHandlers.add(invokableMethod);
         }
      }
      this.handlers.put(HandlerType.INITIALIZE, initializeHandlers);
      this.handlers.put(HandlerType.FINALIZE, finalizeHandlers);
   }

   @Override
   public void defaultOpen(Player player) {
      MenuLib.getOpenedUsers().put(player.getUniqueId(), this);
      opener = player;
      inventory = toInventory();
      opened = true;
   }

   @Override
   public void defaultClose(Player player) {
      MenuLib.getOpenedUsers().remove(player.getUniqueId());
      opened = false;
   }

   @Override
   public abstract void open(Player player);

   @Override
   public abstract void close(Player player);
}
