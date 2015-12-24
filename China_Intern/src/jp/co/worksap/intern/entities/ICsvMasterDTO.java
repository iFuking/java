package jp.co.worksap.intern.entities;

import java.io.Serializable;

public interface ICsvMasterDTO extends Serializable {
	
	public String[] toCSVHeaders();
	
	public String[] toCSVStringArray();
}
