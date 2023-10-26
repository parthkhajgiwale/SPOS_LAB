import java.util.Scanner;

class Process {
    private String name;
    private int priority;
    private int burstTime;

    public Process(String name, int priority, int burstTime) {
        this.name = name;
        this.priority = priority;
        this.burstTime = burstTime;
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

    @Override
    public String toString() {
        return name + " (Priority: " + priority + ", Burst Time: " + burstTime + ")";
    }
}

public class PriorityScheduling {
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
            processes[i] = new Process(name, priority, burstTime);
        }

        // Sort processes by priority (lower number indicates higher priority)
        java.util.Arrays.sort(processes, (p1, p2) -> p1.getPriority() - p2.getPriority());

        int currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("\nProcess Execution Order:");

        for (Process process : processes) {
            int waitingTime = currentTime;
            int turnaroundTime = waitingTime + process.getBurstTime();
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            currentTime += process.getBurstTime();
            System.out.println("Executing " + process + ", Waited for " + waitingTime + " units.");
        }

        double avgWaitingTime = totalWaitingTime / numProcesses;
        double avgTurnaroundTime = totalTurnaroundTime / numProcesses;

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);

        scanner.close();
    }
}
