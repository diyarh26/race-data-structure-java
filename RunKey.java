public class RunKey extends RunnerID {
    protected float runTime;
    RunKey(float runTime){
        this.runTime = runTime;
    }
    @Override
    public boolean isSmaller(RunnerID other) {
        if(other.getClass().equals(RunnerIDInfinty.class))
            return true;
        else if (other.getClass().equals(RunnerIDMInfinty.class)) {
            return false;
        }
        else {
            return this.runTime < ((RunKey) other).runTime;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.runTime);
    }
}