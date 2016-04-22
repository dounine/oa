package net.yasion.demo.dto;

import net.yasion.common.dto.TransformBaseDTO;
import net.yasion.common.support.common.dao.interfaces.ITransformMixDTO;

public class TestDTO extends TransformBaseDTO implements ITransformMixDTO {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getPrefix(Object value, String alias, Integer index) {
		return "";
	}
}