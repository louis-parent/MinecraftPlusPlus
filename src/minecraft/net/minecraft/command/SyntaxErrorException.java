package net.minecraft.command;

public class SyntaxErrorException extends CommandException
{
	public SyntaxErrorException()
	{
		this("commands.generic.snytax");
	}

	public SyntaxErrorException(String message, Object... replacements)
	{
		super(message, replacements);
	}

	@Override
	public synchronized Throwable fillInStackTrace()
	{
		return this;
	}
}
