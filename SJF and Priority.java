import java.util.Scanner;
import java.util.PriorityQueue;

class Process {
    private String name;
    private int burstTime;
    private int priority;
    private int waitingTime;
    private int turnaroundTime;
    private int remainingBurstTime;

    public Process(String name, int burstTime, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.remainingBurstTime = burstTime;
    }

    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
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

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }
}

public class SchedulingMenu {
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
            System.out.print("Enter priority for Process " + (i + 1) + ": ");
            int priority = scanner.nextInt();
            processes[i] = new Process(name, burstTime, priority);
        }

        int choice;
        do {
            System.out.println("\nSelect a scheduling algorithm:");
            System.out.println("1. Shortest Job First (SJF) Preemptive");
            System.out.println("2. Priority (Non-Preemptive)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    executeSJFPreemptive(processes.clone());
                    break;
                case 2:
                    executePriority(processes.clone());
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

    public static void executeSJFPreemptive(Process[] processes) {
        int currentTime = 0;
        int completedProcesses = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>((p1, p2) -> {
            if (p1.getRemainingBurstTime() != p2.getRemainingBurstTime()) {
                return p1.getRemainingBurstTime() - p2.getRemainingBurstTime();
            } else {
                return p1.getName().compareTo(p2.getName());
            }
        });

        System.out.println("\nProcess Execution Order (SJF Preemptive):");

        while (completedProcesses < processes.length) {
            for (Process process : processes) {
                if (process.getBurstTime() > 0 && process.getPriority() <= currentTime) {
                    readyQueue.offer(process);
                }
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                int executionTime = Math.min(currentProcess.getRemainingBurstTime(), 1);
                currentProcess.setRemainingBurstTime(currentProcess.getRemainingBurstTime() - executionTime);
                currentTime += executionTime;

                if (currentProcess.getRemainingBurstTime() == 0) {
                    completedProcesses++;
                    currentProcess.setTurnaroundTime(currentTime);
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                }

                System.out.println("Executing " + currentProcess.getName() + ", CT: " + currentTime +
                        ", WT: " + currentProcess.getWaitingTime() + ", TAT: " + currentProcess.getTurnaroundTime());
            } else {
                currentTime++;
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

        System.out.println("Average Waiting Time (SJF Preemptive): " + avgWaitingTime);
        System.out.println("Average Turnaround Time (SJF Preemptive): " + avgTurnaroundTime);
    }

    public static void executePriority(Process[] processes) {
        int currentTime = 0;
        int completedProcesses = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>((p1, p2) -> p1.getPriority() - p2.getPriority());

        System.out.println("\nProcess Execution Order (Priority - Non-Preemptive):");

        while (completedProcesses < processes.length) {
            for (Process process : processes) {
                if (process.getBurstTime() > 0 && process.getPriority() <= currentTime) {
                    readyQueue.offer(process);
                }
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                int executionTime = currentProcess.getBurstTime();
                currentProcess.setBurstTime(0);
                currentTime += executionTime;

                completedProcesses++;
                currentProcess.setTurnaroundTime(currentTime);
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());

                System.out.println("Executing " + currentProcess.getName() + ", CT: " + currentTime +
                        ", WT: " + currentProcess.getWaitingTime() + ", TAT: " + currentProcess.getTurnaroundTime());
            } else {
                currentTime++;
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

        System.out.println("Average Waiting Time (Priority - Non-Preemptive): " + avgWaitingTime);
        System.out.println("Average Turnaround Time (Priority - Non-Preemptive): " + avgTurnaroundTime);
    }
}
