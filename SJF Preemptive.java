import java.util.*;

class Process {
    private int id;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int completionTime;
    private int waitingTime;
    private int turnaroundTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void execute(int time, int currentTime) {
        remainingTime -= time;
        if (remainingTime == 0) {
            completionTime = currentTime;
            turnaroundTime = completionTime - arrivalTime;
            waitingTime = turnaroundTime - burstTime;
        }
    }
}

public class PreemptiveSJF {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        List<Process> processes = new ArrayList<>();

        // Input arrival time and burst time for each process
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }

        int currentTime = 0;
        int completedProcesses = 0;
        PriorityQueue<Process> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));

        System.out.println("\nProcess Execution Order:");

        while (completedProcesses < numProcesses) {
            // Add processes that have arrived to the priority queue
            for (Process process : processes) {
                if (!process.isFinished() && process.getArrivalTime() <= currentTime) {
                    priorityQueue.add(process);
                }
            }

            if (!priorityQueue.isEmpty()) {
                Process shortestJob = priorityQueue.poll();
                int executionTime = Math.min(shortestJob.getRemainingTime(), 1);
                shortestJob.execute(executionTime, currentTime);
                currentTime += executionTime;
                System.out.println("Executing " + shortestJob.getId() + " for 1 unit.");
                if (shortestJob.isFinished()) {
                    completedProcesses++;
                }
            } else {
                currentTime++;
            }
        }

        // Calculate and display completion, waiting, and turnaround times
        System.out.println("\nProcess\tCompletion Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            System.out.println("P" + process.getId() + "\t\t" + process.getCompletionTime() + "\t\t" + process.getWaitingTime() + "\t\t" + process.getTurnaroundTime());
        }

        scanner.close();
    }
}
