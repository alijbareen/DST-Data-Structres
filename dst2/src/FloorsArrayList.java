public class FloorsArrayList implements DynamicSet {

    //fields
    private int MaxSize;
    private int size;
    private FloorsArrayLink first;
    private FloorsArrayLink last;
    private double min;
    private double max;
    private int CurrentMaxArray;

    //constructor
    public FloorsArrayList(int N){
        MaxSize = N;
        size = 0;
        CurrentMaxArray = 0;
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
        first = new FloorsArrayLink (Double.NEGATIVE_INFINITY,N+1);
        last = new FloorsArrayLink (Double.POSITIVE_INFINITY,N+1);
        for(int i=0; i<=N; i=i+1) {
            first.setNext(i, last);
            last.setPrev(i, first);
        }
    }

    //returns a specific Link in the list according to a key (input) if the Link exists or null(if the key does not exist).
    public FloorsArrayLink lookup(double key) {
        FloorsArrayLink output = search(key);
        if (output.getKey()!=key)
            output = null;
        return output;
    }

    //returns the Link representing the key (input) -  if exists, or the previous key (if this specific key does not exist).
    public FloorsArrayLink search (double key) {
        FloorsArrayLink current = first;
        int index = CurrentMaxArray;
        while (index>0){
            if (current.getNext(index).getKey()<=key)
            {
                current = current.getNext(index);
            }
            else
            {
                index = index - 1;
            }
        }
        return current;
    }

    //add a new Link to the List.
    public void insert(double key, int arrSize) {
        if (size<MaxSize){
            if (arrSize>CurrentMaxArray){
                CurrentMaxArray = arrSize;
            }
            if (key<min){
                min = key;
            }
            if (key>max){
                max = key;
            }
            FloorsArrayLink prev = search(key);
            FloorsArrayLink next = prev.getNext(1);
            FloorsArrayLink current = new FloorsArrayLink(key, arrSize);
            int index = 1;
            while (index <= current.getArrSize()) {
                current.setNext(index, next);
                next.setPrev(index, current);
                current.setPrev(index, prev);
                prev.setNext(index, current);
                index = index + 1;
                while (index > next.getArrSize())
                {
                    next = next.getNext(index-1);
                }
                while (index > prev.getArrSize())
                {
                    prev = prev.getPrev(index-1);
                }
            }
            size = size + 1;
        }
    }

    //Removes a specific Link from the List.
    public void remove(FloorsArrayLink toRemove) {
        if (toRemove.getKey()==min)
        {
            min=toRemove.getNext(1).getKey();
        }
        if (toRemove.getKey()==max)
        {
            max=toRemove.getPrev(1).getKey();
        }

        boolean isUpdated = false;
        for (int i=toRemove.getArrSize(); i>0; i=i-1){
            toRemove.getNext(i).setPrev(i,toRemove.getPrev(i));
            toRemove.getPrev(i).setNext(i,toRemove.getNext(i));
            if(toRemove.getArrSize()==CurrentMaxArray&!isUpdated) {
                if (toRemove.getNext(i).getKey() != Double.POSITIVE_INFINITY) {
                    CurrentMaxArray = toRemove.getNext(i).getArrSize();
                    isUpdated = true;
                }
                if (toRemove.getPrev(i).getKey() != Double.NEGATIVE_INFINITY ) {
                    CurrentMaxArray = toRemove.getPrev(i).getArrSize();
                    isUpdated = true;
                }
            }
        }
        size = size-1;
    }

    //returns the successor of a specific Link.
    public double successor(FloorsArrayLink link) {
        return link.getNext(1).getKey();
    }

    //returns the predecessor of a specific Link.
    public double predecessor(FloorsArrayLink link) {
        return link.getPrev(1).getKey();
    }

    //returns the minimum Link in the list.
    public double minimum() {
        return min;
    }

    //returns the maximum Link in the list.
    public double maximum() {
        return max;
    }
    //returns the number of Links in the list.
    public int getSize(){
        return size;
    }


}
