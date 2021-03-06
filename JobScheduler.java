/** A class that does the job scheduling
  * @author Escanord Le */
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.util.ListIterator;

public class JobScheduler
{
  /** returns the schedule consisting of jobs scheduled by the input schedule metric 
    * @param jobs a list of jobs that needs to be scheduled
    * @param comp a comparator sorting the job list
    * @param metric the schedule metric for the schedule 
    * @return the schedule of the input list of jobs */
  public static Schedule scheduleJobs(DoubleLinkedList<Job> jobs, Comparator<Job> comp, ScheduleMetric metric)
  {
    DoubleLinkedList.mergeSort(jobs, comp);
    // stores the result schedule
    Schedule schedule = new Schedule();
    
    for (Job job : jobs)
    {
      metric.scheduleJob(schedule, job);
    }
    
    return schedule;
  }
  
  /** reads data from the input file
    * @param sc the scanner of the input file 
    * @return the list of job from the data in the input file
    * @throws InsufficientDataException if there are not enough data to create a job */
  private static DoubleLinkedList<Job> read(Scanner sc) throws InsufficientDataException
  {
    // stores the result list of jobs
    DoubleLinkedList<Job> jobs = new DoubleLinkedList<Job> ();
    
    while (sc.hasNextInt())
    {
      // the iterator for the for loop
      int i = 0;
      // stores the data for one job
      int[] data = new int[5];
      
      for (; i < 5 && sc.hasNextInt(); ++i)
        data[i] = sc.nextInt();
      if (i < 4) 
        throw new InsufficientDataException();
      jobs.addToFront(new Job(data[0], data[1], data[2], data[3], data[4]));
    }
    
    return jobs;
  }
  
  /** starts the program and write out the schedule 
    * @param args input from the user 
    * @throws FileNotFoundException if the input file is not found*/
  public static void main(String[] args) throws FileNotFoundException
  {
    File input = new File(args[0]);
    // initializes the data's reader
    Scanner sc = new Scanner(input);
    // stores the list of job from input file
    DoubleLinkedList<Job> jobs = new DoubleLinkedList<Job> ();
    try
    {
      jobs = read(sc);
    }
    catch (InsufficientDataException e)
    {
      System.out.println("Input data is insufficient to create a complete job, please try again");
    }
    
    // stores the schedule that is as late as possible
    Schedule lateSchedule = scheduleJobs(jobs, Job.getProfitComparator(), new ScheduleAsLateAsPossible());
    // stores the schedule that is as early as possible
    Schedule earlySchedule = scheduleJobs(jobs, Job.getProfitComparator(), new ScheduleAsEarlyAsPossible());
    // stores the profit of late plan
    int latePlanProfit = 0;
    // stores the profit of early plan
    int earlyPlanProfit = 0;
    
    // creates a new file for result output
    File file = new File("JobScheduleResult.txt");
    
    try
    {
      // stores the writer toward the output file
      BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file));
      
      // stores the title for the schedule
      String title = "The late schedule plan is: ";
      outputWriter.write(title, 0, title.length());
      outputWriter.newLine();
      /* Goal of the loop: write the schedule to the output file
       * Goal of each iteration: write each job of the scheduled to the output file */
      for (ScheduleSlot slot : lateSchedule)
      {
        String s = "The job with ID:" + slot.getJob().getId() + " which would start at " + slot.getStartTime() + " has profit: " + slot.getJob().getProfit();
        outputWriter.write(s, 0, s.length());
        outputWriter.newLine();
        latePlanProfit += slot.getJob().getProfit();
      }
      // stores the total profit of the schedule
      String totalProfit = "The total profit for late schedule is: " + latePlanProfit;
      outputWriter.write(totalProfit, 0, totalProfit.length());
      outputWriter.newLine();
      
      title = "The early schedule plan is: ";
      outputWriter.write(title, 0, title.length());
      outputWriter.newLine();
      /* Goal of the loop: write the schedule to the output file
       * Goal of each iteration: write each job of the scheduled to the output file */
      for (ScheduleSlot slot : earlySchedule)
      {
        String s = "The job with ID:" + slot.getJob().getId() + " which would start at " + slot.getStartTime() + " has profit: " + slot.getJob().getProfit();
        outputWriter.write(s, 0, s.length());
        outputWriter.newLine();
        earlyPlanProfit += slot.getJob().getProfit();
      }
      totalProfit = "The total profit for early schedule is: " + earlyPlanProfit;
      outputWriter.write(totalProfit, 0, totalProfit.length());
      outputWriter.newLine();
      // stores which schedule has more profit
      String result;
      if (latePlanProfit > earlyPlanProfit) 
        result = "late schedule has more profit!";
      else if (latePlanProfit < earlyPlanProfit) 
        result = "early schedule has more profit!";
      else 
        result = "both schedules have the same profit!";
      outputWriter.write(result, 0, result.length());
      
