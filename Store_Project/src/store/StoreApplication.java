package store;

import java.util.Scanner;

public class StoreApplication {

    public static void main(String[] args){
        DirectoryInitilization.Setup();
        //TesterClass.test();

        //Prompts user to enter merchant view or customer view
        boolean isValidInput = false;
        Scanner userInput = new Scanner(System.in);

        while(!isValidInput){
            System.out.println("Which view would like to enter in?");
            System.out.println("[1] - Merchant View");
            System.out.println("[2] - Customer View");


            String inputString = userInput.nextLine();
            try{
                int inputInt = Integer.valueOf(inputString);

                if(inputInt == 1){
                    MerchantView.Run();
                    isValidInput = true;
                }
                else if(inputInt == 2){
                    CustomerView.Run();
                    isValidInput = true;
                }
                else{
                    System.out.println("Invalid input. Please try again:");
                }
            }

            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }

    }
}
