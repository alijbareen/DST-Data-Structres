public class HashList {

    private HashListElement first;

    public HashList(){
        this.first = null;
    }

    public HashListElement getFirst() {
        return first;
    }

    public void setFirst(HashListElement first) {
        this.first = first;
    }

    public boolean isEmpty(){return first==null;}

    //add the element to the beginning of the list.
    public void add(FunctionParameters element){
        if (element!=null) {
        	if(isEmpty())
        		first = new HashListElement (element);
        	else {
        		HashListElement newFirst = new HashListElement (element);
        		newFirst.setNext(first);
        		setFirst(newFirst);
        	}
        }
    }
}
