package it.garr.greenmst.types;

import it.garr.greenmst.IGreenMSTService;
import it.garr.greenmst.TopologyCostsLoader;
import it.garr.greenmst.web.serializers.LinkWithCostJSONSerializer;
import net.floodlightcontroller.routing.Link;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Extends the Link class from net.floodlightcontroller.routing to implement Comparable and Comparator interfaces.
 * 
 * @author Luca Prete <luca.prete@garr.it>
 * @author Andrea Biancini <andrea.biancini@garr.it>
 * @author Fabio Farina <fabio.farina@garr.it>
 * 
 * @version 0.8
 * @see net.floodlightcontroller.routing.Link
 * @see java.util.Comparator
 * @see java.lang.Comparable
 * @see it.garr.greenmst.GreenMST
 *
 */

@JsonSerialize(using=LinkWithCostJSONSerializer.class)
public class LinkWithCost extends Link {
    
	public LinkWithCost(long srcId, int srcPort, long dstId, int dstPort) {
		super(srcId, srcPort, dstId, dstPort);
	}
	
	public LinkWithCost(long srcId, int srcPort, long dstId, int dstPort, int cost) {
		super(srcId, srcPort, dstId, dstPort);
		TopologyCosts costs = TopologyCostsLoader.getTopologyCosts();
		costs.getCost(srcId, dstId);
	}
	
	public LinkWithCost(IGreenMSTService greenMST, Link l) {
		super(l.getSrc(), l.getSrcPort(), l.getDst(), l.getDstPort());
	}
	
	public int getCost() {
		TopologyCosts costs = TopologyCostsLoader.getTopologyCosts();
		return costs.getCost(this.getSrc(), this.getDst());
	}

	@Override
	public String toString() {
		return "Link (" + this.getSrc() + ", " + this.getDst() + ") with cost: " + this.getCost();
	}
	
	public LinkWithCost getInverse() {
		return new LinkWithCost(this.getDst(), this.getDstPort(), this.getSrc(), this.getSrcPort());
	}
}