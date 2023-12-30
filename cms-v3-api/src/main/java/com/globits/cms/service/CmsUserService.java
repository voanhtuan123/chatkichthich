package com.globits.cms.service;

import org.springframework.data.domain.Page;

import com.globits.cms.dto.CmsUserDto;
import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;

public interface CmsUserService extends GenericService<User, Long> {

	CmsUserDto getById(Long id);

	Page<UserDto> searchByPage(SearchDto userSearchDto);
}
