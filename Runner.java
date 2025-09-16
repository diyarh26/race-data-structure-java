public class Runner {
    public final RunnerID id;
    public float MinRun;
    public float AvgRun;
    public float SumOfRun;
    public int CountOfRuns;
    public TwoThreeTree<RunKey, Float> RunnerRunsTree;

    public Runner(RunnerID id) {
        this.id = id;
        MinRun = Float.MAX_VALUE;
        AvgRun = Float.MAX_VALUE;
        SumOfRun = 0;
        CountOfRuns = 0;
        RunnerRunsTree = new TwoThreeTree<>();
    }

    public void setMinRun(float MinRun) {
        this.MinRun = MinRun;
    }

    public float getMinRun() {
        return this.MinRun;
    }

    public void setAvgRun(float SumOfRuns, int CountOfRuns) {
        this.AvgRun = (SumOfRuns / CountOfRuns);
    }

    public float getAvgRun() {
        return this.AvgRun;
    }
    public void setAvgRun(float avg) {
        this.AvgRun = avg;
    }
    public void addRunToRunner(float time) {
        if (time < 0) {
            throw new IllegalArgumentException("Run Time Cannot be Negative");
        }
        RunKey temp = new RunKey(time);
        if(this.RunnerRunsTree.contains(temp)){
            throw new IllegalArgumentException("Run Already Exist");
        }
        this.RunnerRunsTree.insert(temp,time);
        if (time < this.MinRun) {
            setMinRun(time);
        }
        SumOfRun += time;
        CountOfRuns++;
        setAvgRun(SumOfRun, CountOfRuns);
    }
    public void removeRunFromRunner(float time) {
        RunKey temp = new RunKey(time);
        if(!this.RunnerRunsTree.contains(temp)){
            throw new IllegalArgumentException("Run Does Not Exist");
        }
        this.RunnerRunsTree.delete(temp);
        CountOfRuns--;
        if (CountOfRuns == 0) {
            setMinRun(Float.MAX_VALUE);
            setAvgRun(Float.MAX_VALUE);
        } else {
            if (MinRun == time) {
                setMinRun(RunnerRunsTree.getData(RunnerRunsTree.getMinVal()));
            }
            SumOfRun -= time;
            setAvgRun(SumOfRun, CountOfRuns);
        }
    }

}