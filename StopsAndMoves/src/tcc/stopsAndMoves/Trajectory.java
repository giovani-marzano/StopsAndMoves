package tcc.stopsAndMoves;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Representa uma trajetória como uma coleção de pontos espaço-temporais
 * amostrados.
 * 
 * @author giovani
 *
 */
public class Trajectory implements List<SamplePoint> {
	/**
	 * Identificaor da trajetoria
	 */
	private final long id;
	
	private List<SamplePoint> pointList;

	@Override
	public void add(int arg0, SamplePoint arg1) {
		pointList.add(arg0, arg1);
	}

	@Override
	public boolean add(SamplePoint arg0) {
		return pointList.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends SamplePoint> arg0) {
		return pointList.addAll(arg0);
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends SamplePoint> arg1) {
		return pointList.addAll(arg0, arg1);
	}

	@Override
	public void clear() {
		pointList.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return pointList.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return pointList.containsAll(arg0);
	}

	@Override
	public SamplePoint get(int arg0) {
		return pointList.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return pointList.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return pointList.isEmpty();
	}

	@Override
	public Iterator<SamplePoint> iterator() {
		return pointList.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return pointList.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<SamplePoint> listIterator() {
		return pointList.listIterator();
	}

	@Override
	public ListIterator<SamplePoint> listIterator(int arg0) {
		return pointList.listIterator(arg0);
	}

	@Override
	public SamplePoint remove(int arg0) {
		return pointList.remove(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		return pointList.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return pointList.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return pointList.retainAll(arg0);
	}

	@Override
	public SamplePoint set(int arg0, SamplePoint arg1) {
		return pointList.set(arg0, arg1);
	}

	@Override
	public int size() {
		return pointList.size();
	}

	@Override
	public List<SamplePoint> subList(int arg0, int arg1) {
		return pointList.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return pointList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return pointList.toArray(arg0);
	}

	public Trajectory(long id) {
		super();
		this.id = id;
	}

	public void setPointsList(List<SamplePoint> pointsList) {
		this.pointList = pointsList;
	}

	public long getId() {
		return id;
	}
}
