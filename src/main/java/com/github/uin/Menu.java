package com.github.uin;

import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Menu extends AbstractMenu implements MenuProvider {

   private int currentSlot;
   private int currentRow;
   private int currentColumn;
   private boolean closeable;
   private UseableItem currentItem;


   public Menu(String title, int size, boolean closeable) {
      super(title, size);
      this.closeable = closeable;
   }

   public Menu(String title, int size) {
      this(title, size, true);
   }



   @Override
   public void open(Player player) {
      defaultOpen(player);
      player.openInventory(inventory());
   }

   @Override
   public void close(Player player) {
      player.closeInventory();
   }

   @Override
   @SuppressWarnings("unchecked")
   public <T extends AbstractMenu> T menu() {
      return (T) getClass().cast(this);
   }


   @Override
   public int rows() {
      return size() / 9 <= 0 ? 1 : size() / 9;
   }

   @Override
   public int columns() {
      return 9;
   }

   @Override
   public int currentSlot() {
      return currentSlot;
   }

   @Override
   public int currentRow() {
      return currentRow;
   }

   @Override
   public int currentColumn() {
      return currentColumn;
   }

   @Override
   public boolean isCloseable() {
      return closeable;
   }

   @Override
   public UseableItem currentItem() {
      return currentItem;
   }

   @Override
   public void setTitle(String title) {
      super.setTitle(title);
      if (opened()) {
         EntityPlayer p = ((CraftPlayer) opener()).getHandle();
         p.playerConnection.sendPacket(new PacketPlayOutOpenWindow(p.activeContainer.windowId, "minecraft:chest", new ChatMessage(title), size()));
         p.updateInventory(p.activeContainer);
      }
   }

   @Override
   public void setCurrentRow(int row) {
      this.currentRow = row;
   }

   @Override
   public void setCurrentColumn(int column) {
      this.currentColumn = column;
   }

   @Override
   public void setCurrentSlot(int slot) {
      this.currentSlot = slot;
   }

   @Override
   public void setCloseable(boolean closeable) {
      this.closeable = closeable;
   }

   @Override
   public void setCurrentItem(UseableItem item) {
      this.currentItem = item;
   }

}
