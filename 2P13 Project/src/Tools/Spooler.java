package Tools;
import java.util.ArrayList;
import java.util.*;

public final class Spooler {
    
    public double reqFrequency;
    int numRequests;
    JobGenerator jg;
    FIFOcomparator fc;
    SPJFcomparator sc;
    PQcomparator pq;
    ArrayList<Request> list,test;
    
    // Average Turnaround Times
    public double []averages = new double[26];
    // Maximum Turnaround Times
    public double []maxes = new double[25];
    
    /***************************************************
        Spooler class
        
        User enters:
        Time frame for arrivals (double)
        Number of requests over that time frame (integer).
    ***************************************************/
    public Spooler(){
        // Defualt constructor
    }
    // Spooler, uses job generator to create numReq number of job requests
    // and the parameter jobRate (our jobs per minute{1,2...,9}).
    public Spooler(int numReq, int jobRate){ 
        numRequests = numReq;
        reqFrequency = jobRate;
        jg = new JobGenerator();
        fc = new FIFOcomparator();
        sc = new SPJFcomparator();
        pq = new PQcomparator();
        // Create ArrayList using randomly generated jobs.
        list = jg.generate(numRequests, jobRate);
        // Sort list based on arrival time (defensive programming)
        Collections.sort(list, fc);
        
        // FIFO
        test = runFIFO(list);
        averages[0] = getAvgTT(test);
        
        // SPJF
        test = runSPJF(list);
        averages[1] = getAvgTT(test);
        maxes[0] = getMaxTT(test);
        
        // PAPQ Combinations
        test = runPAPQ(list,0,1);
        averages[2] = getAvgTT(test);
        maxes[1] = getMaxTT(test);
        
        test = runPAPQ(list,0,2);
        averages[3] = getAvgTT(test);
        maxes[2] = getMaxTT(test);
        
        test = runPAPQ(list,0,4);
        averages[4] = getAvgTT(test);
        maxes[3] = getMaxTT(test);
        
        test = runPAPQ(list,1,1);
        averages[5] = getAvgTT(test);
        maxes[4] = getMaxTT(test);
        
        test = runPAPQ(list,1,2);
        averages[6] = getAvgTT(test);
        maxes[5] = getMaxTT(test);

        test = runPAPQ(list,1,4);
        averages[7] = getAvgTT(test);
        maxes[6] = getMaxTT(test);
        
        test = runPAPQ(list,2,1);
        averages[8] = getAvgTT(test);
        maxes[7] = getMaxTT(test);
        
        test = runPAPQ(list,2,2);
        averages[9] = getAvgTT(test);
        maxes[8] = getMaxTT(test);
        
        test = runPAPQ(list,2,4);
        averages[10] = getAvgTT(test);
        maxes[9] = getMaxTT(test);
        
        test = runPAPQ(list,4,1);
        averages[11] = getAvgTT(test);
        maxes[10] = getMaxTT(test);
        
        test = runPAPQ(list,4,2);
        averages[12] = getAvgTT(test);
        maxes[11] = getMaxTT(test);
        
        test = runPAPQ(list,4,4);
        averages[13] = getAvgTT(test);
        maxes[12] = getMaxTT(test);
        
        // LBAQ Combinations
        test = runLBAQ(list,0,1);
        averages[14] = getAvgTT(test);
        maxes[13] = getMaxTT(test);
        
        test = runLBAQ(list,0,2);
        averages[15] = getAvgTT(test);
        maxes[14] = getMaxTT(test);
        
        test = runLBAQ(list,0,4);
        averages[16] = getAvgTT(test);
        maxes[15] = getMaxTT(test);
        
        test = runLBAQ(list,1,1);
        averages[17] = getAvgTT(test);
        maxes[16] = getMaxTT(test);
        
        test = runLBAQ(list,1,2);
        averages[18] = getAvgTT(test);
        maxes[17] = getMaxTT(test);
        
        test = runLBAQ(list,1,4);
        averages[19] = getAvgTT(test);
        maxes[18] = getMaxTT(test);
        
        test = runLBAQ(list,2,1);
        averages[20] = getAvgTT(test);
        maxes[19] = getMaxTT(test);
        
        test = runLBAQ(list,2,2);
        averages[21] = getAvgTT(test);
        maxes[20] = getMaxTT(test);
        
        test = runLBAQ(list,2,4);
        averages[22] = getAvgTT(test);
        maxes[21] = getMaxTT(test);
        
        test = runLBAQ(list,4,1);
        averages[23] = getAvgTT(test);
        maxes[22] = getMaxTT(test);
        
        test = runLBAQ(list,4,2);
        averages[24] = getAvgTT(test);
        maxes[23] = getMaxTT(test);
        
        test = runLBAQ(list,4,4);
        averages[25] = getAvgTT(test);
        maxes[24] = getMaxTT(test);
    }
    /*************************
        FIFO QUEUE SIMULATION
    *************************/
    ArrayList<Request> runFIFO(ArrayList<Request> li){
        ArrayList<Request> buffer = new ArrayList();
        ArrayList<Request> printList = new ArrayList(li.size());
        double clock = 0;
        Request r = li.get(0);
        int i = 0;
        while (i<li.size()){
            // Increment clock sufficiently to next job time
            if (clock < r.getArrival()){
                // Don't increment clock if items are in the buffer
                if (buffer.isEmpty()) clock += Math.abs(clock - 
                        (double)r.getArrival());
            }
            // While the clock is > arrival time of queue'd up requests
            // Add those requests to the buffer
            // Increment indexing
            while(r.getArrival()<=clock){
                buffer.add(r);
                i++;
                if (i == li.size())break;
                r = li.get(i);
            }
            Collections.sort(buffer, fc); // Not necessary
            // Remove the top item in the buffer
            // Increment clock by appropriate print time
            if (!buffer.isEmpty()){
                clock += (((double)(buffer.get(0).getPages()))*(2.0/3.0));
                buffer.get(0).setPrintTime(clock);
                printList.add(buffer.remove(0));
            }
        }
        if(!buffer.isEmpty()){
            Collections.sort(buffer, fc);
            while(!buffer.isEmpty()){
                clock += (((double)(buffer.get(0).getPages()))*(2.0/3.0));
                buffer.get(0).setPrintTime(clock);
                printList.add(buffer.remove(0));
            }
        }
        return printList;
    }
    /*************************
        SPJF QUEUE SIMULATION
    *************************/
    ArrayList<Request> runSPJF(ArrayList<Request> li){
        ArrayList<Request> buffer = new ArrayList();
        ArrayList<Request> printList = new ArrayList(li.size());
        double clock = 0;
        Request r = li.get(0);
        int i = 0;
        while (i<li.size()){
            // Increment clock sufficiently to next job time
            if (clock < r.getArrival()){
                // Don't increment clock if items are in the buffer
                if (buffer.isEmpty()) clock += Math.abs(clock - (double)r.getArrival());
            }
            // While the clock is > arrival time of queue'd up requests
            // Add those requests to the buffer
            // Increment indexing
            while(r.getArrival()<=clock){
                buffer.add(r);
                i++;
                if (i == li.size())break;
                r = li.get(i);
            }
            Collections.sort(buffer, sc); // sort by page #
            // Remove the top item in the buffer
            // Increment clock by appropriate print time
            if (!buffer.isEmpty()){
                clock += (((double)(buffer.get(0).getPages()))*(2.0/3.0));
                buffer.get(0).setPrintTime(clock);
                printList.add(buffer.remove(0));
            }
        }
        if(!buffer.isEmpty()){
            Collections.sort(buffer, sc);
            while(!buffer.isEmpty()){
                clock += (((double)(buffer.get(0).getPages()))*(2.0/3.0));
                buffer.get(0).setPrintTime(clock);
                printList.add(buffer.remove(0));
            }
        }
        return printList;
    }
    /*************************
        PAPQ QUEUE SIMULATION
    *************************/
    ArrayList<Request> runPAPQ(ArrayList<Request> li, double A, double B){
        ArrayList<Request> buffer = new ArrayList();
        ArrayList<Request> printList = new ArrayList(li.size());
        double clock = 0;
        Request r = li.get(0);
        int i = 0;
        while (i<li.size()){
            // Increment clock sufficiently to next job time
            if (clock < r.getArrival()){
                // Don't increment clock if items are in the buffer
                if (buffer.isEmpty()) clock += Math.abs(clock - 
                        (double)r.getArrival());
            }
            // While the clock is > arrival time of queue'd up requests
            // Add those requests to the buffer
            // Increment indexing
            while(r.getArrival()<=clock){
                buffer.add(r);
                r.setPriority(A + (B*(clock - r.getArrival())));//priority value
                i++; // increment index -- track of how many have been added
                if (i == li.size())break; // if all elements added, break loop
                r = li.get(i);
            }
            Collections.sort(buffer, pq); // sort by priority
            // Remove the top item in the buffer
            // Increment clock by appropriate print time
            if (!buffer.isEmpty()){
                clock += (((double)(buffer.get(0).getPages()))*(2.0/3.0));
                buffer.get(0).setPrintTime(clock);
                printList.add(buffer.remove(0));
            }
            for (Request t : buffer){
                t.setPriority((A + B*(clock - t.getArrival())));
            }
        }
        if(!buffer.isEmpty()){
            for (Request t : buffer){
                t.setPriority((A + B*(clock - t.getArrival())));
            }
            Collections.sort(buffer, pq);
            while(!buffer.isEmpty()){
                clock += (((buffer.get(0).getPages()))*(2.0/3.0));
                buffer.get(0).setPrintTime(clock);
                printList.add(buffer.remove(0));
            }
        }
        return printList;
    }
    /*************************
        LBAQ QUEUE SIMULATION
    *************************/
    ArrayList<Request> runLBAQ(ArrayList<Request> li, double A, double B){
        ArrayList<Request> buffer = new ArrayList();
        ArrayList<Request> printList = new ArrayList(li.size());
        double clock = 0;
        Request r = li.get(0);
        int i = 0;
        while (i<li.size()){
            // Increment clock sufficiently to next job time
            if (clock < r.getArrival()){
                // Don't increment clock if items are in the buffer
                if (buffer.isEmpty()) clock += Math.abs(clock - 
                        (double)r.getArrival());
            }
            // While the clock is > arrival time of queue'd up requests
            // Add those requests to the buffer
            // Increment indexing
            while(r.getArrival()<=clock){
                buffer.add(r);
                r.setPriority((A + (B*(double)(clock - r.getArrival())))/
                        r.getPages());// Setting priority value
                i++; // increment index -- keeping track of how many have been 
                    //  added
                if (i == li.size())break; // if all elements added, break loop
                r = li.get(i);
            }
            Collections.sort(buffer, pq); // sort by priority
            // Remove the top item in the buffer
            // Increment clock by appropriate print time
            if (!buffer.isEmpty()){
                clock += (((double)(buffer.get(0).getPages()))*(2.0/3.0));
                buffer.get(0).setPrintTime(clock);
                printList.add(buffer.remove(0));
            }
            for (Request t : buffer){
                t.setPriority((A + (B*(clock - t.getArrival())))/t.getPages());
                // Setting priority value
            }
        }
        if(!buffer.isEmpty()){
            for (Request t : buffer){
                t.setPriority((A + (B*(double)(clock - t.getArrival())))
                        /t.getPages());// Setting priority value
            }
            Collections.sort(buffer, pq); // sort by priority
            while(!buffer.isEmpty()){
                clock += (((double)(buffer.get(0).getPages()))*(2.0/3.0));
                buffer.get(0).setPrintTime(clock);
                printList.add(buffer.remove(0));
            }
        }
        return printList;
    }
    // Gets the Average Turnaround Time for a set of data from some Queue
    private double getAvgTT(ArrayList<Request> li){
        double tt = 0;
        for (Request r : li){
            tt += (r.getPrintTime() - r.getArrival());
        }
        tt = (tt / ((double)li.size()));
        return tt;
    }
    // Gets the Max Turnaround Time for a set of data from some Queue
    private double getMaxTT(ArrayList<Request> li){
        double max = 0;
        double temp;
        for (Request r : li){
            temp = (r.getPrintTime() - r.getArrival());
            if (temp >= max) max = temp;
        }
        return max;
    }
}
