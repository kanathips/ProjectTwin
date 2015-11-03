package com.projecttwin.handeller;
import com.badlogic.gdx.utils.TimeUtils;
/**
 * This class use to be Timer 
 * @author NewWy
 *
 */
public class Timer {
	//TODO Create time counter
    private long start;
    private long secsToWait;

    public Timer(long secsToWait)
    {
    	setTime(secsToWait);
    }

    /**
     * start the timer 
     */
    public void start()
    {
    	start = TimeUtils.millis() / 1000;
    }
    
    /**
     * set time to wait in second
     * @param secsToWait
     */
    public void setTime(long secsToWait){
    	this.secsToWait = secsToWait;
    }

    /**
     * get time left
     * @return time left
     */
    public long getTimeLeft(){
    	return secsToWait - getTimePass();
    }
    
    public long getTimePass(){
    	return (TimeUtils.millis() / 1000 - start);
    }
    
    /**
     * 
     * @return status of timer is complete or not
     */
    public boolean hasCompleted()
    {
        return  getTimeLeft() <= 0;
    }
    
    public String toString(){
    	return "[ Time Target : " + secsToWait + " Time Left : " + getTimeLeft() + " ]";
    }
}