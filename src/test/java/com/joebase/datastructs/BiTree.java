package com.joebase.datastructs;

public interface BiTree {

    public abstract void preRootTraverse(BiTreeNode t);
    public abstract void preRootTraverse();
    public abstract void inRootTraverse(BiTreeNode t);
    public abstract void inRootTraverse();
    public abstract void postRootTraverse(BiTreeNode t);
    public abstract void postRootTraverse();
    public abstract void levelTraverse();
    public abstract BiTreeNode searchNode(BiTreeNode t, Object x);
    public abstract int countNode(BiTreeNode t);
    public abstract int countLeafNode(BiTreeNode t);
    public abstract int countDepth(BiTreeNode t);

}