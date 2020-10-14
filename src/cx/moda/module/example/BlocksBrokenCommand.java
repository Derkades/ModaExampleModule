package cx.moda.module.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import moda.plugin.moda.module.command.ModuleCommandExecutor;
import moda.plugin.moda.util.BukkitFuture;

public class BlocksBrokenCommand extends ModuleCommandExecutor<ExampleModule> {

	public BlocksBrokenCommand(final ExampleModule module) {
		super(module);
	}


	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			this.getModule().getLang().send(sender, ExampleMessage.COMMAND_NOTPLAYER);
			return true;
		}

		final Player player = (Player) sender;
		
		final BukkitFuture<Integer> future = this.getModule().getStorage().getBrokenBlocks(player.getUniqueId());

		future.onComplete((i) -> {
			this.getModule().getLang().send(player, ExampleMessage.COMMAND_BLOCKSBROKEN, "amount", i);
		});

		future.onException((e) -> {
			this.getModule().getLang().send(player, ExampleMessage.COMMAND_ERROR);
			e.printStackTrace();
		});

		return true;
	}

}
