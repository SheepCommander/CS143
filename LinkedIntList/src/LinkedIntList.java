public class LinkedIntList implements IntList {
    private Node front;

    // Constructs an empty LinkedIntList with no data.
    public LinkedIntList() {}

    // Constructs a LinkedIntList with one node.
    public LinkedIntList(int val) {
        this.front = new Node(val);
    }

    // pre: nodes are not in a self-referential loop.
    // Adds a new node to the end of the LinkedList.
    public void add(int value) {
        if (this.front == null) {
            this.front = new Node(value);
            return;
        }
        Node tail = _getTailNode();
        tail.next = new Node(value);
	}


    // pre: nodes are not in a self-referential loop.
    // Returns a string representation
    public String toString() {
        if (this.size() == 0) return "[]";
        String out = "[";
        Node node = front;
        while (node != null) {
            out += node.data+", ";
            node = node.next;
        }
        out = out.substring(0, out.lastIndexOf(",")) + "]";
        return out;
	}

    // pre: nodes are not in a self-referential loop.
    // Returns the number of nodes
    public int size() {
        if (front == null) return 0;

        int size = 0;
        Node node = front;
        while (node != null) {// runs once for every non-null node
            node = node.next;
            size++;
        }
        return size;
	}
    // pre: 0 <= index < size
    // pre: nodes are not in a self-referential loop.
    // Returns the data of the node at the index
    public int get(int index) {
        return _getNode(index).data;
	}

    private Node _getNode(int index) {
        if (index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();

        Node node = front; // index 0
        for (int i=0; i<index; i++) {// if index = 0 doesnt run
            node = node.next;
        }
        return node;
	}

    // pre: nodes are not in a self-referential loop.
    // Returns the index of the first node with that value.
    public int indexOf(int value) {
        if (this.front == null) return -1;
        Node node = front;
        int i = 0;
        while (node != null) {
            if (node.data == value) return i;
            i++;
            node = node.next;
        }
        return -1;
	}
    // pre: 0 <= index < size
    // pre: nodes are not in a self-referential loop.
    // 
    public void add(int index, int value) {
        if (index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();
        
        if (index == 0) {
            Node oldFront = this.front;
            this.front = new Node(value);
            this.front.next = oldFront;
            return;
        }

        Node left = this._getNode(index-1);
        Node right = this._getNode(index);
        left.next = new Node(value);
        left.next.next = right;
	}
    // pre: 0 <= index < size
    // pre: nodes are not in a self-referential loop.
    public void remove(int index) {
        if (index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();

        if (this.size() == 1) {
            this.front = null;
            return;
        }
        if (index == 0) {
            this.front = front.next;
            return;
        }
        if (index == size() - 1) {
            Node beforeLast = front;
            while (beforeLast.next.next != null) {
                beforeLast = beforeLast.next;
            } // now beforeLast.next.next = null, meaning this is the second to last
            beforeLast.next = null; // remove the last
        }
        if (index < size() - 1) { // 0 < index < size-1
            // i gave up on the logic and added a _getNode(index) helper method
            Node beforeIndex = _getNode(index-1);
            Node indexNode = _getNode(index);
            Node afterIndex = _getNode(index+1);
            indexNode.next = null;
            beforeIndex.next = afterIndex;
        }
	}
    // Checks if the contents of this list and the other list are equal
    // pre: nodes are not in a self-referential loop.
    public boolean equals(Object object) {
        if (object.getClass() != this.getClass())
            return false;
        
        LinkedIntList other = (LinkedIntList) object;
        if (other.size() != this.size())
            return false;
        
        for (int i=0; i<this.size(); i++) {
            int thisI = this.get(i);
            int otherI = other.get(i);
            if (thisI != otherI) return false;
        }
        return true;
	}
    
    // pre: 0 <= index < size
    // pre: nodes are not in a self-referential loop.
    // Inserts the contents of {@param list} into {@param index}
    public void insertList(int index, LinkedIntList list) {
        for (int i=0; i<list.size(); i++) {
            this.add(index+i, list.get(i));
        }
	}

    // pre: nodes are not in a self-referential loop.
    private Node _getTailNode() {
        if (front == null) return null;
        if (size() == 1) return front;

        Node node = front;
        while (node.next != null) {
            node = node.next;
        }
        return node;
    }
}
