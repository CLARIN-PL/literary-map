package pl.edu.pwr.litmap.heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import pl.edu.pwr.litmap.textobjects.NameClass;
import pl.edu.pwr.litmap.textobjects.NamedTextobject;
import pl.edu.pwr.litmap.textobjects.Textobject;
import scala.collection.mutable.StringBuilder;

public class LocationNameHeuristicResult {
	
	public static final LocationNameHeuristicResult EMPTY = new LocationNameHeuristicResult();

	private SortedMap<Integer, ArrayList<NamedTextobject>> textobjectsLayers = new TreeMap<>(Collections.reverseOrder());
    
    public void addTextobject(Textobject to) {
    	ArrayList<NamedTextobject> layer = textobjectsLayers.get(to.getNameClass().getTopologicalHierarchyOrderNumber());
    	if (layer == null) {
    		layer = new ArrayList<NamedTextobject>();
    		textobjectsLayers.put(to.getNameClass().getTopologicalHierarchyOrderNumber(), layer);
    		
    	}
    	addTextobjectToLayer(to, layer);
	}
	
	public boolean containTextobject(Textobject to) {
		ArrayList<NamedTextobject> layer = textobjectsLayers.get(to.getNameClass().getTopologicalHierarchyOrderNumber());
		if (layer == null) {
			return false;
		} else {
			return layer.contains(to);
		}
	}
	
	public void addTextobjectToLayer(Textobject to, ArrayList<NamedTextobject> layer) {
		layer.add(NamedTextobject.create(to, to.getBaseName()));
		if (!to.getBaseName().equals(to.getForceBaseName())) {
			layer.add(NamedTextobject.create(to, to.getForceBaseName()));
		}
		if (!to.getRawText().equals(to.getBaseName()) && !to.getRawText().equals(to.getForceBaseName())) {
			layer.add(NamedTextobject.create(to, to.getRawText()));
		}
	}
	
	public List<List<NamedTextobject>> getTextobjectsLayersList() {
		List<List<NamedTextobject>> layerList = new ArrayList<>();
		for (Map.Entry<Integer, ArrayList<NamedTextobject>> layer : this.textobjectsLayers.entrySet()) {
			layerList.add(layer.getValue());
		}
		return layerList;
	}

	public int getNumberOfLayers() {
		return this.textobjectsLayers.size();
	}
	
	public boolean containtsTextobjectWithTopologicalHierarchyOrderNumber(int topologicalHierarchyOrderNumber) {
		ArrayList<NamedTextobject> layer = textobjectsLayers.get(topologicalHierarchyOrderNumber);
		return layer != null;
	}

	public boolean containtsTextobjectWithNameClass(NameClass nameclass) {
		return containtsTextobjectWithTopologicalHierarchyOrderNumber(nameclass.getTopologicalHierarchyOrderNumber());
	}
	
//	public String getResultString() {
//		ArrayList<Textobject> tos = getResultTextobjects();
//		
//		StringBuilder sb = new StringBuilder();
//		for (Textobject to : tos) {
//			if (sb.length() > 0) {
//				sb.append(LOCATION_TEXT_SEARCH_SEPARATOR);
//			}
//			sb.append(to.getBaseName());
//		}
//		
//		return sb.toString();
//	}
	
	public LocationNameHeuristicResult clone() {
		LocationNameHeuristicResult locationNameHeuristicResult = new LocationNameHeuristicResult();
		locationNameHeuristicResult.textobjectsLayers = new TreeMap<Integer, ArrayList<NamedTextobject>>(Collections.reverseOrder());		for (int key : this.textobjectsLayers.keySet()) {
			locationNameHeuristicResult.textobjectsLayers.put(key, new ArrayList<NamedTextobject>(this.textobjectsLayers.get(key)));
		}
		return locationNameHeuristicResult;
	}
	
}
