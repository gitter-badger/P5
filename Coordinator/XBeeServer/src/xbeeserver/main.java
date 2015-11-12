/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xbeeserver;

import java.util.Timer;
import java.util.TimerTask;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.XBeeNetwork;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.listeners.IDiscoveryListener;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeMessage;

/**
 *
 * @author brugeren
 */
public class main extends javax.swing.JFrame implements IDataReceiveListener,
        IDiscoveryListener {

    private static String PORT = "/dev/ttyUSB0";
    // TODO Replace with the baud rate of you receiver module.
    private static final int BAUD_RATE = 9600;
    private XBeeDevice device;
    private XBeeNetwork network;

    //setup timer to continuisly discover network
    Timer timer;
    final TimerTask timerTask = new TimerTask() {
        public void run() {
            DiscoverNetwork();
        }
    };

    /**
     * Creates new form main
     */
    public main() {
        initComponents();

        //XBee device init
        device = new XBeeDevice(PORT, BAUD_RATE);

        try {
            //If device can open, device is succesfully connected
            device.open();
            OutputTextArea.append("Device succesfully connected");
            
            //Invokes dataReceived when data is received, other listener available that raises on "packet received"
            device.addDataListener(this);
            
            network = device.getNetwork();
            network.addDiscoveryListener(this);
            network.setDiscoveryTimeout(5000);
            DiscoverNetwork();

            //starts timer to update network or some other thing
            /*
            timer = new Timer();
            timer.schedule(timerTask, 15000);
            */
        } catch (XBeeException e) {
            OutputTextArea.append("Device not found:\n" + e.getMessage());
        }

    }

    public void DiscoverNetwork() {
        RemoteDevicesList.setText("");
        OutputTextArea.append("\nDiscovering network");
        network.startDiscoveryProcess();
    }

    // <editor-fold defaultstate="open" desc="SendMethods">
    private void SendData(byte[] dataToSend, XBee64BitAddress sendTo) {
        try {
            // Obtain the remote XBee device from the XBee network.
            RemoteXBeeDevice remoteDevice = network.getDevice(sendTo);
            if (remoteDevice == null) {
                OutputTextArea.append("\nCouldn't find the remote XBee device with '" + sendTo + "'64bit address.");
            }

            OutputTextArea.append(String.format("\nSending data to %s >> %s | %s... ", remoteDevice.get64BitAddress(),
                    new String(dataToSend)));

            device.sendData(remoteDevice, dataToSend);

            OutputTextArea.append("\nSuccess");

        } catch (XBeeException e) {
            OutputTextArea.append("\nError sending data to specific address: ");
            e.printStackTrace();
        }
    }

    private void BroadcastData(String toSend) {
        try {
            OutputTextArea.append("\n"
                    + String.format("Sending broadcast data: '%s'...%n",
                            new String(toSend)));
            device.sendBroadcastData(toSend.getBytes());

            OutputTextArea.append("\nSuccess");

        } catch (XBeeException e) {
            OutputTextArea.append("\nError:\n" + e.getMessage());
        }
    }
    // </editor-fold> 

    // <editor-fold defaultstate="open" desc="Listeners">
    @Override
    public void dataReceived(XBeeMessage xbeeMessage) {
        OutputTextArea.append("\n"
                + String.format("From %s >> %s | %s%n", xbeeMessage.getDevice()
                        .get64BitAddress(), new String(xbeeMessage.getData())));
    }

    @Override
    public void deviceDiscovered(RemoteXBeeDevice discoveredDevice) {

        RemoteDevicesList.append("\n"
                + String.format(">> Device discovered: %s%n",
                        discoveredDevice.toString()));
    }

    @Override
    public void discoveryError(String error) {
        RemoteDevicesList.append("\n>> There was an error discovering devices: "
                + error);
    }

    @Override
    public void discoveryFinished(String error) {
        if (error == null) {
            RemoteDevicesList
                    .append("\n>> Discovery process finished successfully.");
        } else {
            RemoteDevicesList
                    .append("\n>> Discovery process finished due to the following error: "
                            + error);
        }

    }

    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        OutputTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        RemoteDevicesList = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        OutputTextArea.setColumns(20);
        OutputTextArea.setLineWrap(true);
        OutputTextArea.setRows(5);
        jScrollPane1.setViewportView(OutputTextArea);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        RemoteDevicesList.setColumns(20);
        RemoteDevicesList.setLineWrap(true);
        RemoteDevicesList.setRows(5);
        jScrollPane2.setViewportView(RemoteDevicesList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
// </editor-fold>  

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if (args.length > 0) {
            PORT = args[0];
        }
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
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea OutputTextArea;
    private javax.swing.JTextArea RemoteDevicesList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}