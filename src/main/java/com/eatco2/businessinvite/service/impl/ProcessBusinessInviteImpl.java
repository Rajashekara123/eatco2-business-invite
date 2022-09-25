/**
 * 
 */
package com.eatco2.businessinvite.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eatco2.businessinvite.domain.BusinessInviteEntity;
import com.eatco2.businessinvite.dto.BusinessInviteDTO;
import com.eatco2.businessinvite.exception.BusinessInviteException;
import com.eatco2.businessinvite.repository.BusinessInviteRepository;
import com.eatco2.businessinvite.response.BusineesInviteStatusResponse;
import com.eatco2.businessinvite.response.BusinessInviteResponse;
import com.eatco2.businessinvite.response.BusinessInviteResponseForReminder;
import com.eatco2.businessinvite.response.BusinessInviteResponseForUpdate;
import com.eatco2.businessinvite.response.InviteeStatus;
import com.eatco2.businessinvite.service.EmailService;
import com.eatco2.businessinvite.service.ProcessBusinessInvite;

/**
 * @author Rajashekara
 *
 */
@Service
public class ProcessBusinessInviteImpl implements ProcessBusinessInvite {

	Logger logger = LoggerFactory.getLogger(ProcessBusinessInviteImpl.class);

	@Autowired
	private EmailService emailService;
	@Autowired
	private BusinessInviteRepository businessInviteRepository;
	@Autowired
	private Environment env;

	/**
	 *
	 */
	@Override
	@Transactional(rollbackFor = BusinessInviteException.class)
	public void processSendInvite(BusinessInviteDTO businessInviteDTO, BusinessInviteResponse businessInviteResponse)
			throws BusinessInviteException {
		logger.info("processSendInvite service is started");
		String process_status = null;
		try {
			List<BusinessInviteEntity> inviteIdList = businessInviteRepository.checkInvitationSendStaus(
					businessInviteDTO.getUserId(), businessInviteDTO.getInviterEmailAddress(),
					businessInviteDTO.getInviteeEmailAddress());
			if (inviteIdList != null && !inviteIdList.isEmpty()) {
				BusinessInviteEntity businessInviteEntity = inviteIdList.get(0);
				if (businessInviteEntity.getStatus().equals(env.getProperty("INVITATION_SENT_DB_STATUS").trim())) {
					process_status = env.getProperty("ALREADY_INVITED").trim();
				} else {
					process_status = env.getProperty("ALREADY_SIGNEDUP").trim();
				}
				logger.info("User {} is {}", businessInviteEntity.getInviteeEmailAddress(), process_status);
				businessInviteResponse.setCreatedDate(businessInviteEntity.getCreatedDate());
			} else {
				Date date = new Date();
				logger.info("date is {}", date);
				BusinessInviteEntity businessInviteEntity = new BusinessInviteEntity();
				businessInviteEntity.setUserId(businessInviteDTO.getUserId());
				businessInviteEntity.setInviterBusinessName(businessInviteDTO.getInviterBusinessName());
				businessInviteEntity.setInviterEmailAddress(businessInviteDTO.getInviterEmailAddress());
				businessInviteEntity.setInviterBusinessCategoryName(businessInviteDTO.getInviterBusinessCategoryName());
				businessInviteEntity.setInvitedBusinessName(businessInviteDTO.getInvitedBusinessName());
				businessInviteEntity.setInviteeBusinessCategoryName(businessInviteDTO.getInviteeBusinessCategoryName());
				businessInviteEntity.setInviteeEmailAddress(businessInviteDTO.getInviteeEmailAddress());
				businessInviteEntity.setCreatedDate(date);
				businessInviteEntity.setStatus(env.getProperty("INVITATION_SENT_DB_STATUS").trim());
				businessInviteEntity = businessInviteRepository.save(businessInviteEntity);
				process_status = emailService.processAndSendEmail(businessInviteDTO, false);
				logger.info("Record saved successfully in to DB {}", businessInviteEntity);
				businessInviteResponse.setCreatedDate(date);
			}
		} catch (BusinessInviteException bie) {
			throw bie;
		} catch (Exception e) {
			logger.error("Exception occured while processing send Invite ", e.getMessage());
			logger.error("ERROR ", e);
			throw new BusinessInviteException(env.getProperty("ERROR_MESSAGE_DB").trim());
		}
		businessInviteResponse.setStatus(process_status);
	}

