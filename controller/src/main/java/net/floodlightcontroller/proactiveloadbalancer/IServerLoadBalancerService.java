package net.floodlightcontroller.proactiveloadbalancer;

import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.proactiveloadbalancer.old.ServerLoadBalancer.Stats;
import net.floodlightcontroller.proactiveloadbalancer.old.network.LoadBalanceTarget;
import net.floodlightcontroller.proactiveloadbalancer.old.network.Server;
import net.floodlightcontroller.proactiveloadbalancer.old.network.Switch;

import java.util.List;

import org.projectfloodlight.openflow.types.IPv4Address;

public interface IServerLoadBalancerService extends IFloodlightService {
    void setMaxPrefixLength();
    void setMaxPrefixLength(int maxPrefixLength);

    // ---- OLD SHIT ----
    // Servers
    void addServer(Server server);
    List<Server> getServers();
    void removeServer(Server server);

    // Switches
    void addSwitch(Switch sw);
    List<Switch> getSwitches();
    void removeSwitch(Switch sw);

    // Targets
    void addTarget(Switch sw, String portName, LoadBalanceTarget target);

    // Transitions
    void requestTransition(boolean fromCurrent);

    // Stats methods
    Stats getStats(Switch sw);
    
    // ---- NEW SHIT ----
    void addMapping(IPv4Address vip, AddressPool mapping);
    boolean hasMappingFor(IPv4Address vip);
    AddressPool deleteMappingFor(IPv4Address vip);
}
