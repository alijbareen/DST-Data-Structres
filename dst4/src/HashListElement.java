public class HashListElement {

    private FunctionParameters data;

    private HashListElement next;

    public HashListElement (FunctionParameters data){
        this.data = data;
        this.next = null;
    }

    public FunctionParameters getData() {
        return this.data;
    }

    public void setData(FunctionParameters data) {
        this.data = data;
    }

    public HashListElement getNext() {
        return next;
    }

    public void setNext(HashListElement next) {
        this.next = next;
    }
}
