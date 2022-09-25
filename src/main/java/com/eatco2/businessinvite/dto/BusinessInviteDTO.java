/**
 * 
 */
package com.eatco2.businessinvite.dto;

import lombok.Data;

/**
 * @author Rajashekara 
 *
 */
@Data
public class BusinessInviteDTO {

	private long userId;
	private String invitedBusinessName;
	private String inviterBusinessName;
	private String inviteeEmailAddress;
	private String inviterEmailAddress;
	private String inviterBusinessCategoryName;
	private String inviteeBusinessCategoryName;
	
}
