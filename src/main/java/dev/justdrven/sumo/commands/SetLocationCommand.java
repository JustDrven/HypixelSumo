package dev.justdrven.sumo.commands;

import com.google.gson.JsonObject;

import dev.justdrven.sumo.SumoPlugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class SetLocationCommand implements CommandExecutor {

    public final String CANT_USE = "§cThis command can use only admins or players";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("sumo.commands.locations")) {
                sender.sendMessage(CANT_USE);
                return false;
            }

            final Player p = ((Player)sender);
            if (args.length == 0) {
                p.sendMessage("§eUsage: /setloc <locationId>");
                return true;
            }

            if (args.length == 1) {
                final String locId = args[0];

                CompletableFuture.runAsync(() -> {

                    setLocation(locId, p.getLocation());
                    SumoPlugin.getMapConfig().save();

                    p.sendMessage("§aLocation §e" + locId + "§a was successfully saved!");
                });

                return true;
            }

            sender.sendMessage("§cThis command is invalid");
        } else {
            sender.sendMessage(CANT_USE);
        }

        return true;
    }

    private void setLocation(final String locationId, Location l) {
        JsonObject data = new JsonObject();

        data.addProperty("x", l.getX());
        data.addProperty("y", l.getY());
        data.addProperty("z", l.getZ());
        data.addProperty("yaw", l.getYaw());
        data.addProperty("pitch", l.getPitch());

        SumoPlugin.getMapConfig().getSettings().get("locations").getAsJsonObject().add(locationId, data);
    }
}
