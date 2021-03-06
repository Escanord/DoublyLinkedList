/** Test for methods in DLLIterator class
  * @author Escanord Le */

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.*;

public class Project4Tester
{
  /** Test DLLIterator class */
  @Test
  public void testDLLIterator()
  {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    ListIterator<Integer> iterator =(ListIterator<Integer>)(list.iterator());
    
    /* test add method */
    iterator.add(6);
    assertEquals("test empty list and add method", new Integer(6), list.getFront().getElement());
    assertEquals("test empty list and add method", new Integer(6), list.getBack().getElement());
    // add to front
    for (int i = 1; i <= 5; ++i)
      iterator.add(i);
    iterator = (ListIterator<Integer>)(list.iterator());
    iterator.next();
    iterator.add(15);
    assertEquals("test add method", new Integer(2), iterator.next());
    iterator.previous();
    assertEquals("test add method", new Integer(15), iterator.previous());
    // add to back
    while (iterator.hasNext())iterator.next();
    iterator.add(10);
    assertEquals("test add to back of the list", new Integer(10), list.getBack().getElement());
    iterator.previous();
    iterator.previous();
    // add to middle
    iterator.add(99);
    assertEquals("test add to middle of the list", new Integer(99), list.getBack().getPrevious().getPrevious().getElement());
    
    // test hasNext method
    assertTrue("Test hasNext method returning true", iterator.hasNext());
    while (iterator.hasNext())iterator.next();
    assertFalse("Test hasNext method returning false", iterator.hasNext());
    
    // test hasPrevios method
    iterator =(ListIterator<Integer>)(list.iterator());
    assertFalse("Test hasPrevious method returning false", iterator.hasPrevious());
    iterator.next();
    assertTrue("Test hasPrevious method returning true", iterator.hasPrevious());
    
    // test previous method
    iterator = (ListIterator<Integer>)(list.iterator());
    boolean result = false;
    try {
      iterator.previous();
    }
    catch (java.util.NoSuchElementException e) {
      /** good good good */
      result = true;
    }
    catch (Exception e) {
      result =false;
    }
    assertTrue("test previous method throwing exception", result);
    iterator.next();
    assertEquals("test previous method", new Integer(1), iterator.previous());
    
    // test next method
    assertEquals("test next method", new Integer(1), iterator.next());
    for (int i = 1; i <= 6; ++i)
      iterator.next();
    iterator = (ListIterator<Integer>)(list.iterator());
    result = false;
    try {
      iterator.previous();
    }
    catch (java.util.NoSuchElementException e) {
      /** good good good */
      result = true;
    }
    catch (Exception e) {
      result =false;
    }
    assertTrue("test next method throwing exception", result);
    
    // test remove method
      list = new DoubleLinkedList<Integer>();
      for (int i = 5; i > 0; i--)
        list.addToFront(i);
    
      /* testing removing the first element through the iterator after calling next() and previous() */
      ListIterator<Integer> listIterator =(ListIterator<Integer>)(list.iterator());
      listIterator.next();
      listIterator.remove();
      listIterator.next();
      listIterator.previous();
      listIterator.remove();
    
      /* the list should now be 2 through 5 */
      int i = 3;
      for (Integer x: list)
        assertEquals("The iterator failed when removing the first element", new Integer(i++), x);
      if (i != 6)
        fail("The iterator failed when removing the first element");
      listIterator = (ListIterator<Integer>)(list.iterator());
      listIterator.add(2);
    
      /* testing removing element 3 */
      listIterator = (ListIterator<Integer>)(list.iterator());
      listIterator.next();
      listIterator.next();
      listIterator.remove();
    
      DLNode<Integer> head = list.getFront();
      DLNode<Integer> tail = list.getBack();
    
      assertEquals("Iterator failed to remove middle element", new Integer(2), head.getElement());
      assertEquals("Iterator failed to remove middle element", new Integer(4), head.getNext().getElement());
      assertEquals("Iterator failed to remove middle element", new Integer(5), head.getNext().getNext().getElement());
    
      assertEquals("Iterator failed to remove middle element", new Integer(5), tail.getElement());
      assertEquals("Iterator failed to remove middle element", new Integer(4), tail.getPrevious().getElement());
      assertEquals("Iterator failed to remove middle element", new Integer(2), tail.getPrevious().getPrevious().getElement());
    
      /* testing removing the last element after calling next() and previous() */
      while (listIterator.hasNext())
        listIterator.next();
      listIterator.remove();
    
      head = list.getFront();
      tail = list.getBack();
    
      assertEquals("Iterator failed to remove middle element", new Integer(2), head.getElement());
      assertEquals("Iterator failed to remove middle element", new Integer(4), head.getNext().getElement());
    
      assertEquals("Iterator failed to remove middle element", new Integer(4), tail.getElement());
      assertEquals("Iterator failed to remove middle element", new Integer(2), tail.getPrevious().getElement());
      
      listIterator = (ListIterator<Integer>)(list.iterator());
      while (listIterator.hasNext())
        listIterator.next();
      listIterator.add(5);
      listIterator.previous();
      listIterator.remove();
      
      assertEquals("Iterator failed to remove middle element", new Integer(2), head.getElement());
      assertEquals("Iterator failed to remove middle element", new Integer(4), head.getNext().getElement());
    
      assertEquals("Iterator failed to remove middle element", new Integer(4), tail.getElement());
      assertEquals("Iterator failed to remove middle element", new Integer(2), tail.getPrevious().getElement());
    
      /* testing removing before calling next or previous */
      listIterator = (ListIterator<Integer>)(list.iterator());
      try {
        listIterator.remove();
        fail("Calling iterator's remove() before calling next() should throw an IllegalStateException");
      }
      catch (IllegalStateException e) {
        // all is good
      }
      catch (Exception e) {
        fail("The wrong exception thrown by the iterator remove() method.");
      }
      /* testing removing after calling add */
      listIterator.next();
      listIterator.add(13);
      try {
        listIterator.remove();
        fail("Calling iterator's remove() after calling add method should throw an IllegalStateException");
      }
      catch (IllegalStateException e) {
        // all is good
      }
      catch (Exception e) {
        fail("The wrong exception thrown by the iterator remove() method.");
      }
  }
  
