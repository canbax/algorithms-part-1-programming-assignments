import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Node first, last;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException ("null can't be enqueued");

        size++;

        Node n = new Node(item);
        n.next = first;
        first = n;

        if (size == 1)
            last = n;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException ("empty dequeue");


        int n = StdRandom.uniform(0, size);
        size--;

        Node curr = first;
        Node prev = null;
        while (n > 0){
            prev = curr;
            curr = curr.next;
            n--;
        }

        Item i = curr.it;
        if (prev == null){
            first = first.next;
        }else{
            prev.next = curr.next;
            if (prev.next == null)
                this.last = prev;
        }

        if (size == 0)
            last = null;

        return i;
    }

    private Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException ("empty dequeue");

        size--;

        Node curr = first;
        Item i = curr.it;
        first = first.next;

        if (size == 0)
            last = null;

        return i;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException ("empty dequeue");

        int n = StdRandom.uniform(0, size);
        Node curr = first;
        while (n > 0){
            curr = curr.next;
            n--;
        }

        return curr.it;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

        return new Iterator<Item>() {
            RandomizedQueue<Item> myQueue = randomDeepCopy();

            @Override
            public boolean hasNext() {
                return !myQueue.isEmpty();
            }

            @Override
            public Item next() {
                return myQueue.removeFirst();
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException("remove method in iterator");
            }
        };
    }

    private class Node {
        Item it;
        Node next;

        public Node(Item it){
            this.it = it;
        }
    }

    private RandomizedQueue<Item> randomDeepCopy() {
        RandomizedQueue<Item> deepCopy = new RandomizedQueue<>();

        Node curr = this.first;
        for (int i = 0; i < this.size; i++){
            deepCopy.enqueue(curr.it);
            curr = curr.next;
        }
        for (int i = 0; i < this.size; i++){
            Item it = deepCopy.dequeue();
            deepCopy.enqueue(it);
        }
        return deepCopy;
    }

    // unit testing (optional)
//    public static void main(String[] args) {
//        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
//
//        int n = 10;
//        for (int i = 0; i < n; i++){
//            rq.enqueue(i+1);
//        }
//
//        Iterator<Integer> it = rq.iterator();
//        Iterator<Integer> it2 = rq.iterator();
//        while (it.hasNext()){
//            System.out.println(it.next());
//        }
//
//        System.out.println("---------------------------------");
//
//        while (it2.hasNext()){
//            System.out.println(it2.next());
//        }
//
//        System.out.println("done ");
//    }
}