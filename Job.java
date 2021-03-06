/** Creates a Job storing information about id, time, profit, etc of any specific job
  * @author Escanord Le */
import java.util.Comparator;
public class Job implements Comparable<Job>
{
  /*********** FIELDS ************/
  // stores the unique value that identifies the job
  private int id;
  
  // stores the earliest time that the job can be started
  private int earliestStart;
  
  // stores the latest time at which the job must be completed
  private int deadline;
  
  // stores the time needed to complete the job
  private int duration;
  
  // stores the amount of profit can be earned if the job is completed
  private int profit;
  
  /********* CONSTRUCTOR *********/
  /** Creates a job with its information
    * @param id            the unique value that identifies the job
    * @param earliestStart the earliest time that the job can be started
    * @param deadline      the latest time at which the job must be completed
    * @param duration      the time needed to complete the job
    * @param profit        the amount of profit can be earned if the job is completed */
  public Job (int id, int earliestStart, int deadline, int duration, int profit)
  {
    this.id = id;
    this.earliestStart = earliestStart;
    this.deadline = deadline;
    this.duration = duration;
    this.profit = profit;
  }
  
  /***** NON-STATIC METHODS ******/
  /** returns the id of the job
    * @return the id of the job */
  public int getId()
  {
    return this.id;
  }
  
  /** returns the earliest time that job can be started
    * @return the the earliest time that job can be started */
  public int getEarliestStart()
  {
    return this.earliestStart;
  }
  
  /** returns the deadline of the job
    * @return the deadline of the job */
  public int getDeadline()
  {
    return this.deadline;
  }
  
  /** returns the duration of the job
    * @return the duration of the job */
  public int getDuration()
  {
    return this.duration;
  }
  
  /** returns the profit of the job
    * @return the profit of the job */
  public int getProfit()
  {
    return this.profit;
  }
  
  /** compares if the input object equals to this job
    * @param another the object that need to be compared
    * @return <p> <code> true </code>  if the input and this job equal 
    *         <p> <code> false </code> if the input object is not job object or the input and this job does not equal */
  @Override
  public boolean equals(Object another)
  {
    if (another instanceof Job)
    {
      // typecasts another to job object
      Job anotherJob = (Job)(another);
      
      return this.getId() == anotherJob.getId();
    }
    return false;
  }
  
  /** compares the input job with this job
    * @param anotherJob the job that is compared with this job
    * @return a negative integer, zero or a positive integer if the input job has id less than, equal, or larger than the id of this job */
  @Override
  public int compareTo(Job anotherJob)
  {
    return this.getId() - anotherJob.getId();
  }
  
  /** returns the comparator by earliest starting time of Job object
    * @return the comparator by earliest starting time of Job object */
  public static Comparator<Job> getStartComparator()
  {
    return new StartComparator();
  }
  
  /** returns the comparator by profit of Job object
    * @return the comparator by profit of Job object */
  public static Comparator<Job> getProfitComparator()
  {
    return new ProfitComparator();
  }
  
  /** returns the comparator by deadline of Job object
    * @return the comparator by deadline of Job object */
  public static Comparator<Job> getDeadlineComparator()
  {
    return new DeadlineComparator();
  }
  
  /** a class implements Comparator interface that compares two Job objects by their earliest start times
    * @author Escanord Le */
  private static class StartComparator implements Comparator<Job>
  {
    /** compares two input Job objects
      * @param job1 the first job to be compared
      * @param job2 the second job to be compared
      * @return a negative integer, zero, or a positive integer if the first job has the earliest start less than, equal, or larger than that of the second job */
    @Override
    public int compare(Job job1, Job job2)
    {
      return job1.getEarliestStart() - job2.getEarliestStart();
    }
  }
  
  /** a class implements Comparator interface that compares two Job objects by their profits
    * @author Escanord Le */
  private static class ProfitComparator implements Comparator<Job>
  {
    /** compares two input Job objects
      * @param job1 the first job to be compared
      * @param job2 the second job to be compared
      * @return a positive integer, zero, or a negative integer if the first job has the profit less than, equal, or larger than that of the second job */
    @Override
    public int compare(Job job1, Job job2)
    {
      return job2.getProfit() - job1.getProfit();
    }
  }
  
  /** a class implements Comparator interface that compares two Job objects by their deadlines
    * @author Escanord Le */
  private static class DeadlineComparator implements Comparator<Job>
  {
    /** compares two input Job objects
      * @param job1 the first job to be compared
      * @param job2 the second job to be compared
      * @return a positive integer, zero, or a negative integer if the first job has the deadline larger, equal, or smaller than that of the second job */
    @Override
    public int compare(Job job1, Job job2)
    {
      return job1.getDeadline() - job2.getDeadline();
    }
  }
}