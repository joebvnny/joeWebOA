package com.joebase.datastructs;

import org.junit.Test;

public class _TestDS {

    @Test
    public void testList() {
        List<Integer> myList = new ArrayList<Integer>();
//        List<Integer> myList = new LinkedList<Integer>();
//        List<Integer> myList = new DuLinkedList<Integer>();
        myList.insert(0, 2);
        myList.insert(1, 4);
        myList.insert(2, 6);
        myList.insert(3, 8);
        myList.insert(4, 10);
        myList.insert(5, 12);
        myList.insert(6, 14);
        myList.insert(7, 16);
        myList.insert(8, 18);
        myList.insert(9, 20);
        System.out.println(myList.size());
        myList.display();
        myList.insert(10, 99);
        System.out.println(myList.size());
        myList.display();
        myList.remove(9);
        System.out.println(myList.size());
        myList.display();
        myList.update(9, 1);
        myList.display();
    }
    
    @Test
    public void testStack() {
        Stack<Integer> stack = new LinkedStack<Integer>();
        stack.push(2);
        stack.push(4);
        stack.push(6);
        stack.push(8);
        stack.display();
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        stack.display();
        stack.pop();
        stack.pop();
        stack.display();
    }
        
    @Test
    public void testQueue() {
        Queue<Integer> queue = new LinkedQueue<Integer>();
        queue.offer(2);
        queue.offer(4);
        queue.offer(6);
        queue.offer(8);
        queue.offer(10);
        queue.display();
        queue.poll();
        queue.display();
        queue.poll();
        queue.display();
        queue.poll();
        queue.display();
        queue.poll();
        queue.display();
        queue.poll();
        queue.display();
//        System.out.println(queue.getHead());
//        System.out.println(queue.getTail());
        queue.offer(99);
        queue.display();
        queue.offer(90);
        queue.display();
//        System.out.println(queue.getHead());
//        System.out.println(queue.getTail());
        
        Queue<PriorityData> pq = new PriorityQueue<PriorityData>();
        PriorityData pd1 = new PriorityData("Joe", 1);
        PriorityData pd2 = new PriorityData("Dina", 8);
        PriorityData pd3 = new PriorityData("Bunny", 2);
        PriorityData pd4 = new PriorityData("Oxford", 5);
        PriorityData pd5 = new PriorityData("Taxes", 3);
        PriorityData pd6 = new PriorityData("Zero", 0);
        pq.offer(pd1);
        pq.offer(pd2);
        pq.offer(pd3);
        pq.offer(pd4);
        pq.offer(pd5);
        pq.offer(pd6);
        pq.display();
        pq.poll();
        pq.display();
        pq.poll();
        pq.poll();
        pq.display();
        pq.offer(pd6);
        pq.display();
    }
    
    @Test
    public void testString() {
        String mainStr = new ArrayString("ababcabdabcabca");
        String subStr = new ArrayString("abcabc");
        System.out.println(mainStr.indexOf(subStr, 0));
    }
    
    @Test
    public void testBinaryTree() {
        BinaryTree myTree = BiTreeCreator.createBiTree1();
        myTree.preRootTraverse(myTree.getRoot());
        System.out.println();
        myTree.preRootTraverse();
        System.out.println();
        myTree.inRootTraverse(myTree.getRoot());
        System.out.println();
        myTree.inRootTraverse();
        System.out.println();
        myTree.postRootTraverse(myTree.getRoot());
        System.out.println();
        myTree.postRootTraverse();
        System.out.println();
        myTree.levelTraverse();
        System.out.println();
        System.out.println("total tree nodes : " + myTree.countNode(myTree.getRoot()));
        System.out.println("total leaf nodes : " + myTree.countLeafNode(myTree.getRoot()));
        System.out.println("tree depth : " + myTree.countDepth(myTree.getRoot()));
        BiTreeNode found = myTree.searchNode(myTree.getRoot(), 'G');
        System.out.println(found.getData());
    }
    
    
}