	/**
	 *
	 */
	@Override
	public BusineesInviteStatusResponse getInviteStatuses(BusinessInviteDTO businessInviteDTO, String status) {
		logger.info("getInviteStatuses service is started");
		BusineesInviteStatusResponse busineesInviteStatusResponse = new BusineesInviteStatusResponse();
		busineesInviteStatusResponse.setUserId(businessInviteDTO.getUserId());		
		busineesInviteStatusResponse.setInviterEmailAddress(businessInviteDTO.getInviterEmailAddress());
		List<InviteeStatus> inviteeStatusList = new ArrayList<>();
		InviteeStatus inviteeStatus = null;
		try {
			List<BusinessInviteEntity> inviteStatusList = businessInviteRepository.getInvitationStausesForUser(
					businessInviteDTO.getUserId(), businessInviteDTO.getInviterEmailAddress(), status);
			if (inviteStatusList != null && !inviteStatusList.isEmpty()) {
				busineesInviteStatusResponse.setInviterBusinessName(inviteStatusList.get(0).getInviterBusinessName());
				busineesInviteStatusResponse.setInviterBusinessCategoryName(inviteStatusList.get(0).getInviterBusinessCategoryName());
				for (BusinessInviteEntity businessInviteEntity : inviteStatusList) {
					inviteeStatus = new InviteeStatus();
					inviteeStatus.setInvitedBusinessName(businessInviteEntity.getInvitedBusinessName());
					inviteeStatus.setInviteeEmailAddress(businessInviteEntity.getInviteeEmailAddress());
					inviteeStatus.setInviteeBusinessCategoryName(businessInviteEntity.getInviteeBusinessCategoryName());
					inviteeStatus.setCreatedDate(businessInviteEntity.getCreatedDate());
					if (!businessInviteEntity.getStatus().equals(env.getProperty("INVITATION_SENT_DB_STATUS").trim())) {
						inviteeStatus.setModifiedDate(businessInviteEntity.getModifiedDate());
					}
					inviteeStatus.setStatus(businessInviteEntity.getStatus());
					inviteeStatusList.add(inviteeStatus);
				}
			} else {
				inviteeStatus = new InviteeStatus();
				if (status.equals(env.getProperty("INVITATION_SENT_DB_STATUS").trim())) {
					inviteeStatus.setStatus(env.getProperty("INVITATION_NO_REOCRD_FOUND_FOR_PENDING"));
				} else {
					inviteeStatus.setStatus(env.getProperty("INVITATION_NO_REOCRD_FOUND_FOR_SIGNEDUP"));
				}
				inviteeStatusList.add(inviteeStatus);
			}

		} catch (Exception e) {
			logger.error("Exception occured while getInviteStatuses ", e.getMessage());
			logger.error("ERROR ", e);
			inviteeStatus = new InviteeStatus();
			inviteeStatus.setStatus(env.getProperty("ERROR_MESSAGE_DB").trim());
			inviteeStatusList.add(inviteeStatus);
		}
		busineesInviteStatusResponse.setInviteeStatusList(inviteeStatusList);
		return busineesInviteStatusResponse;
	}

