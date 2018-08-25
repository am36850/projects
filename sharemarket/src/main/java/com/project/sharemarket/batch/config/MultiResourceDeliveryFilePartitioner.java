package com.project.sharemarket.batch.config;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class MultiResourceDeliveryFilePartitioner implements Partitioner {

    private static final String DEFAULT_KEY_NAME = "fileName";
    private static final String PARTITION_KEY = "partition";
    private Resource[] resources;

    public MultiResourceDeliveryFilePartitioner(Resource[] inputResources) {
        this.resources = inputResources;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String,ExecutionContext> executionContextMap = new HashMap<>(gridSize);
        int i = 0;
        for (Resource resource : resources) {
            ExecutionContext context = new ExecutionContext();
            Assert.state(resource.exists(), "Resource does not exist: "
                    + resource);
            context.putString(DEFAULT_KEY_NAME, resource.getFilename());
            executionContextMap.put(PARTITION_KEY + i, context);
            i++;
        }
        return executionContextMap;
    }
}
