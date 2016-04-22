package net.yasion.common.service.impl;

import java.io.File;

import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IWebFileDAO;
import net.yasion.common.dto.WebFileDTO;
import net.yasion.common.model.TbWebFile;
import net.yasion.common.service.IWebFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("webFileService")
@Transactional
public class WebFileServiceImpl extends BaseServiceImpl<TbWebFile, WebFileDTO, String> implements IWebFileService {

	private IWebFileDAO webFileDAO;

	public IWebFileDAO getWebFileDAO() {
		return webFileDAO;
	}

	@Autowired
	public void setWebFileDAO(IWebFileDAO webFileDAO) {
		this.webFileDAO = webFileDAO;
	}

	@Override
	protected IBaseDAO<TbWebFile, String> getDefaultDAO() {
		return this.webFileDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public TbWebFile findByUUIDName(String uuidName) {
		return this.webFileDAO.getUniqueByProperty("fileName", uuidName);
	}

	@Override
	@Transactional(readOnly = true)
	public TbWebFile findByMd5(String md5) {
		return this.webFileDAO.getUniqueByProperty("md5", md5);
	}

	@Override
	public boolean removeByUUIDName(String uuidName) {
		Integer count = this.webFileDAO.deleteByProperty("fileName", uuidName);
		return (0 < count);
	}

	@Override
	public Integer removeById(String id) {
		TbWebFile webFile = this.findById(id);
		if (null != webFile) {
			this.webFileDAO.deleteByEntity(webFile);
			String filePath = webFile.getFilePath();
			filePath = (filePath.startsWith("/") ? filePath : "/" + filePath);
			String realPath = HttpInternalObjectManager.getServletContext().getRealPath(filePath);
			this.deleteFile(realPath);
			return 1;
		}
		return 0;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		try {
			File file = new File(sPath);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}