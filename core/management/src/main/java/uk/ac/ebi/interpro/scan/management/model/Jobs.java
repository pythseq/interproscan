package uk.ac.ebi.interpro.scan.management.model;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simply holds a List of Jobs.  Only used so that the List can be
 * parameterized to hold only Job objects for use in Spring.
 *
 * @author Phil Jones
 * @author David Binns
 * @version $Id$
 * @since 1.0
 */
public class Jobs {

    private static final Logger LOGGER = Logger.getLogger(Jobs.class.getName());

    private String baseDirectoryTemporaryFiles;

    private Map<String, Job> jobMap;

    private Map<String, Step> stepMap;

    private final Object stepMapLocker = new Object();

    /**
     * Returns all of the jobs of type "analysis".
     *
     * @return all of the jobs of type "analysis".
     */
    public Jobs getAnalysisJobs() {
        List<Job> analysisJobs = new ArrayList<Job>();
        for (Job job : jobMap.values()) {
            if (job.isAnalysis()) {
                analysisJobs.add(job);
            }
        }
        return new Jobs(analysisJobs);
    }

    public List<Job> getJobList() {
        return new ArrayList<Job>(jobMap.values());
    }

    /**
     * Spring constructor
     */
    public Jobs() {
    }

    public Jobs(List<Job> jobList) {
        setJobList(jobList);
    }

    @Required
    public void setJobList(List<Job> jobList) {
        this.jobMap = new HashMap<String, Job>(jobList.size());
        for (Job job : jobList) {
            jobMap.put(job.getId(), job);
        }
    }

    public String getBaseDirectoryTemporaryFiles() {
        return baseDirectoryTemporaryFiles;
    }

    @Required
    public void setBaseDirectoryTemporaryFiles(String baseDirectoryTemporaryFiles) {
        this.baseDirectoryTemporaryFiles = baseDirectoryTemporaryFiles;
    }

    public Job getJobById(String id) {
        return jobMap.get(id);
    }

    public Step getStepById(String stepId) {
        if (stepMap == null) {
            synchronized (stepMapLocker) {
                if (stepMap == null) {
                    this.stepMap = new HashMap<String, Step>();
                    for (Job job : jobMap.values()) {
                        for (Step step : job.getSteps()) {
                            stepMap.put(step.getId(), step);
                        }
                    }
                }
            }
        }
        return stepMap.get(stepId);
    }

    /**
     * Returns a subset of all of the defined jobs
     * that are restricted by the jobIds (bean IDs) passed in.
     * <p/>
     * This is done on the basis that the actual jobId contains the id passed in.
     * <p/>
     * Allows analyses to be restricted, e.g. by command line arguments.
     *
     * @param jobIds being an array of bean IDs to be included.
     * @return a new Jobs object, containing the restricted set of Jobs.
     */
    public Jobs subset(String[] jobIds) {
        final List<Job> subsetJobs = new ArrayList<Job>();
        for (String id : jobIds) {
            for (String candidate : jobMap.keySet()) {
                if (candidate.contains(id)) {
                    subsetJobs.add(jobMap.get(candidate));
                }
            }
        }
        return new Jobs(subsetJobs);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Jobs");
        sb.append("{jobMap=").append(jobMap);
        sb.append(", stepMap=").append(stepMap);
        sb.append('}');
        return sb.toString();
    }


}
