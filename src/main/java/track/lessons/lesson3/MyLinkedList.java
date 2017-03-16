package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List implements Stack , Queue {

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        return remove(size - 1);
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeu() {
        return remove(0);
    }

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    @Override
    void add(int item) {
        if (size() == 0) {
            Node newNode = new Node(null, null, item);
            tail = newNode;
            head = newNode;
            size++;
        } else {
            Node newNode = new Node(tail, null, item);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            size++;
        }

    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if ( (idx > (size - 1)) || (idx < 0) ) {
            throw new NoSuchElementException();
        } else {
            int counter = 0;
            Node node = head;
            while (counter != idx) {
                node = node.next;
                counter++;
            }
            int returnValue = node.val;
            if ( ( node.next != null) && (node.prev != null) ) {
                Node nextNode = node.next;
                Node prevNode = node.prev;
                nextNode.prev = prevNode;
                prevNode.next = nextNode;
                node.next = null;
                node.prev = null;
                node.val = 0;
                // все указатели и значения обнулены , готов для удаления гарбиш коллектором
            } else if ( (node.next == null) && (node.prev == null) ) {
                node.val = 0;
                tail = null;
                head = null;
            } else if ( idx == 0 ) {
                head = node.next;
                head.prev = null;
                node.next = null;
                node.val = 0;
            } else if ( idx == size - 1 ) {
                tail = node.prev;
                tail.next = null;
                node.prev = null;
                node.val = 0;
            }
            size--;
            return returnValue ;
        }
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if ( (idx > (size - 1) ) || (idx < 0) ) {
            throw new NoSuchElementException();
        } else {
            int counter = 0;
            Node node = head;
            while (counter != idx) {
                node = node.next;
                counter++;
            }
            return node.val;
        }
    }

    @Override
    int size() {
        return size;
    }

}

