
public class LinkedList {
	private Link first;
	
	public LinkedList () {
		this.first = null;
	}
	
	public Link getFirst () {
		return first;
	}
	
	public void setFirst (Link first) {
		this.first=first;
	}
	
	public boolean isEmpty () {
		return getFirst() == null;
	}
	
	public void add (int toAdd) {
		Link first=new Link (toAdd);
		first.setNext(getFirst());
	}
	
	public boolean contains (int check) {
		boolean isTrue = false;
		if(!isEmpty()) {
			Link link =  new Link(getFirst());
			while(link!=null & !isTrue){
				if(link.getData()==check) {
					isTrue = true;
				}
				link = link.getNext();
			}
		}
        return isTrue;
	}
}
