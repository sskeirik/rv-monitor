
package HasNext_2;
import java.util.*;

public class HasNext_2 {
	public static void main(String[] args){
		Vector<Integer> v = new Vector<Integer>();

		v.add(1);
		v.add(2);
		v.add(4);
		v.add(8);

		Iterator i = v.iterator();
		int sum = 0;

		boolean b;
		while(b=i.hasNext()){
			mop.HasNextRuntimeMonitor.hasnexttrueEvent(i,b);
			mop.HasNextRuntimeMonitor.hasnextfalseEvent(i,b);
			mop.HasNextRuntimeMonitor.nextEvent(i);
			sum += (Integer)i.next();
		}
		mop.HasNextRuntimeMonitor.hasnexttrueEvent(i,b);
		mop.HasNextRuntimeMonitor.hasnextfalseEvent(i,b);

		System.out.println("sum: " + sum);
	}
}



