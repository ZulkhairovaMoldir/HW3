
import java.util.*;
import java.util.LinkedList;

public class MyHashTable<K, V> {
    private class HashNode<K, V> {
        private K key;
        private V value;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<HashNode<K, V>>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {
        this.chainArray = new LinkedList[M];
        for (int i = 0; i < M; i++) {
            chainArray[i] = new LinkedList<>();
        }
    }

    public MyHashTable(int M) {
        this.M = M;
        this.chainArray = new LinkedList[M];
        for (int i = 0; i < M; i++) {
            chainArray[i] = new LinkedList<>();
        }
    }

    private int hash(K key) {
        return key.hashCode() % M;
    }

    public void put(K key, V value) {
        int index = hash(key);
        LinkedList<HashNode<K, V>> chain = chainArray[index];
        for (HashNode<K, V> node : chain) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        chain.add(new HashNode<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        LinkedList<HashNode<K, V>> chain = chainArray[index];
        for (HashNode<K, V> node : chain) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        LinkedList<HashNode<K, V>> chain = chainArray[index];
        for (HashNode<K, V> node : chain) {
            if (node.key.equals(key)) {
                chain.remove(node);
                size--;
                return node.value;
            }
        }
        return null;
    }

    public boolean contains(V value) {
        for (int i = 0; i < M; i++) {
            LinkedList<HashNode<K, V>> chain = chainArray[i];
            for (HashNode<K, V> node : chain) {
                if (node.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public K getKey(V value) {
        for (int i = 0; i < M; i++) {
            LinkedList<HashNode<K, V>> chain = chainArray[i];
            for (HashNode<K, V> node : chain) {
                if (node.value.equals(value)) {
                    return node.key;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        MyHashTable<String, Integer> table = new MyHashTable<>();
        table.put("A", 1);
        table.put("L", 2);
        table.put("M", 3);
        System.out.println(table.get("A")); // Output: 1
        System.out.println(table.get("D")); // Output: null
        System.out.println(table.contains(2)); // Output: true
        System.out.println(table.getKey(3)); // Output: C
        table.remove("B");
        System.out.println(table.get("B")); // Output: null
    }
}


public class BST<K extends Comparable<K>, V> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(K key, V val) {
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        return x;
    }

    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Iterable<Node> iterator() {
        List<Node> list = new ArrayList<>();
        inOrder(root, list);
        return list;
    }

    private void inOrder(Node x, List<Node> list) {
        if (x == null) return;
        inOrder(x.left, list);
        list.add(x);
        inOrder(x.right, list);
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        BST<Integer, String> bst = new BST<>();
        bst.put(3, "Moldir");
        bst.put(2, "Nazym");
        bst.put(6, "Hanzada");
        bst.put(1, "Aigerim");
        bst.put(5, "Aizere");
        bst.put(4, "Adya");

        for (Node node : bst.iterator()) {
            System.out.println("key : " + node.key + " value: " + node.val);
        }
    }
}
