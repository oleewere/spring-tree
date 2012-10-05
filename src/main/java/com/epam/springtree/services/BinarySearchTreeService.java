package com.epam.springtree.services;

import java.util.List;

import com.epam.springtree.domain.Node;

public interface BinarySearchTreeService {	
	public Node<Integer> getTreeFromList(List<Integer> numbers);
}
