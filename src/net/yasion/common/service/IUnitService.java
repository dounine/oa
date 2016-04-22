package net.yasion.common.service;

import java.util.List;

import net.yasion.common.dto.UnitDTO;
import net.yasion.common.model.TbUnit;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

public interface IUnitService extends IBaseService<TbUnit, UnitDTO, String> {

	public List<TbUnit> findByCodes(String[] codes);

	public IResultSet<TbUnit> lFindByDTOOnAllUnit(UnitDTO dto, Integer pageNumber, Integer pageSize);

	public IResultSet<TbUnit> lFindSubUnit(UnitDTO dto, Integer pageNumber, Integer pageSize);

	public IResultSet<TbUnit> lFindByDTOOnNotSubUnit(UnitDTO dto, Integer pageNumber, Integer pageSize);

}