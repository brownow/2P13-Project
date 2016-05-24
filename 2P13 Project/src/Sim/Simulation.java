package Sim;
import Tools.*;
import java.util.Scanner;
import org.jfree.data.xy.*;
import org.jfree.chart.*;
import javax.swing.*;
import org.jfree.chart.plot.PlotOrientation;

/******************************************************************************
*   The Simulation!
*
* The simulation behaves as follows:
* 1) Prompt user for number of j jobs in one run
* 2) Prompt user for number of r runs in one simulation
* 3) Using these figures, generate r*j*9 appropriate Spooler objects
*   i) Spooler automatically runs all queues as part of it's creation, this is
*       to facilitate easy simulation
* 4) Using the data from the Spooler objects, create appropriate averages for
*       graph plotting
* 5) Plot graphs, and prompt user for additional tasks.
 *****************************************************************************/

/*************************************************
 * Simulation Class.
 *************************************************/
public final class Simulation {
        Scanner s = new Scanner(System.in);
        JFrame frame = new JFrame("Simulation");
        JPanel container = new JPanel();
        JScrollPane scrPane = new JScrollPane(container);
        
    /*************************************************
    * Simulation Constructor.
    *************************************************/    
    public Simulation() {
        int jobs;
        double runs;
        System.out.print("\t***SPOOLer Simulation***\n\n>");
        System.out.print("Enter number of jobs:\n>");
        jobs = s.nextInt();
        System.out.print("\nEnter number of runs:\n>");
        runs = s.nextInt();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        frame.setSize(1350, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        // Declaring Spooler Objects
        Spooler a = new Spooler();
        Spooler b = new Spooler();
        Spooler c = new Spooler();
        Spooler d = new Spooler();
        Spooler e = new Spooler();
        Spooler f = new Spooler();
        Spooler g = new Spooler();
        Spooler h = new Spooler();
        Spooler i = new Spooler();
        
        // Creating an array of Spooler objects to facilitate data access
        Spooler[] spools = {a,b,c,d,e,f,g,h,i};
        
        // Creating the array to hold all the points to be graphed
        double[][] points = new double[51][9];
        System.out.print("\nCrunching numbers...");
        
        // Create a new "run" of the Spooler at each rate
        for(int j=0; j<runs; j++){
            a = new Spooler(jobs,9);
            spools[0] = a;
            b = new Spooler(jobs,8);
            spools[1] = b;
            c = new Spooler(jobs,7);
            spools[2] = c;
            d = new Spooler(jobs,6);
            spools[3] = d;
            e = new Spooler(jobs,5);
            spools[4] = e;
            f = new Spooler(jobs,4);
            spools[5] = f;
            g = new Spooler(jobs,3);
            spools[6] = g;
            h = new Spooler(jobs,2);
            spools[7] = h;
            i = new Spooler(jobs,1);
            spools[8] = i;
            
            // Sum all averages for all queue types.
            for (int z=0; z<26; z++){
                points[z][0] +=spools[0].averages[z];
                points[z][1] +=spools[1].averages[z];
                points[z][2] +=spools[2].averages[z];
                points[z][3] +=spools[3].averages[z];
                points[z][4] +=spools[4].averages[z];
                points[z][5] +=spools[5].averages[z];
                points[z][6] +=spools[6].averages[z];
                points[z][7] +=spools[7].averages[z];
                points[z][8] +=spools[8].averages[z];
            }
            
            // Sum all max values for all queue types.
            for (int z=0; z<25; z++){
                points[z+26][0] +=spools[0].maxes[z];
                points[z+26][1] +=spools[1].maxes[z];
                points[z+26][2] +=spools[2].maxes[z];
                points[z+26][3] +=spools[3].maxes[z];
                points[z+26][4] +=spools[4].maxes[z];
                points[z+26][5] +=spools[5].maxes[z];
                points[z+26][6] +=spools[6].maxes[z];
                points[z+26][7] +=spools[7].maxes[z];
                points[z+26][8] +=spools[8].maxes[z];
            }  
        }
        
        // Divide sum by runs to get overall avg average
        for (int z=0; z<26; z++){
            points[z][0] = (points[z][0] / runs);
            points[z][1] = (points[z][1] / runs);
            points[z][2] = (points[z][2] / runs);
            points[z][3] = (points[z][3] / runs);
            points[z][4] = (points[z][4] / runs);
            points[z][5] = (points[z][5] / runs);
            points[z][6] = (points[z][6] / runs);
            points[z][7] = (points[z][7] / runs);
            points[z][8] = (points[z][8] / runs);
        }
        
        // Divide sum by runs to get overall max avg
        for (int y=0; y<=24; y++){
            points[y+26][0] = (points[y+26][0] / runs);
            points[y+26][1] = (points[y+26][1] / runs);
            points[y+26][2] = (points[y+26][2] / runs);
            points[y+26][3] = (points[y+26][3] / runs);
            points[y+26][4] = (points[y+26][4] / runs);
            points[y+26][5] = (points[y+26][5] / runs);
            points[y+26][6] = (points[y+26][6] / runs);
            points[y+26][7] = (points[y+26][7] / runs);
            points[y+26][8] = (points[y+26][8] / runs);
        }
        System.out.print("\nGraphing results...");
        
        // FIFO Graph creation
        XYSeries fifoaverage = new XYSeries("Average Turnaround Time");
        for (int z=0; z<9; z++){
        fifoaverage.add(spools[z].reqFrequency, points[0][z]);
        }
        XYDataset fifoData = new XYSeriesCollection(fifoaverage);
        JFreeChart fifoChart = ChartFactory.createXYLineChart("FIFO", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", fifoData, 
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel = new JPanel();
        ChartPanel cp = new ChartPanel(fifoChart);
        panel.add(cp);
        container.add(panel);
        
        // SPJF Graph creation
        XYSeries spjfAverage = new XYSeries("Average Turnaround Time");
        XYSeries spjfMax = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            spjfAverage.add(spools[z].reqFrequency, points[1][z]);
        }
        for (int z=0; z<9; z++){
            spjfMax.add(spools[z].reqFrequency, points[26][z]);
        }
        XYSeriesCollection spjfData = new XYSeriesCollection();
        spjfData.addSeries(spjfAverage);
        spjfData.addSeries(spjfMax);
        JFreeChart spjfChart = ChartFactory.createXYLineChart("SPJF", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", spjfData,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel1 = new JPanel();
        ChartPanel cp1 = new ChartPanel(spjfChart);
        panel.add(cp1);
        panel.validate();
        container.add(panel1);
        
         // PAPQ 0 1 Graph creation
        XYSeries papq01Average = new XYSeries("Average Turnaround Time");
        XYSeries papq01Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq01Average.add(spools[z].reqFrequency, points[2][z]);
        }
        for (int z=0; z<9; z++){
            papq01Max.add(spools[z].reqFrequency, points[27][z]);
        }
        XYSeriesCollection papq01Data = new XYSeriesCollection();
        papq01Data.addSeries(papq01Average);
        papq01Data.addSeries(papq01Max);
        JFreeChart papq01Chart = ChartFactory.createXYLineChart("PAPQ A=0,B=1", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq01Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel2 = new JPanel();
        ChartPanel cp2 = new ChartPanel(papq01Chart);
        panel.add(cp2);
        panel.validate();
        container.add(panel2);
        
         // PAPQ 0 2 Graph creation
        XYSeries papq02Average = new XYSeries("Average Turnaround Time");
        XYSeries papq02Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq02Average.add(spools[z].reqFrequency, points[3][z]);
        }
        for (int z=0; z<9; z++){
            papq02Max.add(spools[z].reqFrequency, points[28][z]);
        }
        XYSeriesCollection papq02Data = new XYSeriesCollection();
        papq02Data.addSeries(papq02Average);
        papq02Data.addSeries(papq02Max);
        JFreeChart papq02Chart = ChartFactory.createXYLineChart("PAPQ A=0,B=2", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq02Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel3 = new JPanel();
        ChartPanel cp3 = new ChartPanel(papq02Chart);
        panel.add(cp3);
        panel.validate();
        container.add(panel3);
        
        // PAPQ 0 4 Graph creation
        XYSeries papq04Average = new XYSeries("Average Turnaround Time");
        XYSeries papq04Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq04Average.add(spools[z].reqFrequency, points[4][z]);
        }
        for (int z=0; z<9; z++){
            papq04Max.add(spools[z].reqFrequency, points[29][z]);
        }
        XYSeriesCollection papq04Data = new XYSeriesCollection();
        papq04Data.addSeries(papq04Average);
        papq04Data.addSeries(papq04Max);
        JFreeChart papq04Chart = ChartFactory.createXYLineChart("PAPQ A=0,B=4", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq04Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel4 = new JPanel();
        ChartPanel cp4 = new ChartPanel(papq04Chart);
        panel.add(cp4);
        panel.validate();
        container.add(panel4);
        
        // PAPQ 1 1 Graph creation
        XYSeries papq11Average = new XYSeries("Average Turnaround Time");
        XYSeries papq11Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq11Average.add(spools[z].reqFrequency, points[5][z]);
        }
        for (int z=0; z<9; z++){
            papq11Max.add(spools[z].reqFrequency, points[30][z]);
        }
        XYSeriesCollection papq11Data = new XYSeriesCollection();
        papq11Data.addSeries(papq11Average);
        papq11Data.addSeries(papq11Max);
        JFreeChart papq11Chart = ChartFactory.createXYLineChart("PAPQ A=1,B=1", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq11Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel5 = new JPanel();
        ChartPanel cp5 = new ChartPanel(papq11Chart);
        panel.add(cp5);
        panel.validate();
        container.add(panel5);
        
        // PAPQ 1 2 Graph creation
        XYSeries papq12Average = new XYSeries("Average Turnaround Time");
        XYSeries papq12Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq12Average.add(spools[z].reqFrequency, points[6][z]);
        }
        for (int z=0; z<9; z++){
            papq12Max.add(spools[z].reqFrequency, points[31][z]);
        }
        XYSeriesCollection papq12Data = new XYSeriesCollection();
        papq12Data.addSeries(papq12Average);
        papq12Data.addSeries(papq12Max);
        JFreeChart papq12Chart = ChartFactory.createXYLineChart("PAPQ A=1,B=2", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq12Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel6 = new JPanel();
        ChartPanel cp6 = new ChartPanel(papq12Chart);
        panel.add(cp6);
        panel.validate();
        container.add(panel6);
        
        // PAPQ 1 4 Graph creation
        XYSeries papq14Average = new XYSeries("Average Turnaround Time");
        XYSeries papq14Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq14Average.add(spools[z].reqFrequency, points[7][z]);
        }
        for (int z=0; z<9; z++){
            papq14Max.add(spools[z].reqFrequency, points[32][z]);
        }
        XYSeriesCollection papq14Data = new XYSeriesCollection();
        papq14Data.addSeries(papq14Average);
        papq14Data.addSeries(papq14Max);
        JFreeChart papq14Chart = ChartFactory.createXYLineChart("PAPQ A=1,B=4", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq14Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel7 = new JPanel();
        ChartPanel cp7 = new ChartPanel(papq14Chart);
        panel.add(cp7);
        panel.validate();
        container.add(panel7);
        
        // PAPQ 2 1 Graph creation
        XYSeries papq21Average = new XYSeries("Average Turnaround Time");
        XYSeries papq21Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq21Average.add(spools[z].reqFrequency, points[8][z]);
        }
        for (int z=0; z<9; z++){
            papq21Max.add(spools[z].reqFrequency, points[33][z]);
        }
        XYSeriesCollection papq21Data = new XYSeriesCollection();
        papq21Data.addSeries(papq21Average);
        papq21Data.addSeries(papq21Max);
        JFreeChart papq21Chart = ChartFactory.createXYLineChart("PAPQ A=2,B=1", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq21Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel8 = new JPanel();
        ChartPanel cp8 = new ChartPanel(papq21Chart);
        panel.add(cp8);
        panel.validate();
        container.add(panel8);
        
        // PAPQ 2 2 Graph creation
        XYSeries papq22Average = new XYSeries("Average Turnaround Time");
        XYSeries papq22Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq22Average.add(spools[z].reqFrequency, points[9][z]);
        }
        for (int z=0; z<9; z++){
            papq22Max.add(spools[z].reqFrequency, points[34][z]);
        }
        XYSeriesCollection papq22Data = new XYSeriesCollection();
        papq22Data.addSeries(papq22Average);
        papq22Data.addSeries(papq22Max);
        JFreeChart papq22Chart = ChartFactory.createXYLineChart("PAPQ A=2,B=2", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq22Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel9 = new JPanel();
        ChartPanel cp9 = new ChartPanel(papq22Chart);
        panel.add(cp9);
        panel.validate();
        container.add(panel9);
        
        // PAPQ 2 4 Graph creation
        XYSeries papq24Average = new XYSeries("Average Turnaround Time");
        XYSeries papq24Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq24Average.add(spools[z].reqFrequency, points[10][z]);
        }
        for (int z=0; z<9; z++){
            papq24Max.add(spools[z].reqFrequency, points[35][z]);
        }
        XYSeriesCollection papq24Data = new XYSeriesCollection();
        papq24Data.addSeries(papq24Average);
        papq24Data.addSeries(papq24Max);
        JFreeChart papq24Chart = ChartFactory.createXYLineChart("PAPQ A=2,B=4", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq24Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel10 = new JPanel();
        ChartPanel cp10 = new ChartPanel(papq24Chart);
        panel.add(cp10);
        panel.validate();
        container.add(panel10);
        
        // PAPQ 4 1 Graph creation
        XYSeries papq41Average = new XYSeries("Average Turnaround Time");
        XYSeries papq41Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq41Average.add(spools[z].reqFrequency, points[11][z]);
        }
        for (int z=0; z<9; z++){
            papq41Max.add(spools[z].reqFrequency, points[36][z]);
        }
        XYSeriesCollection papq41Data = new XYSeriesCollection();
        papq41Data.addSeries(papq41Average);
        papq41Data.addSeries(papq41Max);
        JFreeChart papq41Chart = ChartFactory.createXYLineChart("PAPQ A=4,B=1", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq41Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel11 = new JPanel();
        ChartPanel cp11 = new ChartPanel(papq41Chart);
        panel.add(cp11);
        panel.validate();
        container.add(panel11);
        
        // PAPQ 4 2 Graph creation
        XYSeries papq42Average = new XYSeries("Average Turnaround Time");
        XYSeries papq42Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq42Average.add(spools[z].reqFrequency, points[12][z]);
        }
        for (int z=0; z<9; z++){
            papq42Max.add(spools[z].reqFrequency, points[37][z]);
        }
        XYSeriesCollection papq42Data = new XYSeriesCollection();
        papq42Data.addSeries(papq42Average);
        papq42Data.addSeries(papq42Max);
        JFreeChart papq42Chart = ChartFactory.createXYLineChart("PAPQ A=4,B=2", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq42Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel12 = new JPanel();
        ChartPanel cp12 = new ChartPanel(papq42Chart);
        panel.add(cp12);
        panel.validate();
        container.add(panel12);
        
        // PAPQ 4 4 Graph creation
        XYSeries papq44Average = new XYSeries("Average Turnaround Time");
        XYSeries papq44Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            papq44Average.add(spools[z].reqFrequency, points[13][z]);
        }
        for (int z=0; z<9; z++){
            papq44Max.add(spools[z].reqFrequency, points[38][z]);
        }
        XYSeriesCollection papq44Data = new XYSeriesCollection();
        papq44Data.addSeries(papq44Average);
        papq44Data.addSeries(papq44Max);
        JFreeChart papq44Chart = ChartFactory.createXYLineChart("PAPQ A=4,B=4", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", papq44Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel13 = new JPanel();
        ChartPanel cp13 = new ChartPanel(papq44Chart);
        panel.add(cp13);
        panel.validate();
        container.add(panel13);
        
         // LBAQ 0 1 Graph creation
        XYSeries lbaq01Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq01Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq01Average.add(spools[z].reqFrequency, points[14][z]);
        }
        for (int z=0; z<9; z++){
            lbaq01Max.add(spools[z].reqFrequency, points[39][z]);
        }
        XYSeriesCollection lbaq01Data = new XYSeriesCollection();
        lbaq01Data.addSeries(lbaq01Average);
        lbaq01Data.addSeries(lbaq01Max);
        JFreeChart lbaq01Chart = ChartFactory.createXYLineChart("LBAQ A=0,B=1", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq01Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel14 = new JPanel();
        ChartPanel cp14 = new ChartPanel(lbaq01Chart);
        panel.add(cp14);
        panel.validate();
        container.add(panel14);
        
         // LBAQ 0 2 Graph creation
        XYSeries lbaq02Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq02Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq02Average.add(spools[z].reqFrequency, points[15][z]);
        }
        for (int z=0; z<9; z++){
            lbaq02Max.add(spools[z].reqFrequency, points[40][z]);
        }
        XYSeriesCollection lbaq02Data = new XYSeriesCollection();
        lbaq02Data.addSeries(lbaq02Average);
        lbaq02Data.addSeries(lbaq02Max);
        JFreeChart lbaq02Chart = ChartFactory.createXYLineChart("LBAQ A=0,B=2", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq02Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel15 = new JPanel();
        ChartPanel cp15 = new ChartPanel(lbaq02Chart);
        panel.add(cp15);
        panel.validate();
        container.add(panel15);
        
        // LBAQ 0 4 Graph creation
        XYSeries lbaq04Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq04Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq04Average.add(spools[z].reqFrequency, points[16][z]);
        }
        for (int z=0; z<9; z++){
            lbaq04Max.add(spools[z].reqFrequency, points[40][z]);
        }
        XYSeriesCollection lbaq04Data = new XYSeriesCollection();
        lbaq04Data.addSeries(lbaq04Average);
        lbaq04Data.addSeries(lbaq04Max);
        JFreeChart lbaq04Chart = ChartFactory.createXYLineChart("LBAQ A=0,B=4", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq04Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel16 = new JPanel();
        ChartPanel cp16 = new ChartPanel(lbaq04Chart);
        panel.add(cp16);
        panel.validate();
        container.add(panel16);
        
        // LBAQ 1 1 Graph creation
        XYSeries lbaq11Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq11Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq11Average.add(spools[z].reqFrequency, points[17][z]);
        }
        for (int z=0; z<9; z++){
            lbaq11Max.add(spools[z].reqFrequency, points[42][z]);
        }
        XYSeriesCollection lbaq11Data = new XYSeriesCollection();
        lbaq11Data.addSeries(lbaq11Average);
        lbaq11Data.addSeries(lbaq11Max);
        JFreeChart lbaq11Chart = ChartFactory.createXYLineChart("LBAQ A=1,B=1", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq11Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel17 = new JPanel();
        ChartPanel cp17 = new ChartPanel(lbaq11Chart);
        panel.add(cp17);
        panel.validate();
        container.add(panel17);
        
        // LBAQ 1 2 Graph creation
        XYSeries lbaq12Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq12Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq12Average.add(spools[z].reqFrequency, points[18][z]);
        }
        for (int z=0; z<9; z++){
            lbaq12Max.add(spools[z].reqFrequency, points[43][z]);
        }
        XYSeriesCollection lbaq12Data = new XYSeriesCollection();
        lbaq12Data.addSeries(lbaq12Average);
        lbaq12Data.addSeries(lbaq12Max);
        JFreeChart lbaq12Chart = ChartFactory.createXYLineChart("LBAQ A=1,B=2", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq12Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel18 = new JPanel();
        ChartPanel cp18 = new ChartPanel(lbaq12Chart);
        panel.add(cp18);
        panel.validate();
        container.add(panel18);
        
        // LBAQ 1 4 Graph creation
        XYSeries lbaq14Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq14Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq14Average.add(spools[z].reqFrequency, points[19][z]);
        }
        for (int z=0; z<9; z++){
            lbaq14Max.add(spools[z].reqFrequency, points[44][z]);
        }
        XYSeriesCollection lbaq14Data = new XYSeriesCollection();
        lbaq14Data.addSeries(lbaq14Average);
        lbaq14Data.addSeries(lbaq14Max);
        JFreeChart lbaq14Chart = ChartFactory.createXYLineChart("LBAQ A=1,B=4", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq14Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel19 = new JPanel();
        ChartPanel cp19 = new ChartPanel(lbaq14Chart);
        panel.add(cp19);
        panel.validate();
        container.add(panel19);
        
        // LBAQ 2 1 Graph creation
        XYSeries lbaq21Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq21Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq21Average.add(spools[z].reqFrequency, points[20][z]);
        }
        for (int z=0; z<9; z++){
            lbaq21Max.add(spools[z].reqFrequency, points[45][z]);
        }
        XYSeriesCollection lbaq21Data = new XYSeriesCollection();
        lbaq21Data.addSeries(lbaq21Average);
        lbaq21Data.addSeries(lbaq21Max);
        JFreeChart lbaq21Chart = ChartFactory.createXYLineChart("LBAQ A=2,B=1", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq21Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel20 = new JPanel();
        ChartPanel cp20 = new ChartPanel(lbaq21Chart);
        panel.add(cp20);
        panel.validate();
        container.add(panel20);
        
        // LBAQ 2 2 Graph creation
        XYSeries lbaq22Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq22Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq22Average.add(spools[z].reqFrequency, points[21][z]);
        }
        for (int z=0; z<9; z++){
            lbaq22Max.add(spools[z].reqFrequency, points[46][z]);
        }
        XYSeriesCollection lbaq22Data = new XYSeriesCollection();
        lbaq22Data.addSeries(lbaq22Average);
        lbaq22Data.addSeries(lbaq22Max);
        JFreeChart lbaq22Chart = ChartFactory.createXYLineChart("LBAQ A=2,B=2", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq22Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel21 = new JPanel();
        ChartPanel cp21 = new ChartPanel(lbaq22Chart);
        panel.add(cp21);
        panel.validate();
        container.add(panel21);
        
        // LBAQ 2 4 Graph creation
        XYSeries lbaq24Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq24Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq24Average.add(spools[z].reqFrequency, points[22][z]);
        }
        for (int z=0; z<9; z++){
            lbaq24Max.add(spools[z].reqFrequency, points[47][z]);
        }
        XYSeriesCollection lbaq24Data = new XYSeriesCollection();
        lbaq24Data.addSeries(lbaq24Average);
        lbaq24Data.addSeries(lbaq24Max);
        JFreeChart lbaq24Chart = ChartFactory.createXYLineChart("LBAQ A=2,B=4", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq24Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel22 = new JPanel();
        ChartPanel cp22 = new ChartPanel(lbaq24Chart);
        panel.add(cp22);
        panel.validate();
        container.add(panel22);
        
        // LBAQ 4 1 Graph creation
        XYSeries lbaq41Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq41Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq41Average.add(spools[z].reqFrequency, points[23][z]);
        }
        for (int z=0; z<9; z++){
            lbaq41Max.add(spools[z].reqFrequency, points[48][z]);
        }
        XYSeriesCollection lbaq41Data = new XYSeriesCollection();
        lbaq41Data.addSeries(lbaq41Average);
        lbaq41Data.addSeries(lbaq41Max);
        JFreeChart lbaq41Chart = ChartFactory.createXYLineChart("LBAQ A=4,B=1", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq41Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel23 = new JPanel();
        ChartPanel cp23 = new ChartPanel(lbaq41Chart);
        panel.add(cp23);
        panel.validate();
        container.add(panel23);
        
        // LBAQ 4 2 Graph creation
        XYSeries lbaq42Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq42Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq42Average.add(spools[z].reqFrequency, points[24][z]);
        }
        for (int z=0; z<9; z++){
            lbaq42Max.add(spools[z].reqFrequency, points[49][z]);
        }
        XYSeriesCollection lbaq42Data = new XYSeriesCollection();
        lbaq42Data.addSeries(lbaq42Average);
        lbaq42Data.addSeries(lbaq42Max);
        JFreeChart lbaq42Chart = ChartFactory.createXYLineChart("LBAQ A=4,B=2", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq42Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel24 = new JPanel();
        ChartPanel cp24 = new ChartPanel(lbaq42Chart);
        panel.add(cp24);
        panel.validate();
        container.add(panel24);
        
        // LBAQ 4 4 Graph creation
        XYSeries lbaq44Average = new XYSeries("Average Turnaround Time");
        XYSeries lbaq44Max = new XYSeries("Max Turnaround Time");
        for (int z=0; z<9; z++){
            lbaq44Average.add(spools[z].reqFrequency, points[25][z]);
        }
        for (int z=0; z<9; z++){
            lbaq44Max.add(spools[z].reqFrequency, points[50][z]);
        }
        XYSeriesCollection lbaq44Data = new XYSeriesCollection();
        lbaq44Data.addSeries(lbaq44Average);
        lbaq44Data.addSeries(lbaq44Max);
        JFreeChart lbaq44Chart = ChartFactory.createXYLineChart("LBAQ A=4,B=4", 
                "Print Frequency (Pages per Minute)", 
                "Turnaround Time (Seconds)", lbaq44Data,
                PlotOrientation.VERTICAL, true, false, false);
        JPanel panel25 = new JPanel();
        ChartPanel cp25 = new ChartPanel(lbaq44Chart);
        panel.add(cp25);
        panel.validate();
        container.add(panel25);
        
        // Display to user
        frame.add(scrPane);
        frame.setVisible(true);
        System.out.print("\nDone!\n");
        
        // The following code is extra information for confirming graph accuracy
        // The user is prompted for this information at the end of the program.
        System.out.print("\nEnter 1 to output queue data."
                + "\nClose graphs to quit.\n>");
        int choice = s.nextInt();
        if (choice == 1){
            
            System.out.print("Queue Type\t\tPage Rate\t\tAverage\t\tMaximum\n");
            System.out.print("FIFO\n");
            int inc = 1;
            for (int n=8;n>=0;n--){
                System.out.printf("\t\t\t%d\t\t\t%.2f\t\t%s\n",
                        inc,points[0][n],"-");
                inc++;
            }
            System.out.print("SPJF\n");
            printData(points[1],points[26]);
            System.out.print("PAPQ\n");
            printData(points[2],points[27]);
            System.out.print("LBAQ\nA=0,B=1\n");
            printData(points[14],points[39]);
            System.out.print("LBAQ\nA=0,B=2\n");
            printData(points[15],points[40]);
            System.out.print("LBAQ\nA=0,B=4\n");
            printData(points[16],points[41]);
            System.out.print("LBAQ\nA=1,B=1\n");
            printData(points[17],points[42]);
            System.out.print("LBAQ\nA=1,B=2\n");
            printData(points[18],points[43]);
            System.out.print("LBAQ\nA=1,B=4\n");
            printData(points[19],points[44]);
            System.out.print("LBAQ\nA=2,B=1\n");
            printData(points[20],points[45]);
            System.out.print("LBAQ\nA=2,B=2\n");
            printData(points[21],points[46]);
            System.out.print("LBAQ\nA=2,B=4\n");
            printData(points[22],points[47]);
            System.out.print("LBAQ\nA=4,B=1\n");
            printData(points[23],points[48]);
            System.out.print("LBAQ\nA=4,B=2\n");
            printData(points[24],points[49]);
            System.out.print("LBAQ\nA=4,B=4\n");
            printData(points[25],points[50]);
        }
    }
    
    private static void printData(double[] avg, double[] max){
        int num = 1;
        for(int n = 8; n>=0; n--){
            System.out.printf("\t\t\t%d\t\t\t%.2f\t\t%.2f\n",num,avg[n],max[n]);
            num++;
        }
    }

    public static void main(String[] args) {new Simulation();}
}