  /** Test Job class */
  @Test
  public void testJob()
  {
    // test constructor and getter methods
    Job job = new Job(1, 1, 4, 2, 10);
    assertEquals("Test constructor and getId method", 1, job.getId());
    assertEquals("Test constructor and getEarliestStart method", 1, job.getEarliestStart());
    assertEquals("Test constructor and getDeadline method", 4, job.getDeadline());
    assertEquals("Test constructor and getDuration method", 2, job.getDuration());
    assertEquals("Test constructor and getProfit method", 10, job.getProfit());
    
    //test equals method
    Job job1 = new Job(1, 1, 4, 2, 10);
    assertTrue("Test equals method returning true", job.equals(job1));
    job1 = new Job(2, 3, 40, 5, 60);
    assertFalse("Test equals method returning false", job.equals(job1));
    
    // test getStartComparator method
    assertTrue("Test getStartComparator method", job.getStartComparator() instanceof Comparator);
    
    // test getProfitComparator method
    assertTrue("Test getProfitComparator method", job.getProfitComparator() instanceof Comparator);
    
    // test compareTo method
    job1 = new Job(1, 1, 1, 1, 1);
    Job job2 = new Job(2, 2, 1, 1, 1);
    Job job3 = new Job(3, 5, 1, 1, 1);
    Job job4 = new Job(4, 7, 1, 1, 1);
    assertTrue("Test compareTo method", job1.compareTo(job2) < 0);
    assertTrue("Test compareTo method", job4.compareTo(job3) > 0);
    assertTrue("Test compareTo method", job1.compareTo(job1) == 0);
  }
  
  /** test StartComparator class */
  @Test
  public void testStartComparator()
  {
    // test compare method
    Job job1 = new Job(1, 1, 1, 1, 1);
    Job job2 = new Job(2, 2, 1, 1, 1);
    Job job3 = new Job(3, 5, 1, 1, 1);
    Job job4 = new Job(4, 7, 1, 1, 1);
    Job[] jobArray = new Job[] {job2, job1, job4, job3};
    Arrays.sort(jobArray, job1.getStartComparator());
    for (int i = 0; i < jobArray.length - 1; ++i)
      if (jobArray[i].getEarliestStart() > jobArray[i+1].getEarliestStart()) fail("Testing compare method by earliest start time fails");
  }
  
  /** test ProfitComparator class */
  @Test
  public void testProfitComparator()
  {
    // test compare method
    Job job1 = new Job(1, 1, 1, 1, 7);
    Job job2 = new Job(2, 2, 1, 1, 1);
    Job job3 = new Job(3, 5, 1, 1, 5);
    Job job4 = new Job(4, 7, 1, 1, 4);
    Job[] jobArray = new Job[] {job2, job1, job4, job3};
    Arrays.sort(jobArray, job1.getProfitComparator());
    for (int i = 0; i < jobArray.length - 1; ++i)
      if (jobArray[i].getProfit() < jobArray[i+1].getProfit()) fail("Testing compare method by profit fails");
  }
  
