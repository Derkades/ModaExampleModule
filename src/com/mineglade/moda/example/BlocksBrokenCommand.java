package com.mineglade.moda.example;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mineglade.moda.example.storage.ExampleStorageHandler;
import com.mineglade.moda.lib.derkutils.bukkit.BukkitFuture;
import com.mineglade.moda.modules.LangFile;

public class BlocksBrokenCommand extends Command {

	private final LangFile lang;
	private final ExampleStorageHandler storage;

	protected BlocksBrokenCommand(final LangFile lang, final ExampleStorageHandler storage) {
		super("blocksbroken", "View numer of blocks broken", "/<command>", Arrays.asList("bb"));
		this.lang = lang;
		this.storage = storage;
	}

	@Override
	public boolean execute(final CommandSender sender, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			this.lang.send(sender, ExampleMessage.COMMAND_NOTPLAYER);
			return true;
		}

		final Player player = (Player) sender;

		final BukkitFuture<Integer> future = this.storage.getBrokenBlocks(player.getUniqueId());

		future.onComplete((i) -> {
			this.lang.send(player, ExampleMessage.COMMAND_BLOCKSBROKEN, "amount", i);
		});

		future.onException((e) -> {
			this.lang.send(player, ExampleMessage.COMMAND_ERROR);
			e.printStackTrace();
		});

		return true;
	}

}
