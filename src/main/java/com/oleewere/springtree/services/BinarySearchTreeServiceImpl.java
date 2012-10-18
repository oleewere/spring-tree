package com.oleewere.springtree.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.nodeservices.NodeComparator;
import com.oleewere.springtree.nodeservices.NodeHelper;


@Service
public class BinarySearchTreeServiceImpl implements BinarySearchTreeService {
    @Autowired
    private NodeComparator<Integer> comp;
	@Autowired
	private NodeHelper nodeHelper;
	@Override
	public Node<Integer> getTreeFromList(List<Integer> numbers) {
		return nodeHelper.buildBinarySearchTree(numbers, comp);
	}
	
}
