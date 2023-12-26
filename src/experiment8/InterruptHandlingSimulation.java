package experiment8;

/**
 * @Author scinloop
 * @Date 2023/12/26 15:34
 */
import java.util.LinkedList;
import java.util.Queue;

// Process class representing a process in the system
class Process {
    private String id;
    private int duration;

    public Process(String id, int duration) {
        this.id = id;
        this.duration = duration;
    }

    // Simulates the execution of a process
    public void execute() {
        System.out.println("Process " + id + " is executing.");
        while (duration > 0) {
            try {
                Thread.sleep(1000); // Simulate work by sleeping for 1 second
                duration--;
            } catch (InterruptedException e) {
                System.out.println("Process " + id + " was interrupted.");
                handleInterrupt();
            }
        }
    }

    // Handles an interrupt
    private void handleInterrupt() {
        System.out.println("Process " + id + " is handling an interrupt.");
        // Perform interrupt handling specific to the process here
    }

    public String getId() {
        return id;
    }
}

// Interrupt class representing an interrupt
class Interrupt {
    private String type;

    public Interrupt(String type) {
        this.type = type;
    }

    public void handle() {
        System.out.println("Handling " + type + " interrupt.");
        // Handle the interrupt
    }

    public String getType() {
        return type;
    }
}

// SegmentedSystem class that handles processes and interrupts
class SegmentedSystem {
    private Queue<Process> processQueue;
    private Queue<Interrupt> interruptQueue;

    public SegmentedSystem() {
        this.processQueue = new LinkedList<>();
        this.interruptQueue = new LinkedList<>();
    }

    public void addProcess(Process process) {
        processQueue.add(process);
    }

    public void addInterrupt(Interrupt interrupt) {
        interruptQueue.add(interrupt);
    }

    // Simulates the system execution
    public void run() {
        while (!processQueue.isEmpty()) {
            Process currentProcess = processQueue.poll();
            Thread processThread = new Thread(currentProcess::execute);
            processThread.start();

            // Simulate that an interrupt can occur at any time during the process execution
            while (processThread.isAlive()) {
                if (!interruptQueue.isEmpty()) {
                    Interrupt interrupt = interruptQueue.poll();
                    interrupt.handle();
                    processThread.interrupt(); // Simulate the process being interrupted
                }
            }
        }
    }
}

// Main class to run the simulation
public class InterruptHandlingSimulation {
    public static void main(String[] args) {
        SegmentedSystem system = new SegmentedSystem();

        // Add processes to the system
        system.addProcess(new Process("P1", 3));
        system.addProcess(new Process("P2", 2));

        // Add interrupts to the system
        system.addInterrupt(new Interrupt("I/O"));
        system.addInterrupt(new Interrupt("Timer"));

        // Run the system
        system.run();
    }
}

