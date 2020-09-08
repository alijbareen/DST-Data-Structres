public class Comparator {

    //compare 2 strings by lexicografical order
    public static int compare (String s1, String s2){
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int output = 0;
        while (s1.length()>0&s2.length()>0){
            output=s1.charAt(0)-s2.charAt(0);
            s1=s1.substring(1);
            s2=s2.substring(1);
            if (output!=0){
                return output;
            }
        }
        if (s1.length()>0){
            return 1;
        }
        if (s2.length()>0){
            return -1;
        }
        return 0;

    }
}
