import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args){

        int k = Integer.parseInt(args[0]);
//        int k = 10;

        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()){
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++){
            StdOut.println(rq.dequeue());
        }

//       try {
//
//           File f = new File("tale.txt");
//
//           BufferedReader b = new BufferedReader(new FileReader(f));
//
//           String readLine = "";
//
//           System.out.println("Reading file using Buffered Reader");
//
//           while ((readLine = b.readLine()) != null) {
//               for (String s: readLine.split(" "))
//                   rq.enqueue(s);
//           }
//
//       } catch (IOException e) {
//           e.printStackTrace();
//       }

//       for (int i = 0; i < k; i++){
//         System.out.println(rq.dequeue());
//       }

    }
}