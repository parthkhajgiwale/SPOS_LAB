import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PageReplacementSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int frameSize;
        ArrayList<Integer> pageReferences = new ArrayList<>();

        System.out.print("Enter the number of frames: ");
        frameSize = scanner.nextInt();

        System.out.print("Enter page references (space-separated): ");
        scanner.nextLine(); // Consume the newline character
        String[] referenceString = scanner.nextLine().split(" ");
        for (String s : referenceString) {
            pageReferences.add(Integer.parseInt(s));
        }

        while (true) {
            System.out.println("\nChoose a page replacement algorithm:");
            System.out.println("1. FIFO (First-In-First-Out)");
            System.out.println("2. LRU (Least Recently Used)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    simulateFIFO(frameSize, pageReferences);
                    break;
                case 2:
                    simulateLRU(frameSize, pageReferences);
                    break;
                case 3:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void simulateFIFO(int frameSize, ArrayList<Integer> pageReferences) {
        Queue<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        System.out.println("FIFO Page Replacement Simulation:");

        for (int page : pageReferences) {
            if (frames.size() < frameSize) {
                if (!frames.contains(page)) {
                    frames.add(page);
                    pageFaults++;
                }
            } else {
                if (!frames.contains(page)) {
                    int replacedPage = frames.poll();
                    frames.add(page);
                    pageFaults++;
                }
            }
            System.out.println("Page " + page + ": " + frames);
        }

        System.out.println("Total Page Faults: " + pageFaults);
    }

    public static void simulateLRU(int frameSize, ArrayList<Integer> pageReferences) {
        ArrayList<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("LRU Page Replacement Simulation:");

        for (int page : pageReferences) {
            if (frames.size() < frameSize) {
                if (!frames.contains(page)) {
                    frames.add(page);
                    pageFaults++;
                }
            } else {
                if (!frames.contains(page)) {
                    int oldestPageIndex = 0;
                    int leastRecent = pageReferences.size();

                    for (int i = 0; i < frameSize; i++) {
                        int index = frames.get(i);
                        int indexOfPage = pageReferences.indexOf(index);

                        if (indexOfPage < leastRecent) {
                            leastRecent = indexOfPage;
                            oldestPageIndex = i;
                        }
                    }

                    frames.set(oldestPageIndex, page);
                    pageFaults++;
                }
            }
            System.out.println("Page " + page + ": " + frames);
        }

        System.out.println("Total Page Faults: " + pageFaults);
    }
}
