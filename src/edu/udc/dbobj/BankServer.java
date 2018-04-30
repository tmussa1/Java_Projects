package edu.udc.dbobj;

import edu.udc.bank.AcctType;
import static edu.udc.bank.AcctType.ACCT_BUS;
import static edu.udc.bank.AcctType.ACCT_CC;
import static edu.udc.bank.AcctType.ACCT_CD;
import static edu.udc.bank.AcctType.ACCT_CHECKING;
import static edu.udc.bank.AcctType.ACCT_LOAN;
import static edu.udc.bank.AcctType.ACCT_SAVINGS;
import edu.udc.bank.BankAcctInterface;
import edu.udc.bank.Depositor;
import edu.udc.util.Utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tofik Mussa and Connor Ireland
 */

//This class popultates data
//from the database into two pojo classes

public class BankServer {
   
   public static BankServer bs;
   
   private BankServer() {}
   private Connection connection = null;
   
   public static BankServer getInstance(){
       if(bs == null)
           bs = new BankServer();
       return bs;
   }
   
   public void connect(String dbName){
    if(connection != null)
        return;
    try 
    {
        String dbUrl = "jdbc:derby:"+dbName;
        connection = DriverManager.getConnection(dbUrl);
        if(connection == null)
            System.out.println("conn null");
    }
    catch(SQLException sqe)
    {
        System.out.println("connect Error " + sqe);
    }
}
   //everything from both tables populates the POJOs
   //Descending account value 
   public ArrayList<BankAcctInterface> getAllAccts(){
       Statement st = null;
       ResultSet rs = null;
       
       ArrayList<BankAcctInterface> baList = new ArrayList<BankAcctInterface>();
       
       String sql = "Select ba_id,ba_value,ba_holder,ba_type,ba_dtopen,ba_special_info, ba_howadded, dp_id,"
               + " dp_ba_id, dp_addr, dp_postal_code, dp_pic from " +
               "BankAcct join depositor on ba_id = dp_ba_id order by ba_value desc";
        try
        {
        st = connection.createStatement();
        rs = st.executeQuery(sql);
        
        while(rs.next()){
         BankImplement baImp = new BankImplement();
         DepositorImplement deImp = new DepositorImplement();
         
         baImp.UniqueId = rs.getString("ba_id");
         baImp.AcctValue = rs.getDouble("ba_value");
         baImp.AcctHolder = rs.getString("ba_holder");
         baImp.DateOpen = rs.getDate("ba_dtopen");
         baImp.specialInfo = rs.getString("ba_special_info");
         baImp.howAdded = rs.getString("ba_howadded");
         String acctType= rs.getString("ba_type");
         
         if(acctType == "ch"){  //The enums are added
            baImp.AcctEnum = ACCT_CHECKING;
         } else if (acctType == "sav"){
             baImp.AcctEnum = ACCT_SAVINGS;
         } else if(acctType == "cc"){
             baImp.AcctEnum = ACCT_CC;
         } else if(acctType == "cd"){
             baImp.AcctEnum = ACCT_CD;
         } else if(acctType == "ln"){
             baImp.AcctEnum = ACCT_LOAN;
         } else if(acctType == "bu"){
             baImp.AcctEnum = ACCT_BUS;
         }
         
         
         deImp.personID = rs.getString("dp_id"); 
         deImp.postalCode = rs.getString("dp_postal_code");
         deImp.streetAddr = rs.getString("dp_addr");
         
         baImp.depo = deImp;
         
         
         baList.add(baImp);
            
        }
        
        } catch(SQLException sqe){
            System.out.println("Error " + sqe);
        }
        return baList;
   }
   
   //This updates the database successfully
   
   public int updateAcct(BankAcctInterface ba) throws SQLException{
       String sql = "Update BankAcct Set ba_holder =? where ba_id = ?";
       
       PreparedStatement pst = connection.prepareStatement(sql);
       
       pst.setString(1,ba.getAcctHolder());
       pst.setString(2, ba.getUniqueID());
       pst.executeUpdate();
       
       return 1;
   }
   
   //Properly deletes and uses the rollback technique
   //It also deletes from multiple tables
   public int removeAcct(BankAcctInterface ba) throws SQLException{

    try{
       String sql = "Delete from BankAcct where ba_id in(select ba_id from BankAcct inner join depositor" + 
               "on ba_id = dp_ba_id left join on ph_depo_key = dp_ba_id where ba_id = ?)";
       PreparedStatement pst = connection.prepareStatement(sql);
       connection.setAutoCommit(false);
       pst.clearParameters();
       
       pst.setString(1, ba.getUniqueID());
       pst.executeUpdate();
       
       connection.commit();
    }  
    catch(SQLException sqe){
       try{
           connection.rollback();
       } catch(SQLException sq) {
           System.err.println("Deleting error" + sq);
           throw sq; //To be caught by the GUI
       }      
        
    }
       
    return 1;
   }
}

