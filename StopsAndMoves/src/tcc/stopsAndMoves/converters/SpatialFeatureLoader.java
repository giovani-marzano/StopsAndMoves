package tcc.stopsAndMoves.converters;

import java.awt.geom.Path2D;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import tcc.stopsAndMoves.SpatialFeature;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.Loader;

/**
 * Carrega as regiões de interesse de duas fontes (Loaders): uma que contem o
 * id da região e sua duração minima e outra que contem os pontos que delimitam
 * a região.
 * 
 * <p>
 * Esta implementação assume que os dados estão organizados (ordem dos atributos)
 * da seguinte forma:
 * </p>
 * <dl>
 * <dt>RegionLoader:</dt>
 * <dd><ol>
 *   <li>Id da região</li>
 *   <li>Tempo (duração) mínimo para a região</li>
 * </ol></dd>
 * </dl>
 * <dl>
 * <dt>RegionPointsLoader:</dt>
 * <dd><ol>
 *  <li>Id da região.
 *      <p>A implementação assume que os dados estão agrupados pelo
 *      Id da região, ou seja, pontos da mesma região aparecem em sequência.</p>
 *  </li>
 *  <li>Indice do ponto na região ( representa a ordem que os pontos devem ser
 *      considerados para montar o poligono que delimita a região ).
 *      <p>A implementação assume que os pontos de uma região são lidos em
 *      ordem crescente de seus indices.</p>
 *  </li>
 *  <li>Latitude do ponto</li>
 *  <li>Longitude do ponto</li>
 * </ol></dd>
 * </dl>
 * 
 * @author giovani
 *
 */
public class SpatialFeatureLoader {
	public static final int INDEX_SPID = 0;
	public static final int INDEX_MINTIME = 1;
	public static final int INDEX_INDEX = 1;
	public static final int INDEX_LAT = 2;
	public static final int INDEX_LON = 3;
	
	private Loader regionLoader = null;
	private Loader regionPointsLoader = null;
	
	private HashMap<Long, SpatialFeature> hashTab;
	
	public SpatialFeatureLoader() {
		hashTab = new HashMap<>();
	}
	
	private SpatialFeature getSpatialFeature(long id) {
		SpatialFeature sp = null;
		
		sp = hashTab.get(id);
		
		if (sp == null) {
			sp = new SpatialFeature(id);
			hashTab.put(id, sp);
		}
		
		return sp;
	}
	
	private void loadSpatialFeatures() throws IOException {
		if (regionLoader == null) {
			throw new IOException("SpatialFeature Loader not defined");
		}
		Instances inst = regionLoader.getDataSet();
		
		Enumeration<?> en = inst.enumerateInstances();
		
		while (en.hasMoreElements()) {
			Instance spInst = (Instance) en.nextElement();
			SpatialFeature sp = getSpatialFeature((long)spInst.value(INDEX_SPID));
			
			sp.setMinimunTime(spInst.value(INDEX_MINTIME));
		}
	}
	
	private void loadRegionsPoints() throws IOException {
		if (regionPointsLoader == null) {
			throw new IOException("SpatialFeature points Loader not defined");
		}
		
		Instances inst = regionPointsLoader.getDataSet();
		Enumeration<?> en = inst.enumerateInstances();
		
		Path2D.Double polygon = null;
		long lastID = Long.MIN_VALUE;
		
		while (en.hasMoreElements()) {
			Instance pointInst = (Instance) en.nextElement();
			long id = 0;
			double lat, lon;
			
			id = (long) pointInst.value(INDEX_SPID);
			lat = pointInst.value(INDEX_LAT);
			lon = pointInst.value(INDEX_LON);
			
			if (id != lastID) {
				if (polygon != null) {
					// Fecha poligono anterior
					polygon.closePath();
					
					SpatialFeature sp = getSpatialFeature(lastID);
					sp.setArea(polygon);
				}
				
				// Primeiro ponto do poligono
				polygon = new Path2D.Double();
				polygon.moveTo(lon, lat);
				lastID = id;
			}
			else {
				// Ponto que continua poligono anterior
				polygon.lineTo(lon, lat);
			}
		}
		
		// Fechando o ultimo poligono
		if ( polygon != null ) {
			polygon.closePath();
			
			SpatialFeature sp = getSpatialFeature(lastID);
			sp.setArea(polygon);
		}
	}
	
	public Set<SpatialFeature> getSpatialFeatures() throws IOException {
		// Carrega as definiçoes das regiões
		loadSpatialFeatures();
		// Carrega as áreas das regiões
		loadRegionsPoints();
		
		Set<SpatialFeature> set = new HashSet<>(hashTab.values());
		
		return set;
	}

	public Loader getRegionLoader() {
		return regionLoader;
	}

	public void setRegionLoader(Loader regionLoader) {
		this.regionLoader = regionLoader;
	}

	public Loader getRegionPointsLoader() {
		return regionPointsLoader;
	}

	public void setRegionPointsLoader(Loader regionPointsLoader) {
		this.regionPointsLoader = regionPointsLoader;
	}
	
}
