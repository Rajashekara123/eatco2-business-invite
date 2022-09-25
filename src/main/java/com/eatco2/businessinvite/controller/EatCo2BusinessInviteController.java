/**
 * 
 */
package com.eatco2.businessinvite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatco2.businessinvite.dto.BusinessInviteDTO;
import com.eatco2.businessinvite.exception.BusinessInviteException;
import com.eatco2.businessinvite.response.BusineesInviteStatusResponse;
import com.eatco2.businessinvite.response.BusinessInviteResponse;
import com.eatco2.businessinvite.response.BusinessInviteResponseForReminder;
import com.eatco2.businessinvite.response.BusinessInviteResponseForUpdate;
import com.eatco2.businessinvite.service.ProcessBusinessInvite;
import com.eatco2.businessinvite.util.BusinessInviteUtil;

/**
 * @author Rajashekara
 *
 */
@RestController
@RequestMapping("/businessinvite")
public class EatCo2BusinessInviteController {

	Logger logger = LoggerFactory.getLogger(EatCo2BusinessInviteController.class);

	@Autowired
	private ProcessBusinessInvite processBusinessInvite;
	@Autowired
	private BusinessInviteUtil businessInviteUtil;

	/**
	 * @param businessInviteDTO
	 * @return
	 */
	@PostMapping(value = "/sendinvite")
	public BusinessInviteResponse sendBusinessInvite(@RequestBody BusinessInviteDTO businessInviteDTO) {
		logger.info("sendBusinessInvite called ..");
		BusinessInviteResponse businessInviteResponse = new BusinessInviteResponse();
		businessInviteUtil.getBusinessInviteResponse(businessInviteDTO, businessInviteResponse);
		try {
			processBusinessInvite.processSendInvite(businessInviteDTO, businessInviteResponse);

		} catch (BusinessInviteException e) {
			businessInviteResponse.setStatus(e.getMessage());
		}
		return businessInviteResponse;
	}

	/**
	 * @param businessInviteDTO
	 * @return
	 */
	@GetMapping(value = "/invitestatus")
	public BusineesInviteStatusResponse getBusinessInviteStatus(@RequestParam("userId") long userId,
			@RequestParam("inviterEmailAddress") String inviterEmailAddress, @RequestParam("status") String status) {
		logger.info("getBusinessInviteStatus called ..");
		BusinessInviteDTO businessInviteDTO = new BusinessInviteDTO();
		businessInviteDTO.setUserId(userId);
		businessInviteDTO.setInviterEmailAddress(inviterEmailAddress);
		logger.debug("getBusinessInviteStatus request {}", businessInviteDTO);
		return processBusinessInvite.getInviteStatuses(businessInviteDTO, status);

	}

	@PostMapping(value = "/sendinvitereminder")
	public BusinessInviteResponseForReminder sendBusinessInviteReminder(@RequestParam("userId") long userId,
			@RequestParam("inviterEmailAddress") String inviterEmailAddress,
			@RequestParam("inviteeEmailAddress") String inviteeEmailAddress,
			@RequestParam(value="inviteeNewEmailAddress",required=false) String inviteeNewEmailAddress) {
		logger.info("sendBusinessInviteReminder called ..");
		BusinessInviteDTO businessInviteDTO = new BusinessInviteDTO();
		businessInviteDTO.setUserId(userId);
		businessInviteDTO.setInviterEmailAddress(inviterEmailAddress);
		businessInviteDTO.setInviteeEmailAddress(inviteeEmailAddress);
		BusinessInviteResponseForReminder businessInviteResponseForReminder = new BusinessInviteResponseForReminder();
		businessInviteUtil.getBusinessInviteResponseForReminder(businessInviteDTO, businessInviteResponseForReminder);
		try {
			processBusinessInvite.sendBusinessInviteReminder(businessInviteDTO, businessInviteResponseForReminder,
					inviteeNewEmailAddress);
		} catch (BusinessInviteException e) {
			businessInviteResponseForReminder.setStatus(e.getMessage());
		}
		return businessInviteResponseForReminder;

	}

	@PostMapping(value = "/updateinvitestatus")
	public BusinessInviteResponseForUpdate updateInviteStatus(@RequestParam("userId") long userId,
			@RequestParam("inviteeEmailAddress") String inviteeEmailAddress) {
		logger.info("sendBusinessInviteReminder called ..");
		BusinessInviteDTO businessInviteDTO = new BusinessInviteDTO();
		businessInviteDTO.setUserId(userId);
		businessInviteDTO.setInviteeEmailAddress(inviteeEmailAddress);
		BusinessInviteResponseForUpdate businessInviteResponse = new BusinessInviteResponseForUpdate();
		try {
			processBusinessInvite.updateBusinessInvite(businessInviteDTO, businessInviteResponse);

		} catch (BusinessInviteException e) {
			businessInviteResponse.setStatus(e.getMessage());
		}
		return businessInviteResponse;

	}

}
