/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jukebox;

import DbManip.DataManip;



/**
 *
 * @author Anand
 */
public class JukeBox {
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
            DataManip dm = new DataManip();
            dm.init();
            MusicPlayer mp=new MusicPlayer();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
}
