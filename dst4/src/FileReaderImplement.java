import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReaderImplement implements FileReader {

    Scanner sc;


    public FileReaderImplement (String path){
        File file = new File(path);
        this.sc = null;
        try
        {
            this.sc = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    //read the next line
    public String nextLine() {
            return sc.nextLine();
        }

    @Override
    //check if there is next line
    public Boolean hasNextLine() {
        return sc.hasNextLine();
    }
}
