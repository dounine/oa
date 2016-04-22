package net.yasion.common.core.logger.aop.enumeration;

public enum LoggerType {
	LOGIN("LOGIN"), NORMAL("NORMAL");

	private LoggerType(String type) {
		this.setType(type);
	}

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.getType();
	}
}