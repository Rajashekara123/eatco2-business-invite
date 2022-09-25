/**
 * 
 */
package com.eatco2.businessinvite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eatco2.businessinvite.domain.BusinessInviteEntity;

/**
 * @author Rajashekara
 *
 */
@Repository
public interface BusinessInviteRepository extends JpaRepository<BusinessInviteEntity, Long> {

	@Query("SELECT p FROM BusinessInviteEntity p WHERE "
			+ "p.userId = :userId and p.inviterEmailAddress = :inviterEmailAddress and p.inviteeEmailAddress = :inviteeEmailAddress")
	List<BusinessInviteEntity> checkInvitationSendStaus(@Param("userId") Long userId,
			@Param("inviterEmailAddress") String inviterEmailAddress,
			@Param("inviteeEmailAddress") String inviteeEmailAddress);

	@Query("SELECT p FROM BusinessInviteEntity p WHERE "
			+ "p.userId = :userId and p.inviterEmailAddress = :inviterEmailAddress and p.status = :status")
	List<BusinessInviteEntity> getInvitationStausesForUser(@Param("userId") Long userId,
			@Param("inviterEmailAddress") String inviterEmailAddress, @Param("status") String status);

	@Query("SELECT p FROM BusinessInviteEntity p WHERE "
			+ "p.userId = :userId and p.inviterEmailAddress = :inviterEmailAddress and p.inviteeEmailAddress = :inviteeEmailAddress and p.status = :status")
	List<BusinessInviteEntity> getPendingInvitation(@Param("userId") Long userId,
			@Param("inviterEmailAddress") String inviterEmailAddress,
			@Param("inviteeEmailAddress") String inviteeEmailAddress, @Param("status") String status);

	@Query("SELECT p FROM BusinessInviteEntity p WHERE "
			+ "p.userId = :userId and p.inviteeEmailAddress = :inviteeEmailAddress and p.status = :status")
	List<BusinessInviteEntity> getPendingInvitationForUpdate(@Param("userId") Long userId,
			@Param("inviteeEmailAddress") String inviteeEmailAddress, @Param("status") String status);

}
