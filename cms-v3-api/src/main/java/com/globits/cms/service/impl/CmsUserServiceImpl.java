package com.globits.cms.service.impl;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.cms.dto.CmsUserDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.dto.UserCategoryPermissionsDto;
import com.globits.cms.service.CmsUserService;
import com.globits.cms.service.UserCategoryPermissionsService;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.UserRepository;

@Service
public class CmsUserServiceImpl extends GenericServiceImpl<User, Long> implements CmsUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	UserCategoryPermissionsService userCategoryPermissionsService;

	@Override
	public CmsUserDto getById(Long id) {
		if (id != null) {
			User entity = userRepository.getOne(id);
			if (entity != null && entity.getId() != null) {

				Hashtable<UUID, UserCategoryPermissionsDto> hashtableUserCategoryPermissions = new Hashtable<>();
				CmsUserDto user = new CmsUserDto(entity);
				
				UserDto userBasic = new UserDto();
				userBasic.setId(entity.getId());
				
				hashtableUserCategoryPermissions = userCategoryPermissionsService.getNewListOrGetListByUserId(entity.getId());
				
				user.setUserCategoryPermissions(new HashSet<>(hashtableUserCategoryPermissions.values()));
				return user;
			}
		}
		return null;
	}

	@Override
	public Page<UserDto> searchByPage(SearchDto userSearchDto) {
		if(userSearchDto == null)
			return null;

		int pageIndex = userSearchDto.getPageIndex();
		int pageSize = userSearchDto.getPageSize();

		if(pageIndex > 0)
			pageIndex--;
		else
			pageIndex = 0;

		String whereClause = " WHERE 1=1";
		String sql = "SELECT new com.globits.security.dto.UserDto(entity) from User entity";

		if(userSearchDto.getKeyword() != null && StringUtils.hasText(userSearchDto.getKeyword()))
			whereClause += " AND entity.username LIKE :keyword OR entity.email LIKE :keyword OR entity.person.displayName LIKE :keyword";

		sql += whereClause;
		Query query = manager.createQuery(sql, UserDto.class);

		if(userSearchDto.getKeyword() != null && StringUtils.hasText(userSearchDto.getKeyword()))
			query.setParameter("keyword", "%" + userSearchDto.getKeyword() + "%");

		int totalElement = query.getResultList().size();

		int startPosition = pageIndex * pageSize;
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		List<UserDto> entities = query.getResultList();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<UserDto> result = new PageImpl<>(entities, pageable, totalElement);
		return result;
	}
}
