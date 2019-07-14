package com.mineglade.moda.example;

import com.mineglade.moda.modules.IMessage;

public enum ExampleMessage implements IMessage {

	COMMAND_NOTPLAYER("command.note-player", "You must execute this command as a player."),
	COMMAND_BLOCKSBROKEN("command.blocks-broken", "You have broken {amount} blocks."),
	COMMAND_ERROR("command.error", "An error occured when trying to retrieve the number of broken blocks."),

	;

	private String path;
	private String defaultMessage;

	ExampleMessage(final String path, final String defaultMessage) {
		this.path = path;
		this.defaultMessage = defaultMessage;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public String getDefault() {
		return this.defaultMessage;
	}

}