      outputWriter.flush();
      outputWriter.close();
    }
    catch (IOException e)
    {
      System.out.println("Error writing to file!");
    }
  }
  
  /**
   * Creates a file with random job data, based on the parameters given.  The job data will be 
   * sorted by earliest start time.
   * @param numJobs   the number of jobs to create
   * @param maxStart  the latest time at which a job may set as its earliest start time
   * @param maxDuration  the maximum time that a job can take to complete
   * @param maxLag   the greatest time between the earliest start time for a job and the latest time that a job must start to meet its deadline
   * @param maxProfit  the maximum amount of profit from a job
   * @return an array containing the random jobs
   */
  public static Job[] createRandomJobs(int numJobs, int maxStart, int maxDuration, int maxLag, int maxProfit) {
    Job[] jobs = new Job[numJobs];
    
    // For each desired job, create 4 random numbers based on the parameters, use the numbers to create a job,
    // and store the job in an array.
    for (int i = 0; i < numJobs; i++) {
      int start = (int)(Math.random() * maxStart) + 1;
      int duration = (int)(Math.random() * maxDuration) + 1;
      int deadline = start + duration + (int)(Math.random() * (maxLag + 1));
      int profit = (int)(Math.random() * maxProfit) + 1;
      jobs[i] = new Job(i, start, deadline, duration, profit); 
    }

    // Sort the jobs by earliest start time
    Arrays.sort(jobs, Job.getStartComparator());
  
    return jobs;
  }
  
  /**
   * Creates a file with job data.
   * @param fileName  the name of the file to store the job data.  The file must not exist.
   * @param jobs an array containing the jobs
   */
  public static void createJobFile(String fileName, Job[] jobs) {
    // Check that the output file does not already exist
    File file = new File(fileName);
    if (file.exists()) {
      System.out.println("Error: file " + fileName + " already exists.");
      return;
    }
    
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
 
      // For each job in the array, write the job to the file as 5 numbers separated by spaces.
      // Place an each job on a new line.
      for (int i = 0; i < jobs.length; i++) {
        String s = i + " " + jobs[i].getEarliestStart() + " " + jobs[i].getDeadline() + " " + jobs[i].getDuration() + " " + jobs[i].getProfit();
        writer.write(s, 0, s.length());
        writer.newLine();
      }
      writer.flush();
      writer.close();
    }
    catch (IOException e) {
      System.out.println("Error writing to file " + fileName);
    }
  }
  
  /** returns the schedule consisting of  scheduled jobs
    * @param jobs a list of jobs that needs to be scheduled
    * @return the maximize profit that can get */
  public static int extraScheduleJobs (DoubleLinkedList<Job> jobs)
  {
    // sorts the list
    DoubleLinkedList.mergeSort(jobs, Job.getDeadlineComparator());
    // stores the result schedule
    Schedule schedule = new Schedule();
    // stores the iterator of the list of jobs
    ListIterator<Job> iterator =(ListIterator<Job>)(jobs.iterator());
    // stores the max deadline in the list
    int maxDeadline = 0;
    while (iterator.hasNext())
    {
      Job job = iterator.next();
      maxDeadline = Math.max(maxDeadline, job.getDeadline());
    }
    iterator =(ListIterator<Job>)(jobs.iterator());
    ++maxDeadline;
    
    // stores the dynamic programming matrix
    int[] f = new int[maxDeadline + 1];
    int[] g = new int[maxDeadline + 1];
    for (int i = 0; i <= maxDeadline - 1; ++i)
    {
      f[i] = 0;
      g[i] = 0;
    }
    
    while (iterator.hasNext())
    {
      // stores the current job in the list
      Job job = iterator.next();
      /* Goal of the loop: computes f matrix 
       * Goal of each iteration: computes f[i]*/
      for (int i = job.getEarliestStart() + job.getDuration(); i <= job.getDeadline(); ++i)
        f[i] = Math.max(f[i], g[i - job.getDuration()] + job.getProfit());
      /* Goal of the loop: updates the g matrix
       * Goal of each iteration: updates the g[i] */
      for (int i = 0; i <= maxDeadline - 1; ++i)
      {
        g[i] = Math.max(g[i], f[i]);
        if (i - 1 >= 0) 
          g[i] = Math.max(g[i], g[i - 1]);
      }
    }
    
    return g[maxDeadline - 1];
  }
  
  /** indicates not enough data to creates a new job object 
    * @author Escanord Le */
  private static class InsufficientDataException extends Exception
  {
  }
}