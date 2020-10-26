## MenuLib
### A Inventory library for Spigot

---

### Download jar

##### https://github.com/uinnn/MenuLib/releases/tag/1.0

---

#### MenuLib is a library created for Spigot 1.8x to facilitate the creation of inventories. <br>

---

##### This library is free to use, without any license, you don't need to leave the credits. <br>

--- 

#### Quick information about the library:
- Very customizable, you can easily extend the library.
- Easy handling of items that have functionality.
- Easy creation of listeners for menu opening and closing events.
- Properties, acting as a menu metadata.

---

### Setup
To implement and start using this library,
place the following code in your onEnable method

```java
   @Override
   public void onEnable() {
      MenuLib.setup(this);
   }
```

--- 

### Creating menu
To create a menu is very simple, just make a Menu class wrapper!

```java
   Menu menu = new Menu("title" /* title */, 18 /* size */, true /* closeable */);
   // or
   Menu menu = new Menu("title" /* title */, 18 /* size */); // here, closeable is true
```

---

### Adding items 
To add items to the menu, simply create a UseableItem and use the method addItem(int slot, UseableItem item)

```java
   // a empty item, this don't execute nothing when click
   UseableItem emptyItem = new UseableItem(Material.GLASS);
   
   // a item that executes a consumer when click
   UseableItem ofItem = new UseableItem(Material.GLASS).setConsumer(event
      -> event.getWhoClicked().sendMessage("§aClicked!"));
      
   // adding the items in the menu
   menu.addItem(2, emptyItem);
   menu.addItem(4, ofItem);
```

---

### Properties (metadata)
To add properties to the menu, it's also easy, just use the method insertProperty(String key, MenuProperty property)

```java
  // this is a null-safe version, in most cases it will try not to return null
  // Adding a itemstack to the menu properties
  menu.insertProperty("item", MenuProperty.of(new ItemStack(Material.GLASS)));

  // a non null-safe version, if a property is null, will return null
  menu.insertProperty("item", MenuProperty.nullable(new ItemStack(Material.GLASS)));
```

To get a property:

```java
  ItemStack item = menu.getProperty("item").asItemStack();
  int number = menu.getProperty("number").asInteger();
  Object defaultValue = menu.getProperty("default").property();
  Entity entity = menu.getProperty("entity").asEntity();
  Menu custom = menu.getProperty("sub-menu").asCustom(Menu.class);
  // and more...
```

Other methods related:

```java
  // to verify if has a property
  menu.hasProperty("key");
      
  // to remove a property
  menu.removeProperty("key");
```

---

### MenuHandlers (listener)
A MenuHandler is an annotation that you use in a method you created to execute listeners <br>
We must first register them in our menu:

```java
  // is highly recommended to put this in the class that have the methods annoted with MenuHandler
  menu.setHandlers(getClass() /* the class */, this /* instance of the methods, in most cases a 'this' resolves */);
```

Here we will create a method that will be executed when the menu opens:

```java
  @MenuHandler
  public void onOpen(Menu menu) {
    menu.opener().sendMessage("§aHello World!");
  }
```

Now we will create a method that will be executed when the menu is closed:

```java
  @MenuHandler(type = HandlerType.FINALIZE)
  public void onClose(Menu menu) {
    menu.opener().sendMessage("§cBye World! :( ");
  }
```

Well, as you saw the only rule you should follow, is that the method must contain only a single parameter, which is the Menu parameter

---


### More detailed documentation will be made soon!











