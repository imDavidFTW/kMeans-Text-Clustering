package cluster;

import java.util.LinkedList;

public class ClusteringAlgorithm{
    private Group group;
    private LinkedList<Integer>[] cluster;
    private int[] centroid;
    private int maxIteration;

    @SuppressWarnings("unchecked")
    public ClusteringAlgorithm(int numCluster, Group group, int maxIteration){
        this.group = group;
        this.maxIteration = maxIteration;
        this.centroid = new int[numCluster];
        this.cluster = new LinkedList[numCluster];
        for(int i = 0; i < numCluster; i++){
            centroid[i] = i;
            this.cluster[i] = new LinkedList<Integer>();
        }
        kMeans();
    }

    private void kMeans(){
        int n = group.size();
        int iteration = 0;
        while(iteration < maxIteration){
            for(int i = 0; i < cluster.length; i++){
                cluster[i].clear();
            }
            for(int x = 0; x < n; x++){
                double max = 0;
                int maxIndex = 0;
                for(int i = 0; i < cluster.length; i++){
                    double temp = group.similarity(centroid[i], x);
                    if(temp > max){
                        maxIndex = i;
                        max = temp;
                    }
                }
                cluster[maxIndex].add(x);
            }
            for(int i = 0; i < cluster.length; i++){
                centroid[i] = findCentroid(cluster[i], group);
            }
            iteration++;
        }
    }

    private int findCentroid(LinkedList<Integer> cluster, Group group){
        double max = 0;
        int centroid = 0;
        for(int num : cluster){
            double sum = 0;
            for(int num2 : cluster){
                sum = sum + group.similarity(num, num2);
                if(sum > max){
                    centroid = num;
                    max = sum;
                }
            }
        }
        return centroid;
    }

    public LinkedList<Integer> getCluster(int i){
        return cluster[i];
    }
}