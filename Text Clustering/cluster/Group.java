package cluster;

public interface Group 
{
	public int size();
	public double similarity(int i, int j);
}