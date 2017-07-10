package com.defsat.phystrix.client.infoboard;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.defsat.metric.AgentFactory;
import com.defsat.metric.IMetricAgent;
import com.defsat.metric.config.MetricConfig;
import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.util.PlatformSpecific;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhystrixMetricsPoller {

    private final ScheduledExecutorService executor;
    private final int delay;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private volatile ScheduledFuture<?> scheduledTask = null;
    private IMetricAgent agent;

    
    public PhystrixMetricsPoller(int delay, MetricConfig metricConfig) {

        ThreadFactory threadFactory = null;
        if (!PlatformSpecific.isAppEngine()) {
            threadFactory = new MetricsPollerThreadFactory();
        } else {
            threadFactory = PlatformSpecific.getAppEngineThreadFactory();
        }

        executor = new ScheduledThreadPoolExecutor(1, threadFactory);
        this.delay = delay;
        this.agent = AgentFactory.getNewAgent(metricConfig);
    }

    /**
     * Start polling.
     */
    public synchronized void start() {
        // use compareAndSet to make sure it starts only once and when not running
        if (running.compareAndSet(false, true)) {
            log.info("Starting PhystrixMetricsPoller....");
            try {
                scheduledTask = executor.scheduleWithFixedDelay(new MetricsPoller(), 0, delay, TimeUnit.SECONDS);
            } catch (Throwable ex) {
                log.error("Exception while creating the PhystrixMetricsPoller task");
                ex.printStackTrace();
                running.set(false);
            }
        }
    }

    /**
     * Pause (stop) polling. Polling can be started again with <code>start</code> as long as <code>shutdown</code> is not called.
     */
    public synchronized void pause() {
        // use compareAndSet to make sure it stops only once and when running
        if (running.compareAndSet(true, false)) {
            log.info("Stopping the PhystrixMetricsPoller...");
            scheduledTask.cancel(true);
        }
    }

    /**
     * Stops polling and shuts down the ExecutorService.
     * <p>
     * This instance can no longer be used after calling shutdown.
     */
    public synchronized void shutdown() {
        pause();
        executor.shutdown();
    }

    public boolean isRunning() {
        return running.get();
    }

    /**
     * Used to protect against leaking ExecutorServices and threads if this class is abandoned for GC without shutting down.
     */
    @SuppressWarnings("unused")
    private final Object finalizerGuardian = new Object() {
        protected void finalize() throws Throwable {
            if (!executor.isShutdown()) {
                log.warn("{} was not shutdown. Caught in Finalize Guardian and shutting down.", PhystrixMetricsPoller.class.getSimpleName());
                try {
                    shutdown();
                } catch (Exception e) {
                    log.error("Failed to shutdown {}", PhystrixMetricsPoller.class.getSimpleName(), e);
                }
            }
        };
    };


    private class MetricsPoller implements Runnable {

		@Override
        public void run() {
            try {
            	long currentTime = System.currentTimeMillis();
                for (HystrixCommandMetrics commandMetrics : HystrixCommandMetrics.getInstances()) {
                    sendCommandMetrics(commandMetrics, currentTime);
                }
                
                for (HystrixThreadPoolMetrics threadPoolMetrics : HystrixThreadPoolMetrics.getInstances()) {
                    if (hasExecutedCommandsOnThread(threadPoolMetrics)) {
                        sendThreadPoolMetrics(threadPoolMetrics, currentTime);
                    }
                }
                
            } catch (Exception e) {
                log.warn("Failed to output metrics", e.getMessage());
                return;
            }
        }

        
        private void sendCommandMetrics(final HystrixCommandMetrics commandMetrics, long currentTime) throws Exception {
        	
        	Map<String,String> tags = Maps.newConcurrentMap();
            HystrixCommandKey key = commandMetrics.getCommandKey();
            //tags
            tags.put("commandKey", key.name());
            tags.put("group", commandMetrics.getCommandGroup().name());
            tags.put("ip", InetAddress.getLocalHost().getHostAddress().toString());
           
            
            //circuitBreaker
            String circuitMeasureName = "CircuitBreaker";
            Map<String,Object> circuitBreakerfields = Maps.newConcurrentMap();
            HystrixCircuitBreaker circuitBreaker = HystrixCircuitBreaker.Factory.getInstance(key);
            
            int status = 0;
            if (circuitBreaker != null) {
            	if(circuitBreaker.isOpen()){
            		status =1;
            	}
            }
            circuitBreakerfields.put("isBreakerOpen", status);
            agent.log(circuitMeasureName, tags, circuitBreakerfields,currentTime);
            
            
            
            //healthCounts
//          String healthMeasureName = "healthCounts";
//          Map<String,Object> healthFields = Maps.newConcurrentMap();
//            
//          HealthCounts healthCounts = commandMetrics.getHealthCounts();
//          healthFields.put("errorPercentage", healthCounts.getErrorPercentage());
//          healthFields.put("errorCount", healthCounts.getErrorCount());
//          healthFields.put("requestCount", healthCounts.getTotalRequests());
//          agent.log(healthMeasureName, tags, healthFields,currentTime);
            

            // rolling counters
            String rollingMeasureName = "RollingCounts";
            Map<String,Object> rollingFields = Maps.newConcurrentMap();
            
//			rollingFields.put("BadRequests", commandMetrics.getRollingCount(HystrixEventType.BAD_REQUEST));
//			rollingFields.put("Emit", commandMetrics.getRollingCount(HystrixEventType.EMIT));
//			rollingFields.put("ExceptionsThrown", commandMetrics.getRollingCount(HystrixEventType.EXCEPTION_THROWN));
//			rollingFields.put("FallbackEmit", commandMetrics.getRollingCount(HystrixEventType.FALLBACK_EMIT));
//			rollingFields.put("FallbackFailure", commandMetrics.getRollingCount(HystrixEventType.FALLBACK_FAILURE));
//			rollingFields.put("FallbackMissing", commandMetrics.getRollingCount(HystrixEventType.FALLBACK_MISSING));
//			rollingFields.put("FallbackRejection", commandMetrics.getRollingCount(HystrixEventType.FALLBACK_REJECTION));
//			rollingFields.put("FallbackSuccess", commandMetrics.getRollingCount(HystrixEventType.FALLBACK_SUCCESS));
//			rollingFields.put("currentConcurrentExecutionCount", commandMetrics.getCurrentConcurrentExecutionCount());
//			rollingFields.put("rollingMaxConcurrentExecutionCount", commandMetrics.getRollingMaxConcurrentExecutions());
            rollingFields.put("failure",  commandMetrics.getRollingCount(HystrixEventType.FAILURE));
            rollingFields.put("semaphoreRejected", commandMetrics.getRollingCount(HystrixEventType.SEMAPHORE_REJECTED));
			rollingFields.put("shortCircuited", commandMetrics.getRollingCount(HystrixEventType.SHORT_CIRCUITED));
			rollingFields.put("success", commandMetrics.getRollingCount(HystrixEventType.SUCCESS));
			rollingFields.put("threadPoolRejected", commandMetrics.getRollingCount(HystrixEventType.THREAD_POOL_REJECTED));
			rollingFields.put("timeout", commandMetrics.getRollingCount(HystrixEventType.TIMEOUT));
			agent.log(rollingMeasureName, tags, rollingFields,currentTime);
			
			
            // latency percentiles
//			String latencyMeasureName = "Latency";
//	        Map<String,Object> latencyFields = Maps.newConcurrentMap();
			
//	        latencyFields.put("latencyExecute_mean", commandMetrics.getExecutionTimeMean());
//	        latencyFields.put("latencyExecute_90", commandMetrics.getExecutionTimePercentile(90));
//	        latencyFields.put("latencyExecute_95", commandMetrics.getExecutionTimePercentile(95));
//	        latencyFields.put("latencyExecute_99", commandMetrics.getExecutionTimePercentile(99));
//	        latencyFields.put("latencyExecute_99.5", commandMetrics.getExecutionTimePercentile(99.5));
//
//	        latencyFields.put("latencyTotal_90", commandMetrics.getTotalTimePercentile(90));
//	        latencyFields.put("latencyTotal_95", commandMetrics.getTotalTimePercentile(95));
//	        latencyFields.put("latencyTotal_99", commandMetrics.getTotalTimePercentile(99));
//	        latencyFields.put("latencyTotal_99.5", commandMetrics.getTotalTimePercentile(99.5));
//	        latencyFields.put("latencyTotal_mean", commandMetrics.getTotalTimeMean());
//	        agent.log(latencyMeasureName, tags, latencyFields,currentTime);
	        
	        
            // property values
//	        String propertyMeasureName = "Config";
//	        Map<String,Object> propertyFields = Maps.newConcurrentMap();
//            HystrixCommandProperties commandProperties = commandMetrics.getProperties();
//            
//            
//            propertyFields.put("requestThreshold", commandProperties.circuitBreakerRequestVolumeThreshold().get());
//            propertyFields.put("circuitBreakerTime", commandProperties.circuitBreakerSleepWindowInMilliseconds().get());
//            propertyFields.put("errorThreshold", commandProperties.circuitBreakerErrorThresholdPercentage().get());
//            int isolation = commandProperties.executionIsolationStrategy().get().name().equals("THREAD") ? 1 : 0;
//            propertyFields.put("isolationStgy", isolation);
//            propertyFields.put("timeout", commandProperties.executionTimeoutInMilliseconds().get());
//            propertyFields.put("maxRequest", commandProperties.executionIsolationSemaphoreMaxConcurrentRequests().get());
//          propertyFields.put("semaphoreFallbackMaxRequest", commandProperties.fallbackIsolationSemaphoreMaxConcurrentRequests().get());
//          propertyFields.put("threadPool", commandMetrics.getThreadPoolKey().name());
            
//            agent.log(propertyMeasureName, tags, propertyFields,currentTime);       
        }

        private boolean hasExecutedCommandsOnThread(HystrixThreadPoolMetrics threadPoolMetrics) {
            return threadPoolMetrics.getCurrentCompletedTaskCount().intValue() > 0;
        }

        private void sendThreadPoolMetrics(final HystrixThreadPoolMetrics threadPoolMetrics,long currentTime) throws IOException {
            HystrixThreadPoolKey key = threadPoolMetrics.getThreadPoolKey();
            int endIndex = key.name().indexOf("-");
            String threadPoolName = endIndex > 0 ? key.name().substring(0,endIndex) +"Pool" : key.name();
            
            //threadPool
            String threadPoolMeasureName = "ThreadPool";
        	Map<String,String> tags = Maps.newConcurrentMap();
        	Map<String,Object> fields = Maps.newConcurrentMap();
            
        	tags.put("threadPoolKey", key.name());
        	tags.put("poolName", threadPoolName);
        	tags.put("ip", InetAddress.getLocalHost().getHostAddress().toString());
            
//          fields.put("activeCount", threadPoolMetrics.getCurrentActiveCount().intValue());
//          fields.put("completedTaskCount", threadPoolMetrics.getCurrentCompletedTaskCount().longValue());
//          fields.put("largestPoolSize", threadPoolMetrics.getCurrentLargestPoolSize().intValue());
//          fields.put("maximumPoolSize", threadPoolMetrics.getCurrentMaximumPoolSize().intValue());
//          fields.put("queueSize", threadPoolMetrics.getCurrentQueueSize().intValue());
//          fields.put("taskCount", threadPoolMetrics.getCurrentTaskCount().longValue());
            fields.put("maxActive", threadPoolMetrics.getRollingMaxActiveThreads());
            fields.put("corePoolSize", threadPoolMetrics.getCurrentCorePoolSize().intValue());
            fields.put("poolSize", threadPoolMetrics.getCurrentPoolSize().intValue());
            
            agent.log(threadPoolMeasureName, tags, fields,currentTime);
        }

    }

    private class MetricsPollerThreadFactory implements ThreadFactory {
        private static final String MetricsThreadName = "HystrixMetricPoller";

        private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();

        public Thread newThread(Runnable r) {
            Thread thread = defaultFactory.newThread(r);
            thread.setName(MetricsThreadName);
            thread.setDaemon(true);
            return thread;
        }
    }
    
    /*{
    "type": "HystrixCommand",
    "name": "getContact",
    "group": "contact",
    "currentTime": 1480493338204,
    "isCircuitBreakerOpen": false,
    "errorPercentage": 0,
    "errorCount": 0,
    "requestCount": 0,
    "rollingCountBadRequests": 0,
    "rollingCountCollapsedRequests": 0,
    "rollingCountEmit": 0,
    "rollingCountExceptionsThrown": 0,
    "rollingCountFailure": 0,
    "rollingCountFallbackEmit": 0,
    "rollingCountFallbackFailure": 0,
    "rollingCountFallbackMissing": 0,
    "rollingCountFallbackRejection": 0,
    "rollingCountFallbackSuccess": 0,
    "rollingCountResponsesFromCache": 0,
    "rollingCountSemaphoreRejected": 0,
    "rollingCountShortCircuited": 0,
    "rollingCountSuccess": 0,
    "rollingCountThreadPoolRejected": 0,
    "rollingCountTimeout": 0,
    "currentConcurrentExecutionCount": 0,
    "rollingMaxConcurrentExecutionCount": 0,
    "latencyExecute_mean": 0,
    "latencyExecute": {
        "0": 0,
        "25": 0,
        "50": 0,
        "75": 0,
        "90": 0,
        "95": 0,
        "99": 0,
        "100": 0,
        "99.5": 0
    },
    "latencyTotal_mean": 0,
    "latencyTotal": {
        "0": 0,
        "25": 0,
        "50": 0,
        "75": 0,
        "90": 0,
        "95": 0,
        "99": 0,
        "100": 0,
        "99.5": 0
    },
    "propertyValue_circuitBreakerRequestVolumeThreshold": 20,
    "propertyValue_circuitBreakerSleepWindowInMilliseconds": 10000,
    "propertyValue_circuitBreakerErrorThresholdPercentage": 30,
    "propertyValue_circuitBreakerForceOpen": false,
    "propertyValue_circuitBreakerForceClosed": false,
    "propertyValue_circuitBreakerEnabled": true,
    "propertyValue_executionIsolationStrategy": "SEMAPHORE",
    "propertyValue_executionIsolationThreadTimeoutInMilliseconds": 1000,
    "propertyValue_executionTimeoutInMilliseconds": 1000,
    "propertyValue_executionIsolationThreadInterruptOnTimeout": true,
    "propertyValue_executionIsolationThreadPoolKeyOverride": null,
    "propertyValue_executionIsolationSemaphoreMaxConcurrentRequests": 1,
    "propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests": 800,
    "propertyValue_metricsRollingStatisticalWindowInMilliseconds": 10000,
    "propertyValue_requestCacheEnabled": true,
    "propertyValue_requestLogEnabled": true,
    "reportingHosts": 1,
    "threadPool": "contact"
    }*/
    
}

