package net.yasion.common.service;

import net.yasion.common.dto.PersonalProfileDTO;
import net.yasion.common.model.TbPersonalProfile;

public interface IPersonalProfileService extends IBaseService<TbPersonalProfile, PersonalProfileDTO, String> {

	public TbPersonalProfile getCurrentUserPersonalProfile();

}