package it.garr.greenmst;

import it.garr.greenmst.types.TopologyCosts;

public final class TopologyCostsLoader {
	private static final TopologyCosts INSTANCE = new TopologyCosts();
	
	private TopologyCostsLoader() {
		// Do nothing
	}
	
	public static TopologyCosts getTopologyCosts() {
		return TopologyCostsLoader.INSTANCE;
	}
	
}