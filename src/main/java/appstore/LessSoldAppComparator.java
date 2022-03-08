package appstore;

import java.util.Comparator;

public class LessSoldAppComparator implements Comparator<App>
{
	@Override
    public int compare(App app1, App app2) 
	{
       return Integer.compare(app1.getTimesSoldLastWeek(), app1.getTimesSoldLastWeek());
    }
}
