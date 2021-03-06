/** a job that have many sub-jobs needed to be completed in order
  * @author Escanord Le */
import java.util.LinkedList;

public class CompoundJob extends Job
{
  /*********** FIELDS ************/
  // stores the sub-jobs of the compound job
  private LinkedList<Job> subJobs = new LinkedList<Job> ();
  
  /********* CONSTRUCTOR *********/
  /** creates the compound job with given profit and sub-jobs that need to be completed 
    * @param profit the money earned by completing this compound job
    * @param subJobs the array of sub-jobs */
  public CompoundJob(int profit, Job... subJobs)
  {
    super(subJobs[0].getId(), subJobs[0].getEarliestStart(), subJobs[subJobs.length - 1].getDeadline(), CompoundJob.calcDuration(subJobs), profit);
    
    /* compute the earliest start and deadline for the subJobs */
    // stores the duration of the compound job
    int duration = this.getDuration() - subJobs[0].getDuration();
    // stores the deadline of the sub-job
    int deadline;
    // stores the earliest start of the sub-job
    int earliestStart;
    
    deadline = this.getDeadline() - duration;
    subJobs[0] = new Job(subJobs[0].getId(), subJobs[0].getEarliestStart(), deadline, subJobs[0].getDuration(), subJobs[0].getProfit());
    for (int i = 1; i < subJobs.length - 1; ++i)
    {
      earliestStart = this.getEarliestStart() + this.getDuration() - duration;
      duration -= subJobs[i].getDuration();
      deadline = this.getDeadline() - duration;
      subJobs[i] = new Job(subJobs[i].getId(), earliestStart, deadline, subJobs[i].getDuration(), subJobs[i].getProfit());
    }
    earliestStart = this.getEarliestStart() + this.getDuration() - duration;
    subJobs[subJobs.length - 1] = new Job(subJobs[subJobs.length - 1].getId(), earliestStart, subJobs[subJobs.length - 1].getDeadline(), subJobs[subJobs.length - 1].getDuration(), subJobs[subJobs.length - 1].getProfit());
      
    /* add the sub-jobs to the linked list */
    for (Job job : subJobs)
    {
      this.subJobs.add(job);
    }
  }
  
  /***** NON-STATIC METHODS ******/
  /** returns the sub-jobs of the compound job
    * @return LinkedList of sub-jobs */
  public LinkedList<Job> getSubJobs()
  {
    return this.subJobs;
  }
  
  /******* STATIC METHODS ********/
  /** compute the duration of the compound job
    * @return the duration of the compound job*/
  private static int calcDuration(Job... subJobs)
  {
    // stores the duration of the compound job
    int duration = 0;
    
    for (Job job : subJobs)
    {
      duration += job.getDuration();
    }
    
    return duration;
  }
}