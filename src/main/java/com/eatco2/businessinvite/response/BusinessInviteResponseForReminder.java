/**
 * 
 */
package com.eatco2.businessinvite.response;

import lombok.Data;

/**
 * @author Rajashekara
 *
 */
@Data
public class BusinessInviteResponseForReminder {
	private long userId;
	private String inviteeEmailAddress;
	private String inviterEmailAddress;
	private String status;

}
