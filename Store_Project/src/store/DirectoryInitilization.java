package store;

import javax.xml.crypto.Data;
import java.io.File;

public class DirectoryInitilization {
    public static void Setup(){

        //Create Data directory, and directories for Inventory Data, Order Data, Customer Data and Finance Data
        File DataDir = new File("Data");
        File InventoryDir = new File("Data/Inventory");
        File OrderDir = new File("Data/Order");
        File CustomerDir = new File("Data/Customer");
        File FinanceDir = new File("Data/Finance");

        //If the /Data path doesn't exist, then make it, and add the other subdirectories
        if (!DataDir.exists()){
            if(DataDir.mkdir()){
                InventoryDir.mkdir();
                OrderDir.mkdir();
                CustomerDir.mkdir();
                FinanceDir.mkdir();
            }
            else{
                System.out.println("Directory failed to be made.");
            }
        }

        if(!InventoryDir.exists()){
            InventoryDir.mkdir();
        }

        if(!OrderDir.exists()){
            OrderDir.mkdir();
        }

        if(!CustomerDir.exists()){
            CustomerDir.mkdir();
        }

        if(!FinanceDir.exists()){
            FinanceDir.mkdir();
        }
    }
}
