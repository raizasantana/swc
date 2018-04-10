package domain;

public abstract class ReturnMessages {
	
	public static final String INVALID_ID = "{\"message\": \"Invalid ID\"}";
	public static final String INVALID_NAME = "{\"message\": \"Invalid name\"}";
	public static final String NULL_ID = "{\"message\": \"ID must be not null\"}";
	public static final String NULL_NAME = "{\"message\": \"Name must be not null\"}";
	public static final String NULL_PARAM = "{\"message\": \"All parameters must be non-null\"}";
	public static final String NO_ELEMENTS = "{\"message\": \"The database is empty\"}";
	public static final String NOT_FOUND = "{\"message\": \"This planet does not exists\"}";
	public static final String ERROR_CREATE = "{\"message\": \"Error while creating planet\"}";
	public static final String ERROR_DELETE = "{\"message\": \"Error while deleting planet\"}";
	public static final String SUCCESS_DELETE = "{\"message\": \"Planet successfully deleted\"}";
}

