package net.jwn.jwnsminigamemod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.jwn.jwnsminigamemod.minigame.ModMinigames;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Relative;

import java.util.EnumSet;
import java.util.List;

import static net.jwn.jwnsminigamemod.minigame.ModMinigames.FLAT_MINIGAME_KEY;

public class MinigameCommand {
    public MinigameCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("minigame")
            .then(Commands.argument("game", StringArgumentType.word())
            .suggests((ctx, builder) -> {
                List<String> GAME_NAMES = List.of("game1", "game2", "game3");
                for (String name : GAME_NAMES) {
                    builder.suggest(name);
                }
                return builder.buildFuture();
            })
            .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        ServerLevel level = context.getSource().getServer().getLevel(FLAT_MINIGAME_KEY);
        System.out.println(level!=null);
        if (player!=null && level!=null) {
            String game = StringArgumentType.getString(context, "game");
            player.sendSystemMessage(Component.literal("Let's play! " + game));

            int[] roomIndex = ModMinigames.findEmptyRoomIndex();
            if (roomIndex == null) {
                player.sendSystemMessage(Component.literal("빈 방이 없습니다."));
                return 0;
            }

//            ModMinigames.setRoomUsed(roomIndex[0], roomIndex[1]);
            BlockPos spawn = ModMinigames.getRoomCenter(9, 5, 0);

            player.teleportTo(
                level,
                spawn.getX() + 0.5,
                spawn.getY(),
                spawn.getZ() + 0.5,
                EnumSet.of(Relative.X_ROT, Relative.Y_ROT),
                0.0f,
                0.0f,
                false
            );

            return 1;
        }

        return 0;
    }
}
