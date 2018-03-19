public class AgentThreads extends Thread {
    private Agents A;
    private Thread thread;
    public AgentThreads(Agents a) {
        super(a);
        A = a;
    }
    public Agents getA() {
        return A;
    }

    public void setA(Agents a) {
        A = a;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public void link (Agents agents, int weights){
        A.unidirectionTo(agents,weights);
    }
}
