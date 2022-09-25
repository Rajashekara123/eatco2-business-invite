/**
 * 
 */
package com.eatco2.businessinvite.response;

import java.util.Date;

import lombok.Data;

/**
 * @author Rajashekara
 *
 */
@Data
public class BusinessInviteResponse {
	private long userId;
	private String invitedBusinessName;
	private String inviterBusinessName;
	private String inviteeEmailAddress;
	private String inviterEmailAddress;
	private String inviterBusinessCategoryName;
	private String inviteeBusinessCategoryName;
	private Date createdDate;
	private String status;

}
