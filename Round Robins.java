import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class RoundRobin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        System.out.print("Enter the time quantum: ");
        int timeQuantum = scanner.nextInt();

        int[] arrivalTime = new int[numProcesses];
        int[] burstTime = new int[numProcesses];
        int[] remainingTime = new int[numProcesses];

        // Input arrival time and burst time for each process
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            remainingTime[i] = burstTime[i];
        }

        Queue<Integer> queue = new LinkedList<>();
        int[] completionTime = new int[numProcesses];
        int currentTime = 0;
        int index = 0;

        while (true) {
            boolean done = true;
            for (int i = 0; i < numProcesses; i++) {
                if (arrivalTime[i] <= currentTime && remainingTime[i] > 0) {
                    done = false;
                    if (remainingTime[i] <= timeQuantum) {
                        currentTime += remainingTime[i];
                        completionTime[i] = currentTime;
                        remainingTime[i] = 0;
                    } else {
                        currentTime += timeQuantum;
                        remainingTime[i] -= timeQuantum;
                    }
                    while (index < numProcesses && arrivalTime[index] <= currentTime) {
                        queue.add(index);
                        index++;
                    }
                }
            }
            if (done) {
                break;
            }
        }

        // Calculate waiting time and turnaround time
        int[] waitingTime = new int[numProcesses];
        int[] turnaroundTime = new int[numProcesses];

        for (int i = 0; i < numProcesses; i++) {
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
        }

        // Calculate average waiting time and turnaround time
        double avgWaitingTime = 0;
        double avgTurnaroundTime = 0;

        for (int i = 0; i < numProcesses; i++) {
            avgWaitingTime += waitingTime[i];
            avgTurnaroundTime += turnaroundTime[i];
        }

        avgWaitingTime /= numProcesses;
        avgTurnaroundTime /= numProcesses;

        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("P" + (i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" + completionTime[i] + "\t\t" + waitingTime[i] + "\t\t" + turnaroundTime[i]);
        }

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);

        scanner.close();
    }
}
