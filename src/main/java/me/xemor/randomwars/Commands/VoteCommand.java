package me.xemor.randomwars.Commands;

import me.xemor.randomwars.Events.Event;
import me.xemor.randomwars.Events.EventHandler;
import me.xemor.randomwars.RandomWars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VoteCommand implements TabExecutor, CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            RandomWars randomWars = RandomWars.getRandomWars();
            if (!randomWars.getAlivePlayers().contains(player.getUniqueId())) {
                if (args.length != 1) {
                    player.sendMessage(ChatColor.RED + "You have not entered which event you want to vote for!");
                }
                String voteStr = args[0];
                EventHandler eventHandler = randomWars.getEventHandler();
                Event eventToVoteFor = eventHandler.getEvent(voteStr);
                eventHandler.removeVote(player.getUniqueId());
                eventHandler.addVote(player.getUniqueId(), eventToVoteFor);
                player.sendMessage(ChatColor.GREEN + "You have successfully voted for " + eventToVoteFor.getName());
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> events = new ArrayList<>();
        for (Event event : RandomWars.getRandomWars().getEventHandler().getAllEvents()) {
            if (event.getName().startsWith(args[0])) {
                events.add(event.getName().replace(" ", "_"));
            }
        }
        return events;
    }
}
