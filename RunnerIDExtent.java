public class RunnerIDExtent extends RunnerID {

    protected RunnerID id;
    public RunnerIDExtent(RunnerID id){
        super();
        this.id=id;
    }
    @Override
    public boolean isSmaller(RunnerID other) {
        if(other.getClass().equals(RunnerIDInfinty.class))
            return true;
        else if (other.getClass().equals(RunnerIDMInfinty.class)) {
            return false;
        }
        else {
            return this.id.isSmaller(((RunnerIDExtent)other).id);
        }
    }
    @Override
    public String toString() {
        return this.id.toString();
    }

    public RunnerID getId() {
        return id;
    }
}