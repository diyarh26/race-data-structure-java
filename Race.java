public class Race {
    private TwoThreeTree<RunnerIDExtent,Runner> IDRunnersTree;
    private TwoThreeTree<RunnerIDFloat,Runner> MinRunRunnersTree;
    private TwoThreeTree<RunnerIDFloat,Runner> AvgRunRunnersTree;
    private boolean initRace = false;
    public void init()
    {
        IDRunnersTree = new TwoThreeTree<>();
        MinRunRunnersTree = new TwoThreeTree<>();
        AvgRunRunnersTree = new TwoThreeTree<>();
        initRace = true;
    }
    public void addRunner(RunnerID id)
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        RunnerIDExtent newId = new RunnerIDExtent(id);
        if(IDRunnersTree.contains(newId)){
            throw new IllegalArgumentException("Runner Already Exist");
        }
        Runner runner = new Runner(id);
        Float avg = runner.getAvgRun();
        Float min = runner.getMinRun();
        RunnerIDFloat minKey = new RunnerIDFloat(newId,min);
        RunnerIDFloat avgKey = new RunnerIDFloat(newId,avg);
        IDRunnersTree.insert(newId,runner);
        MinRunRunnersTree.insert(minKey,runner);
        AvgRunRunnersTree.insert(avgKey,runner);
    }
    public void removeRunner(RunnerID id)
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        RunnerIDExtent newId = new RunnerIDExtent(id);
        if(!IDRunnersTree.contains(newId)){
            throw new IllegalArgumentException("Runner Does Not Exist");
        }
        Float temp = IDRunnersTree.getData(newId).getAvgRun();
        Float temp2 = IDRunnersTree.getData(newId).getMinRun();
        RunnerIDFloat key = new RunnerIDFloat(newId,temp);
        AvgRunRunnersTree.delete(key);
        RunnerIDFloat key2 = new RunnerIDFloat(newId,temp2);
        MinRunRunnersTree.delete(key2);
        IDRunnersTree.delete(newId);
    }

    public void addRunToRunner(RunnerID id, float time)
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        RunnerIDExtent newId = new RunnerIDExtent(id);
        if(!IDRunnersTree.contains(newId)){
            throw new IllegalArgumentException("Runner Does Not Exist");
        }
        Runner runner = IDRunnersTree.getData(newId);
        RunKey temp5 = new RunKey(time);
        Float oldAvg = runner.getAvgRun();
        Float oldMin = runner.getMinRun();
        RunnerIDFloat minKey = new RunnerIDFloat(newId,oldMin);
        RunnerIDFloat avgKey = new RunnerIDFloat(newId,oldAvg);
        if(!(AvgRunRunnersTree.contains(avgKey)) && !(MinRunRunnersTree.contains(minKey))){
            runner.addRunToRunner(time);
            Float newAvg = runner.getAvgRun();
            Float newMin = runner.getMinRun();
            RunnerIDFloat key1 = new RunnerIDFloat(newId,newAvg);
            RunnerIDFloat key2 = new RunnerIDFloat(newId,newMin);
            AvgRunRunnersTree.insert(key1,runner);
            MinRunRunnersTree.insert(key2,runner);
        } else {
            if(runner.RunnerRunsTree.contains(temp5))
            {
                throw new IllegalArgumentException("Run Already Exist");
            }
            if (time < 0) {
                throw new IllegalArgumentException("Run Time Cannot be Negative");
            }
            AvgRunRunnersTree.delete(avgKey);
            MinRunRunnersTree.delete(minKey);
            runner.addRunToRunner(time);
            Float newAvg = runner.getAvgRun();
            Float newMin = runner.getMinRun();
            RunnerIDFloat key1 = new RunnerIDFloat(newId,newAvg);
            RunnerIDFloat key2 = new RunnerIDFloat(newId,newMin);
            AvgRunRunnersTree.insert(key1,runner);
            MinRunRunnersTree.insert(key2,runner);
        }
    }
    public void removeRunFromRunner(RunnerID id, float time)
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        RunnerIDExtent newId = new RunnerIDExtent(id);
        if(!IDRunnersTree.contains(newId)){
            throw new IllegalArgumentException("Runner Does Not Exist");
        }
        Runner runner = IDRunnersTree.getData(newId);
        RunKey temp5 = new RunKey(time);
        if(!(runner.RunnerRunsTree.contains(temp5)))
        {
            throw new IllegalArgumentException("Run Does Not Exist");
        }
        Float temp = runner.getAvgRun();
        Float temp2 = runner.getMinRun();
        RunnerIDFloat key = new RunnerIDFloat(newId,temp);
        RunnerIDFloat key2 = new RunnerIDFloat(newId,temp2);
        if((AvgRunRunnersTree.contains(key)) && (MinRunRunnersTree.contains(key2))){
            AvgRunRunnersTree.delete(key);
            MinRunRunnersTree.delete(key2);
            runner.removeRunFromRunner(time);
            Float temp3 = runner.getAvgRun();
            Float temp4 = runner.getMinRun();
            RunnerIDFloat key3 = new RunnerIDFloat(newId,temp3);
            RunnerIDFloat key4 = new RunnerIDFloat(newId,temp4);
            AvgRunRunnersTree.insert(key3,runner);
            MinRunRunnersTree.insert(key4,runner);
        }
    }

    public RunnerID getFastestRunnerAvg()
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        if(IDRunnersTree.getElements()==0){
            throw new IllegalArgumentException("No Runner Have Entered The Race");
        }
        return AvgRunRunnersTree.getMinVal().getId();
    }
    public RunnerID getFastestRunnerMin()
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        if(IDRunnersTree.getElements()==0){
            throw new IllegalArgumentException("No Runner Have Entered The Race");
        }
        return MinRunRunnersTree.getMinVal().getId();
    }

    public float getMinRun(RunnerID id)
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        if(IDRunnersTree.getElements()==0){
            throw new IllegalArgumentException("No Runner Have Entered The Race");
        }
        RunnerIDExtent newId = new RunnerIDExtent(id);
        return IDRunnersTree.getData(newId).getMinRun();
    }
    public float getAvgRun(RunnerID id){
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        if(IDRunnersTree.getElements()==0){
            throw new IllegalArgumentException("No Runner Have Entered The Race");
        }
        RunnerIDExtent newId = new RunnerIDExtent(id);
        return IDRunnersTree.getData(newId).getAvgRun();
    }

    public int getRankAvg(RunnerID id)
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        RunnerIDExtent newId = new RunnerIDExtent(id);
        Runner runner = IDRunnersTree.getData(newId);
        Float temp = runner.getAvgRun();
        RunnerIDFloat key = new RunnerIDFloat(newId,temp);
        return AvgRunRunnersTree.rank(key);
    }

    public int getRankMin(RunnerID id)
    {
        if(initRace==false)
        {
            throw new IllegalArgumentException("Race Have Not Started");
        }
        RunnerIDExtent newId = new RunnerIDExtent(id);
        Runner runner = IDRunnersTree.getData(newId);
        Float temp = runner.getMinRun();
        RunnerIDFloat key = new RunnerIDFloat(newId,temp);
        return MinRunRunnersTree.rank(key);
    }
}