package functions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {

    private Node head;
    private int count;


    private static class Node{
        public Node next;
        public Node prev;
        public double x;
        public double y;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private void addNode(double x, double y){
        Node newNode = new Node(x, y);
        if (head == null){
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node last = head.prev;
            last.next = newNode;
            head.prev = newNode;
            newNode.prev = last;
            newNode.next = head;
        }
        count++;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues){
        if (xValues.length < 2)
            throw new IllegalArgumentException("list must contain at least two elements");

        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2)
            throw new IllegalArgumentException("array must contain at least two elements");

        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        if (xFrom == xTo) {
            double yValue = source.apply(xFrom);
            for (int i = 0; i < count; i++) {
                addNode(xFrom, yValue);
            }
        } else {
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 0; i < count; i++) {
                double x = xFrom + i * step;
                addNode(x, source.apply(x));
            }
        }

    }
    @Override
    public int getCount() {
        return count;
    }


    @Override
    public double leftBound() {
        return head.x;
    }


    @Override
    public double rightBound() {
        return head.prev.x;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= count)
            throw new IllegalArgumentException("index is out of bounds");

        Node current = head;
        if (index < count / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = head.prev;
            for (int i = count - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public double getX(int index) {
        if (index < 0 || index >= count)
            throw new IllegalArgumentException("index is out of bounds");

        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        getNode(index).y = value;
    }


    @Override
    public int indexOfX(double x) {
        Node current = head;
        for (int i = 0; i < count; i++) {
            if (current.x == x) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }


    @Override
    public int indexOfY(double y) {
        Node current = head;
        for (int i = 0; i < count; i++) {
            if (current.y == y) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x < leftBound())
            throw new IllegalArgumentException("x is less than left bound");

        Node current = head;
        for (int i = 0; i < count; i++) {
            current = current.next;
            if (current.x > x) return i;
        }
        return 0;
    }


    @Override
    protected double interpolate(double x, int floorIndex) {
        Node left = getNode(floorIndex);
        Node right = left.next;
        return interpolate(x, left.x, right.x, left.y, right.y);
    }

    @Override
    protected double extrapolateLeft(double x) {
        Node left = head;
        Node right = left.next;
        return interpolate(x, left.x, right.x, left.y, right.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        Node left = head.prev.prev;
        Node right = head.prev;
        return interpolate(x, left.x, right.x, left.y, right.y);
    }

    @Override
    public void insert(double x, double y) {
        if (head == null) {
            addNode(x, y);
            return;
        }

        Node current = head;

        do {
            if (current.x == x) {
                current.y = y;
                return;
            }

            if (current.x < x && current.next.x > x) {
                Node newNode = new Node(x, y);
                Node next = current.next;

                current.next = newNode;
                newNode.prev = current;
                newNode.next = next;
                next.prev = newNode;

                count++;
                return;
            }

            current = current.next;
        } while (current != head);

        if (x < head.x) {
            Node newNode = new Node(x, y);
            Node last = head.prev; // head указывает на первый элемент в списке

            newNode.next = head;
            newNode.prev = last;
            last.next = newNode;
            head.prev = newNode;
            head = newNode; // Обновляем головной узел

            count++;
            return;
        }

        if (x > head.prev.x) {
            addNode(x, y);
        }
    }

    @Override
    public void remove(int index) {
        if (count == 0)
            throw new IllegalArgumentException("list is empty");

        if (index < 0 || index >= count)
            throw new IllegalArgumentException("index is out of bounds");

        Node toRemove = getNode(index);

        if (count == 1) {
            head = null;
        } else {
            if (toRemove == head) {
                head = head.next;
            }
            Node prev = toRemove.prev;
            Node next = toRemove.next;
            prev.next = next;
            next.prev = prev;
        }
        count--;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            private Node node = head;

            @Override
            public Point next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                Point p = new Point(node.x, node.y);
                node = node.next;
                if (node == head)
                    node = null;
                return p;
            }

            @Override
            public boolean hasNext() {
                return node != null;
            }
        };
    }


}
