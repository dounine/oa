package net.yasion.common.dao.impl;

import net.yasion.common.dao.IWebFileDAO;
import net.yasion.common.model.TbWebFile;

import org.springframework.stereotype.Repository;

@Repository("webFileDAO")
public class WebFileDAOImpl extends BaseDAOImpl<TbWebFile, String> implements IWebFileDAO {

	@Override
	protected Class<TbWebFile> getEntityClass() {
		return TbWebFile.class;
	}
}