import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private final int initialCapacity = 2;
    private final double threshold2grow = 1.0, threshold2shrink = 0.25;
    private int size = 0, capacity;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.capacity = this.initialCapacity;
        this.items = (Item[]) new Object[this.capacity];
    }

    private void init(int size, int capacity) {
        this.capacity = capacity;
        this.size = size;
        this.items = (Item[]) new Object[this.capacity];
    }

    private Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException ("empty dequeue");

        Item i = this.items[this.size - 1];
        this.items[this.size - 1] = null;
        this.size--;

        if (this.size * 1.0 / this.capacity < this.threshold2shrink) {
            setCapacity(this.capacity / 2);
        }
        return i;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return this.size;
    }

    private void setCapacity(int newCapacity) {
        this.capacity = newCapacity;

        Item[] newItems = (Item[]) new Object[this.capacity];

        for (int i = 0; i < this.size; i++) {
            newItems[i] = this.items[i];
        }
        this.items = newItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException ("null can't be enqueued");

        this.items[this.size] = item;
        this.size++;

        // increase capacity
        if (this.size*1.0 / this.capacity >= this.threshold2grow) {
            setCapacity(2 * this.capacity);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.size == 0)
            throw new NoSuchElementException ("empty dequeue");

        int n = StdRandom.uniform(0, this.size);
        // exchange nth element with last element and delete last element
        Item chosenItem = this.items[n];
        this.items[n] = this.items[this.size - 1];
        removeLast();
        return chosenItem;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException ("empty dequeue");

        int n = StdRandom.uniform(0, this.size);
        return this.items[n];
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
                return myQueue.removeLast();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove method in iterator");
            }
        };
    }

    private RandomizedQueue<Item> randomDeepCopy() {
        RandomizedQueue<Item> deepCopy = new RandomizedQueue<>();
        deepCopy.init(this.size, this.capacity);
        int originalSize = this.size;
        for (int i = 0; i < originalSize; i++) {
            deepCopy.items[i] = dequeue();
        }
        for (int i = 0; i < originalSize; i++) {
            enqueue(deepCopy.items[i]);
        }
        return deepCopy;
    }

    // unit testing (optional)
//    public static void main(String[] args) {
//        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
//
//        int n = 64;
//        for (int i = 0; i < n; i++){
//            rq.enqueue(i+1);
//        }
//
//        Iterator<Integer> it = rq.iterator();
//        int m = 32;
//        while (it.hasNext()){
//            System.out.println(it.next());
//        }
//
//
//        System.out.println("done ");
//    }
}