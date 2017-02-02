package net.floodlightcontroller.proactiveloadbalancer;

import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.IPv4Address;

import net.floodlightcontroller.core.module.IFloodlightService;

public interface IProactiveLoadBalancerService extends IFloodlightService {
    // Strategy
    void setStrategy(Strategy strategy);

    // Topology
    void setTopology(Topology topology);

	void addSwitch(DatapathId dpid);
	DatapathId deleteSwitch(DatapathId dpid);
	
    void addAddressPool(IPv4Address vip, AddressPool addressPool);
    AddressPool deleteAddressPool(IPv4Address vip);
}
