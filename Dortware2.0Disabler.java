public class Disabler extends Module {

    private final Timer timer = new Timer();

    public Disabler(final String name, final int key, final Module.Category category) {
        super(name, key, category);
        this.addModes("Mineplex Combat", "Verus Combat", "Sloth");
    }

    @Override
    public void onDisable() {
    
    }

    @Override
    public void onEnable() {
        timer.reset();
    }

    @Handler
    public Consumer<PacketInEvent> packetInEventConsumer = (event) -> {
        if (isMode("Sloth")) {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                S08PacketPlayerPosLook playerPosLook = (S08PacketPlayerPosLook) event.getPacket();
                playerPosLook.y += 0.001;
            }
        }
    };

    @Handler
    public Consumer<UpdateEvent> eventConsumer = (event) -> {
          
    };

    @Handler
    public Consumer<PacketInEvent> eventConsumer0 = (event) -> {
        if (event.getPacket() instanceof S2APacketParticles) {
            event.cancel();
        }
    };

    @Handler
    public Consumer<PacketOutEvent> eventConsumer1 = (event) -> {

        if (isMode("Mineplex Combat"))
            if (event.getPacket() instanceof C00PacketKeepAlive) {
                C00PacketKeepAlive packetKeepAlive = (C00PacketKeepAlive) event.getPacket();
                packetKeepAlive.key -= 32;
            }
        
        if(isMode("Verus Combat")) {
            if(event.getPacket() instanceof C0BPacketEntityAction) {
                event.cancel();
            }
            if(event.getPacket() instanceof C0FPacketConfirmTransaction) {
                event.cancel();
            }
        }
        
        if (isMode("Sloth")) {
            if (event.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer packetPlayer = (C03PacketPlayer) event.getPacket();
                if (!timer.delay(2000L)) {
                    for (int i = 0; i < 10; ++i) {
                        double y = i > 2 && i < 8 ? -18.0D : 0.001D;
                        PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - y, mc.thePlayer.posZ, true));
                    }
                } else {
                    packetPlayer.y += 0.41999998688697815D;
                }
            }
        }
    };
}
