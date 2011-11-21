package helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import play.Logger;

public class OneRModel {
	
	private Data dataHelper;
	
	//Skip the first column with the names
	private int firstColumn = 1;
	private int numberOfColumns = 5;
	
	public OneRModel() {
		dataHelper = new Data();
	}

	public List<List<String>> getOneRModel() {
		List<List<String>> oneRModel = new ArrayList<List<String>>();
		
		List<String> indices = getIndices(); 
		List<String> attributes = getAttributes();
		
		oneRModel.add(getTableHeader());
		for (int i = 0; i < attributes.size(); i++) {
			List<String> row = new ArrayList<String>();
			row.add(indices.get(i));
			row.add(attributes.get(i));
			row.add(dataHelper.getGroupedValuesForAttributeInOrdinalData(dataHelper.getIndexOfAttribute(attributes.get(i)), false).toString());
			oneRModel.add(row);
		}
		
		return oneRModel;
	}
	
	public List<String> getTableHeader() {
		ArrayList<String> tableHeader = new ArrayList<String>();
		tableHeader.add("");
		tableHeader.add("Attribuut");
		tableHeader.add("Rules");
		tableHeader.add("Errors");
		tableHeader.add("Total errors");
		return tableHeader;
	}
	
	public List<String> getIndices() {
		List<String> indices = new ArrayList<String>();
		for (int i = firstColumn; i < numberOfColumns; i++)
			indices.add(String.valueOf(i));
		return indices;
	}
	
	public List<String> getAttributes() {
		List<String> attributes = dataHelper.getAttributes();
		List<String> tableHeader = new ArrayList<String>();
		for (int i = firstColumn; i < numberOfColumns; i++)
			tableHeader.add(attributes.get(i));	
		return tableHeader;
	}
	
}
