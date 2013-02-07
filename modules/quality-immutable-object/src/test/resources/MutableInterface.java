package net.sf.qualitycheck.immutableobject;

import java.util.Date;
import java.util.List;

public interface MutableInterface {

	String getName();

	Date getTime();

	List<String> getMessages();

	boolean isActive();

	boolean hasName();

	void setIsUncool(int madInt);

}
