import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
 
public class RoadController {
 
    public static void main(String[] args) 
    {
        final Road road = new Road();
         
        Thread east = new Thread( new Runnable() {
             
         @Override
         public void run() {
          
             while(true)
             {
                 Village village = new Village(road);
                 Thread eastThread = new Thread(village);
                 village.setName("East Villager: "+ eastThread.getId());
                 eastThread.start();
                 try
                 {
                    long sleepTime = (long)(Math.random()*10);
                    TimeUnit.SECONDS.sleep(sleepTime);
                 }
                 catch(Exception e)
                 {
                     System.out.println(e);
                 }
             }
              
         }
     });
      
     Thread west = new Thread( new Runnable() {
          
         @Override
         public void run() {
              
             while(true)
             {
                 Village village = new Village(road);
                 Thread westThread = new Thread(village);
                 village.setName("West Villager: "+ westThread.getId());
                 westThread.start();
                 try
                 {
                    long sleepTime = (long)(Math.random()*10);
                     TimeUnit.SECONDS.sleep(sleepTime);
                 }
                 catch(Exception e)
                 {
                     System.out.println(e);
                 }
             }
         }
     });
         
        east.start();
        west.start();
    }
 
}
 
class Road
{
    private final Semaphore semaphore;
     
    public Road()
    {
        semaphore = new Semaphore(1);
    }
    public void crossRoad(Village village)
    {
        try
        {
            System.out.printf(" %s wants to cross the road.\n", village.getName());
            semaphore.acquire();
            System.out.printf(" %s is crossing the road.\n", village.getName());
            TimeUnit.SECONDS.sleep((long)(Math.random() * 10));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            System.out.printf(" %s has finished crossing the road.\n",village.getName());
            semaphore.release();
        }
    }
}
 
class Village implements Runnable
{
    private String name;
    private Road road;
     
    public Village(Road road)
    {
        this.road = road;
    }
     
    public void run()
    {
        road.crossRoad(this);
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
}