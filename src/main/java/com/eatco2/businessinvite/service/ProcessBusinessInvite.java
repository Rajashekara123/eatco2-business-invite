/**
 * 
 */
package com.eatco2.businessinvite.service;

import com.eatco2.businessinvite.dto.BusinessInviteDTO;
import com.eatco2.businessinvite.exception.BusinessInviteException;
import com.eatco2.businessinvite.response.BusineesInviteStatusResponse;
import com.eatco2.businessinvite.response.BusinessInviteResponse;
import com.eatco2.businessinvite.response.BusinessInviteResponseForReminder;
import com.eatco2.businessinvite.response.BusinessInviteResponseForUpdate;

/**
 * @author Rajashekara
 *
 */
public interface ProcessBusinessInvite {

	void processSendInvite(BusinessInviteDTO businessInviteDTO, BusinessInviteResponse businessInviteResponse)
			throws BusinessInviteException;

	BusineesInviteStatusResponse getInviteStatuses(BusinessInviteDTO businessInviteDTO, String status);

	void sendBusinessInviteReminder(BusinessInviteDTO businessInviteDTO,
			BusinessInviteResponseForReminder businessInviteResponseForReminder, String inviteeNewEmailAddress)
			throws BusinessInviteException;

	void updateBusinessInvite(BusinessInviteDTO businessInviteDTO,
			BusinessInviteResponseForUpdate businessInviteResponseForUpdate) throws BusinessInviteException;

}
