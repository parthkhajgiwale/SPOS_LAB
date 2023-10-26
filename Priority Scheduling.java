import java.util.Scanner;
import java.util.Arrays;

class Process {
    private String name;
    private int priority;
    private int burstTime;
    private int arrivalTime;
    private int completionTime;
    private int waitingTime;
    private int turnaroundTime;

    public Process(String name, int priority, int burstTime, int arrivalTime) {
        this.name = name;
        this.priority = priority;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getBurstTime() {
        return burstTime;
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

    public void setCompletionTime(int time) {
        completionTime = time;
    }

    public void setWaitingTime(int time) {
        waitingTime = time;
    }

    public void setTurnaroundTime(int time) {
        turnaroundTime = time;
    }

    @Override
    public String toString() {
        return name + " (Priority: " + priority + ", Burst Time: " + burstTime + ", Arrival Time: " + arrivalTime + ")";
    }
}

public class PrioritySchedulingWithArrivalTime {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        Process[] processes = new Process[numProcesses];

        // Input details for each process
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter name for Process " + (i + 1) + ": ");
            String name = scanner.next();
            System.out.print("Enter priority for Process " + (i + 1) + ": ");
            int priority = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            processes[i] = new Process(name, priority, burstTime, arrivalTime);
        }

        // Sort processes based on priority and arrival time
        Arrays.sort(processes, (p1, p2) -> {
            if (p1.getPriority() == p2.getPriority()) {
                return p1.getArrivalTime() - p2.getArrivalTime();
            }
            return p1.getPriority() - p2.getPriority();
        });

        int currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("\nProcess Execution Order:");

        for (Process process : processes) {
            int waitingTime = currentTime - process.getArrivalTime();
            int turnaroundTime = waitingTime + process.getBurstTime();
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            process.setCompletionTime(currentTime + process.getBurstTime());
            process.setWaitingTime(waitingTime);
            process.setTurnaroundTime(turnaroundTime);

            currentTime += process.getBurstTime();
            System.out.println("Executing " + process + ", CT: " + process.getCompletionTime() + ", WT: " + waitingTime + ", TAT: " + turnaroundTime);
        }

        double avgWaitingTime = totalWaitingTime / numProcesses;
        double avgTurnaroundTime = totalTurnaroundTime / numProcesses;

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);

        scanner.close();
    }
}
