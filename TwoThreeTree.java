public class TwoThreeTree <K extends RunnerID, V> {
    protected TreeNode root;
    private int elements;
    protected K minVal;

    public TwoThreeTree() {
        this.root = null;
        this.elements = 0;
        this.minVal = (K) new RunnerIDInfinty();
        init();
    }

    private void init() {
        TreeNode x = new TreeNode();
        Leaf l = new Leaf(null);
        Leaf m = new Leaf(null);
        l.keys = (K) new RunnerIDMInfinty();
        m.keys = (K) new RunnerIDInfinty();
        this.root = x;
        l.size = 0;
        m.size = 0;
        x.leftChild = l;
        x.middleChild = m;
        x.size = 0;
        l.parent = x;
        m.parent = x;
        x.keys = m.keys;
    }

    private class TreeNode {
        K keys;
        TreeNode leftChild;
        TreeNode middleChild;
        TreeNode rightChild;
        TreeNode parent;
        int size;

        TreeNode() {
            keys = null;
            parent = null;
            leftChild = null;
            rightChild = null;
            middleChild = null;
            size = 0;
        }

        public TreeNode getRightChild() {
            return rightChild;
        }

        public void setRightChild(TreeNode rightChild) {
            this.rightChild = rightChild;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(TreeNode leftChild) {
            this.leftChild = leftChild;
        }

        public TreeNode getMiddleChild() {
            return middleChild;
        }

        public void setMiddleChild(TreeNode middleChild) {
            this.middleChild = middleChild;
        }

        public TreeNode getParent() {
            return parent;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    private class Leaf extends TreeNode {
        private V data;

        Leaf(V data) {
            super();
            this.data = data;
        }

        public V getData() {
            return data;
        }

        public void setData(V data) {
            this.data = data;
        }
    }

    public int getSize(K key) {
        Leaf t = contains(this.root, key);
        return t.getSize();
    }

    public int getElements() {
        return elements;
    }

    public boolean contains(K key) {
        Leaf t = contains(this.root, key);
        if (t == null){
            return false;
        }
        return true;
    }

    public Leaf contains(TreeNode x, K key) {
        if (x == null) {
            return null;
        }

        if (isLeaf(x) && x.keys != null) {
            if (!(key.isSmaller(x.keys)) && !(x.keys.isSmaller(key))) {
                return (Leaf) x;
            } else {
                return null;
            }
        }

        if (x.leftChild != null && x.leftChild.keys != null) {
            if (key.isSmaller(x.leftChild.keys) || (!(key.isSmaller(x.leftChild.keys)) && !(x.leftChild.keys.isSmaller(key)))) {
                return contains(x.leftChild, key);
            }
        }
        if (x.rightChild == null) {
            return contains(x.middleChild, key);
        } else if (x.middleChild != null && x.middleChild.keys != null) {
            if (key.isSmaller(x.middleChild.keys) || (!(key.isSmaller(x.middleChild.keys)) && !(x.middleChild.keys.isSmaller(key)))) {
                return contains(x.middleChild, key);
            }
        }
        if (x.rightChild != null && x.rightChild.keys != null) {
            return contains(x.rightChild, key);
        }
        return null;
    }

    public V getData(K key) {
        Leaf t = contains(root, key);
        if (t == null) {
            throw new IllegalArgumentException("key Does Not Exist");
        }
        return t.getData();
    }

    public boolean isLeaf(TreeNode t) {
        return (t.leftChild == null);
    }

    public void updateKey(TreeNode x) {
        x.keys = x.leftChild.keys;
        if (x.middleChild != null) {
            x.keys = x.middleChild.keys;
        }
        if (x.rightChild != null) {
            x.keys = x.rightChild.keys;
        }
    }

    public void setChildren(TreeNode x, TreeNode l, TreeNode m, TreeNode r) {
        x.leftChild = l;
        x.middleChild = m;
        x.rightChild = r;
        l.parent = x;
        if (m != null) {
            m.parent = x;
        }
        if (r != null) {
            r.parent = x;
        }
        updateKey(x);
    }

    public TreeNode insertAndSplit(TreeNode x, TreeNode z) {
        TreeNode l = null, m = null, r = null;

        if (!isLeaf(x)) {
            l = x.leftChild;
            m = x.middleChild;
            r = x.rightChild;
        }
        if (r == null && l != null) {
            if (z.keys.isSmaller(l.keys)) {
                setChildren(x, z, l, m);
            } else if (z.keys.isSmaller(m.keys)) {
                setChildren(x, l, z, m);
            } else {
                setChildren(x, l, m, z);
            }
            updateSize(x);
            return null;
        }
        TreeNode y = new TreeNode();
        if (l != null && z.keys.isSmaller(l.keys)) {
            setChildren(x, z, l, null);
            setChildren(y, m, r, null);
        } else if (m != null && z.keys.isSmaller(m.keys)) {
            setChildren(x, l, z, null);
            setChildren(y, m, r, null);
        } else if (r != null && z.keys.isSmaller(r.keys)) {
            setChildren(x, l, m, null);
            setChildren(y, z, r, null);
        } else {
            setChildren(x, l, m, null);
            setChildren(y, r, z, null);
        }
        calculateSize(x);
        calculateSize(y);
        return y;
    }

    private void calculateSize(TreeNode node) {
        if (node == null) return;
        node.size = 0;
        if (node.leftChild != null) node.size += node.leftChild.size;
        if (node.middleChild != null) node.size += node.middleChild.size;
        if (node.rightChild != null) node.size += node.rightChild.size;
        calculateSize(node.parent);
    }

    private void updateSize(TreeNode node) {
        while (node != null) {
            node.size += 1;
            node = node.parent;
        }
    }

    public void insert(K key, V data) {
        if(data == null)
        {
            throw new IllegalArgumentException("Data cannot be null");
        }
        Leaf z = new Leaf(data);
        z.keys = key;
        z.setSize(1);
        elements++;
        insert(z);
        if (key != null) {
            if (key.isSmaller(minVal)) {
                setMinVal(key);
            }
        }
    }

    private void setMinVal(K minVal) {
        this.minVal = minVal;
    }

    public K getMinVal() {
        return this.minVal;
    }

    public void insert(TreeNode z) {
        TreeNode y = this.root;
        while (!isLeaf(y)) {
            if (z.keys.isSmaller(y.leftChild.keys)) {
                y = y.leftChild;
            } else if (z.keys.isSmaller(y.middleChild.keys)) {
                y = y.middleChild;
            } else {
                y = y.rightChild;
            }
        }
        TreeNode x = y.parent;
        z = insertAndSplit(x, z);
        while (x != this.root) {
            x = x.parent;
            if (z != null) {
                z = insertAndSplit(x, z);
            } else {
                updateKey(x);
            }
            calculateSize(x);
        }
        if (z != null) {
            TreeNode w = new TreeNode();
            setChildren(w, x, z, null);
            this.root = w;
            calculateSize(w);
        }
    }

    private void updateAncestorsSize(TreeNode node) {
        while (node != null) {
            node.size--;
            node = node.parent;
        }
    }

    public TreeNode Successor(TreeNode x) {
        if (x == null) return null;
        TreeNode z = x.parent;
        while (z != null && (x == z.rightChild || (z.rightChild == null && x == z.middleChild))) {
            x = z;
            z = z.parent;
        }
        TreeNode y;
        if (z == null) {
            return null;
        } else if (x == z.leftChild) {
            y = z.middleChild;
        } else {
            y = z.rightChild;
        }

        while (y != null && !isLeaf(y)) {
            y = y.leftChild;
        }
        if (y != null && y.keys.isSmaller(new RunnerIDInfinty())) {
            return y;
        } else {
            return null;
        }
    }

    public void delete(K key) {
        Leaf x = contains(root, key);
        if (key != null && x != null) {
            if (elements > 1) {
                if (!(key.isSmaller(minVal)) && !(minVal.isSmaller(key))) {
                    setMinVal(Successor(x).keys);
                }
            } else {
                setMinVal((K) new RunnerIDInfinty());
            }
        }
        if (x != null) {
            delete(x);
            elements--;
        }
    }

    private void delete(Leaf x) {
        TreeNode y = x.parent;
        if (y != null) {
            if (x == y.leftChild) {
                setChildren(y, y.middleChild, y.rightChild, null);
            } else if (x == y.middleChild) {
                setChildren(y, y.leftChild, y.rightChild, null);
            } else {
                setChildren(y, y.leftChild, y.middleChild, null);
            }
            x = null;
        }
        if (y.rightChild == null) {
            updateAncestorsSize(y);
        }
        while (y != null) {
            if (y.middleChild != null) {
                updateKey(y);
                y = y.parent;
            } else {
                if (y != this.root) {
                    y = borrowOrMerge(y);
                } else {
                    this.root = y.leftChild;
                    if (y.leftChild != null)
                        y.leftChild.parent = null;
                    y = null;
                    return;
                }
            }
        }
    }

    public TreeNode borrowOrMerge(TreeNode y) {
        if (y == null || y.parent == null) {
            return null;
        }
        TreeNode z = y.parent;
        if (y == z.leftChild) {
            TreeNode x = z.middleChild;
            if (x.rightChild != null) {
                setChildren(y, y.leftChild, x.leftChild, null);
                setChildren(x, x.middleChild, x.rightChild, null);
                calculateSize(y);
                calculateSize(x);
            } else {
                setChildren(x, y.leftChild, x.leftChild, x.middleChild);
                calculateSize(x);
                y = null;
                setChildren(z, x, z.rightChild, null);
                calculateSize(z);
            }
            return z;
        }
        if (y == z.middleChild) {
            TreeNode x = z.leftChild;
            if (x.rightChild != null) {
                setChildren(y, x.rightChild, y.leftChild, null);
                setChildren(x, x.leftChild, x.middleChild, null);
                calculateSize(y);
                calculateSize(x);
            } else {
                setChildren(x, x.leftChild, x.middleChild, y.leftChild);
                calculateSize(x);
                y = null;
                setChildren(z, x, z.rightChild, null);
                calculateSize(z);
            }
            return z;
        }
        y = z.rightChild;
        TreeNode x = z.middleChild;
        if (x.rightChild != null) {
            setChildren(y, x.rightChild, y.leftChild, null);
            setChildren(x, x.leftChild, x.middleChild, null);
            calculateSize(y);
            calculateSize(x);
        } else {
            setChildren(x, x.leftChild, x.middleChild, y.leftChild);
            calculateSize(x);
            y = null;
            setChildren(z, z.leftChild, x, null);
            calculateSize(z);
        }
        return z;
    }
    public int rank(K key) {
        TreeNode node = contains(this.root, key);
        return rank(node);
    }

    private int rank(TreeNode x) {
        int rank = 1;
        TreeNode y = null;
        if (x != null && x.parent != null) {
            y = x.parent;
        }
        while (y != null) {
            if (x == y.middleChild && y.leftChild != null) {
                rank += y.leftChild.size;
            } else if (x == y.rightChild) {
                if (y.leftChild != null) {
                    rank += y.leftChild.size;
                }
                if (y.middleChild != null) {
                    rank += y.middleChild.size;
                }
            }
            x = y;
            y = y.parent;
        }
        return rank;
    }
}