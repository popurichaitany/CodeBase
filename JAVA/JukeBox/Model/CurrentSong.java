/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 *
 * @author Anand
 */
public class CurrentSong {
    private static String fleName,title="NA",album="NA",artist="NA",duration="NA",min="00",sec="00";
    private static long length;
    private static int mins,seconds;
    private static Mp3File m3p;
    private static ID3v2 id3v2Tag;
    public CurrentSong(String fname)
    {
        try
        {
            
            fleName=fname;
            m3p=new Mp3File(fname);
            length=m3p.getLengthInSeconds();
            mins=(int)length/60;
            seconds=(int)length%60;
            calcDuration(seconds,mins);
            id3v2Tag=m3p.getId3v2Tag();
            if(!id3v2Tag.getTitle().equals(""))
                title=id3v2Tag.getTitle();
            if(!id3v2Tag.getAlbum().equals(""))
                album =id3v2Tag.getAlbum();
            if(!id3v2Tag.getArtist().equals(""))
                artist =id3v2Tag.getArtist();
        }catch(IOException ioe)   
        {
                    
        }catch(UnsupportedTagException uex)
        {
            
        }catch(InvalidDataException ide)
        {
                
        }catch(NullPointerException npex)
        {
            try
            {
                File f=new File(fleName);
                title=f.getName(); 
                ID3v1 idv1Tag=m3p.getId3v1Tag();
                if(!idv1Tag.getTitle().equals(""))
                    title=idv1Tag.getTitle();
                if(!idv1Tag.getAlbum().equals(""))
                    album =idv1Tag.getAlbum();
                if(!idv1Tag.getArtist().equals(""))
                    artist =idv1Tag.getArtist();
            }catch(NullPointerException npe)
            {
                System.out.println(npe.getMessage());
                try
                {
                    File f=new File(fleName);
                    title=f.getName();  
                    AudioFileFormat m_aff=AudioSystem.getAudioFileFormat(f);
                    Map<?,?> mp3_prop=((TAudioFileFormat)m_aff).properties();
                    Long mcr=(Long)mp3_prop.get("duration");
                    int ms=(int)(mcr/1000);
                    seconds=(ms/1000)%60;
                    mins=(ms/1000)/60;
                    calcDuration(seconds,mins);
                }catch(UnsupportedAudioFileException | IOException ex)
                {
                    System.out.println(ex.getMessage());
                    
                }
            }
        }
        
    }
    
    private static void calcDuration(int s,int m)
    {
        min=""+m;
        if(seconds<10)
        {
            sec="0"+s;
        }
        else
        {
            sec=""+s;
        }
        duration=min+":"+sec;
    }
    public String getFleName() {
        return fleName;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public String getMin() {
        return min;
    }

    public String getSec() {
        return sec;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    
}
