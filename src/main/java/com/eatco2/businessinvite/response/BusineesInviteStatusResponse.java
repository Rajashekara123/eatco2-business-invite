/**
 * 
 */
package com.eatco2.businessinvite.response;

import java.util.List;

import lombok.Data;

/**
 * @author Rajashekara
 *
 */
@Data
public class BusineesInviteStatusResponse {
	private long userId;
	private String inviterBusinessName;
	private String inviterEmailAddress;
	private String inviterBusinessCategoryName;
	private List<InviteeStatus> inviteeStatusList;
}
