import java.util.Scanner;

class Process {
    private String name;
    private int burstTime;
    private int waitingTime;
    private int turnaroundTime;

    public Process(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }

    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
}

public class FCFS_RoundRobinScheduler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        Process[] processes = new Process[numProcesses];

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter name for Process " + (i + 1) + ": ");
            String name = scanner.next();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(name, burstTime);
        }

        int choice;
        do {
            System.out.println("\nSelect a scheduling algorithm:");
            System.out.println("1. First-Come, First-Served (FCFS)");
            System.out.println("2. Round Robin (RR)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    executeFCFS(processes);
                    break;
                case 2:
                    executeRoundRobin(processes);
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 3);

        scanner.close();
    }

    public static void executeFCFS(Process[] processes) {
        int currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("\nProcess Execution Order (FCFS):");

        for (Process process : processes) {
            int waitingTime = currentTime;
            int turnaroundTime = waitingTime + process.getBurstTime();
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            process.setWaitingTime(waitingTime);
            process.setTurnaroundTime(turnaroundTime);

            currentTime += process.getBurstTime();
            System.out.println("Executing " + process.getName() + ", CT: " + currentTime + ", WT: " + waitingTime + ", TAT: " + turnaroundTime);
        }

        double avgWaitingTime = totalWaitingTime / processes.length;
        double avgTurnaroundTime = totalTurnaroundTime / processes.length;

        System.out.println("Average Waiting Time (FCFS): " + avgWaitingTime);
        System.out.println("Average Turnaround Time (FCFS): " + avgTurnaroundTime);
    }

    public static void executeRoundRobin(Process[] processes) {
        int timeQuantum = 2;
        int currentTime = 0;
        int completedProcesses = 0;

        System.out.println("\nProcess Execution Order (Round Robin):");

        while (completedProcesses < processes.length) {
            for (Process process : processes) {
                if (!process.getName().equals("") && process.getBurstTime() > 0) {
                    int executionTime = Math.min(timeQuantum, process.getBurstTime());
                    int waitingTime = currentTime;
                    int turnaroundTime = waitingTime + executionTime;
                    process.setWaitingTime(waitingTime);
                    process.setTurnaroundTime(turnaroundTime);

                    System.out.println("Executing " + process.getName() + " for " + executionTime + " units, CT: " + (currentTime + executionTime) + ", WT: " + waitingTime + ", TAT: " + turnaroundTime);

                    process.setBurstTime(process.getBurstTime() - executionTime);
                    currentTime += executionTime;

                    if (process.getBurstTime() == 0) {
                        process.setName(""); // Mark as completed
                        completedProcesses++;
                    }
                }
            }
        }

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        double avgWaitingTime = totalWaitingTime / processes.length;
        double avgTurnaroundTime = totalTurnaroundTime / processes.length;

        System.out.println("Average Waiting Time (Round Robin): " + avgWaitingTime);
        System.out.println("Average Turnaround Time (Round Robin): " + avgTurnaroundTime);
    }
}
