package net.yasion.common.service;

import net.yasion.common.dto.WebFileDTO;
import net.yasion.common.model.TbWebFile;

public interface IWebFileService extends IBaseService<TbWebFile, WebFileDTO, String> {

	public TbWebFile findByUUIDName(String uuidName);

	public TbWebFile findByMd5(String md5);

	public boolean removeByUUIDName(String uuidName);

	public boolean deleteFile(String sPath);

}