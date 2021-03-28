package cx.moda.module.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cx.moda.moda.module.command.ModuleCommandExecutor;

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

		this.getModule().getScheduler().async(() -> {
			int broken;
			try {
				broken = this.getModule().getStorage().getBrokenBlocks(player.getUniqueId());
			} catch (final Exception e) {
				this.getModule().getScheduler().run(() -> {
					this.getModule().getLang().send(player, ExampleMessage.COMMAND_ERROR);
				});
				e.printStackTrace();
				return;
			}

			this.getModule().getScheduler().run(() -> {
				this.getModule().getLang().send(player, ExampleMessage.COMMAND_BLOCKSBROKEN, "amount", broken);
			});
		});

		return true;
	}

}
