import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<CarrierInfo> carrierList = new LinkedList<>();

        try {
            File file = new File("supply_chain_manifest.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(","); 
                
                String currentCarrierId = data[0].trim(); 
                
                CarrierInfo existingCarrier = null;
                for (CarrierInfo carrier : carrierList) {
                    if (carrier.getCarrierId().equals(currentCarrierId)) {
                        existingCarrier = carrier;
                        break;
                    }
                }

                ShipmentInfo newShipment = new ShipmentInfo();
                newShipment.setShipmentId(data[3].trim());
                newShipment.setPackageType(data[4].trim());
                newShipment.setEcoPriorityScore(Integer.parseInt(data[5].trim()));
                newShipment.setDispatchDate(data[6].trim());
                newShipment.setEstimatedTransitTime(Integer.parseInt(data[7].trim()));
                newShipment.setCarbonTaxCost(Double.parseDouble(data[8].trim()));

                if (existingCarrier != null) {
                    existingCarrier.getShipments().add(newShipment);
                } else {
                    CarrierInfo newCarrier = new CarrierInfo();
                    
                    newCarrier.setCarrierId(currentCarrierId);
                    newCarrier.setCarrierName(data[1].trim());
                    newCarrier.setFleetType(data[2].trim());
                    
                    newCarrier.getShipments().add(newShipment);
                    carrierList.add(newCarrier);
                }
            }
            scanner.close();
            // Phase 2 Execution
            QueueManager queueManager = new QueueManager();
            queueManager.routeAllCarriers(carrierList);
            queueManager.printSortingLaneData();
            // Phase 3 Execution
            StackManager stackManager = new StackManager();
            stackManager.processSettlement(
                    queueManager.getQueue1(), 
                    queueManager.getQueue2(), 
                    queueManager.getQueue3()
            );
            stackManager.printTerminalDepartureLog();
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}