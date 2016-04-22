package net.yasion.common.service.impl;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.ILogDAO;
import net.yasion.common.dto.LogDTO;
import net.yasion.common.model.TbLog;
import net.yasion.common.service.ILogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("logService")
// 事务的传播性的控制,日志的因为是要一个独立事务,不能收其他事务报错的影响
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LogServiceImpl extends BaseServiceImpl<TbLog, LogDTO, String> implements ILogService {

	private ILogDAO logDAO;

	public ILogDAO getLogDAO() {
		return logDAO;
	}

	@Autowired
	public void setLogDAO(ILogDAO logDAO) {
		this.logDAO = logDAO;
	}

	@Override
	protected IBaseDAO<TbLog, String> getDefaultDAO() {
		return this.logDAO;
	}

	@Override
	public TbLog save(LogDTO dto) {
		return super.save(dto);
	}

	@Override
	public TbLog save(TbLog entity) {
		return super.save(entity);
	}

	@Override
	public TbLog update(TbLog entity) {
		return super.update(entity);
	}

	@Override
	public TbLog update(LogDTO dto) {
		return super.update(dto);
	}
}