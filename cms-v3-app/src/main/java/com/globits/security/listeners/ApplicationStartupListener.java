package com.globits.security.listeners;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
// import org.springframework.core.env.Environment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.globits.core.dto.PersonDto;
import com.globits.core.utils.CommonUtils;
import com.globits.security.domain.Role;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;
import com.globits.security.service.RoleService;
import com.globits.security.service.UserService;
@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent>, InitializingBean {

    private static boolean eventFired = false;

    // @Autowired
    // private RoleService roleService;

    // @Autowired
    // private UserService userService;

     @Autowired
     private Environment env;

     @Autowired
     private RoleService roleService;

     @Autowired
     private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);
    
    private void createRoleIfNotExist(String roleName) {

        if (CommonUtils.isEmpty(roleName)) {
            return;
        }

        Role role = roleService.findByName(roleName);

        if (CommonUtils.isNotNull(role)) {
            return;
        }

        if (role == null) {
            role = new Role();
            role.setName(roleName);
        }

        try {
            roleService.save(role);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void createAdminUser() {

		UserDto userDto = userService.findByUsername(com.globits.core.Constants.USER_ADMIN_USERNAME);

		if (CommonUtils.isNotNull(userDto)) {
			return;
		}

		userDto = new UserDto();
		userDto.setUsername(com.globits.core.Constants.USER_ADMIN_USERNAME);
		//userDto.setPassword(SecurityUtils.getHashPassword("admin"));
		userDto.setPassword("admin");
		userDto.setEmail("admin@globits.net");
		userDto.setActive(true);
		userDto.setDisplayName("Admin User");

		Role role = roleService.findByName(com.globits.core.Constants.ROLE_ADMIN);
		
		userDto.getRoles().addAll(Arrays.asList(new RoleDto(role)));
		
		PersonDto person = new PersonDto();
		person.setGender("M");
		person.setFirstName("Admin");
		person.setLastName("User");
		person.setDisplayName("Admin User");
		
		userDto.setPerson(person);
		
		try {
			userService.save(userDto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void createRoles() throws XMLStreamException {

        List<String> roleNames = new ArrayList<>();

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = getClass().getClassLoader().getResourceAsStream("sys-roles.xml");
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in, "UTF-8");

        streamReader.nextTag();
        streamReader.nextTag();

        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
                    case "name": {
                        roleNames.add(streamReader.getElementText());
                        break;
                    }
                }
            }
            streamReader.next();
        }

        streamReader.close();

        for (String roleName : roleNames) {
            createRoleIfNotExist(roleName);
        }
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (eventFired) {
            return;
        }
        try {
			createRoles();
			createAdminUser();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

}
