package net.yasion.common.dao.impl;

import net.yasion.common.dao.IUeditorFileDAO;
import net.yasion.common.model.TbUeditorFile;

import org.springframework.stereotype.Repository;

@Repository("ueditorFileDAO")
public class UeditorFileDAOImpl extends BaseDAOImpl<TbUeditorFile, String> implements IUeditorFileDAO {

	@Override
	protected Class<TbUeditorFile> getEntityClass() {
		return TbUeditorFile.class;
	}
}