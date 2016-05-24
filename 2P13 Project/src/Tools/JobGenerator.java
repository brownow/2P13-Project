package Tools;

import java.util.Random;
import org.uncommons.maths.random.PoissonGenerator;
import org.uncommons.maths.random.ExponentialGenerator;
import java.util.ArrayList;

/*******************************************************************
 * Job Generator Class
  
 * Used to create print jobs with Poisson distributed page numbers and 
 * exponentially distributed arrival times.
 ********************************************************************/
public class JobGenerator {
    
    int jobs;
    double time;
    Random rand;
    PoissonGenerator pg;
    ExponentialGenerator eg;
    ArrayList<Request> returnArray;
    
    /****************************************
     * Default Constructor for Job Generator.
     ****************************************/
    public JobGenerator(){
        rand = new Random();
        pg = new PoissonGenerator(4,rand);
    }
    
    /*************************************************
     * Constructor for Job Generator taking parameters:
     * integer  number of requests
     * integer  pages per minute.
     *************************************************/
    public ArrayList<Request> generate(int numReq, int frequency){
        jobs = numReq;
        returnArray = new ArrayList();
        double arrivetime = 0;
        double freq = frequency;
        int j = pg.nextValue();
            while (j == 0){
                j = pg.nextValue();
            }
        Request job = new Request(arrivetime,j);
        returnArray.add(job);
        for (int i = 0; i<numReq-1; i++){      
             j = pg.nextValue();
            while (j == 0){
                j = pg.nextValue();
            }
            // Request is made, given a random arrival based on the job request
            // rate.
            job = new Request((Math.log(1-Math.random()))/(-(freq/60.0))
                    +arrivetime,j);
            returnArray.add(job);
        }
        return returnArray;
    }
}
