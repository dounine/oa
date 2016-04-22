package net.yasion.common.service;

import java.util.List;
import java.util.Map.Entry;

import net.yasion.common.dto.PermissionDTO;
import net.yasion.common.model.TbPermission;

public interface IPermissionService extends IBaseService<TbPermission, PermissionDTO, String> {

	public List<TbPermission> findByCodes(String[] codes);

	public Entry<List<Object>, Integer> findUrls(Integer pageIndex, String key, Integer pageSize);

}