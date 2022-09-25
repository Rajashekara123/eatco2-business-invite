/**
 * 
 */
package com.eatco2.businessinvite.util;

import org.springframework.stereotype.Component;

import com.eatco2.businessinvite.dto.BusinessInviteDTO;
import com.eatco2.businessinvite.response.BusinessInviteResponse;
import com.eatco2.businessinvite.response.BusinessInviteResponseForReminder;

/**
 * @author Rajashekara
 *
 */
@Component
public class BusinessInviteUtil {

	/**
	 * @param businessInviteDTO
	 * @param businessInviteResponse
	 */
	public void getBusinessInviteResponse(BusinessInviteDTO businessInviteDTO,
			BusinessInviteResponse businessInviteResponse) {
		businessInviteResponse.setUserId(businessInviteDTO.getUserId());
		businessInviteResponse.setInviterBusinessName(
				businessInviteDTO.getInviterBusinessName() != null ? businessInviteDTO.getInviterBusinessName() : null);
		businessInviteResponse.setInviterEmailAddress(
				businessInviteDTO.getInviterEmailAddress() != null ? businessInviteDTO.getInviterEmailAddress() : null);
		businessInviteResponse.setInviterBusinessCategoryName(businessInviteDTO.getInviterBusinessCategoryName() != null
				? businessInviteDTO.getInviterBusinessCategoryName()
				: null);
		businessInviteResponse.setInvitedBusinessName(
				businessInviteDTO.getInvitedBusinessName() != null ? businessInviteDTO.getInvitedBusinessName() : null);
		businessInviteResponse.setInviteeBusinessCategoryName(businessInviteDTO.getInviteeBusinessCategoryName() != null
				? businessInviteDTO.getInviteeBusinessCategoryName()
				: null);
		businessInviteResponse.setInviteeEmailAddress(
				businessInviteDTO.getInviteeEmailAddress() != null ? businessInviteDTO.getInviteeEmailAddress() : null);
	}

	/**
	 * @param businessInviteDTO
	 * @param businessInviteResponse
	 */
	public void getBusinessInviteResponseForReminder(BusinessInviteDTO businessInviteDTO,
			BusinessInviteResponseForReminder businessInviteResponseForReminder) {
		businessInviteResponseForReminder.setUserId(businessInviteDTO.getUserId());
		businessInviteResponseForReminder.setInviterEmailAddress(
				businessInviteDTO.getInviterEmailAddress() != null ? businessInviteDTO.getInviterEmailAddress() : null);
		businessInviteResponseForReminder.setInviteeEmailAddress(
				businessInviteDTO.getInviteeEmailAddress() != null ? businessInviteDTO.getInviteeEmailAddress() : null);
	}

	public boolean validateStringInputForNullOrEmpty(String input) {
		if (input == null || input.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}