	@Override
	@Transactional(rollbackFor = BusinessInviteException.class)
	public void sendBusinessInviteReminder(BusinessInviteDTO businessInviteDTO,
			BusinessInviteResponseForReminder businessInviteResponseForReminder, String inviteeNewEmailAddress)
			throws BusinessInviteException {
		logger.info("sendBusinessInviteReminder service is started");
		try {
			List<BusinessInviteEntity> inviteIdList = businessInviteRepository.getPendingInvitation(
					businessInviteDTO.getUserId(), businessInviteDTO.getInviterEmailAddress(),
					businessInviteDTO.getInviteeEmailAddress(), env.getProperty("INVITATION_SENT_DB_STATUS").trim());
			if (inviteIdList != null && !inviteIdList.isEmpty()) {
				if (inviteeNewEmailAddress != null && !inviteeNewEmailAddress.isEmpty()) {
					BusinessInviteEntity businessInviteEntity = inviteIdList.get(0);
					businessInviteEntity.setInviteeEmailAddress(inviteeNewEmailAddress);
					businessInviteEntity = businessInviteRepository.save(businessInviteEntity);
					logger.info("New email address {} updated successfully",
							businessInviteEntity.getInviteeEmailAddress());
					businessInviteResponseForReminder
							.setInviteeEmailAddress(businessInviteEntity.getInviteeEmailAddress());
				}

				businessInviteResponseForReminder.setStatus(emailService.processAndSendEmail(businessInviteDTO, true));
				logger.info("Remainder Email Processed successfully");
			} else {
				businessInviteResponseForReminder.setStatus(env.getProperty("REMINDER_EMAIL_NO_REOCRD"));
			}

		} catch (BusinessInviteException bie) {
			throw bie;
		} catch (Exception e) {
			logger.error("Exception occured while sending the Business Invite Reminder ", e.getMessage());
			logger.error("ERROR ", e);
			throw new BusinessInviteException(env.getProperty("ERROR_MESSAGE_DB").trim());
		}
	}

	@Override
	@Transactional
	public void updateBusinessInvite(BusinessInviteDTO businessInviteDTO,
			BusinessInviteResponseForUpdate businessInviteResponseForUpdate) throws BusinessInviteException {
		logger.info("updateBusinessInvite service is started");
		try {
			businessInviteResponseForUpdate.setUserId(businessInviteDTO.getUserId());
			businessInviteResponseForUpdate.setInviteeEmailAddress(businessInviteDTO.getInviteeEmailAddress());
			List<BusinessInviteEntity> inviteIdList = businessInviteRepository.getPendingInvitationForUpdate(
					businessInviteDTO.getUserId(), businessInviteDTO.getInviteeEmailAddress(), env.getProperty("INVITATION_SENT_DB_STATUS").trim());
			if (inviteIdList != null && !inviteIdList.isEmpty()) {
				Date date = new Date();
				BusinessInviteEntity businessInviteEntity = inviteIdList.get(0);
				businessInviteEntity.setStatus(env.getProperty("INVITATION_SIGNEDUP_DB_STATUS"));
				businessInviteEntity.setModifiedDate(date);
				businessInviteEntity = businessInviteRepository.save(businessInviteEntity);
				logger.info("Record {} updated successfully", businessInviteEntity);
				businessInviteResponseForUpdate.setInvitedBusinessName(businessInviteEntity.getInvitedBusinessName());
				businessInviteResponseForUpdate.setInviterBusinessName(businessInviteEntity.getInviterBusinessName());
				businessInviteResponseForUpdate
						.setInviteeBusinessCategoryName(businessInviteEntity.getInviteeBusinessCategoryName());
				businessInviteResponseForUpdate
						.setInviterBusinessCategoryName(businessInviteEntity.getInviterBusinessCategoryName());
				businessInviteResponseForUpdate.setCreatedDate(businessInviteEntity.getCreatedDate());
				businessInviteResponseForUpdate.setModifiedDate(businessInviteEntity.getModifiedDate());
				businessInviteResponseForUpdate.setStatus(env.getProperty("INVITATION_DB_UPDATE").trim());
			} else {
				businessInviteResponseForUpdate.setStatus(env.getProperty("INVITATION_DB_UPDATE_NO_RECORD").trim());
			}
		} catch (Exception e) {
			logger.error("Exception occured while updating the Business Invite ", e.getMessage());
			logger.error("ERROR ", e);
			throw new BusinessInviteException(env.getProperty("ERROR_MESSAGE_DB").trim());
		}

	}

}
