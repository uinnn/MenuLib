package com.github.uin;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class UseableItem extends ItemStack {

   private Consumer<InventoryClickEvent> consumer;

   public UseableItem() {
      consumer = e -> {};
   }

   public UseableItem(Material type) {
      super(type);
      consumer = e -> {};
   }

   public UseableItem(Material type, int amount) {
      super(type, amount);
      consumer = e -> {};
   }

   public UseableItem(Material type, int amount, short damage) {
      super(type, amount, damage);
      consumer = e -> {};
   }

   public UseableItem(ItemStack stack) throws IllegalArgumentException {
      super(stack);
      consumer = e -> {};
   }

   public void run(InventoryClickEvent event) {
      this.consumer.accept(event);
   }

   public UseableItem setConsumer(Consumer<InventoryClickEvent> consumer) {
      this.consumer = consumer;
      return this;
   }

   public Consumer<InventoryClickEvent> getConsumer() {
      return consumer;
   }
}
