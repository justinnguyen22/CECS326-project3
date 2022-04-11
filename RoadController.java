import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RoadController {

    // main function
    public static void main(String[] args)
    {
        //create new instance of object Road
        final Road road = new Road();

        //Create east village runnable thread
        Thread east = new Thread( new Runnable() {

            @Override
            public void run() {

                while(true)
                {
                    //create new village object as well as eastVillage thread
                    Village village = new Village(road);
                    Thread eastThread = new Thread(village);
                    // set name and id for east villager
                    village.setName("East Villager: "+ eastThread.getId());
                    // start east Thread
                    eastThread.start();
                    try
                    {
                        // have thread sleep for a random time from 0-10 seconds
                        long sleepTime = (long)(Math.random()*10);
                        TimeUnit.SECONDS.sleep(sleepTime);
                        System.out.println(" East Villager: "+ eastThread.getId() + " eating donut for "+ sleepTime + " seconds");

                    }
                    catch(Exception e)
                    {
                        // if error, print out exception
                        System.out.println(e);
                    }
                }

            }
        });

        //create west Thread
        Thread west = new Thread( new Runnable() {

            @Override
            public void run() {

                while(true)
                {
                    // create new instance of village and westThread
                    Village village = new Village(road);
                    Thread westThread = new Thread(village);
                    // set name and id for west villager
                    village.setName("West Villager: "+ westThread.getId());
                    //start west thread
                    westThread.start();
                    try
                    {
                        // delay thread by sleeping for a random amount of time 0-10 seconds
                        long sleepTime = (long)(Math.random()*10);
                        TimeUnit.SECONDS.sleep(sleepTime);
                        System.out.println(" West Villager: "+ westThread.getId() + " eating donut for "+ sleepTime + " seconds");
                    }
                    catch(Exception e)
                    {
                        // if error print out exception e
                        System.out.println(e);
                    }
                }
            }
        });
        // start both east and west thread for simulation
        east.start();
        west.start();
    }

}

// Road class
class Road
{
    // initialize semaphore
    private final Semaphore semaphore;

    //constructor with semaphore instantiation
    public Road()
    {
        semaphore = new Semaphore(1);
    }
    //function for east or west thread to cross road
    public void crossRoad(Village village)
    {
        try
        {
            // which villager wants to cross the road
            System.out.printf(" %s wants to cross the road.\n", village.getName());
            //acquire semaphore
            semaphore.acquire();
            // Villar is currently crossing the road, should only be 1 at a time
            System.out.printf(" %s is crossing the road.\n", village.getName());
            // sleep thread for a random amount of time between 0-10 seconds
            TimeUnit.SECONDS.sleep((long)(Math.random() * 10));
        }
        catch(Exception e)
        {
            // if error, print out exception e
            System.out.println(e);
        }
        finally
        {
            // Denote that villager has finished crossing the road and release the semaphore
            System.out.printf(" %s has finished crossing the road.\n",village.getName());
            semaphore.release();
        }
    }
}

// Runnable class Village
class Village implements Runnable
{
    // Create fields name and road
    private String name;
    private Road road;

    // village constructor
    public Village(Road road)
    {
        this.road = road;
    }

    // run method, calls crossRoad
    public void run()
    {
        road.crossRoad(this);
    }

    // getter for name of village
    public String getName() {
        return name;
    }

    //setter for name of village
    public void setName(String name) {
        this.name = name;
    }

}
