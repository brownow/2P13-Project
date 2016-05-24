package Tools;
/***************************************************************
 * Print Job Request Class
 * 
 * Holds all relevant information about a single print job.
 ***************************************************************/
public class Request extends Object {
    private double arrivalTime;
    private double printTime;
    private int pages;
    private double priority;
    
    /**************************
     * Default constructor.
     **************************/
    public Request(){
        arrivalTime = 0;
        printTime = 0;
        pages = 0;
        priority = 0;
    }
    
    /**************************
     * Two argument constructor.
     **************************/
    public Request(double arrive, int pageNum){
        arrivalTime = arrive;
        pages = pageNum;
        printTime = 0;
        priority = 0;
    }
    
    /*************************
     * Getters and setters.
     **************************/
    public double getArrival(){
        return arrivalTime;
    }
    public void setArrival(double i){
        arrivalTime = i;
    }
    public double getPrintTime(){
        return printTime;
    }
    public void setPrintTime(double i){
        printTime = i;
    }
    public int getPages(){
        return pages;
    }
    public void setPages(int i){
        pages = i;
    }
    public double getPriority(){
        return priority;
    }
    public void setPriority(double i){
        priority = i;
    }
}
