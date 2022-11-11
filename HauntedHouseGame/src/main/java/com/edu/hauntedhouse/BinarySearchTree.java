package com.edu.hauntedhouse;

public class BinarySearchTree<K extends Comparable<K>, V> {
    private Node root;
    private int size;

    /**
     * Empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /*
     * Find Strategy:
     * - If the current root is null (i.e., tree is empty), return false
     * - If the target matches the current root nodes data, return true
     * - If target < current root node's data, search left subtree
     * - If target > current root node's data, search right subtree
     */

    // Externally, all we want to worry about is the thing we're looking for.
    public boolean find(K target) {
        return find(root, target);
    }

    // Internally, we have to keep track of the current node.
    private boolean find(Node currentNode, K target){
        if (currentNode == null) return false;
        int compareResult = target.compareTo(currentNode.key);
        if (compareResult == 0) return true;
        if (compareResult < 0)
            // Return the result of find in the left subtree.
            return find(currentNode.left, target);
        return find(currentNode.right, target);
    }

    /*
     * Insert strategy:
     * - If the current root is null, replace current root with a
     *      new node containing the item
     * - If the item equals the current root node's data, do nothing.
     * - If item < current root node's data, recursively insert into left subtree.
     * - If item > current root node's data, recursively insert into right subtree.
     */

    public boolean add(K key, V value){
        int oldSize = size;
        root = add(root, key, value);
        return oldSize != size;
    }

    private Node add(Node currentNode, K key, V value){
        if (currentNode == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(currentNode.key) == 0){
            return currentNode;
        }
        if (key.compareTo(currentNode.key) < 0){
            // Go left, and store the left node in the left.
            currentNode.left = add(currentNode.left, key, value);
            return currentNode;
        }
        //else (item.compareTo(currentNode.data) > 0) {
            currentNode.right = add(currentNode.right, key, value);
            return currentNode;
        //}
    }

    public boolean remove(K key){
        Node temp = remove(false, key, root);
        if(temp == null){
            return false;
        }else{
            return true;
        }
    }

    private Node remove(boolean keyFound, K key, Node current){
        if(current == null){return null;}
        if(current.left != null && current.left.key.compareTo(key) == 0 && current.left.left == null){
            Node temp = current.left;
            current.left = current.left.right;
            return temp;
        }else if(current.right != null && current.right.key.compareTo(key) == 0 && current.right.left == null){
            Node temp = current.right;
            current.right = current.right.right;
            return temp;
        }else if(current.key == key){
            if(current.left != null) {
                Node temp = remove(true, key, current.left);
                current.key = temp.key;
                current.value = temp.value;
                return current;
            }
        }
        if(keyFound){
            if(current.right != null){
                Node temp = remove(keyFound, key, current.right);
                if(temp != null && current.right == temp){current.right = null;}
                return temp;
            }else{
                return current;
            }
        }else if(key.compareTo(current.key) < 0){
            return remove(keyFound, key, current.left);
        }else{
            return remove(keyFound, key, current.right);
        }
    }

    public String toString() {
        if (root == null) return "null";
        return root.toString();
    }


    private class Node {
        // The data stored in the node
        K key;
        V value;
        // The root of the left subtree
        Node left;
        // The root of the right subtree
        Node right;

        public Node(K key, V value){
            this.value = value;
            this.key = key;
            left = right = null;
        }

        public String toString() {
            return "(" + left + " - " + value + " - " + right + ")";
            //return data.toString();
        }
    }
}
