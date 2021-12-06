package ioc;

import ioc.ITodoPersistance;

public class FileTodoPersistance implements ITodoPersistance {

	@Override
	public void create() {
		System.out.println("FileTodoPersistance.create ½ÇÇà");
	}

}
