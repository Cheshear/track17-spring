package track.lessons.lesson3;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    private int[] elementData;
    private int size;

    public MyArrayList() {
        this(10);
        elementData = new int[10];
        size = 0;
    }

    public MyArrayList(int capacity) {
        super();
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        this.elementData = new int[capacity];
        size = 0;
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }


    @Override
    void add(int item) {
        if (elementData.length > size) {
            elementData[size] = item;
            size++;
        } else {
            grow( (elementData.length * 3) / 2 + 1);
            elementData[size] = item;
            size++;
        }
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx > elementData.length) {
            throw new NoSuchElementException("no such index" + idx);
        } else {
            int returnValue = elementData[idx];
            for (int i = idx; i < (elementData.length - 2); ++i) {
                elementData[i] = elementData[i + 1];
            }
            size--;
            return returnValue;
        }
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx >= size) {
            throw new NoSuchElementException("no such index" + idx);
        } else {
            return elementData[idx];
        }
    }

    @Override
    int size() {
        return size;
    }
}
