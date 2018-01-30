/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*15BCE0489*/
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
/**
 *
 * @author Yash Agarwal
 */
public class PacketContents implements PacketReceiver {
    public void receivePacket(Packet packet)
    {
        GUI.TA_OUTPUT.append(
        packet.toString() + 
                "\n-----------------------------------------------"+
                "---------------------------------------------------\n\n");
    }
    
}
