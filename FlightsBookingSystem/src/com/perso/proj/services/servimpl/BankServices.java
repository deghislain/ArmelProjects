/**
 * 
 */
package com.perso.proj.services.servimpl;

import java.util.Hashtable;

import com.perso.proj.entities.Account;
import com.perso.proj.enums.EBSOperations;
import com.perso.proj.services.servinterface.IAccountServices;
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IEncryptionService;

/**
 * @author deghislain
 *
 */
public class BankServices implements IBankServices{
	
	//Bank credit card stock
    private Hashtable<String, String> cards;

    //each card comes with an initial amount
    double cardInitialAmount;

    //list of bank accounts
    private Hashtable<String, Account> accounts;

    IEncryptionService encrService;

    private ICreditCardBufferServices cardBuffer;

    private String[] deliveredCards;
    
    private final static int KEY1 = 20; //keys for encryption
    
    private final static int KEY2 = 25;

    //each credit card is associated to an account
    public BankServices(ICreditCardBufferServices ccBuffer){
    	this.cards = new Hashtable<String, String>();
        //here we create not assigned credit card
        this.cards.put("2453.9512.0000.3698", "NOT_ASSIGNED");
        this.cards.put("2451.9512.8000.0698", "NOT_ASSIGNED");
        this.cards.put("2153.9512.1111.3658", "NOT_ASSIGNED");
        this.cards.put("2477.9582.0900.3698", "NOT_ASSIGNED");
        this.cards.put("2977.9562.0400.3618", "NOT_ASSIGNED");


        this.cardInitialAmount = 1000;

        this.accounts = new Hashtable<String, Account>();

        this.encrService = new EncryptionService();

        this.cardBuffer = ccBuffer;

        this.deliveredCards = new String[5];

    }
	

	@Override
	public void runBankService() {
		 processCreditCardApplication();
		 chargeCredCard();
		 deposit();
	}
	
	 private void processCreditCardApplication(){
         for(int i = 0 ; i < 5; i++) {
             String appRef = this.cardBuffer.getCardCell(i, "process");
             if (appRef != null && !appRef.equals("")){
                 String[] token = appRef.split("\\-");
                 if (token.length >= 2){
                     String travAgName = token[0];
                     String operation = token[1];
                     if (operation != null && !operation.isEmpty() && operation.equals(EBSOperations.APPLICATION.name())){
                         if (this.deliveredCards[i] == null || this.deliveredCards[i].equals("")){
                             String cardNum = grantCreditCard(travAgName);
                         if (cardNum != null && !cardNum.equals("")){
                             this.cardBuffer.setCardCell(travAgName, EBSOperations.DELIVERY, cardNum, null, 0);
                                 System.out.println("Bank deliver credit Card to " + travAgName);
                                 this.deliveredCards[i] = cardNum;
                                 break;
                             }
                         }
                     }
                 }
             }
         }
         
     }
	 
	//provide a credit card to the travel agency
     private String grantCreditCard(String travAgName)
     {
         String card = "";
         for(String key : cards.keySet())
         {
             //I retrieve the first not assigned card from the hashtable
             if (cards.get(key).equals("NOT_ASSIGNED")){
                 card = key;
                 //I assigned the card to the current travel agency
                 cards.put(key, travAgName);
                 break;
             }
         }
         //I create the account to be associated to the credit card
         if (card != null && !card.equals("")) {
        	 Account account = createAnAccount(travAgName, card, this.cardInitialAmount);
             accounts.put(card,account);
           //here we encrypt the credit card before sending to Travel Agency
             card = encrService.encrypt(card, KEY1, KEY2); 
         }
        

         return card;
     }
     
     private Account createAnAccount(String travAgName, String card, double initialAmount)
     {
         //create the account assocated to a specific card
    	 IAccountServices accountService = new AccountServices();
         return accountService.createAccount(travAgName, card, initialAmount);
     }

	
		private void chargeCredCard() {
			for (int i = 0; i < 5; i++) {
				String cardRef = this.cardBuffer.getCardCell(i, "charge");
				if (cardRef != null && !cardRef.equals("")) {
					String[] token = cardRef.split("\\-");
					if (token.length >= 4) {
						String operation = token[1];
						if(operation.equals(EBSOperations.CHARGE.name())){
							String cardNum = token[2];
							double amount = Double.parseDouble(token[4]);
							cardNum = encrService.decrypt(cardNum, KEY1, KEY2);
							Account acc = accounts.get(cardNum);
							
							IAccountServices accServices = new AccountServices();
							Account updatedAcc = accServices.charge(acc, amount);
							if(null != updatedAcc) {
								accounts.put(cardNum, updatedAcc);
								this.cardBuffer.setCardCell(token[0], EBSOperations.FEEDBACK, cardNum, "Valid", 0);
							}else {
								this.cardBuffer.setCardCell(token[0], EBSOperations.FEEDBACK, cardNum, "No Valid", 0);
							}
						}
						
					}
				}

			}
		}

	
	private void deposit() {
		for (int i = 0; i < 5; i++) {
			String cardRef = this.cardBuffer.getCardCell(i, "deposit");
			if (cardRef != null && !cardRef.equals("")) {
				String[] token = cardRef.split("\\-");
				if (token.length >= 4) {
					String operation = token[1];
					if(operation.equals(EBSOperations.DEPOSIT.name())){
						String cardNum = token[2];
						double amount = Double.parseDouble(token[4]);
						cardNum = encrService.decrypt(cardNum, KEY1, KEY2);
						Account acc = accounts.get(cardNum);
						
						IAccountServices accServices = new AccountServices();
						Account updatedAcc = accServices.deposit(acc, amount);
						if(null != updatedAcc) {
							accounts.put(cardNum, updatedAcc);
							this.cardBuffer.setCardCell(token[0], EBSOperations.FEEDBACK, cardNum, "Valid", 0);
						}else {
							this.cardBuffer.setCardCell(token[0], EBSOperations.FEEDBACK, cardNum, "No Valid", 0);
						}
					}
					
				}
			}

		}
		
	}

}
