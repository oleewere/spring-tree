package com.oleewere.springtree.services;

import java.util.List;

import com.oleewere.springtree.domain.Node;

public interface BinarySearchTreeService {	
	public Node<Integer> getTreeFromList(List<Integer> numbers);
}
