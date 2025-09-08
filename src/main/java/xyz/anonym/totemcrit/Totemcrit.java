package xyz.anonym.totemcrit;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.slf4j.Logger;

import java.util.concurrent.ThreadLocalRandom;

@Mod(Totemcrit.MODID)
public class Totemcrit {
    public static final String MODID = "totemcrit";
    private static final Logger LOGGER = LogUtils.getLogger();

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("[TotemCrit] Client Setup");
        }
        @SubscribeEvent
        public static void onCrit(CriticalHitEvent event) {
            if (!(Minecraft.getInstance().level instanceof ClientLevel clientLevel) || !event.isVanillaCritical()) {
                return;
            }

            var player = event.getTarget();

            clientLevel.addParticle(
                    ParticleTypes.TOTEM_OF_UNDYING,
                    player.getX(),
                    player.getY() + player.getBbHeight() * 0.5,
                    player.getZ(),
                    0.0, 0.0, 0.0
            );

            // If you want more than one particle, do a loop:
            for (int i = 0; i < 32; i++) {
                double dx = (ThreadLocalRandom.current().nextDouble() - 0.5);
                double dy = (ThreadLocalRandom.current().nextDouble() - 0.5);
                double dz = (ThreadLocalRandom.current().nextDouble() - 0.5);

                clientLevel.addParticle(
                        ParticleTypes.TOTEM_OF_UNDYING,
                        player.getRandomX(1.0),
                        player.getRandomY(),
                        player.getRandomZ(1.0),
                        dx, dy, dz
                );
            }
        }
    }
}
