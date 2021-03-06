/** a slot in a schedule for job
  * @author Escanord Le */
public class ScheduleSlot
{
  /*********** FIELDS ************/
  // stores the job
  private Job job;
  
  // stores the start time of the job
  private int startTime;
  
  /********* CONSTRUCTOR *********/
  /** creates a schedule slot for the job 
    * @param job the job for the slot
    * @param startTime the time that the job will be started */
  public ScheduleSlot (Job job, int startTime)
  {
    this.job = job;
    this.startTime = startTime;
  }
  
  /***** NON-STATIC METHODS ******/
  /** returns the job of the slot
    * @return the job of the slot */
  public Job getJob()
  {
    return this.job;
  }
  
  /** returns the time that the job will be started
    * @return the time that the job will be started */
  public int getStartTime()
  {
    return this.startTime;
  }
}