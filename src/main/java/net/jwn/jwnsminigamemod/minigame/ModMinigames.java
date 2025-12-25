package net.jwn.jwnsminigamemod.minigame;

import net.jwn.jwnsminigamemod.JWNsMinigameMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ModMinigames {
    public static final ResourceKey<Level> FLAT_MINIGAME_KEY =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(JWNsMinigameMod.MOD_ID, "flat_minigame"));

    public static final int SIZE = 512;

    private static final int GRID = 10;
    public static final List<List<Boolean>> ROOMS = new ArrayList<>(GRID);

    static {
        for (int i = 0; i < GRID; i++) {
            List<Boolean> row = new ArrayList<>(GRID);
            for (int j = 0; j < GRID; j++) {
                row.add(false);
            }
            ROOMS.add(row);
        }
    }

    /** ===============
       빈 방 index 반환 (반환값: int[]{row, col}, 없으면 null)
       =============== */
    public static int[] findEmptyRoomIndex() {
        for (int i = 0; i < ROOMS.size(); i++) {
            List<Boolean> row = ROOMS.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (!row.get(j)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /** =========================
       index로 방 상태 설정
       ========================= */
    public static void setRoomUsed(int row, int col) {
        ROOMS.get(row).set(col, true);
    }

    public static void setRoomEmpty(int row, int col) {
        ROOMS.get(row).set(col, false);
    }

    /** =========================
       index로 방 중심 좌표 반환
       ========================= */
    public static BlockPos getRoomCenter(int row, int col, int y) {
        int rows = ROOMS.size();
        int colsAtRow = ROOMS.get(row).size();

        int x0 = (row - rows / 2) * SIZE;
        int z0 = (col - colsAtRow / 2) * SIZE;

        return new BlockPos(
                x0 + SIZE / 2,
                y,
                z0 + SIZE / 2
        );
    }
}
