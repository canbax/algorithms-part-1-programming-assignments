import java.util.Iterator;
import java.util.NoSuchElementException;

// import edu.princeton.cs.algs4;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node first, last;

    // construct an empty deque
    public Deque(){
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null)
            throw new IllegalArgumentException ("null can't added");

        size++;

        Node n = new Node(item);
        n.next = first;
        if (first != null)
            first.prev = n;
        first = n;

        if (size == 1)
            last = n;
    }

    // add the item to the end
    public void addLast(Item item){
        if (item == null)
            throw new IllegalArgumentException("null can't be added");

        size++;

        Node n = new Node(item);
        if (size == 1){
            last = n;
            first = n;
        }else{
            last.next = n;
            n.prev = last;
            last = n;
        }
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (size == 0)
            throw new NoSuchElementException ("empty dequeue");

        size--;

        Item r = first.it;
        first = first.next;
        if (first != null)
            first.prev = null;
        else
            last = null;


        return r;
    }

    // remove and return the item from the end
    public Item removeLast(){
        if (size == 0)
            throw new NoSuchElementException ("empty dequeue");

        size--;

        Item r = last.it;
        last = last.prev;
        if (last != null)
            last.next = null;
        else
            first = null;

        return r;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator(){
        Iterator<Item> r = new Iterator<Item>() {
            private Node curr = null;

            @Override
            public boolean hasNext() {
                return !isEmpty() && curr != last;
            }

            @Override
            public Item next() {
                if (curr == null){
                    curr = first;
                    return curr.it;
                }
                if (curr.next == null)
                    throw new NoSuchElementException("next in iterator");

                curr = curr.next;
                return curr.it;
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException("remove method in iterator");
            }
        };
        return r;
    }

    private class Node {
        Item it;
        Node next;
        Node prev;

        public Node(Item it) {
            this.it = it;
        }
    }

    private void print(){
        Node curr = first;

        System.out.print("\n[ ");
        while (curr != null){
            System.out.print(curr.it + " ");
            curr = curr.next;
        }
        System.out.print(" ]");
    }

    private void print2(){
        Node curr = last;

        System.out.print("\n[ ");
        while (curr != null){
            System.out.print(curr.it + " ");
            curr = curr.prev;
        }
        System.out.print(" ]");
    }

    // unit testing (optional)
    public static void main(String[] args){
//        System.out.println("hello");

        Deque<Integer> deq = new Deque<>();

        for (int i = 0; i < 10; i++)
            deq.addFirst(i+1);

        for (int i = 0; i < 10; i++)
            deq.removeLast();


        deq.removeFirst();

//        deq.print();
//        deq.print2();

        // deq.removeLast();

        // deq.print();
        // deq.print2();
    }
}