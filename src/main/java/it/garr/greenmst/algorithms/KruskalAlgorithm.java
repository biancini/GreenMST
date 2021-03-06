package it.garr.greenmst.algorithms;

import it.garr.greenmst.types.LinkWithCost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KruskalAlgorithm implements IMinimumSpanningTreeAlgorithm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KruskalAlgorithm.class);

	@Override
	public List<LinkWithCost> perform(List<LinkWithCost> topoEdges) throws AlgorithmException {
		return perform(topoEdges, false);
	}
	
	@Override
	// KRUSKAL ALGORITHM -- COLUMBIA UNIV. IMPL.
    public List<LinkWithCost> perform(List<LinkWithCost> topoEdges, boolean reverse) throws AlgorithmException {
		LOGGER.debug("Starting to perform Kruskal algorithm...");
		sortLinks(topoEdges, reverse);
		LOGGER.trace("Kruskal performed on the following topoEdges: " + printEdges(topoEdges));
		
		Map<Long, HashSet<Long>> nodes = new HashMap<Long, HashSet<Long>>();
        generateNodeHashmap(topoEdges, nodes);
        
        LOGGER.trace("Kruskal generated the following nodes structure: " + printNodes(nodes));
    	
    	List<LinkWithCost> mstEdges = new ArrayList<LinkWithCost>();
    	List<LinkWithCost> edgesDone = new ArrayList<LinkWithCost>();
    	
    	LOGGER.trace("Entering Kruskal cycle...");
    	for (LinkWithCost curEdge: topoEdges) {
    		LOGGER.trace("curEdge = {}", new Object[] { curEdge });
    		
	        if (edgesDone.contains(curEdge)) {
	        	LOGGER.trace("Edge already computed by Kruskal. Not computing again!");
	        } else {
	        	edgesDone.add(curEdge); // This way the same edge will not be processed two times (if present two times in topoEdges)
	        	if (nodes.get(curEdge.getSrc()).equals(nodes.get(curEdge.getDst()))) {
	        		LOGGER.trace("Edge has source set equal to destination set. Not considering for MST!");
	        	} else {
	        		Set<Long> src = null, dst = null;
	        		Long dstHashSetIndex = 0L;
	        		
	        		LOGGER.trace("Comparing size of source and destination of curEdge: (src = {}, dst = {}).", new Object[] {nodes.get(curEdge.getSrc()).size(), nodes.get(curEdge.getDst()).size()});
	        		if (nodes.get(curEdge.getSrc()).size() > nodes.get(curEdge.getDst()).size()) {
	        			// have to transfer all nodes including curEdge.to
	        			src = nodes.get(curEdge.getDst());
	        			dstHashSetIndex = curEdge.getSrc();
	        		} else {
	        			// have to transfer all nodes including curEdge.from
	        			src = nodes.get(curEdge.getSrc());
	        			dstHashSetIndex = curEdge.getDst();
	        		}
	        		dst = nodes.get(dstHashSetIndex);
	        		LOGGER.trace("Set src = {}, dst = {}.", new Object[] {printHash(src), printHash(dst)});
	        		
	        		Object[] srcArray = src.toArray();
	        		int transferSize = srcArray.length;
	        		
	        		LOGGER.trace("Moving each node from set: src into set: dst.");
	        		LOGGER.trace("Updating appropriate index in array: nodes.");
	        		for (int j = 0; j < transferSize; j++) {
	        			if (src.remove(srcArray[j])) {
	        				dst.add((Long) srcArray[j]);
	        				nodes.put((Long) srcArray[j], nodes.get(dstHashSetIndex));
	        			} else {
	        				LOGGER.error("Error while removing element {} from array {}.", new Object[] {srcArray[j], src});
	        				throw new AlgorithmException("Kruskal - Error performing Kruskal algorithm (set union).");
	        			}
	        		}
	        		LOGGER.trace("Kruskal updated the nodes structure: " + printNodes(nodes));
	        		
	        		LOGGER.trace("Kruskal add the edge {} to mstEdges.", new Object[] {curEdge});
	        		mstEdges.add(curEdge);
	        	}
	        }
    	}
    	LOGGER.trace("End of Kruskal cycle.");
    	LOGGER.debug("Computed MST by Kruskal: "  + printEdges(mstEdges));
    	LOGGER.debug("End of Kruskal algorithm.");
    	
    	return mstEdges;
    }

	private void generateNodeHashmap(List<LinkWithCost> topoEdges,
			Map<Long, HashSet<Long>> nodes) {
		for (LinkWithCost lt: topoEdges) {
            if (!nodes.containsKey(lt.getSrc())) {
            	// Create set of connect components [singleton] for this node
                nodes.put(lt.getSrc(), new HashSet<Long>());
                nodes.get(lt.getSrc()).add(lt.getSrc());
            }
            
            if (!nodes.containsKey(lt.getDst())) {
            	// Create set of connect components [singleton] for this node
                nodes.put(lt.getDst(), new HashSet<Long>());
                nodes.get(lt.getDst()).add(lt.getDst());
            }
        }
	}

	private void sortLinks(List<LinkWithCost> topoEdges, boolean reverse) {
		if (reverse) {
			Collections.sort(topoEdges, new Comparator<LinkWithCost>() {
				public int compare(LinkWithCost link1, LinkWithCost link2) {
					return new Integer(link2.getCost()).compareTo(link1.getCost());
				}
			});
		} else {
			Collections.sort(topoEdges, new Comparator<LinkWithCost>() {
				public int compare(LinkWithCost link1, LinkWithCost link2) {
					return new Integer(link1.getCost()).compareTo(link2.getCost());
				}
			});
		}
	}
	
	private static String printEdges(Iterable<LinkWithCost> edges) {
    	String s  = "\n";
    	for (LinkWithCost e: edges) {
    		s += e.toString() + "\n";
    	}
    	return s;
    }
	
	private static String printNodes(Map<Long, ? extends Set<Long>> nodes) {
		String s  = "\n";
    	for (Map.Entry<Long, ? extends Set<Long>> entry: nodes.entrySet()) {
    		s += "Node (" + entry.getKey() + "): " + printHash(entry.getValue()) + "\n";
    	}
    	return s;
	}
	
	private static String printHash(Set<Long> value) {
		String s  = "(";
		for (Long set : value) {
			s += set + ", ";
		}
		s += ")";
    	return s;
	}

}
