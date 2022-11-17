package com.edu.hauntedhouse;

import java.util.Iterator;

public class BinarySearchTree<K extends Comparable<K>, V> implements Iterable<V>{
    private Node root;
    private int size;

    /**
     * Empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * Wrapper method for the recursive find method that returns a boolean value based upon if the key is found.
     * @param target The key that is desired to be checked.
     * @return true if the key exists in the BST, false if the key does not exist in the BST.
     */
    public boolean find(K target) {
        return find(root, target);
    }

    /**
     * Recursive method for finding a matching key, 4 cases:
     * 1. The current node is empty, returns false because the key doesn't exist
     * 2. The current node contains the matching key, returns true
     * 3. The target key is less than the current nodes key, returns the result of searching the left subtree
     * 4. The target key is greater than the current nodes key, returns the result of searching the right subtree.
     * @param currentNode The node currently being acted upon.
     * @param target Searches for a match to this key.
     * @return true if the target exists in the BST, false if the target does not exist in the BST.
     */
    private boolean find(Node currentNode, K target){
        if (currentNode == null) return false;
        int compareResult = target.compareTo(currentNode.key);
        if (compareResult == 0) return true;
        if (compareResult < 0)
            // Return the result of find in the left subtree.
            return find(currentNode.left, target);
        return find(currentNode.right, target);
    }

    /**
     * Wrapper class for the recursive getValue method that only requires the key.
     * @param key The associated key for the desired value.
     * @return The value if found.
     */
    public V getValue(K key){
        return getValue(root, key);
    }

    /**
     * Recursive method for finding the value associated to the key and returning it.
     * @param currentNode The node currently being acted upon.
     * @param target The key being searched for.
     * @return The value if found, null otherwise.
     */
    private V getValue(Node currentNode, K target){
        if (currentNode == null) return null;
        int compareResult = target.compareTo(currentNode.key);
        if (compareResult == 0) return currentNode.value;
        if (compareResult < 0)
            // Return the result of find in the left subtree.
            return getValue(currentNode.left, target);
        return getValue(currentNode.right, target);
    }

    /**
     * Add allows the insertion of a key value pair in the BST.
     * @param key The associated key for the value desired to be stored.
     * @param value The value desired to be added to the BST.
     * @return true if the insertion was successful or false if the insertion failed.
     */
    public boolean add(K key, V value){
        int oldSize = size;
        root = add(root, key, value);
        return oldSize != size;
    }

    /**
     * Recursive add method that handles 4 cases:
     * 1. If the root is null it will create a new node and return it.
     * 2. If the item is equal to an item already in the BST it will do nothing.
     * 3. If the item is less than an item in the BST it will try to add to the left subtree.
     * 4. If the item is greater than an item in the BST it will try to add to the right subtree.
     * @param currentNode Typically set as the root to start the adding processes, represents the node currently being
     *                    acted upon.
     * @param key The associated key for the value desired to be added.
     * @param value The value to be added to the BST.
     * @return The current node before insertion unless it is inserting at the root.
     */
    private Node add(Node currentNode, K key, V value){
        if (currentNode == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(currentNode.key) == 0){
            return currentNode;
        }
        if (key.compareTo(currentNode.key) < 0){
            currentNode.left = add(currentNode.left, key, value);
            currentNode.left.parent = currentNode;
            return currentNode;
        }else {
            currentNode.right = add(currentNode.right, key, value);
            currentNode.right.parent = currentNode;
            return currentNode;
        }
    }

    /**
     * Wrapper method for the recursive remove method that checks for if the root contains the matching key before
     * recursively calling the private remove method.
     * @param key The key for the key value pair to be removed from the BST.
     * @return true if the key value pair was removed from the BST, false if the key value pair were not in the tree.
     */
    public boolean remove(K key){
        if(key.compareTo(root.key) == 0){
            if(root.left == null && root.right == null){
                root = null;
            }else if(root.left == null){
                root = root.right;
                root.right.parent = null;
            }else if(root.right == null){
                root = root.left;
                root.left.parent = null;
            }else{
                Node temp = removeHelper(root.left);
                K keyTemp = temp.key;
                V valueTemp = temp.value;
                remove(temp.key);
                root.value = valueTemp;
                root.key = keyTemp;
            }
            return true;
        }
        Node temp = remove(key, root, null);
        return temp != null;
    }

