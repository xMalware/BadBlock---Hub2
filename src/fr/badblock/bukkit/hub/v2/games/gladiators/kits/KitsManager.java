package fr.badblock.bukkit.hub.v2.games.gladiators.kits;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KitsManager {

    public static List<ItemStack[]> loadKit(String name) {
        File file = new File(BadBlockHub.getInstance().getDataFolder(), "kits/" + name + ".yml");

        if (!file.exists()) {
            return null;
        }

        BufferedReader br;
        ArrayList<ItemStack[]> result = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();

            String st;
            while ((st = br.readLine()) != null){
                sb.append(st);
            }
            br.close();

            String[] contents = sb.toString().split("-");
            result.add(BukkitSerialization.fromBase64(contents[0]).getContents());
            result.add(BukkitSerialization.itemStackArrayFromBase64(contents[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void saveKit(PlayerInventory playerInventory, String name) {
        File file = new File(BadBlockHub.getInstance().getDataFolder() + "/kits", name + ".yml");

        file.getParentFile().mkdir();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.exists()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer.print("");
            writer.close();
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(BukkitSerialization.toBase64(playerInventory) + "-"+ BukkitSerialization.itemStackArrayToBase64(playerInventory.getArmorContents()));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
