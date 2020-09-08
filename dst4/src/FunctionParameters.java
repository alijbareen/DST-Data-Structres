public class FunctionParameters {
    private int a;
    private int b;

    public  FunctionParameters(int a, int b){
        this.a=a;
        this.b=b;
    }

    //calculate the hash function with the 2 parameters given
    public int DoFunction (int k, int m1){
        int p = 15486907;
        return ((a*k + b)%p )%m1;
    }
}
