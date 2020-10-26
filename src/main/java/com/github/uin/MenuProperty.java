package com.github.uin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public abstract class MenuProperty implements Property {

   private Object property;
   private final boolean permitsNull;

   public MenuProperty(Object property, boolean permitsNull) {
      this.property = property;
      this.permitsNull = permitsNull;
   }

   public static MenuProperty of(Object property) {
      return new MenuProperty(property, false) {};
   }

   public static MenuProperty nullable(Object property) {
      return new MenuProperty(property, true) {};
   }


   public void setProperty(Object property) {
      this.property = permitsNull && property == null ? null :
            !permitsNull && property == null ? "null" : property;
   }

   public boolean permitsNull() {
      return permitsNull;
   }


   @Override
   public Object property() {
      return property;
   }

   @Override
   public int asInteger() {
      return property instanceof Integer ? (int) property : 0;
   }

   @Override
   public byte asByte() {
      return property instanceof Byte ? (byte) property : 0;
   }

   @Override
   public short asShort() {
      return property instanceof Short ? (short) property : 0;
   }

   @Override
   public long asLong() {
      return property instanceof Long ? (long) property : 0;
   }

   @Override
   public float asFloat() {
      return property instanceof Float ? (float) property : 0;
   }

   @Override
   public double asDouble() {
      return property instanceof Double ? (double) property : 0;
   }

   @Override
   public String asString() {
      return property instanceof String ? (String) property : permitsNull ? null : "";
   }

   @Override
   public ItemStack asItemStack() {
      return property instanceof ItemStack ? (ItemStack) property :
            permitsNull ? null : new ItemStack(Material.AIR);
   }

   @Override
   public UseableItem asUseableItem() {
      return property instanceof UseableItem ? (UseableItem) property :
            permitsNull ? null : new UseableItem(Material.AIR);
   }

   @Override
   public UUID asUUID() {
      return property instanceof UUID ? (UUID) property : permitsNull ? null : UUID.randomUUID();
   }

   @Override
   public Player asPlayer() {
      return property instanceof Player ? (Player) property : permitsNull ?
            null : new ArrayList<Player>(Bukkit.getOnlinePlayers()).get(0);
   }

   @Override
   public LivingEntity asEntity() {
      Location loc = new Location(Bukkit.getWorlds().get(0), 9999, 9999, 9999);
      return property instanceof Entity ? (LivingEntity) property : permitsNull
            ? null : loc.getWorld().spawn(loc, ArmorStand.class);
   }

   @Override
   public <T> T asCustom(Class<T> clazz) {
      T object = clazz.cast(property);
      return permitsNull && object == null ? null : object;
   }

}
