package net.yasion.common.service.impl;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IUeditorFileDAO;
import net.yasion.common.dto.UeditorFileDTO;
import net.yasion.common.model.TbUeditorFile;
import net.yasion.common.model.TbWebFile;
import net.yasion.common.service.IUeditorFileService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ueditorFileService")
@Transactional
public class UeditorFileServiceImpl extends BaseServiceImpl<TbUeditorFile, UeditorFileDTO, String> implements IUeditorFileService {

	private IUeditorFileDAO ueditorFileDAO;

	public IUeditorFileDAO getUeditorFileDAO() {
		return ueditorFileDAO;
	}

	@Autowired
	public void setUeditorFileDAO(IUeditorFileDAO ueditorFileDAO) {
		this.ueditorFileDAO = ueditorFileDAO;
	}

	@Override
	protected IBaseDAO<TbUeditorFile, String> getDefaultDAO() {
		return this.ueditorFileDAO;
	}

	@Override
	public TbUeditorFile save(UeditorFileDTO dto) {
		TbUeditorFile entity = null;
		try {
			entity = new TbUeditorFile();
			dto.copyValuesTo(entity);
			this.setEntityCreateDefaultValue(entity, dto);
			String fileId = dto.getFileId();
			if (StringUtils.isNotBlank(fileId)) {
				TbWebFile webFile = new TbWebFile();
				webFile.setId(fileId);
				entity.setTbWebFile(webFile);
			}
			return this.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}