  /** test DeadlineComparator class */
  @Test
  public void testDeadlineComparator()
  {
    // test compare method
    Job job1 = new Job(1, 1, 7, 1, 1);
    Job job2 = new Job(2, 2, 10, 1, 1);
    Job job3 = new Job(3, 5, 3, 1, 1);
    Job job4 = new Job(4, 7, 9, 1, 1);
    Job[] jobArray = new Job[] {job2, job1, job4, job3};
    Arrays.sort(jobArray, job1.getDeadlineComparator());
    for (int i = 0; i < jobArray.length - 1; ++i)
      if (jobArray[i].getDeadline() > jobArray[i+1].getDeadline()) fail("Testing compare method by earliest start time fails");
  }
  
  /** test CompoundJob class */
  @Test
  public void testCompoundJob()
  {
    // test constructors and getter methods
    Job job1 = new Job(1, 1, 0, 6, 0);
    Job job2 = new Job(1, 0, 0, 3, 0);
    Job job3 = new Job(1, 0, 25, 8, 0);
    CompoundJob job = new CompoundJob(10, job1, job2, job3);
    LinkedList<Job> list = job.getSubJobs();
    assertTrue("Test getSubJobs method", list.get(0).getDuration() == job1.getDuration() && list.get(0).getEarliestStart() == job1.getEarliestStart());
    assertTrue("Test getSubJobs method", list.get(1).getDuration() == job2.getDuration());
    assertTrue("Test getSubJobs method", list.get(2).getDuration() == job3.getDuration() && list.get(2).getDeadline() == job3.getDeadline());
    assertTrue("Test getDuration() method of compound job", job.getDuration() == (job1.getDuration() + job2.getDuration() + job3.getDuration()));
    assertTrue("Test getProfit() method", job.getProfit() == 10);
    assertTrue("Test getEarliestStart() method", job.getEarliestStart() == job1.getEarliestStart());
    assertTrue("Test getDeadline() method", job.getDeadline() == job3.getDeadline());
  }
  
  /** Test ScheduleSlot class */
  @Test
  public void testScheduleSlot()
  {
    // test constructor and getter methods
    Job job = new Job(1, 1, 8, 6, 10);
    ScheduleSlot slot = new ScheduleSlot(job, 2);
    assertTrue("Test getJob() method", job == slot.getJob());
    assertEquals("Test getStartTime() method", slot.getStartTime(), 2);
  }
  
  /** Test ScheduleAsLateAsPossible class */
  @Test
  public void testScheduleAsLateAsPossible()
  {
    /* test scheduleJob method */
    Schedule schedule = new Schedule();
    Job job1 = new Job(1, 1, 8, 6, 10);
    Job job2 = new Job(2, 7, 17, 3, 10);
    Job job3 = new Job(3, 10, 25, 8, 10);
    ScheduleAsLateAsPossible place = new ScheduleAsLateAsPossible();
    // add to empty list
    place.scheduleJob(schedule, job3);
    assertEquals("test add to empty list", schedule.getFront().getElement().getJob(), job3);
    // add to front
    place.scheduleJob(schedule, job1);
    assertEquals("test add to front of the list", schedule.getFront().getElement().getJob(), job1);
    // add to middle
    place.scheduleJob(schedule, job2);
    assertEquals("test add to middle of the list", schedule.getFront().getNext().getElement().getJob(), job2);
    // add compound job
    Job job5 = new Job(5, 1, 0, 1, 0);
    Job job4 = new Job(4, 0, 14, 6, 0);
    CompoundJob compoundJob = new CompoundJob(10, job5, job4);
    place.scheduleJob(schedule, compoundJob);
    assertEquals("test add compound job to the list", schedule.getFront().getElement().getJob(), job5);
    assertEquals("test add compound job to the list", schedule.getFront().getNext().getNext().getElement().getJob(), job4);
    // test cannot add job
    Job job6 = new Job(6, 18, 26, 4, 20);
    assertFalse("test cannot add job into schedule", place.scheduleJob(schedule, job6));
  }
  
