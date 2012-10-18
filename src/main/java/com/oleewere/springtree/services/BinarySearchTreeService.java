package com.oleewere.springtree.services;

import java.util.List;

import com.oleewere.springtree.domain.Node;
/**
 * 
 * @author Oliver_Mihaly_Szabo
 *
 */
public interface BinarySearchTreeService {
	/**
	 * 
	 * @param numbers
	 * @return
	 */
	public Node<Integer> getTreeFromList(List<Integer> numbers);
}
