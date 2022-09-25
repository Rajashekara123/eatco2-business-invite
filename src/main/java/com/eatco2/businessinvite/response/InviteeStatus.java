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
public class InviteeStatus {
	private String invitedBusinessName;
	private String inviteeEmailAddress;
	private String inviteeBusinessCategoryName;
	private Date createdDate;
	private Date modifiedDate;
	private String status;
}