    /**
     * Recursive method for removal of a key value pair, has 4 cases:
     * 1. If the current node is null return null in order to indicate the key value pair wasn't in the BST tree.
     * 2. If the current node contains the matching key,
     *      1. If the current node has no children and the previous nodes left is the current node, replace
     *         the current nodes left with null. If the current node has no children and the previous nodes right
     *         is the current node replace the previous nodes right with null.
     *      2. Else if the current node doesn't have a right child replace the previous's node reference to the current
     *         node with a reference to the current nodes left.
     *      3. Else if the current node doesn't have a left child replace the previous' nodes reference to the current
     *         node with a reference to the current nodes right.
     *      4. Else call the remove helper method to find the node that is the greatest node that is less than the
     *         the current node within the binary search tree. Replace the current node's key and value with the
     *         node that was found then call the remove method on the found node.
     * 3. If the key is less than the current nodes key return the result remove method on the left subtree
     * 4. Else return the result of the remove method on the right subtree.
     * @param key The key for the associated key value pair to be removed.
     * @param current The node currently being acted upon.
     * @param previous The node that was previously acted on.
     * @return the node that was acted upon, (doesn't always removed this node from the tree, sometimes replaces key
     *         and value of the node).
     */
    private Node remove(K key, Node current, Node previous){
        if(current == null){
            return null;
        }else if(key.compareTo(current.key) == 0){
            if(current.left == null && current.right == null) {
                if(previous.left == current) {
                    previous.left = null;
                    return current;
                } else {
                    previous.right = null;
                    return current;
                }
            }else if(current.right == null){
                if(previous.left == current){
                    previous.left = current.left;
                    return current;
                }else{
                    previous.right = current.left;
                    return current;
                }
            }else if(current.left == null){
                if(previous.left == current){
                    previous.left = current.right;
                    return current;
                }else{
                    previous.right = current.right;
                    return current;
                }
            }else{
                Node temp = removeHelper(current.left);
                K keyTemp = temp.key;
                V valueTemp = temp.value;
                remove(temp.key);
                current.value = valueTemp;
                current.key = keyTemp;
                return current;
            }
        }else if(key.compareTo(current.key) < 0){
            return remove(key, current.left, current);
        }else{
            return remove(key, current.right, current);
        }
    }

    /**
     * Searches for the largest node in the subtree.
     * @param current The current node being acted upon.
     * @return The largest node in the subtree.
     */
    private Node removeHelper(Node current){
        if(current.right == null){
            return current;
        }else{
            return removeHelper(current.right);
        }
    }

    /**
     * Calls the nodes to string method that utilizes an in order process for creating a string.
     * @return The inorder string that is the concatenation of all nodes to strings in order.
     */
    public String toString() {
        if (root == null) return "null";
        String result = root.toString();
        result = result.replace("null", "");
        return result;
    }

    @Override
    public Iterator<V> iterator() {
        return new BSTIterator();
    }

    /**
     * BST iterator that stores another bst that is made using each element removed from the current tree as it
     * iterates through.
     */
    private class BSTIterator implements Iterator<V>{
        Node lastNodeVisited;
        Node currentNode;
        Node stoppingPoint;

        public BSTIterator(){
            currentNode = root;
            if(currentNode != null) {
                stoppingPoint = findStoppingPoint(root);
            }
        }
        public boolean hasNext(){
            if(currentNode == null){
                return false;
            }else if(lastNodeVisited == null){
                return true;
            }else if(lastNodeVisited.equals(stoppingPoint)){
                return false;
            }else{
                return true;
            }
        }

        public V next() {
            if(lastNodeVisited == null){
                while(currentNode.left != null){
                    currentNode = currentNode.left;
                }
                lastNodeVisited = currentNode;
                return currentNode.value;
            }else {
                currentNode = nextHelper(currentNode);
                return currentNode.value;
            }
        }

        public Node nextHelper(Node currentNode){
            if(currentNode.equals(lastNodeVisited)){
                if(currentNode.right != null){
                    return nextHelper(currentNode.right);
                }else{
                    return nextHelper(currentNode.parent);
                }
            }else if(currentNode.parent != null && currentNode.parent.equals(lastNodeVisited)){
                if(currentNode.left != null){
                    while(currentNode.left != null){
                        currentNode = currentNode.left;
                    }
                    lastNodeVisited = currentNode;
                    return currentNode;
                }else{
                    lastNodeVisited = currentNode;
                    return currentNode;
                }
            }else if(currentNode.left != null && currentNode.left.equals(lastNodeVisited)){
                lastNodeVisited = currentNode;
                return currentNode;
            }else if(currentNode.parent != null){
                lastNodeVisited = currentNode;
                return nextHelper(currentNode.parent);
            }else{
                return stoppingPoint;
            }
        }

        public Node findStoppingPoint(Node currentNode){
            if(currentNode.right != null){
                return findStoppingPoint(currentNode.right);
            }else{
                return currentNode;
            }
        }
    }



    private class Node {
        // The data stored in the node
        K key;
        V value;
        // The root of the left subtree
        Node left;
        // The root of the right subtree
        Node right;
        // The parent of the node;
        Node parent;

        public Node(K key, V value){
            this.value = value;
            this.key = key;
            left = right = parent = null;
        }

        public String toString() {
            return left + "" + value + "" + right;
        }
    }
}
