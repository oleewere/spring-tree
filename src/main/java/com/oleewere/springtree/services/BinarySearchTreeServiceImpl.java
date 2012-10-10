package com.oleewere.springtree.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.domain.NodeComparator;

import flexjson.JSONSerializer;

@Service
public class BinarySearchTreeServiceImpl implements BinarySearchTreeService {

	@Override
	public String getTreeFromList(List<Integer> numbers) {
		NodeComparator<Integer> comp = new NodeComparator<Integer>();
		return NodeToJson(Node.buildBinarySearchTree(numbers, comp));
	}
	private String NodeToJson(Node<Integer> node) {
		return new JSONSerializer().exclude("*.class").serialize(node);
	}
	
}
