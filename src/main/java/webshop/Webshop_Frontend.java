package webshop;

import choral.lang.Unit;
import webshop.frontend.FrontendState;

class Webshop_Frontend {
	private FrontendState frontendState;

	public Webshop_Frontend( FrontendState frontendState, Unit cartState ) {
		this( frontendState );
	}
	
	public Webshop_Frontend( FrontendState frontendState ) {
		this.frontendState = frontendState;
	}

	public void run() {
		System.out.println( "Frontend service started" );
	}

}
