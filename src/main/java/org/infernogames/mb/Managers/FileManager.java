package org.infernogames.mb.Managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.infernogames.mb.ItemHandler;
import org.infernogames.mb.Reward;
import org.infernogames.mb.Reward.RewardType;
import org.infernogames.mb.Arena.Arena;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class FileManager {
   
   public static File dataFolder;
   
   private File file;
   private FileConfiguration config;
   
   private String name;
   
   public static FileManager getFromArena(Arena arena){
      return getFromArenaName(arena.getName());
   }
   
   public static FileManager getFromArenaName(String name){
      return new FileManager("Arenas" + File.separator + name);
   }
   
   public FileManager(String name) {
      file = new File(dataFolder + File.separator + name + ".yml");
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      config = YamlConfiguration.loadConfiguration(file);
      this.name = name;
   }
   
   public void addDefault(String path, Object thing) {
      if (!getConfig().isSet(path)) {
         getConfig().set(path, thing);
      }
   }
   
   public Reward getReward(String path) {
      Reward r = null;
      String result = getConfig().getString(path);
      if (result.startsWith("$")) {
         r = new Reward(RewardType.Cash, Integer.parseInt(result.replaceFirst("$", "")));
      } else {
         r = new Reward(RewardType.Item, ItemHandler.fromString(result));
      }
      return r;
   }
   
   public void setReward(String path, Reward reward) {
      if (reward.getType() == RewardType.Cash) {
         getConfig().set(path, "$" + reward.getCash());
      } else {
         getConfig().set(path, ItemHandler.toString(reward.getReward()));
      }
   }
   
   public FileConfiguration getConfig() {
      return config;
   }
   
   public void saveConfig() {
      try {
         config.save(file);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   public void reloadConfig() {
      config = YamlConfiguration.loadConfiguration(file);
   }
   
   public String getName(){
      return name;
   }
   
}
