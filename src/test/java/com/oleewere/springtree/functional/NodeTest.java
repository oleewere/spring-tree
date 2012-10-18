package com.oleewere.springtree.functional;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import com.oleewere.springtree.domain.Direction;
import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.nodeservices.NodeComparator;
import com.oleewere.springtree.nodeservices.NodeHelper;

/**
 * Unit test for Generic new NodeHelper().
 * 
 * @param <underTest>
 */
public class NodeTest {

	private Node<Integer> underTest;
	private final NodeComparator<Integer> comp = new NodeComparator<Integer>();

	@Before
	public void setUp() {
		underTest = Node.getInstance(10,
				Node.getInstance(7, Node.getInstance(15, null, null), null),
				Node.getInstance(12, null, null));
	}

	@Test
	public void testTreeStructure() {

		assertEquals(underTest.getData(), (Integer) 10);
		assertEquals(underTest.getLeftChild().getData(), (Integer) 7);
		assertEquals(underTest.getRightChild().getData(), (Integer) 12);
		assertEquals(underTest.getLeftChild().getLeftChild().getData(),
				(Integer) 15);

	}

	@Test
	public void testDepth() {
		underTest = Node.getInstance(10,
				Node.getInstance(7, Node.getInstance(15, null, null), null),
				Node.getInstance(12, null, null));
		assertEquals(underTest.getDepth(underTest), 3);
	}

	@Test
	public void testBuild() {
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(5);
		datas.add(4);
		datas.add(4);
		datas.add(6);
		Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		assertEquals(root.getLeftChild().getLeftChild().getData(), (Integer) 4);
	}

	@Test
	public void testBuildTreeWithOneData() {
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(5);
		Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		assertEquals(root.getDepth(root), 1);
		assertNull(root.getLeftChild());
	}

	@Test
	public void testSearchWithExistingData() {
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(5);
		datas.add(6);
		datas.add(7);
		datas.add(8);
		datas.add(3);
		Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		assertEquals(root.isInBinarySearchTree(root, 3, comp), true);

	}

	@Test
	public void testBuildNullTree() {
		List<Integer> datas = new ArrayList<Integer>();
		Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		assertNull(root);
	}

	@Test
	public void testSearchWithNonExistingData() {
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(6);
		datas.add(3);
		datas.add(5);
		datas.add(6);
		datas.add(7);
		datas.add(8);
		Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		assertEquals(root.isInBinarySearchTree(root, 9, comp), false);
	}

	@Test
	public void testDirections() {
		List<Direction> dirs = new ArrayList<Direction>();
		dirs.add(Direction.L);
		dirs.add(Direction.R);
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(5);
		datas.add(3);
		datas.add(4);
		Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		Node<Integer> subTree = new NodeHelper().subTreeAt(root, dirs);
		assertEquals(subTree.getData(), (Integer) 4);

	}

	@Test
	public void testDirectionsWithNullTree() {
		List<Direction> dirs = new ArrayList<Direction>();
		dirs.add(Direction.L);
		dirs.add(Direction.R);
		assertNull(new NodeHelper().subTreeAt(null, dirs));
	}

	@Test
	public void testWrongDirectionRight() {
		List<Direction> dirs = new ArrayList<Direction>();
		dirs.add(Direction.R);
		dirs.add(Direction.R);
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(5);
		datas.add(3);
		datas.add(4);
		Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		Node<Integer> subTree = new NodeHelper().subTreeAt(root, dirs);
		assertNull(subTree);
	}

	@Test
	public void testWrongDirectionLeft() {
		List<Direction> dirs = new ArrayList<Direction>();
		dirs.add(Direction.L);
		dirs.add(Direction.L);
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(5);
		datas.add(3);
		datas.add(4);
		Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		Node<Integer> subTree = new NodeHelper().subTreeAt(root, dirs);
		assertNull(subTree);
	}
	
	@Test
	public void testCopyWithLeftDirection(){
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(3);
		datas.add(2);
		datas.add(1);
		List<Integer> datas2 = new ArrayList<Integer>();
        datas2.add(1);
		datas2.add(1);
		final Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		final Node<Integer> newSubTree = new NodeHelper().buildBinarySearchTree(datas2, comp);
		
		final Node<Integer> copy = root.copyWith(Direction.L, newSubTree);
		
		assertEquals(copy.getData(),(Integer)3);
		assertEquals(copy.getLeftChild().getData(),(Integer)1);
		assertEquals(root.getLeftChild().getData(),(Integer)2);
	}
	
	@Test
	public void testCopyWithRightDirection(){
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(3);
		datas.add(4);
		datas.add(5);
		List<Integer> datas2 = new ArrayList<Integer>();
        datas2.add(6);
		datas2.add(7);
		final Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		final Node<Integer> newSubTree = new NodeHelper().buildBinarySearchTree(datas2, comp);
		
		final Node<Integer> copy = root.copyWith(Direction.R, newSubTree);
		
		assertEquals(copy.getData(),(Integer)3);
		assertEquals(copy.getRightChild().getData(),(Integer)6);
		assertEquals(root.getRightChild().getData(),(Integer)4);
	}

	@Test
	public void testCopyWithDirections(){
		List<Direction> dirs = new ArrayList<Direction>();
		dirs.add(Direction.L);
		dirs.add(Direction.R);
		
		List<Integer> datas = new ArrayList<Integer>();
		datas.add(6);
		datas.add(3);
		datas.add(5);
		datas.add(6);
		datas.add(7);
		datas.add(8);
		
		List<Integer> datas2 = new ArrayList<Integer>();
		datas2.add(3);
		datas2.add(4);
		
		final Node<Integer> root = new NodeHelper().buildBinarySearchTree(datas, comp);
		final Node<Integer> newSubTree = new NodeHelper().buildBinarySearchTree(datas2, comp);
		
		final Node<Integer> copy = root.copyWith(dirs, newSubTree);
		assertEquals(copy.getData(),(Integer)6);
		assertEquals(root.getLeftChild().getRightChild().getData(),(Integer)5);
		assertEquals(copy.getLeftChild().getRightChild().getData(),(Integer)3);
		
		}
	}