  /** Test ScheduleAsEarlyAsPossible class */
  @Test
  public void testScheduleAsEarlyAsPossible()
  {
    /* test scheduleJob method */
    Schedule schedule = new Schedule();
    Job job1 = new Job(1, 1, 8, 6, 10);
    Job job2 = new Job(2, 7, 17, 3, 10);
    Job job3 = new Job(3, 10, 25, 8, 10);
    ScheduleAsEarlyAsPossible place = new ScheduleAsEarlyAsPossible();
    // add to empty list
    place.scheduleJob(schedule, job3);
    assertEquals("test add to empty list", schedule.getFront().getElement().getJob(), job3);
    // add to front
    place.scheduleJob(schedule, job1);
    assertEquals("test add to front of the list", schedule.getFront().getElement().getJob(), job1);
    // add to middle
    place.scheduleJob(schedule, job2);
    assertEquals("test add to middle of the list", schedule.getFront().getNext().getElement().getJob(), job2);
    // add compound job
    Job job5 = new Job(5, 10, 0, 5, 0);
    Job job4 = new Job(4, 0, 30, 7, 0);
    CompoundJob compoundJob = new CompoundJob(10, job5, job4);
    place.scheduleJob(schedule, compoundJob);
    assertEquals("test add compound job to the list", schedule.getBack().getElement().getJob(), job4);
    assertEquals("test add compound job to the list", schedule.getBack().getPrevious().getElement().getJob(), job5);
    // test cannot add job
    Job job6 = new Job(6, 18, 26, 4, 20);
    assertFalse("test cannot add job into schedule", place.scheduleJob(schedule, job6));
  }
  
  /** Test JobScheduler class */
  @Test
  public void testJobScheduler()
  {
    JobScheduler scheduler = new JobScheduler();
    // test scheduleJobs method
    DoubleLinkedList<Job> list = new DoubleLinkedList<Job> ();
    Job job1 = new Job(1, 1, 8, 6, 10);
    Job job2 = new Job(2, 7, 17, 3, 8);
    Job job3 = new Job(3, 10, 25, 8, 14);
    list.addToFront(job1);
    list.addToFront(job2);
    list.addToFront(job3);
    Job job5 = new Job(5, 1, 0, 1, 0);
    Job job4 = new Job(4, 0, 14, 6, 0);
    CompoundJob compoundJob = new CompoundJob(7, job5, job4);
    list.addToFront(compoundJob);
    Schedule schedule = scheduler.scheduleJobs(list, Job.getProfitComparator(), new ScheduleAsLateAsPossible());
    System.out.println(schedule.getFront().getElement().getJob().getId());
    assertEquals("test scheduleJobs method", schedule.getBack().getElement().getJob(), job3);
    assertEquals("test scheduleJobs method", schedule.getFront().getNext().getElement().getJob(), job1);
    assertEquals("test scheduleJobs method", schedule.getFront().getNext().getNext().getNext().getElement().getJob(), job2);
    assertEquals("test scheduleJobs method", schedule.getFront().getElement().getJob(), job5);
    assertEquals("test scheduleJobs method", schedule.getFront().getNext().getNext().getElement().getJob(), job4);
    
    // test extraScheduleJobs method
    list = new DoubleLinkedList<Job> ();
    job1 = new Job(1, 12, 17, 4, 5);
    job2 = new Job(2, 11, 24, 3, 7);
    job3 = new Job(3, 10, 24, 12, 14);
    job4 = new Job(4, 13, 25, 6, 6);
    list.addToFront(job1);
    list.addToFront(job2);
    list.addToFront(job3);
    list.addToFront(job4);
    
    int profit = scheduler.extraScheduleJobs(list);
    assertEquals("test extraScheduleJobs method", 18, profit);
    schedule = scheduler.scheduleJobs(list, Job.getProfitComparator(), new ScheduleAsLateAsPossible());
    profit = 0;
    for (ScheduleSlot slot : schedule)
    {
      profit += slot.getJob().getProfit();
    }
    assertEquals("test extraScheduleJobs method", 14, profit);
  }
  
  /** Test DoubleLinkedList class */
  @Test
  public void testDoubleLinkedList()
  {
    // test mergeSort method
    DoubleLinkedList<Job> list = new DoubleLinkedList<Job> ();
    Job job1 = new Job(1, 1, 8, 6, 10);
    Job job2 = new Job(2, 7, 17, 3, 8);
    Job job3 = new Job(3, 10, 25, 8, 14);
    list.addToFront(job1);
    list.addToFront(job2);
    list.addToFront(job3);
    Job job5 = new Job(5, 1, 0, 1, 0);
    Job job4 = new Job(4, 0, 14, 6, 0);
    CompoundJob compoundJob = new CompoundJob(7, job5, job4);
    list.addToFront(compoundJob);
    DoubleLinkedList.mergeSort(list, Job.getProfitComparator());
    Job[] jobs = new Job[4];
    int i = 0;
    for (Job job : list)
    {
      jobs[i] = job;
      ++i;
    }
    for (i = 0; i < jobs.length - 1; ++i)
      if (jobs[i].getProfit() < jobs[i+1].getProfit()) fail("Testing mergeSort method by profit fails");
  }
}