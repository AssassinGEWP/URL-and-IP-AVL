import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Read {

    public static class Read_ip {
        Read_ip(){

        }

        public void Read_ip1(AVLTree tree1) throws IOException {

            File file = new File(
                    "C:\\Users\\MSI\\Desktop\\test.txt");
            BufferedReader br
                    = new BufferedReader(new FileReader(file));
            String st ;
            while ((st = br.readLine()) != null) {
                String[] values = st.split(" > ");
                String s1 = values[0];
                String s2 = values[1];
                tree1.insert(s1,s2);
//                System.out.println(s1 + " " + s2);


            }
            br.close();
            return;
        }
    }
}
