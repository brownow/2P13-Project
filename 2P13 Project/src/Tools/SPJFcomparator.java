package Tools;
import java.util.*;

/*******************************************************************
 * SPJF Comparator Class
  
 * Used to facilitate collection sorting using ascending order based
 * on number of pages.
 ********************************************************************/
public class SPJFcomparator implements Comparator<Request>{
    @Override
    public int compare(Request r1, Request r2){
        return ((int)(r1.getPages() - r2.getPages()));
    }
}