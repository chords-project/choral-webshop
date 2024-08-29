package webshop.orchestrated.frontend;

public class FrontendState {
	private String userID;

	public FrontendState( String userID ) {
		this.userID = userID;
	}

	public String userID() {
		return this.userID;
	}

}
