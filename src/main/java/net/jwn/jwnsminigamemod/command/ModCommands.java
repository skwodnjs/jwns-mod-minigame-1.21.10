package net.jwn.jwnsminigamemod.command;

import net.jwn.jwnsminigamemod.JWNsMinigameMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = JWNsMinigameMod.MOD_ID)
public class ModCommands {
    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        new MinigameCommand(event.getDispatcher());
    }
}
