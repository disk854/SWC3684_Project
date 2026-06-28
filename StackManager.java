import java.util.Queue;
import java.util.Stack;

/**
 * Handles the Manifest Settlement (Phase 3).
 * Processes batches of carriers across lanes and pushes them to a dispatch stack.
 */
public class StackManager {
    
    // Encapsulated stack as required by the rubric
    private Stack<CarrierInfo> dispatchedStack;

    public StackManager() {
        this.dispatchedStack = new Stack<>();
    }

    /**
     * Processes fixed batches of 5 carriers from each queue until all are empty.
     */
    public void processSettlement(Queue<CarrierInfo> q1, Queue<CarrierInfo> q2, Queue<CarrierInfo> q3) {
        
        // Continue looping as long as AT LEAST ONE queue still has carriers
        while (!q1.isEmpty() || !q2.isEmpty() || !q3.isEmpty()) {
            
            // Dequeue up to 5 from Regional (Queue 1)
            for (int i = 0; i < 5 && !q1.isEmpty(); i++) {
                dispatchedStack.push(q1.poll());
            }
            
            // Dequeue up to 5 from Cross-Border (Queue 2)
            for (int i = 0; i < 5 && !q2.isEmpty(); i++) {
                dispatchedStack.push(q2.poll());
            }
            
            // Dequeue up to 5 from Industrial Bulk (Queue 3)
            for (int i = 0; i < 5 && !q3.isEmpty(); i++) {
                dispatchedStack.push(q3.poll());
            }
        }
    }

    /**
     * Pops carriers from the stack and displays the terminal departure log.
     */
    public void printTerminalDepartureLog() {
        System.out.println("\n======================================================================");
        System.out.println(">>> TERMINAL DEPARTURE LOG <<<");
        System.out.println("======================================================================");
        
        if (dispatchedStack.isEmpty()) {
            System.out.println("[System Info] The dispatch stack is currently empty.\n");
            return;
        }

        // LIFO (Last-In-First-Out): Pop every entry out to generate the log
        while (!dispatchedStack.isEmpty()) {
            CarrierInfo dispatched = dispatchedStack.pop();
            
            // Dynamically calculate the combined carbon tax for this specific carrier
            double totalCarbonTax = 0.0;
            for (ShipmentInfo shipment : dispatched.getShipments()) {
                totalCarbonTax += shipment.getCarbonTaxCost();
            }
            
            // Formatted terminal printout matching the rubric requirements
            System.out.printf("Carrier: %-20s | Fleet: %-15s | Total Shipments Handled: %-3d | Total Combined Carbon Tax: RM %.2f\n",
                    dispatched.getCarrierName(),
                    dispatched.getFleetType(),
                    dispatched.getShipments().size(),
                    totalCarbonTax);
        }
        System.out.println("======================================================================\n");
    }
}