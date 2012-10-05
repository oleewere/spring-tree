package com.epam.springtree.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.springtree.domain.Node;
import com.epam.springtree.domain.NodeComparator;

@Service
public class BinarySearchTreeServiceImpl implements BinarySearchTreeService {

	@Override
	public Node<Integer> getTreeFromList(List<Integer> numbers) {
		NodeComparator<Integer> comp = new NodeComparator<Integer>();
		return Node.buildBinarySearchTree(numbers, comp);
	}

}
