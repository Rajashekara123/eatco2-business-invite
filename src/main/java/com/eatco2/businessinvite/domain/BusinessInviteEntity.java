/**
 * 
 */
package com.eatco2.businessinvite.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Rajashekara
 *
 */
@Entity
@Table(schema = "business_invite", name= "EATCO2_BUSINESS_INVITE")
@Data
public class BusinessInviteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long inviteId;
    private long userId;
    private String invitedBusinessName;
	private String inviterBusinessName;
	private String inviteeEmailAddress;
	private String inviterEmailAddress;
	private String inviterBusinessCategoryName;
	private String inviteeBusinessCategoryName;
	private Date createdDate;
	private Date modifiedDate;
	private String status;	
	

}
