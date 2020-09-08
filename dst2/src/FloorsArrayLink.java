public class FloorsArrayLink {
	
	//fields
    private FloorsArrayLink [] arrForward;
    private FloorsArrayLink [] arrBackwards;
    private double key;
    private int arrSize;

    //constructor
    public FloorsArrayLink(double key, int arrSize){
        this.key = key;
        this.arrSize = arrSize;
        arrForward = new FloorsArrayLink [arrSize];
        arrBackwards = new FloorsArrayLink [arrSize];
        for (int i=0; i<arrSize; i=i+1){
            arrForward[i] = null;
            arrBackwards[i] = null;
        }
    }

    //methods
    public double getKey() {
        return key;
    }

    public FloorsArrayLink getNext(int i) {
        if(i<=arrSize&i>0)
        {
            return arrForward[i-1];
        }
        else
        {
            return null;
        }
    }

    public FloorsArrayLink getPrev(int i) {
        if(i<=arrSize&i>0)
        {
            return arrBackwards[i-1];
        }
        else
        {
            return null;
        }
    }

    public void setNext(int i, FloorsArrayLink next) {
        if(i<=arrSize&i>0)
            arrForward[i-1]=next;
    }

    public void setPrev(int i, FloorsArrayLink prev) {
        if(i<=arrSize&i>0)
            arrBackwards[i-1]=prev;
    }

    public int getArrSize(){
        return arrSize;
    }
}

