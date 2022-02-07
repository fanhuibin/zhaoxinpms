package com.zhaoxinms.workflow.engine.designer.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.activiti.bpmn.model.ExclusiveGateway;

import com.zhaoxinms.workflow.engine.model.designer.ConditionNode;

public class ConditionNodeCreator {
    
    public static final String GATEWAY_SUFFIX = "-gateway";
    
    public static List<ExclusiveGateway> createGateWay(List<ConditionNode> conditions) {
        List<ExclusiveGateway> gateways = new ArrayList<ExclusiveGateway>();
        
        Map<String, List<ConditionNode>> prev = conditions.stream().collect(Collectors.groupingBy(ConditionNode::getPrevId));
        for (String s: prev.keySet()) {
            ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
            exclusiveGateway.setId(s+ConditionNodeCreator.GATEWAY_SUFFIX);
            gateways.add(exclusiveGateway);
        }
        
        return gateways;
    }
}
