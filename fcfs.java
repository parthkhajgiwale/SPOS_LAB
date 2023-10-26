import java.util.Scanner;

class FCFS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        int[] arrivalTime = new int[numProcesses];
        int[] burstTime = new int[numProcesses];

        // Input arrival time and burst time for each process
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
        }

        // Calculate completion time, waiting time, and turnaround time
        int[] completionTime = new int[numProcesses];
        int[] waitingTime = new int[numProcesses];
        int[] turnaroundTime = new int[numProcesses];

        completionTime[0] = arrivalTime[0] + burstTime[0];
        for (int i = 1; i < numProcesses; i++) {
            if (arrivalTime[i] > completionTime[i - 1]) {
                completionTime[i] = arrivalTime[i] + burstTime[i];
            } else {
                completionTime[i] = completionTime[i - 1] + burstTime[i];
            }
        }

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
