/** attemps to place a job into a schedule with start time as late as possible 
  * @author Escanord Le */
import java.util.Iterator;
import java.util.ListIterator;

public class ScheduleAsLateAsPossible implements ScheduleMetric
{ 
  /** attemps to place a job into a schedule with start time as late as possible 
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
    // stores the time that the job can be completed as late as possible
    int completeTime = job.getDeadline();
    while (iterator.hasNext()) 
      iterator.next();
    
    if (job instanceof CompoundJob)
    {
      // typecasts the job to be CompoundJob object
      CompoundJob compoundJob = (CompoundJob)(job);
      // stores the iterator of sub-jobs list
      ListIterator<Job> compoundJobIterator = (ListIterator<Job>)(compoundJob.getSubJobs().listIterator());
      
      while (compoundJobIterator.hasNext()) 
        compoundJobIterator.next();
      while (compoundJobIterator.hasPrevious())
      {
        // stores if the subJob can be placed into the schedule
        boolean subResult = scheduleJob(schedule, compoundJobIterator.previous());
        
        if (!subResult)
        {
          // stores the iterator of the schedule
          iterator = (ListIterator<ScheduleSlot>)(schedule.iterator());
          // stores the subJob of the compound that needs to be removed
          Job subJob = compoundJobIterator.next();
          
          while (iterator.hasNext())
          {
            if (subJob == iterator.next().getJob())
            {
              iterator.remove();
              if (compoundJobIterator.hasNext()) 
                subJob = compoundJobIterator.next();
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
        ScheduleSlot slot = new ScheduleSlot(job, completeTime - job.getDuration());
        
        iterator.add(slot);
        result = true;
      }
      else while (!result && iterator.hasPrevious())
      {
        // stores the slot returning by previous() method
        ScheduleSlot slot = iterator.previous();
      
        if (completeTime - job.getDuration() >= slot.getStartTime() + slot.getJob().getDuration()) 
          result = true;
        if (completeTime - job.getDuration() < job.getEarliestStart()) 
          result = false;
        if (result)
        {
          // stores the new slot for the job
          slot = new ScheduleSlot(job, completeTime - job.getDuration());
          iterator.next();
          iterator.add(slot);
        }
        else 
          completeTime = Math.min(slot.getStartTime(), job.getDeadline());
        if (!iterator.hasPrevious() && completeTime - job.getDuration() >= job.getEarliestStart() && completeTime <= job.getDeadline())
        {
          result = true;
          // stores the new slot for the job
          slot = new ScheduleSlot(job, completeTime - job.getDuration());
          iterator.add(slot);
        }
      }
    }
    return result;
  }
}