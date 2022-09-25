/**
 * 
 */
package com.eatco2.businessinvite.service;

import com.eatco2.businessinvite.dto.BusinessInviteDTO;
import com.eatco2.businessinvite.exception.BusinessInviteException;

/**
 * @author Rajashekara
 *
 */
public interface EmailService {

	String processAndSendEmail(BusinessInviteDTO businessInviteDTO, boolean reminderEmail)
			throws BusinessInviteException;
}
