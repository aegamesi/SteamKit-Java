package uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr;

import uk.co.thomasc.steamkit.types.JobID;

/**
 * This callback serves as the base class for all job based callbacks. This
 * allows you to retrieve results based on the Job ID without knowing the inner
 * callback type.
 */
public abstract class BaseJobCallback extends CallbackMsg {
    /**
     * Gets the Job ID of this callback. For client based jobs, this will match
     * the Job ID of a function call. For server based jobs, this is provided to
     * respond to the correct job.
     */
    protected JobID jobId;
    /**
     * Gets the type of the callback.
     */
    protected Class<?> callbackType;

    public BaseJobCallback(long jobId) {
        this.jobId = new JobID(jobId);
    }

    /**
     * Gets the Job ID of this callback. For client based jobs, this will match
     * the Job ID of a function call. For server based jobs, this is provided to
     * respond to the correct job.
     */
    public JobID getJobId() {
        return this.jobId;
    }

    /**
     * Gets the type of the callback.
     */
    public Class<?> getCallbackType() {
        return this.callbackType;
    }
}
