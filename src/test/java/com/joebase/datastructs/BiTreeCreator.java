package com.joebase.datastructs;

public class BiTreeCreator {

	public static BinaryTree createBiTree1() {          //           M
		BiTreeNode A = new BiTreeNode('A');              //        /     \
		BiTreeNode B = new BiTreeNode('B');              //       K       L
		BiTreeNode C = new BiTreeNode('C');              //     /   \    /  \
		BiTreeNode D = new BiTreeNode('D');              //    F     H  C    J
		BiTreeNode E = new BiTreeNode('E');              //     \   /       / \
		BiTreeNode F = new BiTreeNode('F', null, A);    //      A  G       D   I
		BiTreeNode G = new BiTreeNode('G', B, null);    //        /             \
		BiTreeNode H = new BiTreeNode('H', G, null);    //       B               E
		BiTreeNode I = new BiTreeNode('I', null, E);
		BiTreeNode J = new BiTreeNode('J', D, I);
		BiTreeNode K = new BiTreeNode('K', F, H);
		BiTreeNode L = new BiTreeNode('L', C, J);
		BiTreeNode root = new BiTreeNode('M', K, L);
		return new BinaryTree(root);
	}

	public static BiTree createBiTree2() {                //	           M
		BiTreeNode B = new BiTreeNode('B');              //         /    \
		BiTreeNode C = new BiTreeNode('C');              //        K      L
		BiTreeNode D = new BiTreeNode('D');              //       / \    / \
		BiTreeNode E = new BiTreeNode('E');              //      F   H  C   J
		BiTreeNode F = new BiTreeNode('F', null, null); //         /      / \
		BiTreeNode G = new BiTreeNode('G', B, null);    //         G      D   I
		BiTreeNode H = new BiTreeNode('H', G, null);    //        /            \
		BiTreeNode I = new BiTreeNode('I', null, E);    //       B              E
		BiTreeNode J = new BiTreeNode('J', D, I);
		BiTreeNode K = new BiTreeNode('K', F, H);
		BiTreeNode L = new BiTreeNode('L', C, J);
		BiTreeNode root = new BiTreeNode('M', K, L);
		return new BinaryTree(root);
	}
}