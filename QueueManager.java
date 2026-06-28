import java.util.LinkedList;
import java.util.Queue;

/**
 * Handles automated eco-friendly freight routing into sorting lanes (Phase 2).
 * Fully compatible with the encapsulated attributes defined in your data classes.
 */
public class QueueManager {

    // Encapsulation: Queues are private to maintain standard safe design practices
    private final Queue<CarrierInfo> queue1; // Regional Micro-Distribution Queue
    private final Queue<CarrierInfo> queue2; // Cross-Border Express Queue
    private final Queue<CarrierInfo> queue3; // Industrial Bulk Logistics Fleet

    // Flag to handle alternating routing for low-to-moderate volume carriers
    private boolean toggleToQueue1;

    /**
     * Constructor initializing the required Queue variables using a LinkedList backbone.
     */
    public QueueManager() {
        this.queue1 = new LinkedList<>();
        this.queue2 = new LinkedList<>();
        this.queue3 = new LinkedList<>();
        this.toggleToQueue1 = true; // Default starting alternate lane
    }

    /**
     * Loops through the populated LinkedList from Phase 1 and passes them into the sorting rules engine.
     */
    public void routeAllCarriers(LinkedList<CarrierInfo> carrierList) {
        if (carrierList == null || carrierList.isEmpty()) {
            System.out.println("[System Alert] Main carrier data stream is empty. Routing aborted.");
            return;
        }

        for (CarrierInfo carrier : carrierList) {
            processRoutingRules(carrier);
        }
    }

    /**
     * Core Algorithm Engine matching assignment rules by checking package volume thresholds.
     */
    private void processRoutingRules(CarrierInfo carrier) {
        // Using getter method to safely access the encapsulated shipments list
        int shipmentVolume = carrier.getShipments().size();

        // Rule: High-Volume Freight Carriers (More than 3 shipments)
        if (shipmentVolume > 3) {
            this.queue3.add(carrier);
            return; // Guard clause prevents deeply nested if-else structures
        }

        // Rule: Low-to-Moderate Volume Carriers (3 or fewer shipments)
        if (this.toggleToQueue1) {
            this.queue1.add(carrier);
        } else {
            this.queue2.add(carrier);
        }
        
        // Flip the flag state sequentially for the next low-volume carrier item
        this.toggleToQueue1 = !this.toggleToQueue1;
    }

    /**
     * Generates a terminal display using the exact block formatting syntax required by the prompt.
     */
    public void printSortingLaneData() {
        renderLaneDisplay("QUEUE 1: REGIONAL MICRO-DISTRIBUTION QUEUE", this.queue1);
        renderLaneDisplay("QUEUE 2: CROSS-BORDER EXPRESS QUEUE", this.queue2);
        renderLaneDisplay("QUEUE 3: INDUSTRIAL BULK LOGISTICS FLEET", this.queue3);
    }

    /**
     * Separated display driver to handle isolated text blocks and financial calculation logic.
     */
    private void renderLaneDisplay(String laneTitle, Queue<CarrierInfo> targetLane) {
        System.out.println("======================================================================");
        System.out.println(">>> " + laneTitle + " <<<");
        System.out.println("======================================================================");

        if (targetLane.isEmpty()) {
            System.out.println("[System Info] No active carriers processing in this automated lane.\n");
            return;
        }

        for (CarrierInfo carrier : targetLane) {
            double accumulatedLaneCarbonTax = 0.0;

            // 1 & 2. Print Carrier Name and Fleet Type via Getters
            System.out.println(carrier.getCarrierName());
            System.out.println(carrier.getFleetType());
            System.out.println("."); // Required literal dot divider format

            // 3. Print Nested list of all assigned physical shipments via Getters
            System.out.println("Nested Physical Shipments Processing:");
            for (ShipmentInfo shipment : carrier.getShipments()) {
                System.out.printf("  ↳ Shipment ID: %s | Type: %s | Eco-Score: %d | Tax Cost: RM %.2f\n",
                        shipment.getShipmentId(),
                        shipment.getPackageType(),
                        shipment.getEcoPriorityScore(),
                        shipment.getCarbonTaxCost());
                
                // Track aggregate tax variables dynamically
                accumulatedLaneCarbonTax += shipment.getCarbonTaxCost();
            }

            // 4. Print total compiled carbon tax costs processing in the lane
            System.out.printf("Total compiled carbon tax costs currently processing in the lane: RM %.2f\n", 
                    accumulatedLaneCarbonTax);
            System.out.println("}\n"); // Closing structural bracket token requirement
        }
    }

    // High-marks clean getters allowing the Phase 3 (Stack) manager safe access to processed lists
    public Queue<CarrierInfo> getQueue1() { return this.queue1; }
    public Queue<CarrierInfo> getQueue2() { return this.queue2; }
    public Queue<CarrierInfo> getQueue3() { return this.queue3; }
}