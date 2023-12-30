package com.globits.cms.rest;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.dto.UserCategoryPermissionsDto;
import com.globits.cms.service.UserCategoryPermissionsService;

@RestController
@RequestMapping("/api/userCategoryPermissions")
public class RestUserCategoryPermissionsController {
	
	@Autowired
	UserCategoryPermissionsService userCategoryPermissionsService;

	/*hàm dùng để get list mới khi tạo mới user*/
	@RequestMapping(value = "get/getNewList", method=RequestMethod.GET)
	public ResponseEntity<List<UserCategoryPermissionsDto>> getNewList() {
		Hashtable<UUID, UserCategoryPermissionsDto> dto = userCategoryPermissionsService.getNewListOrGetListByUserId(null);
		return new ResponseEntity<List<UserCategoryPermissionsDto>>(new ArrayList<>(dto.values()), HttpStatus.OK);
	}
	
}
