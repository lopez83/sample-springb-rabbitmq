package com.lopez83.springb.rabbitmq.model;

/**
 * @author Oscar Lopez - <lopez83@gmail.com>
 *
 */
public class MyMessage {

	public String level;
	public String description;

	public MyMessage(String level, String description) {
		this.level = level;
		this.description = description;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MyMessage [level=" + level + ", description=" + description + "]";
	}
}