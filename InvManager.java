package rip.autumn.module.impl.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.DamageSource;
import rip.autumn.annotations.Label;
import rip.autumn.events.player.MotionUpdateEvent;
import rip.autumn.module.Module;
import rip.autumn.module.ModuleCategory;
import rip.autumn.module.annotations.Aliases;
import rip.autumn.module.annotations.Category;
import rip.autumn.module.option.Option;
import rip.autumn.module.option.impl.DoubleOption;
import rip.autumn.utils.InventoryUtils;
import rip.autumn.utils.Stopwatch;

@Label("Inventory Manager")
@Category(ModuleCategory.PLAYER)
@Aliases({"inventorymanager", "invmanager"})
public final class InventoryManagerMod extends Module {
  public static final Stopwatch INV_STOPWATCH = new Stopwatch();
  
  private List<Integer> allSwords = new ArrayList<>();
  
  private List<Integer>[] allArmors = (List<Integer>[])new List[4];
  
  private List<Integer> trash = new ArrayList<>();
  
  private boolean cleaning;
  
  private int[] bestArmorSlot;
  
  private int bestSwordSlot;
  
  private final DoubleOption swordSlot = new DoubleOption("Sword Slot", 1.0D, 1.0D, 9.0D, 1.0D);
  
  private final DoubleOption delay = new DoubleOption("Delay", 250.0D, 0.0D, 1000.0D, 50.0D);
  
  public InventoryManagerMod() {
    addOptions(new Option[] { (Option)this.swordSlot, (Option)this.delay });
  }
  
  @Listener(MotionUpdateEvent.class)
  public void onEvent(MotionUpdateEvent event) {
    if ((mc.currentScreen == null || mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) && event.isPre()) {
      collectItems();
      collectBestArmor();
      collectTrash();
      int trashSize = this.trash.size();
      boolean trashPresent = (trashSize > 0);
      EntityPlayerSP player = mc.thePlayer;
      int windowId = player.openContainer.windowId;
      int bestSwordSlot = this.bestSwordSlot;
      if (trashPresent) {
        if (!this.cleaning) {
          this.cleaning = true;
          player.sendQueue.addToSendQueueSilent((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        } 
        for (int i = 0; i < trashSize; i++) {
          int slot = ((Integer)this.trash.get(i)).intValue();
          if (checkDelay())
            break; 
          mc.playerController.windowClick(windowId, (slot < 9) ? (slot + 36) : slot, 1, 4, (EntityPlayer)player);
          INV_STOPWATCH.reset();
        } 
        if (this.cleaning) {
          player.sendQueue.addToSendQueueSilent((Packet)new C0DPacketCloseWindow(windowId));
          this.cleaning = false;
        } 
      } 
      if (bestSwordSlot != -1 && 
        !checkDelay()) {
        mc.playerController.windowClick(windowId, (bestSwordSlot < 9) ? (bestSwordSlot + 36) : bestSwordSlot, ((Double)this.swordSlot.getValue()).intValue() - 1, 2, (EntityPlayer)player);
        INV_STOPWATCH.reset();
      } 
    } 
  }
  
  private boolean checkDelay() {
    return !INV_STOPWATCH.elapsed(((Double)this.delay.getValue()).longValue());
  }
  
  public void collectItems() {
    this.bestSwordSlot = -1;
    this.allSwords.clear();
    float bestSwordDamage = -1.0F;
    for (int i = 0; i < 36; i++) {
      ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
      if (itemStack != null && itemStack.getItem() != null)
        if (itemStack.getItem() instanceof net.minecraft.item.ItemSword) {
          float damageLevel = InventoryUtils.getDamageLevel(itemStack);
          this.allSwords.add(Integer.valueOf(i));
          if (bestSwordDamage < damageLevel) {
            bestSwordDamage = damageLevel;
            this.bestSwordSlot = i;
          } 
        }  
    } 
  }
  
  private void collectBestArmor() {
    int[] bestArmorDamageReducement = new int[4];
    this.bestArmorSlot = new int[4];
    Arrays.fill(bestArmorDamageReducement, -1);
    Arrays.fill(this.bestArmorSlot, -1);
    int i;
    for (i = 0; i < this.bestArmorSlot.length; i++) {
      ItemStack itemStack = mc.thePlayer.inventory.armorItemInSlot(i);
      this.allArmors[i] = new ArrayList<>();
      if (itemStack != null && itemStack.getItem() != null && 
        itemStack.getItem() instanceof ItemArmor) {
        ItemArmor armor = (ItemArmor)itemStack.getItem();
        int currentProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
        bestArmorDamageReducement[i] = currentProtectionLevel;
      } 
    } 
    for (i = 0; i < 36; i++) {
      ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
      if (itemStack != null && itemStack.getItem() != null)
        if (itemStack.getItem() instanceof ItemArmor) {
          ItemArmor armor = (ItemArmor)itemStack.getItem();
          int armorType = 3 - armor.armorType;
          this.allArmors[armorType].add(Integer.valueOf(i));
          int slotProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
          if (bestArmorDamageReducement[armorType] < slotProtectionLevel) {
            bestArmorDamageReducement[armorType] = slotProtectionLevel;
            this.bestArmorSlot[armorType] = i;
          } 
        }  
    } 
  }
  
  private void collectTrash() {
    this.trash.clear();
    int i;
    for (i = 0; i < 36; i++) {
      ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
      if (itemStack != null && itemStack.getItem() != null)
        if (!InventoryUtils.isValidItem(itemStack))
          this.trash.add(Integer.valueOf(i));  
    } 
    for (i = 0; i < this.allArmors.length; i++) {
      List<Integer> armorItem = this.allArmors[i];
      if (armorItem != null) {
        List<Integer> list = this.trash;
        for (int i1 = 0, armorItemSize = armorItem.size(); i1 < armorItemSize; i1++) {
          Integer slot = armorItem.get(i1);
          if (slot.intValue() != this.bestArmorSlot[i])
            list.add(slot); 
        } 
      } 
    } 
    List<Integer> integers = this.trash;
    for (int j = 0, allSwordsSize = this.allSwords.size(); j < allSwordsSize; j++) {
      Integer slot = this.allSwords.get(j);
      if (slot.intValue() != this.bestSwordSlot)
        integers.add(slot); 
    } 
  }
}
