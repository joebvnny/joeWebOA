package com.joebase.datastructs;

public class BinaryTree implements BiTree {

    private BiTreeNode root;
    
    public BinaryTree() {
    }
    
    public BinaryTree(BiTreeNode root) {
        this.root = root;
    }
    
    public BinaryTree(String preOrder, String inOrder, int preIndex, int inIndex, int count) {
        if(count > 0) {
            char r = preOrder.charAt(preIndex);
            int i = 0;
            for(; i<count; i++) {
                if(r == inOrder.charAt(i + inIndex))
                    break;
            }
            root = new BiTreeNode(r);
            root.setLchild(new BinaryTree(preOrder, inOrder, preIndex+1, inIndex, i).root);
            root.setRchild(new BinaryTree(preOrder, inOrder, preIndex+i+1, inIndex+i+1, count-i-1).root);
        }
    }
    
    public BiTreeNode getRoot() {
        return root;
    }
    
    public void setRoot(BiTreeNode root) {
        this.root = root;
    }
    
    /**
     * 前根序遍历二叉树（递归法）
     */
    @Override
    public void preRootTraverse(BiTreeNode t) {
        if(t != null) {
            System.out.print(t.getData() + " ");
            preRootTraverse(t.getLchild());
            preRootTraverse(t.getRchild());
        }
    }
    
    /**
     * 前根序遍历二叉树（非递归）
     */
    @Override
    public void preRootTraverse() {
        BiTreeNode t = root;
        if(t != null) {
//            LinkedStack<BiTreeNode> stack = new LinkedStack<BiTreeNode>();
            java.util.Deque<BiTreeNode> stack = new java.util.LinkedList<BiTreeNode>();
            stack.push(t);
            while(!stack.isEmpty()) {
                t = stack.pop();
                System.out.print(t.getData() + " ");
                while(t != null) {
                    if(t.getLchild() != null) {
                        System.out.print(t.getLchild().getData() + " ");
                    }
                    if(t.getRchild() != null) {
                        stack.push(t.getRchild());
                    }
                    t = t.getLchild();
                }
            }
        }
    }
    
    /**
     * 中根序遍历二叉树（递归法）
     */
    @Override
    public void inRootTraverse(BiTreeNode t) {
        if(t != null) {
            inRootTraverse(t.getLchild());
            System.out.print(t.getData() + " ");
            inRootTraverse(t.getRchild());
            
        }
    }
    
    /**
     * 中根序遍历二叉树（非递归）
     */
    @Override
    public void inRootTraverse() {
        BiTreeNode t = root;
        if(t != null) {
            LinkedStack<BiTreeNode> stack = new LinkedStack<BiTreeNode>();
            stack.push(t);
            while(!stack.isEmpty()) {
                while(stack.peek() != null) {
                    stack.push(stack.peek().getLchild());
                }
                stack.pop(); //NULL出栈
                if(!stack.isEmpty()) {
                    t = stack.pop();
                    System.out.print(t.getData() + " ");
                    stack.push(t.getRchild());
                }
            }
        }
    }
    
    /**
     * 后根序遍历二叉树（递归法）
     */
    @Override
    public void postRootTraverse(BiTreeNode t) {
        if(t != null) {
            postRootTraverse(t.getLchild());
            postRootTraverse(t.getRchild());
            System.out.print(t.getData() + " ");
        }
    }
    
    /**
     * 后根序遍历二叉树（非递归）
     */
    @Override
    public void postRootTraverse() {
        BiTreeNode t = root;
        if (t != null) {
            LinkedStack<BiTreeNode> stack = new LinkedStack<BiTreeNode>();
            stack.push(t);   // 根结点进栈
            Boolean visited;  // 访问标记
            BiTreeNode p = null;  // p指向刚被访问的结点
            while(!stack.isEmpty()) {
                while(stack.peek() != null) {  // 将栈顶结点的所有左孩子结点入栈
                    stack.push(stack.peek().getLchild());
                }
                stack.pop(); // 空结点退栈
                while(!stack.isEmpty()) {
                    t = stack.peek();  // 查看栈顶元素
                    if(t.getRchild() == null || t.getRchild() == p) {
                        System.out.print(t.getData() + " ");  // 访问结点
                        stack.pop();  // 移除栈顶元素
                        p = t;  // p指向刚被访问的结点
                        visited = true;  // 设置访问标记
                    } else {
                        stack.push(t.getRchild());  // 右孩子结点入栈
                        visited = false;  // 设置未被访问标记
                    }
                    if(!visited) break;
                }
            }
        }
    }
    
    /**
     * 层次遍历二叉树（非递归）
     */
    @Override
    public void levelTraverse() {
        BiTreeNode t = root;
        if(t != null) {
//            LinkedQueue<BiTreeNode> queue = new LinkedQueue<BiTreeNode>();
            java.util.Queue<BiTreeNode> queue = new java.util.LinkedList<BiTreeNode>();
            queue.offer(t);
            while(!queue.isEmpty()) {
                t = queue.poll();
                System.out.print(t.getData() + " ");
                if(t.getLchild() != null) queue.offer(t.getLchild());
                if(t.getRchild() != null) queue.offer(t.getRchild());
            }
        }
    }
    
    /**
     * 二叉树查找（递归法）
     */
    @Override
    public BiTreeNode searchNode(BiTreeNode t, Object x) {
        if(t != null) {
            if(t.getData().equals(x)) return t;
            else {
                BiTreeNode lresult = searchNode(t.getLchild(), x);
                return(lresult != null ? lresult : searchNode(t.getRchild(), x));
            }
        }
        return null;
    }
    
    @Override
    public int countNode(BiTreeNode t) {
        int count = 0;
        if(t != null) {
            ++count;
            count += countNode(t.getLchild());
            count += countNode(t.getRchild());
        }
        return count;
    }
    
    @Override
    public int countLeafNode(BiTreeNode t) {
        int count = 0;
        if(t != null) {
            if(t.getLchild()==null && t.getRchild()==null) {
                ++count;
            } else {
                count += countLeafNode(t.getLchild());
                count += countLeafNode(t.getRchild());
            }
        }
        return count;
    }
    
    @Override
    public int countDepth(BiTreeNode t) {
        if(t != null) {
            int lDepth = countDepth(t.getLchild());
            int rDepth = countDepth(t.getRchild());
            return (lDepth > rDepth ? lDepth : rDepth) + 1;
        }
        return 0;
    }
    
}