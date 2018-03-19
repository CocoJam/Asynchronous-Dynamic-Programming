import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agents implements Runnable {
    public int getH() {
        return H;
    }

    public void setH(int h) {
        H = h;
    }

    public Map<Agents, Integer> getWeightsout() {
        return weightsout;
    }

    public void setWeightsout(Map<Agents, Integer> weightsout) {
        this.weightsout = weightsout;
    }

    private int H = -1;
    private Map<Agents, Integer> weightsout = new HashMap<>();
    private List<Agents> weightsin = new ArrayList<>();


    public Agents suc(Agents A) throws AgentsNonExsistanceError {
        if (!weightsout.containsKey(A)) {
            throw new AgentsNonExsistanceError();
        } else {
            return A;
        }
    }

    public void w() throws AgentsNonExsistanceError {
        for (Agents agents : weightsout.keySet()) {
            if (!weightsout.containsKey(agents)) {
                throw new AgentsNonExsistanceError();
            } else {
                int updateNumber = agents.getH() + weightsout.get(agents);
                if (updateNumber < H){
                    H = updateNumber;
                }
            }
        }
    }
    public void unidirectionTo (Agents A, int weights){
        this.weightsout.put(A,weights);
        A.backdirectionTo(this);
    }

    public void backdirectionTo (Agents A){
        weightsin.add(A);
    }

    public void requireUpdateH () throws AgentsNonExsistanceError {
        synchronized (Thread.currentThread()){
            this.w();
            System.out.println("Try notify");
            Thread.currentThread().notify();
        }
    }

    @Override
    public void run() {
        synchronized (Thread.currentThread()){
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Agents agents : weightsin) {
                    try {
                        agents.requireUpdateH();
                    } catch (AgentsNonExsistanceError agentsNonExsistanceError) {
                        agentsNonExsistanceError.printStackTrace();
                    }
                }
            }
        }
    }
}
