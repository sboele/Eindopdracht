package helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.Logger;

public class OneRModel {
	
	private Data data;
	
	//Skip the first column with the names
	private int firstColumn = 1;
	private int numberOfColumns = 5;
	
	public OneRModel(boolean isUsingRulesForOrdinalData) {
		data = new Data(isUsingRulesForOrdinalData);
	}

	public List<List<String>> getOneRModel() {
		List<List<String>> oneRModel = new ArrayList<List<String>>();
		
		List<String> indices = getIndices(); 
		List<String> attributes = getAttributes();
				
		oneRModel.add(getTableHeader());
		for (int i = 0; i < attributes.size(); i++) {
			List<String> row = new ArrayList<String>();
			String attribute = attributes.get(i);
			
			row.add(indices.get(i));
			row.add(attribute);
			oneRModel.add(row);
			
			for (int j = 0; j < getRulesPerAttribute(attribute).size(); j++) {
				List<String> newRow = new ArrayList<String>();
				addEmptyRowsToList(newRow, 2);
				newRow.add(getRulesPerAttribute(attribute).get(j));
				newRow.add(getErrorsFieldPerRule(attribute).get(j));
				oneRModel.add(newRow);
			}
			
			addEmptyRowsToList(row, 2);
			row.add(getErrorsFieldPerAttribute(attribute));
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
		List<String> attributes = data.getAttributes();
		List<String> tableHeader = new ArrayList<String>();
		for (int i = firstColumn; i < numberOfColumns; i++)
			tableHeader.add(attributes.get(i));	
		return tableHeader;
	}
	
	public List<String> getRulesPerAttribute(String attribute) {
		List<String> uniqueValuesForAttribute = data.getUniqueValuesForAttributeInOrdinalData(data.getIndexOfAttribute(attribute), false);
		List<String> rulesPerAttribute = new ArrayList<String>();
		for (String uniqueValue : uniqueValuesForAttribute) {
			if (data.getNumberOfGering(uniqueValue) > data.getNumberOfMerkbaar(uniqueValue))
				rulesPerAttribute.add(uniqueValue + " -> Gering");
			else
				rulesPerAttribute.add(uniqueValue + " -> Merkbaar");
		}
		return rulesPerAttribute;
	}
	
	public List<String> getErrorsFieldPerRule(String attribute) {
		List<String> uniqueValuesForAttribute = data.getUniqueValuesForAttributeInOrdinalData(data.getIndexOfAttribute(attribute), false);
		Map<String, Integer> groupedValuesForAttribute = data.getGroupedValuesForAttributeInOrdinalData(data.getIndexOfAttribute(attribute), false);
		List<String> errorsPerRule = new ArrayList<String>();
		for (String uniqueValue : uniqueValuesForAttribute) {
			String errors = getErrorsPerRule(uniqueValue) + "/" + groupedValuesForAttribute.get(uniqueValue);
			errorsPerRule.add(errors);	
		}
		return errorsPerRule;
	}
	
	public int getErrorsPerRule(String value) {
		return data.getNumberOfGering(value) > data.getNumberOfMerkbaar(value) ? data.getNumberOfMerkbaar(value) : data.getNumberOfGering(value);
	}
	
	public String getErrorsFieldPerAttribute(String attribute) {
		List<String> valuesForAttribute = data.getValuesForAttributeInOrdinalData(data.getIndexOfAttribute(attribute), false);
		return getErrorsPerAttribute(attribute) + "/" + valuesForAttribute.size();
	}
	
	public int getErrorsPerAttribute(String attribute) {
		List<String> uniqueValuesForAttribute = data.getUniqueValuesForAttributeInOrdinalData(data.getIndexOfAttribute(attribute), false);
		int errors = 0;
		for(String value : uniqueValuesForAttribute) {
			errors += getErrorsPerRule(value);
		}
		return errors;
	}
	
	private void addEmptyRowsToList(List<String> list, int numberOfRows) {
		for (int i = 0; i < numberOfRows; i++)
			list.add(null);
	}
	
	public String getAttributeWithLeastErrors() {
		String attributetWithLeastErrors = new String();
		int leastErrors = Integer.MAX_VALUE;
		for (String attribute: getAttributes()) {
			if (getErrorsPerAttribute(attribute) < leastErrors) {
				leastErrors = getErrorsPerAttribute(attribute);
				attributetWithLeastErrors = attribute;
			}
		}
		return attributetWithLeastErrors;
	}
}
