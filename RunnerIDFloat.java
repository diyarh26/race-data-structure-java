public class RunnerIDFloat extends RunnerID{

    protected RunnerIDExtent id;
    protected float fId;
    public RunnerIDFloat(RunnerIDExtent id,float fId){
        super();
        this.id = id;
        this.fId = fId;
    }

    @Override
    public boolean isSmaller(RunnerID other) {
        if (other.getClass().equals(RunnerIDInfinty.class))
            return true;
        else if (other.getClass().equals(RunnerIDMInfinty.class)) {
            return false;
        } else {
            if (this.fId == ((RunnerIDFloat) other).fId) {
                return this.id.isSmaller(((RunnerIDFloat) other).id);
            }
            return this.fId < ((RunnerIDFloat) other).fId;
        }
    }
    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
    public RunnerID getId() {
        return id.getId();
    }
}