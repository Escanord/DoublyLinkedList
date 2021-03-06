/** class schedules a job into a current schedule
  * @author Escanord Le */
public interface ScheduleMetric
{
  /** check if it is possible to insert a job into current schedule, and if it is, place a job into appropriate slot of the schedule
    * @param schedule a schedule in which a job could be placed 
    * @param job a job that needs to be placed into the schedule 
    * @return true if we can schedule input job into input schedule, false otherwise*/
  boolean scheduleJob (Schedule schedule, Job job);
}