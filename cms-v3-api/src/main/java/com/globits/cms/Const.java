package com.globits.cms;

public class Const {
	public static String PathFileImage = null; 
	public enum ObjectMapType 
	{
		CATEGORY("CATEGORY"), 
		ARTICLE("ARTICLE");
	 
	    private String value;
	 
	    ObjectMapType(String value) {
	        this.value = value;
	    }
	 
	    public String getValue() {
	        return value;
	    }
	}

	public static enum ArticleStatus{
		AwaitingRemoved(-2),	//Chờ gỡ
		Removed(-1),	//Đã gỡ
		Draft(0),//Bản nháp
		AwaitingApproval(1),//Chờ duyệt
		Approved(2),	//Đã duyệt
		WaitingPost(3),	//Chờ đăng
		AlreadyPosted(4), //Đã đăng
		Publish(5);		//Xuất bản
		
		private Integer value;
		private ArticleStatus(Integer value) {
		    this.value = value;
		}
		public Integer getValue() {
			return value;
		}
	}
	public static enum CategoryType{
		
		ListArticle(1),	//Danh sách bài báo
		Link(2);	//Link
		
		private Integer value;
		private CategoryType(Integer value) {
		    this.value = value;
		}
		public Integer getValue() {
			return value;
		}
	}

	public enum SlugTypeEnum {
		LINK_URL("1", "LINK_URL"),
		CATEGORY("2", "CATEGORY"),
		ARTICLE("3", "ARTICLE");
		private String key;
		private String value;
		SlugTypeEnum(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}

	public enum SlugHomePageEnum {
		NEWS("tin-tuc"),
		NOTIFICATIONS("thong-bao"),
		PARTITIONS("phan-hieu-khoa"),
		TRAIN("dao-tao"),
		FOREIGN_AFFAIR("doi-ngoai"),
		STUDY("nghien-cuu"),
		EVENT("su-kien"),
		STUDENT("sinh-vien");

		private String value;
		SlugHomePageEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
