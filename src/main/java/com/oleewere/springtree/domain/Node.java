package com.oleewere.springtree.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;

public class Node<T> {
	@Getter
	private final T data;
	@Getter
	private final Node<T> leftChild;
	@Getter
	private final Node<T> rightChild;

	private Node(final T data, final Node<T> leftChild, final Node<T> rightChild) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	protected int getDepth(Node<T> node) {
		if (node == null) {
			return 0;
		}
		return 1 + Math.max(getDepth(node.getLeftChild()),
				getDepth(node.getRightChild()));
	}

	public static <T> Node<T> getInstance(T data, Node<T> leftChild,
			Node<T> rightChild) {

		return new Node<T>(data, leftChild, rightChild);
	}

	public static <T> Node<T> buildBinarySearchTree(List<T> data,
			final Comparator<T> comp) {
		if (data.isEmpty()) {
			return null;
		}

		final List<T> leftList = new ArrayList<T>();
		final List<T> rightList = new ArrayList<T>();

		for (T a : data.subList(1, data.size())) {
			if (comp.compare(data.get(0), a) < 0) {
				rightList.add(a);
			} else {
				leftList.add(a);
			}

		}

		return Node.getInstance(data.get(0),
				buildBinarySearchTree(leftList, comp),
				buildBinarySearchTree(rightList, comp));
	}

	public boolean isInBinarySearchTree(Node<T> tree, T elementToSearch,
			Comparator<T> comp) {
		if (tree == null) {
			return false;
		} else if (comp.compare(elementToSearch, tree.getData()) == 0) {
			return true;
		} else if (comp.compare(elementToSearch, tree.getData()) < 0) {
			return isInBinarySearchTree(tree.getLeftChild(), elementToSearch,
					comp);
		} else {
			return isInBinarySearchTree(tree.getRightChild(), elementToSearch,
					comp);
		}
	}

	public static <T> Node<T> subTreeAt(Node<T> tree, List<Direction> directions) {
		if (tree == null) {
			return null;
		}

		if (directions.isEmpty())
			return tree;

		final List<Direction> remainingDirections = directions.subList(1,
				directions.size());

		if (directions.get(0).equals(Direction.L)) {
			return subTreeAt(tree.getLeftChild(), remainingDirections);
		} else {
			return subTreeAt(tree.getRightChild(), remainingDirections);
		}

	}

	public Node<T> copyWith(Direction direction, Node<T> newSubTree) {
		if (direction.equals(Direction.L)) {
			return Node.getInstance(this.getData(), newSubTree,
					this.getRightChild());
		} else {
			return Node.getInstance(this.getData(), this.getLeftChild(),
					newSubTree);
		}
	}


	public Node<T> copyWith(List<Direction> directions, Node<T> newSubTree) {
		if(directions.isEmpty()){
			return newSubTree;
		}
		else {
			final List<Direction> remainingDirections = directions.subList(1, directions.size());
			
			if(directions.get(0).equals(Direction.L)){
				return Node.getInstance(this.getData(), this.getLeftChild().copyWith(remainingDirections,newSubTree), this.getRightChild());
			}
			else {
				return Node.getInstance(this.getData(), this.getLeftChild(), this.getRightChild().copyWith(remainingDirections,newSubTree));
			}
		}

	}

	
	

}
