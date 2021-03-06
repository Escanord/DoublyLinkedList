/** attemps to place a job into a schedule with start time as early as possible 
  * @author Escanord Le */
import java.util.Iterator;
import java.util.ListIterator;

public class ScheduleAsEarlyAsPossible implements ScheduleMetric
{ 
  /** attemps to place a job into a schedule with start time as early as possible 
    * @param schedule a schedule in which a job could be placed 
    * @param job a job that needs to be placed into the schedule 
    * @return if a job can be placed into the schedule */
  @Override
  public boolean scheduleJob (Schedule schedule, Job job)
  {
    // stores if a job can be placed in a schedule
    boolean result = false;
    // stores the iterator of the schedule
    ListIterator<ScheduleSlot> iterator = (ListIterator<ScheduleSlot>)(schedule.iterator());
    // stores the time that the job can be started as early as possible
    int startTime = job.getEarliestStart();
    
    if (job instanceof CompoundJob)
    {
      // typecasts the job to be CompoundJob object
      CompoundJob compoundJob = (CompoundJob)(job);
      // stores the iterator of sub-jobs list
      ListIterator<Job> compoundJobIterator = (ListIterator<Job>)(compoundJob.getSubJobs().listIterator());
      
      while (compoundJobIterator.hasNext())
      {
        // stores if the subJob can be placed into the schedule
        boolean subResult = scheduleJob(schedule, compoundJobIterator.next());
        
        if (!subResult)
        {
          // stores the iterator of the schedule
          iterator = (ListIterator<ScheduleSlot>)(schedule.iterator());
          // stores the subJob of the compound that needs to be removed
          Job subJob = compoundJobIterator.previous();
          
          while (iterator.hasNext()) 
            iterator.next();
          while (iterator.hasPrevious())
          {
            if (subJob == iterator.previous().getJob())
            {
              iterator.remove();
              if (compoundJobIterator.hasPrevious()) 
                subJob = compoundJobIterator.previous();
            }
          }
          return false;
        }
      }
      result = true;
    }
    else 
    {
      if (!iterator.hasPrevious() && !iterator.hasNext())
      {
        // stores the new slot for the job
        ScheduleSlot slot = new ScheduleSlot(job, startTime);
        
        iterator.add(slot);
        result = true;
      }
      else while (!result && iterator.hasNext())
      {
        // stores the slot returning by previous() method
        ScheduleSlot slot = iterator.next();
      
        if (startTime + job.getDuration() <= slot.getStartTime()) 
          result = true;
        if (startTime + job.getDuration() > job.getDeadline()) 
          result = false;
        if (result)
        {
          // stores the new slot for the job
          slot = new ScheduleSlot(job, startTime);
          iterator.previous();
          iterator.add(slot);
        }
        else 
          startTime = Math.max(slot.getStartTime() + slot.getJob().getDuration(), job.getEarliestStart());
        if (!iterator.hasNext() && slot.getStartTime() + slot.getJob().getDuration() <= startTime && startTime + job.getDuration() <= job.getDeadline())
        {
          result = true;
          // stores the new slot for the job
          slot = new ScheduleSlot(job, startTime);
          iterator.add(slot);
        }
      }
    }
    return result;
  }
}