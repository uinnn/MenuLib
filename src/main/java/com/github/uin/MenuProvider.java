package com.github.uin;

public interface MenuProvider extends Provider {

   int rows();
   int columns();
   int currentSlot();
   int currentRow();
   int currentColumn();
   boolean isCloseable();
   UseableItem currentItem();

   void setCurrentRow(int row);
   void setCurrentColumn(int column);
   void setCurrentSlot(int slot);
   void setCloseable(boolean closeable);
   void setCurrentItem(UseableItem item);

}
