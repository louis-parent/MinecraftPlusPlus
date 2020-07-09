package fr.minecraftpp.command;

import java.util.Collections;
import java.util.List;

import fr.minecraftpp.manager.SetManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandMppInfo implements ICommand
{

	@Override
	public int compareTo(ICommand other)
	{
		return this.getCommandName().compareTo(other.getCommandName());
	}

	@Override
	public String getCommandName()
	{
		return "mppinfo";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "";
	}

	@Override
	public List<String> getCommandAliases()
	{
		return Collections.<String>emptyList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		sender.addChatMessage(new TextComponentString(SetManager.getInfoString()));
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return sender.canCommandSenderUseCommand(4, this.getCommandName());
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return Collections.<String>emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

}
