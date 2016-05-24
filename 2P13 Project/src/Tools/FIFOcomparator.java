package Tools;
import java.util.*;

/*******************************************************************
 * FIFO Comparator Class
  
 * Used to facilitate collection sorting using ascending order based
 * on arrival time.
 ********************************************************************/
public class FIFOcomparator implements Comparator<Request>{
    @Override
    public int compare(Request r1, Request r2){
        return (int)((r1.getArrival()*10000) - (r2.getArrival()*10000));
        /* multiply by 10,000 for added precision*/
    }
}