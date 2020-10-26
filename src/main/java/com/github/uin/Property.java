package com.github.uin;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface Property {

   Object property();
   int asInteger();
   byte asByte();
   short asShort();
   long asLong();
   float asFloat();
   double asDouble();
   String asString();
   ItemStack asItemStack();
   UseableItem asUseableItem();
   UUID asUUID();
   Player asPlayer();
   LivingEntity asEntity();
   <T> T asCustom(Class<T> clazz);

}
