package net.yasion.common.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.core.uurp.UurpAdaptor;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IUnitDAO;
import net.yasion.common.dto.UnitDTO;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IUnitService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("unitService")
@Transactional
public class UnitServiceImpl extends BaseServiceImpl<TbUnit, UnitDTO, String> implements IUnitService {

	private IUnitDAO unitDAO;

	public IUnitDAO getUnitDAO() {
		return unitDAO;
	}

	@Autowired
	public void setUnitDAO(IUnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	@Override
	protected IBaseDAO<TbUnit, String> getDefaultDAO() {
		return this.unitDAO;
	}

	@Override
	public List<TbUnit> findByCodes(String[] codes) {
		Criterion criterion = null;
		for (int i = 0, len = codes.length; i < len; i++) {
			Criterion thisCriterion = Restrictions.eq("code", codes[i]);
			if (null != criterion) {
				criterion = Restrictions.or(criterion, thisCriterion);
			} else {
				criterion = thisCriterion;
			}
		}
		IResultSet<TbUnit> entityList = unitDAO.listByCriteria(new Criterion[] { criterion }, new Order[] { Order.desc("id") }, null);
		return entityList.getResultList();
	}

	@Override
	public IResultSet<TbUnit> findByDTO(UnitDTO dto, Integer pageNumber, Integer pageSize, List<Criterion> additionCriterionList) {
		additionCriterionList = (null != additionCriterionList) ? additionCriterionList : new ArrayList<Criterion>();
		if (StringUtils.isNotBlank(dto.getParentId())) {
			UurpAdaptor uurpAdaptor = SpringBeanManager.getUurpAdaptor();
			List<TbUnit> subUnit = uurpAdaptor.queryUnitTree(dto.getParentId());
			if (CollectionUtils.isNotEmpty(subUnit)) {
				if (1 == subUnit.size()) {
					additionCriterionList.add(Restrictions.eq("id", subUnit.get(0).getId()));
				} else if (2 == subUnit.size()) {
					additionCriterionList.add(Restrictions.or(Restrictions.eq("id", subUnit.get(0).getId()), Restrictions.eq("id", subUnit.get(1).getId())));
				} else {
					Criterion or = Restrictions.eq("id", subUnit.get(0).getId());
					for (int i = 1, len = subUnit.size(); i < len; i++) {
						Criterion or2 = Restrictions.eq("id", subUnit.get(i).getId());
						or = Restrictions.or(or, or2);
					}
					additionCriterionList.add(or);
				}
			} else {// 没有sub Unit直接按父单位过滤
				additionCriterionList.add(Restrictions.eq("parent.id", dto.getParentId()));
			}
			return super.findByDTO(dto, pageNumber, pageSize, additionCriterionList);
		} else {
			return super.findByDTO(dto, pageNumber, pageSize, additionCriterionList);
		}
	}

	@Override
	public IResultSet<TbUnit> lFindByDTOOnNotSubUnit(UnitDTO dto, Integer pageNumber, Integer pageSize) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(dto);
		if (null != logicalDeleteCriterion) {
			criterionList.add(logicalDeleteCriterion);
		}
		if (StringUtils.isNotBlank(dto.getId())) {
			TbUnit thisUnit = unitDAO.get(dto.getId());
			UurpAdaptor uurpAdaptor = SpringBeanManager.getUurpAdaptor();
			List<TbUnit> subUnit = uurpAdaptor.queryUnitTree(dto.getId());
			subUnit = (CollectionUtils.isNotEmpty(subUnit) ? subUnit : new ArrayList<TbUnit>());
			subUnit.add(thisUnit);
			for (int i = 0, len = subUnit.size(); i < len; i++) {
				TbUnit unit = subUnit.get(i);
				criterionList.add(Restrictions.not(Restrictions.eq("id", unit.getId())));
			}
			return super.findByDTO(dto, pageNumber, pageSize, criterionList);
		} else {
			return super.findByDTO(dto, pageNumber, pageSize, criterionList);
		}
	}

	@Override
	public IResultSet<TbUnit> lFindByDTOOnAllUnit(UnitDTO dto, Integer pageNumber, Integer pageSize) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(dto);
		if (null != logicalDeleteCriterion) {
			criterionList.add(logicalDeleteCriterion);
		}
		return super.findByDTO(dto, pageNumber, pageSize, criterionList);
	}

	@Override
	public IResultSet<TbUnit> lFindSubUnit(UnitDTO dto, Integer pageNumber, Integer pageSize) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(dto);
		if (null != logicalDeleteCriterion) {
			criterionList.add(logicalDeleteCriterion);
		}
		if (StringUtils.isNotBlank(dto.getParentId())) {
			criterionList.add(Restrictions.eq((StringUtils.isBlank(dto.getCriterionAlias()) ? "parent.id" : dto.getCriterionAlias() + ".parent.id"), dto.getParentId()));
		} else {
			criterionList.add(Restrictions.isNull((StringUtils.isBlank(dto.getCriterionAlias()) ? "parent.id" : dto.getCriterionAlias() + ".parent.id")));
		}
		return super.findByDTO(dto, pageNumber, pageSize, criterionList);
	}

	@Override
	public Integer removeById(String id) {
		TbUnit unit = this.findById(id);
		Set<TbUser> unitUsers = unit.getTbUsers();
		Iterator<TbUser> unitUserIt = unitUsers.iterator();
		while (unitUserIt.hasNext()) {
			TbUser unitUser = unitUserIt.next();
			unitUser.setTbUnit(null);
		}
		unitUsers.clear();
		// 因为TbUser中的级联使用了lazy="false"，意思是TbUser很可能已经加载了当前要删除的关系对象在内存，并且是持久态，当TbUser修改了
		// 也会将本次删除的关系重新保存回去，所以删除时候需要先删除TbUser中跟当前关系对应的对象。否则回报错：deleted object would be re-saved by cascade
		return super.removeById(id);
	}

	@Override
	public Integer removeByIds(String[] ids) {
		Integer count = 0;
		for (int i = 0, len = ids.length; i < len; i++) {
			count += (this.removeById(ids[i]));
		}
		return count;
	}

	@Override
	public TbUnit save(UnitDTO dto) {
		TbUnit entity = new TbUnit();
		try {
			dto.copyValuesTo(entity);
			if (StringUtils.isNotBlank(dto.getParentId())) {
				TbUnit parentUnit = this.unitDAO.get(dto.getParentId());
				entity.setParent(parentUnit);
			}
			this.setEntityCreateDefaultValue(entity, dto);
			return this.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TbUnit update(UnitDTO dto) {
		TbUnit entity = null;
		try {
			entity = this.unitDAO.get(dto.getId());
			dto.copyValuesTo(entity);
			if (StringUtils.isNotBlank(dto.getParentId()) && (null == entity.getParent() || !dto.getParentId().equals(entity.getParent().getId()))) {
				if (!entity.getId().equals(dto.getParentId())) {
					UurpAdaptor uurpAdaptor = SpringBeanManager.getUurpAdaptor();
					List<TbUnit> subUnitList = uurpAdaptor.queryUnitTree(entity.getId());
					TbUnit settingParentUnit = this.unitDAO.get(dto.getParentId());
					if (null == subUnitList || !subUnitList.contains(settingParentUnit)) {
						TbUnit parentUnit = this.unitDAO.get(dto.getParentId());
						entity.setParent(parentUnit);
					}
				}
			} else if (StringUtils.isBlank(dto.getParentId())) {// 作为顶级单位
				entity.setParent(null);
			}
			this.setEntityModifiedDefaultValue(entity, dto);
			return this.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}