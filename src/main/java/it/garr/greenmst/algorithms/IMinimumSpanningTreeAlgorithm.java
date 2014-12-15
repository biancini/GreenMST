package it.garr.greenmst.algorithms;

import it.garr.greenmst.types.LinkWithCost;

import java.util.List;

public interface IMinimumSpanningTreeAlgorithm {

	public List<LinkWithCost> perform(List<LinkWithCost> topoEdges) throws AlgorithmException;
	public List<LinkWithCost> perform(List<LinkWithCost> topoEdges, boolean reverse) throws AlgorithmException;

}
