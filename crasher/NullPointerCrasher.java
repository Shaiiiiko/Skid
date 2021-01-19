public class NullPointerCrasher {
   public static void start() {
      for(int i = 0; i < 1000; ++i) {
         Minecraft.getMinecraft().player.connection.sendPacket(new CPacketChatMessage((new Random()).toString()));
      }

   }
}
