*******************************************
CS 321: P2
Instructor: Francesca Spezzano
Author: Irene Galca
Date: 9/27/2019
*******************************************

Files:

CPUScheduling.java (Source file)
Averager.java (Averaging class)
MaxHeap.java (Max heap class implementing heap functions)
Process.java (Process class includes getters and setter for time and pritority)
ProcessGenerator.java (Process generator class generates new process)
PQueue.java (Priority queue class implements queue functions)
======================================================================================
What the program does:

This program is designed to use max heap and priority queue implementation to
arrange and organize jobs according to priority and arrival time.

Every time a new job arrives in the heap it is compared to any child or parent
jobs. If the job priority is higher then it is swapped until the values of the heap are
organized. Then the heap is implemented in the priority queue.
If the job has time remaining, it will decrement the given process time for that job.
If there is no time remaining, the job will finish and proceed to the next job.
It will continue to do so until your given simulation time runs out.


How to run the program:

In onyx you'll want to compile the java classes use the command:

$ javac *.java  (to compile all java classes at once.)

Then you'll want to run the program by typing in the command line:

$ java CPUScheduling <MaxProcessTime> <MaxPriorityLevel> <TimeToIncrementPriority> <SimulationTime> <ProcessArrivalRate>

Example:

$ java CPUScheduling 5 5 5 100 0.4

In these examples, you can expect an output similar to this:

******************************************************************
second   0: 
second   1: JOB 1 arrives, timeRequired 5, priority 1
	        (Assign to JOB 1: timeRemaining 4, priority 1)
second   2: JOB 2 arrives, timeRequired 5, priority 1
	        (Assign to JOB 1: timeRemaining 3, priority 1)
second   3:     (Assign to JOB 1: timeRemaining 2, priority 1)
second   4:     (Assign to JOB 1: timeRemaining 1, priority 1)
second   5: JOB 5 arrives, timeRequired 1, priority 1
	        (Assign to JOB 1: finish, priority 1)
second   6:     (Assign to JOB 2: timeRemaining 4, priority 1)
etc...
******************************************************************

The output will be different, each time due to the random values in maxProcessTime and 
maxLevel parameters in ProcessGenerator.