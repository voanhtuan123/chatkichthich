package com.globits.cms.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_cms_comment")
@XmlRootElement
public class Comment extends BaseObject {
	
}
