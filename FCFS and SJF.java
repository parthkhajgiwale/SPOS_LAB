import java.util.Scanner;

class Process {
    private String name;
    private int burstTime;
    private int arrivalTime;
    private int waitingTime;
    private int turnaroundTime;
    private int remainingTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.remainingTime = burstTime;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
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

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}

public class SJF_FCFS_Scheduler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        Process[] processes = new Process[numProcesses];

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter name for Process " + (i + 1) + ": ");
            String name = scanner.next();
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(name, arrivalTime, burstTime);
        }

        int choice;
        do {
            System.out.println("\nSelect a scheduling algorithm:");
            System.out.println("1. Shortest Job First (SJF - Preemptive)");
            System.out.println("2. First-Come, First-Served (FCFS)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    executeSJF(processes);
                    break;
                case 2:
                    executeFCFS(processes);
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

    public static void executeSJF(Process[] processes) {
        int currentTime = 0;
        int completedProcesses = 0;

        System.out.println("\nProcess Execution Order (SJF - Preemptive):");

        while (completedProcesses < processes.length) {
            int minRemainingTime = Integer.MAX_VALUE;
            int shortestJobIndex = -1;

            for (int i = 0; i < processes.length; i++) {
                if (processes[i].getArrivalTime() <= currentTime && processes[i].getRemainingTime() < minRemainingTime) {
                    minRemainingTime = processes[i].getRemainingTime();
                    shortestJobIndex = i;
                }
            }

            if (shortestJobIndex == -1) {
                currentTime++;
                continue;
            }

            Process shortestJob = processes[shortestJobIndex];
            shortestJob.setRemainingTime(shortestJob.getRemainingTime() - 1);

            if (shortestJob.getRemainingTime() == 0) {
                completedProcesses++;
                int turnaroundTime = currentTime - shortestJob.getArrivalTime() + 1;
                int waitingTime = turnaroundTime - shortestJob.getBurstTime();
                shortestJob.setTurnaroundTime(turnaroundTime);
                shortestJob.setWaitingTime(waitingTime);
            }

            System.out.println("Executing " + shortestJob.getName() + ", CT: " + (currentTime + 1) + ", WT: " + shortestJob.getWaitingTime() + ", TAT: " + shortestJob.getTurnaroundTime());

            currentTime++;
        }

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        double avgWaitingTime = totalWaitingTime / processes.length;
        double avgTurnaroundTime = totalTurnaroundTime / processes.length;

        System.out.println("Average Waiting Time (SJF - Preemptive): " + avgWaitingTime);
        System.out.println("Average Turnaround Time (SJF - Preemptive): " + avgTurnaroundTime);
    }

    public static void executeFCFS(Process[] processes) {
        int currentTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("\nProcess Execution Order (FCFS):");

        for (Process process : processes) {
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime();
            }

            int waitingTime = currentTime - process.getArrivalTime();
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
}
