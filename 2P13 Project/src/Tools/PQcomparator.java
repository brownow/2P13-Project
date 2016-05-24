package Tools;
import java.util.*;

/*******************************************************************
 * Priority Comparator Class
  
 * Used to facilitate collection sorting using descending order based
 * on priority.
 ********************************************************************/
public class PQcomparator implements Comparator<Request>{
    @Override
    public int compare(Request r1, Request r2){
        return ((int)((r2.getPriority()*10000) - (r1.getPriority()*10000)));
        /* multiply by 10,000 for added precision*/
    }
}