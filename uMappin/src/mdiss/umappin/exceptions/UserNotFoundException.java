package mdiss.umappin.exceptions;

public class UserNotFoundException extends Exception {

	/**
	 * Exception used when user not found with user/password
	 */
	private static final long serialVersionUID = 7477233881979619759L;
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
