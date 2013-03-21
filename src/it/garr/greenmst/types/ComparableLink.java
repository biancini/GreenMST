package it.garr.greenmst.types;

import it.garr.greenmst.web.serializers.ComparableLinkJSONSerializer;

import java.util.Comparator;

import net.floodlightcontroller.routing.Link;

import org.codehaus.jackson.map.annotate.JsonSerialize;

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

@JsonSerialize(using=ComparableLinkJSONSerializer.class)
public class ComparableLink extends Link implements Comparator<Link>, Comparable<Link>{
    
	private int cost = 1;
	
	public ComparableLink(long srcId, int srcPort, long dstId, int dstPort) {
		super(srcId, srcPort, dstId, dstPort);
	}
	
	public ComparableLink(Link l) {
		super(l.getSrc(), l.getSrcPort(), l.getDst(), l.getDstPort());
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
    public int compareTo(Link other) {
    	return compare(this, other);
    }
	
	public ComparableLink getInverse() {
		return new ComparableLink(this.getDst(), this.getDstPort(), this.getSrc(), this.getSrcPort());
	}
	
	@Override
	public int compare(Link l1, Link l2) {
		ComparableLink link1 = (l1 instanceof ComparableLink) ? (ComparableLink) l1 : new ComparableLink(l1);
		ComparableLink link2 = (l1 instanceof ComparableLink) ? (ComparableLink) l2 : new ComparableLink(l1);
		
		if (link1.getSrc() == link2.getSrc() && link1.getSrcPort() == link2.getSrcPort() &&
				link1.getDst() == link2.getDst() && link1.getDstPort() == link2.getDstPort())  {
			
			if (link1.cost == link2.cost) {
				if (link1.cost != -1) return 0;
				return (link1.getSrc() > link2.getSrc() || link1.getDst() > link2.getDst()) ? 1 : -1;
			}
			
			return (link1.cost > link2.cost) ? 1 : -1;
		} else {
			return (link1.getSrc() > link2.getSrc() || link1.getDst() > link2.getDst()) ? 1 : -1;
		}
	}
}