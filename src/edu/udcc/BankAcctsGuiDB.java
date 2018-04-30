package edu.udcc;

import edu.udc.bank.AcctType;
import static edu.udc.bank.AcctType.ACCT_BUS;
import static edu.udc.bank.AcctType.ACCT_CC;
import static edu.udc.bank.AcctType.ACCT_CD;
import static edu.udc.bank.AcctType.ACCT_CHECKING;
import static edu.udc.bank.AcctType.ACCT_SAVINGS;
import edu.udc.bank.BankAcctInterface;
import edu.udc.dbobj.BankImplement;
import edu.udc.dbobj.BankServer;
import edu.udc.dbobj.DBServer;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Tofik Mussa and Connor Ireland
 */

//This is out GUI
public class BankAcctsGuiDB extends javax.swing.JFrame {

    private customTableModel CTM;
    private BankServer bvr;
    List<BankAcctInterface> listbac;
    
    
    public BankAcctsGuiDB() {
        initComponents();
        
        bvr = BankServer.getInstance(); //Singleton instance
        initControls();
    }

    private void initControls(){
        CTM = new customTableModel();
        jTable1.setModel(CTM);
        
        
        bvr.connect("\\\\TOSHIBA\\Users\\TOSHIBAPC\\Desktop\\bank_db");
        //DBServer.createDBData("\\\\TOSHIBA\\Users\\TOSHIBAPC\\Desktop\\bank_db");
        listbac = bvr.getAllAccts();
        
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one selection at a time
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(0, 49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        //Deleting logic here
        
        if(jTable1.getSelectedRow() == -1){ //If no rows are selected
            JOptionPane.showMessageDialog(null, "No Rows Selected to delete");
        }
        
        int row = jTable1.getSelectedRow();
        BankAcctInterface bac = listbac.get(row);
        if(row != -1){
        CTM.removeRow(row);
        }
        
        try {
        int rowsaffected = bvr.removeAcct(bac); 
            JOptionPane.showMessageDialog(null, "Successfully deleted" + rowsaffected + " rows");//if deletion successful 
        }catch(Exception sqe){
            JOptionPane.showMessageDialog(null, "Deleted 0 rows"); //If deletion failed
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0); //Closes the window
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        bvr.getAllAccts(); //Refreshes Table
        CTM.fireTableDataChanged();
    }//GEN-LAST:event_jButton3ActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BankAcctsGuiDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BankAcctsGuiDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BankAcctsGuiDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BankAcctsGuiDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BankAcctsGuiDB().setVisible(true);
            }
        });
    }
 //Our custom table model
    
    class customTableModel extends AbstractTableModel{
       
        
        customTableModel(){
            
            //Collections.sort(listbac, new BankComparator());
        }
        
        String [] columnNames = new String [] {"Acct ID", "Balance", "Holder", "Address", "Date Opened"};
        @Override
        public String getColumnName(int colIndex){
            return columnNames[colIndex];
        }
        @Override
        public int getRowCount() {
            return listbac.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            BankAcctInterface bac = listbac.get(row);
           
            switch(col){
                    case 0:
                        return bac.getUniqueID();
                        
                    case 1:
                       NumberFormat usFormat = NumberFormat.getCurrencyInstance();
                       return usFormat.format(bac.getAcctValue());
                    case 2:
                       return bac.getAcctHolder();
                    case 3:
                        return bac.getDepositor().getStreetAddr();
            }
                    return new SimpleDateFormat("MMM d, yyyy").format(bac.getDateOpened());
        }
        
        //Updating logic here
        
        public boolean isCellEditable(int row, int col){
            return col == 2;
        }
       
        public void setValueAt(Object ob, int row, int col){
            BankImplement bac = (BankImplement) listbac.get(row); //everything is ccoded against the interface except update
            
            
            if(col == 2){
                bac.AcctHolder = (String) ob;
            }
            
            try{
                int rowsaffected = bvr.updateAcct(bac);
            }
            
            catch(Exception sqe){
                 System.out.println("Updating error " + sqe);
            }
        }
        
        //To remove a row
        
        public void removeRow(int row){
            listbac.remove(row);
        }
      
    }
    
    //Our comparator
    //It is not a requirement for this assignment and we have not used it
    
    class BankComparator implements Comparator<BankAcctInterface>{

        @Override
        public int compare(BankAcctInterface t, BankAcctInterface t1) {
            return (int) (t1.getAcctValue() - t.getAcctValue());
        }   
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
