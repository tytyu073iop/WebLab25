package by.bsu.daoPhysical;

import by.bsu.dao.*;
import by.bsu.entities.*;

public class DaoCreditCards extends Dao<CreditCard> {

    public DaoCreditCards() throws JDBCConnectionException {
        super(CreditCard.class);
    }
    
    public void makePayment(CreditCard CreditCard, int to_account_id, double amount) throws DAOException, JDBCConnectionException {
		DaoAccounts da = new DaoAccounts();
		DaoPayments dp = new DaoPayments();
		Payment p = new Payment(CreditCard.getAccount(), da.readSingle(to_account_id, Account_.accountId), amount);
		
		dp.create(p);
	}
	
	public void deactivateCard(CreditCard creditCard) throws DAOException {
		creditCard.setActive(false);
		update(creditCard);
	}

}
