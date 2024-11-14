import java.io.Serializable;
public class DLList<E> implements Serializable{
    private Node<E> dummy;
    private int size;

    public DLList() {
        dummy = new Node<E>(null);
        dummy.setNext(dummy);
        dummy.setPrev(dummy);
    }

    private Node<E> getNode(int ind) {
        Node<E> node = dummy.next();
        for(int i=0; i<ind; i++) {
            node = node.next();
        }

        return node;
    }

    public void add(E e) {
        Node<E> n = new Node<E>(e);
        Node<E> end = dummy.prev();
        end.setNext(n);
        n.setPrev(end);
        n.setNext(dummy);
        dummy.setPrev(n);
   /*     n.setNext(dummy);
        n.setPrev(dummy.prev());
        dummy.prev().setNext(n);
        dummy.setPrev(n); */

        size++;
    }

    public void add(int ind, E e) {
        Node<E> n = getNode(ind);
        Node<E> c = new Node<E>(e);

        c.setNext(n);
        c.setPrev(n.prev());
        c.prev().setNext(c);
        n.setPrev(c);

        size++;
    }

    public E get(int ind) {
        return getNode(ind).get();
    }

    public void remove(int ind) {
        Node<E> n = getNode(ind);

        n.prev().setNext(n.next());
        n.next().setPrev(n.prev());

        size--;
    }

    public void remove(E e) {
        Node<E> n = dummy;
        for(int i=-1; i<size; i++) {
            n = n.next();
            if(n.get().equals(e)) {
                n.prev().setNext(n.next());
                n.next().setPrev(n.prev());
                size--;
            }
        }
    }

    public void set(int ind, E e) {
        getNode(ind).set(e);
    }

    public int size(){
        return size;
    }

    public String toString() {
        if(size==0) {
            return "";
        }

        String s = "" + dummy.next().get().toString();
        Node<E> c = dummy.next().next();

        while(c!=dummy) {
            s = s + c.get().toString();
            c = c.next();
        }
        
        return s + "";
    }

    public boolean contains(E e) {
        Node<E> c = dummy.next();
        while(c!=dummy) {
            if(c.get().equals(e)) {
                return true;
            }
            c = c.next();
        }
        return false;
    }
}