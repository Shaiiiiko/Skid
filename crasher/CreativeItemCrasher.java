public class CreativeItemCrasher {
   public static double packets = 10.0D;

   public static void start() {
      EntityPlayerSP player = Minecraft.getMinecraft().player;
      if (player.capabilities.isCreativeMode) {
         for(int j = 0; (double)j < packets; ++j) {
            Minecraft.getMinecraft().player.connection.sendPacket(new CPacketCreativeInventoryAction(j % 9 + 1, new ItemStack(Blocks.STONE, 64)));
         }
      }

   }
}
