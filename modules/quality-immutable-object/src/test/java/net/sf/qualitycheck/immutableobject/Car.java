package net.sf.qualitycheck.immutableobject;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public interface Car extends Serializable {

	@Nonnull
	List<String> getCodes();

	@Nonnegative
	int getWheels();

	@Nonnull
	String getName();
	
}
